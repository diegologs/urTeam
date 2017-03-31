package urteam.sport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sports")
public class SportRestController {

	@Autowired
	private SportService sportService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Sport>> getSports() {
		List<Sport> sports = sportService.getSports();
		if (sports != null) {
			return new ResponseEntity<>(sports, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{sportName}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Sport> getSport(@PathVariable String sportName) {
		Sport sport = sportService.getSport(sportName);
		if (sport != null) {
			return new ResponseEntity<>(sport, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Sport createSport(@RequestBody Sport newSport) {
		return sportService.createSport(newSport);
	}
	
	@RequestMapping(value = "/{sportName}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Sport> updateSport(@PathVariable String sportName, @RequestBody Sport sportToUpdate) {
		Sport sport = sportService.updateSport(sportName,sportToUpdate);
		if (sport != null) {
			return new ResponseEntity<>(sport, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{sportName}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Sport> deleteSport(@PathVariable String sportName){
		try{
			sportService.deleteSport(sportName);
			return new ResponseEntity<>(null,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
}
