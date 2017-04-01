package urteam.stats;

import java.util.Map;

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
@RequestMapping("/api/stats")
public class StatsRestController {

	@Autowired
	private StatsService statsService;

	@RequestMapping(value = "/{nickname}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Map<String, UserSportStats>> getUserStats(@PathVariable String nickname) {
		Map<String, UserSportStats> userSportStats = statsService.getUserStats(nickname);
		if (userSportStats != null) {
			return new ResponseEntity<>(userSportStats, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{nickname}/{sportName}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Stat> createUserStats(@PathVariable String nickname, @PathVariable String sportName,
			@RequestBody Stat stat) {
		Stat userNewStat = statsService.newUserStat(nickname, sportName, stat);
		if (userNewStat != null) {
			return new ResponseEntity<>(userNewStat, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
