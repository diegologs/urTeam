package urteam.user;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urteamController;
import urteam.community.Community;
import urteam.event.Event;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private urteamController urteamController;

	@RequestMapping("/userprofile/{id}")
	public String userProfile(Model model, @PathVariable Long id) {
		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		List<User> friends = user.getFollowing();
		List<Community> communities = user.getCommunityList();
		model.addAttribute("following", friends);
		model.addAttribute("communities", communities);
		model.addAttribute("members", communities.size());
		model.addAttribute("numberOfFollowers", user.getNumberOfFollower());
		return "user";
	}

	@RequestMapping("/userprofile/{id}/edit")
	public String userProfileEdit(Model model, @PathVariable long id, @RequestParam String username,
			@RequestParam String surname, @RequestParam String email, @RequestParam String bio,
			@RequestParam String city, @RequestParam String country, @RequestParam("file") MultipartFile file)
			throws ParseException {
		User editedUser = userRepository.findOne(id);

		editedUser.setUserName(username);
		editedUser.setSurname(surname);
		editedUser.setEmail(email);
		editedUser.setBio(bio);
		editedUser.setCity(city);
		editedUser.setCountry(country);

		if (file != null){
			String filename = editedUser.getAvatar();
			if (urteamController.uploadImageFile(model, file, filename, ConstantsUrTeam.USER_AVATAR,
					editedUser.getGeneratedId())) {
				editedUser.setAvatar(filename);
			}
		}
		userRepository.save(editedUser);

		return "redirect:/userprofile/{id}";
	}

}
