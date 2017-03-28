package urteam.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import urteam.stats.Stats_dos;

@Entity
public class UserSport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private double sportTotalTime;
	private String sportName;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<Stats_dos> sportStats = new ArrayList<>();

	public UserSport() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public List<Stats_dos> getSportStats() {
		return sportStats;
	}

	public void setSportStats(List<Stats_dos> sportStats) {
		this.sportStats = sportStats;
	}

	public void addSportStats(Stats_dos stats) {
		this.sportStats.add(stats);
		this.updateSportTotalTime();
	}

	public void updateSportTotalTime() {
		sportTotalTime = 0;
		for (Stats_dos times : sportStats) {
			sportTotalTime = sportTotalTime + times.getTotalSesionTime();
		}
	}

	public double getSportTotalTime() {
		return sportTotalTime;
	}

	public void setSportTotalTime(double sportTotalTime) {
		this.sportTotalTime = sportTotalTime;
	}

}
