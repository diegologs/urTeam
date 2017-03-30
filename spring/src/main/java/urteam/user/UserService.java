package urteam.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User getUser(String nickname) {
		return userRepository.findByNickname(nickname);
	}

	public User createNewUser(User user, String password) {
		Date date = new Date();
		SimpleDateFormat userIdFormater = new SimpleDateFormat("mmddyyyy-hhMMss");
		String generatedId = userIdFormater.format(date);
		user.setGeneratedId(generatedId);
		user.setPasswordHash(password);
		ArrayList<String> roles = new ArrayList<>(Arrays.asList("ROLE_USER"));
		user.setRoles(roles);
		userRepository.save(user);
		return user;
	}

	public User updateUser(String nickname, String username, String surname, String email, String bio, String city,
			String country, MultipartFile file) {

		User userToEdit = userRepository.findByNickname(nickname);

		if (userToEdit != null) {
			userToEdit.setUserName(username);
			userToEdit.setSurname(surname);
			userToEdit.setEmail(email);
			userToEdit.setBio(bio);
			userToEdit.setCity(city);
			userToEdit.setCountry(country);
			// if (file != null) {
			// String filename = "avatar-" + editedUser.getGeneratedId();
			// if (urteamController.uploadImageFile(model, file, filename,
			// ConstantsUrTeam.USER_AVATAR,
			// editedUser.getGeneratedId())) {
			// editedUser.setAvatar(filename);
			// }
			// }

			// }

			userRepository.save(userToEdit);
			return userToEdit;
		} else {
			return null;
		}
	}
}
