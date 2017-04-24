package urteam.community;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import urteam.event.Event;
import urteam.news.News;
import urteam.news.News.BasicNews;
import urteam.user.User;


@RestController
@RequestMapping("/api/groups")
public class CommunityRestController {


	 
	interface CompleteNews extends News.BasicNews{} 
	interface CompleteCommunity extends Community.MinimalCommunity,Community.BasicCommunity, Community.CommunityUsers, Community.CommunityNews, User.MinimalUser, BasicNews {
	}
	// 
	

	@Autowired
	private CommunityService service;

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Page<Community>> getGroups(Pageable pageable) {
		Page<Community> communities = service.findAll(pageable);
		if (communities != null) {
			return new ResponseEntity<>(communities,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Community> getGroup(@PathVariable long id) {

		Community community = service.findOne(id);

		if (community != null) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);
		} else {
			return new ResponseEntity<Community>(community, HttpStatus.NOT_FOUND);
		}

	}

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}/info", method = RequestMethod.PUT)
	public ResponseEntity<Community> addInfo(@PathVariable long id, @RequestBody String info) {

		Community community = service.findOne(id);

		if (service.editInfo(community, info) && community != null) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);

		} else {
			return new ResponseEntity<Community>(HttpStatus.UNAUTHORIZED);
		}

	}

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Community> edit(@PathVariable long id, @RequestBody Community communityEdited) {

		Community community = service.findOne(id);

		if (service.edit(id, communityEdited) && community != null) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);

		} else {
			return new ResponseEntity<Community>(HttpStatus.UNAUTHORIZED);
		}

	}

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}/image", method = RequestMethod.PUT)
	public ResponseEntity<Community> addImage(@PathVariable long id, @RequestBody MultipartFile file)
			throws ParseException {

		Community community = service.findOne(id);

		service.addImage(community, file);

		if (community != null) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);
		} else {
			return new ResponseEntity<Community>(HttpStatus.UNAUTHORIZED);
		}
	}

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}/avatar", method = RequestMethod.PUT)
	public ResponseEntity<Community> setImage(@PathVariable long id, @RequestBody MultipartFile file)
			throws ParseException {

		Community community = service.findOne(id);

		service.setImage(community, file);

		if (community != null) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);
		} else {
			return new ResponseEntity<Community>(HttpStatus.UNAUTHORIZED);
		}
	}

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Community> add(@RequestBody Community community, MultipartFile file) {

	
		if (service.add(community, file) == true) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);
		} else {
			return new ResponseEntity<Community>(HttpStatus.BAD_REQUEST);
		}

	}

	@JsonView(CompleteNews.class)
	@RequestMapping(value = "/{id}/news", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<News> addNews(@PathVariable long id, @RequestBody News news) {

		Community community = service.findOne(id);
		

		if (service.addNews(community, news) == true && community != null) {
			return new ResponseEntity<>(news, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@JsonView(CompleteNews.class)
	@RequestMapping(value = "/{id}/news", method = RequestMethod.GET)
	public List<News> getNews(@PathVariable long id) {

		Community community = service.findOne(id);

		return service.findNews(community);

	}

	
	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}/followers", method = RequestMethod.GET)
	public ResponseEntity<Community> getfollowers(@PathVariable long id) {

		Community community = service.findOne(id);

	

		if (community != null) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);
		} else {
			return new ResponseEntity<Community>(community, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}/followers", method = RequestMethod.PUT)
	public ResponseEntity<Community> follow(@PathVariable long id) {

		Community community = service.findOne(id);

		service.follow(community);

		if (community != null) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);
		} else {
			return new ResponseEntity<Community>(community, HttpStatus.NOT_FOUND);
		}
	}
	

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}/followers", method = RequestMethod.DELETE)
	public ResponseEntity<Community> unfollow(@PathVariable long id) {

		Community community = service.findOne(id);

		service.unfollow(community);

		if (community != null) {
			return new ResponseEntity<Community>(community, HttpStatus.OK);
		} else {
			return new ResponseEntity<Community>(community, HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Event> deleteEvent(@PathVariable long id) {
		service.delete(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

}
