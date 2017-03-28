package urteam.sport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
