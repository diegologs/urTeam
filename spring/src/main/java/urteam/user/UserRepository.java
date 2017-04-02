package urteam.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByNickname(String nickname);
	List<User> findByUsernameOrSurnameIgnoreCase(String name, String surname);
	List<User> findByUsernameIgnoreCase(String name);
	List<User> findByNicknameIgnoreCase(String nickname);
	
	@Query("Select c from User c where c.nickname like %?1%")
	List<User> findByNicknameContaining(String cirteria);
	

}