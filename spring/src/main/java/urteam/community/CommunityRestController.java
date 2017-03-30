package urteam.community;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import urteam.ConstantsUrTeam;
import urteam.urteamController;
import urteam.event.Event;

import urteam.news.News;
import urteam.news.NewsRepository;
import urteam.user.User;
import urteam.user.UserComponent;
import urteam.user.UserRepository;


@RestController
@RequestMapping("/api/groups")
public class CommunityRestController {

	interface CompleteCommunity extends Community.BasicCommunity{} 
	
	@Autowired
	private CommunityService service;

	@JsonView(CompleteCommunity.class)	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<Community> groups() {
		
		return service.findAll();
	}
	
	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Community> getGroup(@PathVariable long id) {
		
		Community community = service.findOne(id);
		
		if(community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}
				
		
	}


	
	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}/setInfo", method = RequestMethod.PUT)
	public ResponseEntity<Community> addInfo(@PathVariable long id, @RequestBody String info) {
		
		
		Community community = service.findOne(id);
		
		
		if(service.editInfo(community, info) && community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		
					
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Community> edit(@PathVariable long id, @RequestBody Community communityEdited) {
		
		
		Community community = service.findOne(id);
		
		
		if(service.edit(communityEdited) && community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		
					
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}
		
	}

	
	@JsonView(CompleteCommunity.class)
	@RequestMapping("/{id}/addImage")
	public ResponseEntity<Community> addImage(@PathVariable long id, @RequestParam("file") MultipartFile file) throws ParseException {
		
		
		Community community = service.findOne(id);
		
		service.addImage(community, file);
		
		
		if(community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(CompleteCommunity.class)
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Community> add(@RequestBody Community community, MultipartFile file) throws ParseException {

		
		service.add(community, file);
		
		if(community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}

	}

	
	@JsonView(CompleteCommunity.class)
	@RequestMapping("/group/{id}/follow")
	public ResponseEntity<Community> follow(@PathVariable long id) {
		
		Community community = service.findOne(id);
		
		service.follow(community);
		
		
		if(community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}
	}
	
	
}
