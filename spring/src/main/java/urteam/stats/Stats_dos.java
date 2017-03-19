package urteam.stats;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Stats_dos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private double totalSesionTime;
	private String date;
	
	
	public Stats_dos() {
		
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
