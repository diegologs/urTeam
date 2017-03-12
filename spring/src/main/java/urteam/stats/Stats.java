package urteam.stats;

import java.sql.Date;

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
	private float totalSesionTime;
	private Date date;
	
	public Stats(){}
	
	
	public Stats(String userId, Sport sport, float totalSesionTime, Date date) {
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
	public float getTotalSesionTime() {
		return totalSesionTime;
	}
	public void setTotalSesionTime(float totalSesionTime) {
		this.totalSesionTime = totalSesionTime;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
