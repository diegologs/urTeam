package urteam.event;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import urteam.user.*;

@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String sport;
	private double price;
	private String info;
	private String main_photo;
	private String place;
	private Date start_date;
	private Date end_date;
	
	@OneToOne
	private User owner_id;
	
	@OneToMany
	private List<User> participants_IDs;
	

	public Event() {
	}

	public Event(String name, String sport, double price, String info, String place, Date start_date, Date end_date) {
		this.name = name;
		this.sport = sport;
		this.price = price;
		this.info = info;
		this.place = place;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	public Event(String name, String sport, String info){
		this.name = name;
		this.sport = sport;
		this.info = info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMain_photo() {
		return main_photo;
	}

	public void setMain_photo(String main_photo) {
		this.main_photo = main_photo;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public User getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(User owner_id) {
		this.owner_id = owner_id;
	}

	public List<User> getParticipants_IDs() {
		return participants_IDs;
	}

	public void setParticipants_IDs(List<User> participants_IDs) {
		this.participants_IDs = participants_IDs;
	}
	
	
	
	
	
	
	
	

}
