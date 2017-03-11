package urteam.event;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import urteam.community.Community;


public interface EventRepository extends JpaRepository<Event, Long>{
	
	List<Event> findFirst3BySport(String sport);
	List<Event> findBySport(String sport);
	List<Event> findByName(String name);
	
}

