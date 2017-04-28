package urteam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import urteam.community.Community;
import urteam.event.Event;
import urteam.user.User;

@RestController
@RequestMapping("/api/searchbox")
public class UrTeamRestController {
	
	public interface MinimalSearchUser extends User.MinimalUser {
	}
	
	public interface MinimalSearchEvent extends User.MinimalUser , Event.MinimalEvent{	
	}
	
	public interface MinimalSearchCommunity extends User.MinimalUser, Community.MinimalCommunity{
	}
	
	public interface MinimalSearchAll extends User.MinimalUser, Event.BasicEvent,Community.MinimalCommunity{
	}

	@Autowired
	urTeamService urTeamService;
	
	@JsonView(MinimalSearchUser.class)
	@RequestMapping(value = "/users/{criteria}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsersByCriteria(@PathVariable String criteria) {
		List<User> foundUsers = urTeamService.getUsersByCriteria(criteria);
		if (foundUsers != null) {
			return new ResponseEntity<>(foundUsers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(MinimalSearchEvent.class)
	@RequestMapping(value = "/events/{criteria}", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getEventsByCriteria(@PathVariable String criteria) {
		List<Event> foundEvents = urTeamService.getEventsByCriteria(criteria);
		if (foundEvents != null) {
			return new ResponseEntity<>(foundEvents, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@JsonView(MinimalSearchCommunity.class)
	@RequestMapping(value = "/groups/{criteria}", method = RequestMethod.GET)
	public ResponseEntity<List<Community>> getCommunitiesByCriteria(@PathVariable String criteria) {
		List<Community> foundCommunites = urTeamService.getCommunitiesByCriteria(criteria);
		if (foundCommunites != null) {
			return new ResponseEntity<>(foundCommunites, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/all/{criteria}", method = RequestMethod.GET)
	public ResponseEntity<Search> searchAll(@PathVariable String criteria) {
		Search foundElements = urTeamService.searchAll(criteria);
		if (foundElements != null) {
			return new ResponseEntity<>(foundElements, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
