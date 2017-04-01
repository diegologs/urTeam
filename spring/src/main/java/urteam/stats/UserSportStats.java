package urteam.stats;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UserSportStats {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private double sportTotalTime;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<Stat> stats = new ArrayList<>();

	public UserSportStats() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Stat> getStats() {
		return stats;
	}

	public void setStats(List<Stat> sportStats) {
		this.stats = sportStats;
	}

	public void addStats(Stat stats) {
		this.stats.add(stats);
		this.updateSportTotalTime();
	}

	public void updateSportTotalTime() {
		sportTotalTime = 0;
		for (Stat times : stats) {
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
