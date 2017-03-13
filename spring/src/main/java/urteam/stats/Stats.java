package urteam.stats;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import urteam.sport.Sport;

@Entity
public class Stats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String userId;
	
	@OneToOne
	private Sport sport;
	private double totalSesionTime;
	private String date;
	
	public Stats(){}
	
	
	public Stats(String userId, Sport sport, double totalSesionTime, String date) {
		super();
		this.userId = userId;
		this.sport = sport;
		this.totalSesionTime = totalSesionTime;
		this.date = date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Sport getSport() {
		return sport;
	}
	public void setSport(Sport sport) {
		this.sport = sport;
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
