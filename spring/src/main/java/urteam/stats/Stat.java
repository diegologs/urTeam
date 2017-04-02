package urteam.stats;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import urteam.user.User.BasicUser;

@Entity
public class Stat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(BasicUser.class)
	private long id;
	@JsonView(BasicUser.class)
	private double totalSesionTime;
	@JsonView(BasicUser.class)
	private String date;
	
	
	public Stat() {
		
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public double getTotalSesionTime() {
		return totalSesionTime;
	}


	public void setTotalSesionTime(double totalSesionTime) {
		this.totalSesionTime = totalSesionTime;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
}
