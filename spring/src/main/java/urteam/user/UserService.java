package urteam.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urTeamService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private urTeamService imageService;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User getUser(String nickname) {
		return userRepository.findByNickname(nickname);
	}

	public User findOne(long id) {
		return userRepository.findOne(id);
	}

	public User createNewUser(User user) {
		if (userRepository.findByNickname(user.getNickname()) == null) {
			Date date = new Date();
			SimpleDateFormat userIdFormater = new SimpleDateFormat("mmddyyyy-hhMMss");
			String generatedId = userIdFormater.format(date);
			user.setGeneratedId(generatedId);
			ArrayList<String> roles = new ArrayList<>(Arrays.asList("ROLE_USER"));
			user.setRoles(roles);
			userRepository.save(user);
			return user;
		} else {
			return null;
		}
	}

	public User updateUserInfo(String nickname, User user) {
		User userToEdit = userRepository.findByNickname(nickname);
		if (userToEdit != null) {
			user.setId(userToEdit.getId());
			userRepository.save(user);
			return user;
		} else {
			return null;
		}
	}

	public User updateUserAvatar(String nickname, MultipartFile file) {

		User userToEdit = userRepository.findByNickname(nickname);
		if (userToEdit != null) {
			try {
				if (imageService.uploadImageFile(file, "avatar-" + userToEdit.getGeneratedId(),
						ConstantsUrTeam.USER_AVATAR, userToEdit.getGeneratedId())) {
					userToEdit.setAvatar("avatar-" + userToEdit.getGeneratedId());
					userRepository.save(userToEdit);
					return userToEdit;
				} else {
					return null;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	public byte[] getUserAvatar(String nickname) throws FileNotFoundException, IOException{
		User user = userRepository.findByNickname(nickname);
		if(user != null){
			 byte[] file = imageService.getFile(ConstantsUrTeam.USER_AVATAR, user.getGeneratedId(),
					user.getAvatar()); 
			if(file != null){
				return file;
			}
		} else {
			return null;
		}
		return null;
	}

	public List<User> getFriends(String nickname) {
		List<User> followerList = userRepository.findByNickname(nickname).getFollowing();
		if (followerList != null) {
			return followerList;
		} else {
			return null;
		}
	}
	public List<User> getUsersByCriteria(String nickname) {
		List<User> followerList = userRepository.findByNicknameContaining(nickname);
		if (followerList != null) {
			return followerList;
		} else {
			return null;
		}
	}

	public List<User> getFollowers(String nickname) {
		List<User> followerList = userRepository.findByNickname(nickname).getFollowers();
		if (followerList != null) {
			return followerList;
		} else {
			return null;
		}
	}

	public List<User> followUnfollow(User logged, String nicknameToFollow) {

		User user = userRepository.findByNickname(nicknameToFollow);

		if (logged.getFollowing().contains(user)) {
			logged.getFollowing().remove(user);
		} else {
			logged.getFollowing().add(user);
		}

		userRepository.save(logged);
		return user.getFollowers();
	}
}
