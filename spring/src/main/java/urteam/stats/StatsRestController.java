package urteam.stats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatsRestController {

	@Autowired
	private StatsService statsService;

	@RequestMapping(value = "/{nickname}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<UserSportStats>> getUserStats(@PathVariable String nickname) {
		List<UserSportStats> userSportStats = statsService.getUserStats(nickname);
		if (userSportStats != null) {
			return new ResponseEntity<>(userSportStats, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
