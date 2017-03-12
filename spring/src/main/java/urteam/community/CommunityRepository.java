package urteam.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CommunityRepository extends JpaRepository<Community, Long>{
	
	List<Community> findByName(String name);
	
	List<Community> findBySport(String sport);
	


}
