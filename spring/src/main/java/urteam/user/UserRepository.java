package urteam.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByNickname(String nickname);
	List<User> findByUsernameOrSurnameIgnoreCase(String name, String surname);
	List<User> findByUsernameIgnoreCase(String name);
	List<User> findByNicknameIgnoreCase(String nickname);
	

}