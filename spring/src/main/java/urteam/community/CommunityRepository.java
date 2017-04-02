package urteam.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;





public interface CommunityRepository extends JpaRepository<Community, Long>{
	
	List<Community> findByName(String name);
	List<Community> findBySport(String sport);
	List<Community> findByNameIgnoreCase(String name);
	
	@Query("Select c from Community c where c.name like %?1%")
	List<Community> findByNameContaining(String cirteria);
}
