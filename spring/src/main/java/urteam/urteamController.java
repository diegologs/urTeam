package urteam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import urteam.community.Community;
import urteam.community.CommunityRepository;
import urteam.event.*;
import urteam.sport.SportRepository;
import urteam.user.*;

@Controller
public class urteamController {

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SportRepository sportRepo;

	@Autowired
	private CommunityRepository communityRepo;

	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request) {
		// Añadir elementos basicos
		List<Event> eventos = eventRepo.findFirst3BySport("Mountain Bike");
		model.addAttribute("first_events", eventos);

		eventos = eventRepo.findFirst3BySport("Running");
		model.addAttribute("second_events", eventos);

		eventos = eventRepo.findFirst3BySport("Roller");
		model.addAttribute("third_events", eventos);

		// Comprobar si hay un usuario logueado y añadirlo
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			// Comprobar si es admin
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "index";
		} else {
			return "index";
		}
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {

				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
				ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

				container.addErrorPages(error401Page, error404Page, error403Page, error500Page);
			}
		};
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/loginerror")
	public String loginerror(Model model) {
		model.addAttribute("loginerror", true);
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

	@RequestMapping("/search")
	public String userSearch(Model model, String toSearch) {
		List<User> foundUsers = null;
		List<Community> foundCommunities = null;
		List<Event> foundEvents = null;

		if (toSearch != null) {
			String[] userSplitSearch = toSearch.split(" ");
			if (userSplitSearch.length == 1) {
				foundUsers = userRepo.findByNicknameIgnoreCase(toSearch);
				if (foundUsers.size() == 0) {
					foundUsers = userRepo.findByUsernameIgnoreCase(userSplitSearch[0]);
				}
				model.addAttribute("foundUsers", foundUsers);
			} else if (userSplitSearch.length > 1) {
				foundUsers = userRepo.findByUsernameOrSurnameIgnoreCase(userSplitSearch[0], userSplitSearch[1]);
				model.addAttribute("foundUsers", foundUsers);
			}
		}

		/* search communities */
		if (toSearch != null) {
			foundCommunities = communityRepo.findByNameIgnoreCase(toSearch);
			model.addAttribute("foundCommunities",foundCommunities);
		}
		/* search events */
		if (toSearch != null) {
			foundEvents = eventRepo.findByNameIgnoreCase(toSearch);
			model.addAttribute("foundEvents",foundEvents);
		}
		return "search";
	}

}
