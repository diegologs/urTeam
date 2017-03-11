package urteam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

	@RequestMapping(value = "/image/upload", method = RequestMethod.POST)
	public String uploadImageFile(Model model, @RequestParam("file") MultipartFile file, String action) {

		String fileName = "test.jpeg";

		if (!file.isEmpty()) {
			try {
				File filesFolder = new File("imgs");
				if (!filesFolder.exists()) {
					filesFolder.mkdirs();
				}
				File uploadedFile = new File(filesFolder.getAbsolutePath(), fileName);
				file.transferTo(uploadedFile);

				return "/events";

			} catch (Exception e) {
				model.addAttribute("fileName", fileName);
				model.addAttribute("error", e.getClass().getName() + ":" + e.getMessage());
				return "/events";

			}
		} else {
			model.addAttribute("error", "The file is empty");
			return "/events";
		}

	}

	@RequestMapping("/image/{fileName}")
	public void handleFileDownload(@PathVariable String fileName, HttpServletResponse res)
			throws FileNotFoundException, IOException {

		File file = new File("imgs", fileName + ".jpg");

		if (file.exists()) {
			res.setContentType("image/jpeg");
			res.setContentLength(new Long(file.length()).intValue());
			FileCopyUtils.copy(new FileInputStream(file), res.getOutputStream());
		} else {
			res.sendError(404, "File" + fileName + "(" + file.getAbsolutePath() + ") does not exist");
		}
	}
}
