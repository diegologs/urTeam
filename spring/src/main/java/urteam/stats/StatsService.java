package urteam.stats;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urteam.sport.Sport;
import urteam.sport.SportService;
import urteam.user.User;
import urteam.user.UserRepository;
import urteam.user.UserService;

@Service
public class StatsService {

	@Autowired
	SportService sportService;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	public Map<String, UserSportStats> getUserStats(String nickname) {
		User user = userRepository.findByNickname(nickname);
		return user.getSportStats();
	}

	public Stat newUserStat(String nickname, String sport, Stat stat) {

		User user = userService.getUser(nickname);
		if (user.getSportStats().containsKey(sport)) {
			user.getSportStats().get(sport).getStats().add(stat);
		} else {
			UserSportStats userSportStats = new UserSportStats();
			userSportStats.getStats().add(stat);
			user.getSportStats().put(sport, userSportStats);
		}
		user.getSportStats().get(sport).updateSportTotalTime();
		userRepository.save(user);
		return stat;
	}

	public double computeUserScore(User user) {

		double totalScore = 0;

		Map<String, UserSportStats> userSportStats = user.getSportStats();
		for (Map.Entry<String, UserSportStats> entry : userSportStats.entrySet()) {
			Sport sport = sportService.getSport(entry.getKey());
			totalScore += sport.getMultiplicator() * entry.getValue().getSportTotalTime() * 100;
		}
		return totalScore;
	}

	public int computeUserLevel(User user) {

		double userScore = Double.parseDouble(user.getScore());
		return (int) (userScore / 1000);
	}

	public int computeUserBarLevel(User user) {

		double userScore = Double.parseDouble(user.getScore());
		int userbarlevel = ((int) userScore % 1000) / 10;
		return userbarlevel;
	}

}
