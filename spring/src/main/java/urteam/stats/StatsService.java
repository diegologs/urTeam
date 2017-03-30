package urteam.stats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urteam.community.CommunityRepository;
import urteam.sport.Sport;
import urteam.sport.SportService;
import urteam.user.User;
import urteam.user.UserRepository;

@Service
public class StatsService {

	@Autowired
	SportService sportService;

	@Autowired
	StatsRepository statsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommunityRepository communityRepository;

	public String addUserStats(String nickname,String sport,String date,
			double sesionTime) {
		User user = userRepository.findByNickname(nickname);

		Stat statsDos = new Stat();
		statsDos.setDate(date);
		statsDos.setTotalSesionTime(sesionTime);

		if (user.containsUserSport(sport)) {
			user.getUserSportsList().get(user.userSportPosition(sport)).addSportStats(statsDos);
		} else {
			UserSportStats userSport = new UserSportStats();
			userSport.setSportName(sport);
			userSport.addSportStats(statsDos);
			user.addUserSportsList(userSport);
		}
		user.setScore(String.valueOf(computeUserScore(user)));
		userRepository.save(user);
		return "cambiar";
	}
	
	public List<UserSportStats> getUserStats(String nickname){
		User user = userRepository.findByNickname(nickname);
		return user.getUserSportsList();
	}
	
	
	

	public double computeUserScore(User user) {

		double totalScore = 0;

		List<UserSportStats> userSport = user.getUserSportsList();

		for (UserSportStats u : userSport) {
			Sport sport = sportService.getSport(u.getSportName());
			for (Stat s : u.getSportStats()) {
				totalScore += sport.getMultiplicator() * s.getTotalSesionTime() * 100;
			}
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
