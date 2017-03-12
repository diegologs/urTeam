package urteam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import urteam.event.Event;
import urteam.event.EventRepository;

@Controller
public class urteamController {

	@Autowired
	private EventRepository eventRepo;

	@RequestMapping("/")
	public String index(Model model) {

		List<Event> eventos = eventRepo.findFirst3BySport("Mountain Bike");
		model.addAttribute("first_events", eventos);

		eventos = eventRepo.findFirst3BySport("Running");
		model.addAttribute("second_events", eventos);

		eventos = eventRepo.findFirst3BySport("Roller");
		model.addAttribute("third_events", eventos);

		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	public Boolean uploadImageFile(Model model, MultipartFile file, String name, String type, String generatedId) {

		String folderPath = "imgs";

		if (!file.isEmpty()) {
			String fileName = name + ".jpeg";
			try {
				switch (type) {
				case ConstantsUrTeam.USER_AVATAR:
					folderPath = ConstantsUrTeam.USERS_AVATAR_FOLDER;
					break;
				case ConstantsUrTeam.EVENT_AVATAR:
					folderPath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId;
					break;
				case ConstantsUrTeam.EVENT_IMGS:
					folderPath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId + "/gallery";
					break;
				case ConstantsUrTeam.COMMUNITY_AVATAR:
					folderPath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId;
					break;
				case ConstantsUrTeam.COMMUNITY_IMGS:
					folderPath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId + "/gallery";
					break;
				default:
					break;
				}
				File filesFolder = new File(folderPath);
				if (!filesFolder.exists()) {
					filesFolder.mkdirs();
				}
				File uploadedFile = new File(filesFolder.getAbsolutePath(), fileName);
				file.transferTo(uploadedFile);

				return true;

			} catch (Exception e) {
				model.addAttribute("fileName", fileName);
				model.addAttribute("error", e.getClass().getName() + ":" + e.getMessage());
				return false;

			}
		} else {
			model.addAttribute("error", "The file is empty");
			return false;
		}

	}

	@RequestMapping("/image/{type}/{generatedId}/{fileName}")
	public void handleFileDownload(@PathVariable String type, @PathVariable String generatedId,
			@PathVariable String fileName, HttpServletResponse res) throws FileNotFoundException, IOException {

		String filePath = null;

		switch (type) {
		case ConstantsUrTeam.USER_AVATAR:
			filePath = ConstantsUrTeam.USERS_AVATAR_FOLDER + "/" + fileName + ".jpeg";
			break;
		case ConstantsUrTeam.EVENT_AVATAR:
			filePath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId + "/" + fileName + ".jpeg";
			break;
		case ConstantsUrTeam.EVENT_IMGS:
			filePath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId + "/gallery/" + fileName + ".jpeg";
			break;
		case ConstantsUrTeam.COMMUNITY_AVATAR:
			filePath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId + "/" + fileName + ".jpeg";
			break;
		case ConstantsUrTeam.COMMUNITY_IMGS:
			filePath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId + "/gallery/" + fileName + ".jpeg";
			break;
		default:
			break;
		}
		File file = new File(filePath);

		if (file.exists()) {
			res.setContentType("image/jpeg");
			res.setContentLength(new Long(file.length()).intValue());
			FileCopyUtils.copy(new FileInputStream(file), res.getOutputStream());
		} else {
			res.sendError(404, "File" + fileName + "(" + file.getAbsolutePath() + ") does not exist");
		}
	}
}
