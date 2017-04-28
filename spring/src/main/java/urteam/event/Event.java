package urteam.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonView;

import urteam.user.User;

@Entity
public class Event {
	
	public interface MinimalEvent{}
	
	public interface BasicEvent{}
	
	public interface MembersEvent{}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(MinimalEvent.class)
	private long id;
	
	private String eventId = "aleatorio";
	
	@Size(min = 2, max = 21)
	@JsonView(MinimalEvent.class)
	private String name;
	
	//@OneToOne
	@JsonView(BasicEvent.class)
	private String sport;
	
	@JsonView(BasicEvent.class)
	private double price;
	
	@Column(columnDefinition = "TEXT")
	@JsonView(BasicEvent.class)
	private String info;
	
	@JsonView(BasicEvent.class)
	private String main_photo;
	
	@JsonView(BasicEvent.class)
	private String place;
	
	@Basic
	private ArrayList<String> eventImages = new ArrayList<String>();

	@DateTimeFormat(pattern = "dd/MM")
	@Temporal(TemporalType.DATE)
	@JsonView(BasicEvent.class)
	private Date start_date;
	
	@DateTimeFormat(pattern = "dd/MM")
	@Temporal(TemporalType.DATE)
	@JsonView(BasicEvent.class)
	private Date end_date;
	
	@JsonView(BasicEvent.class)
	private int day_date;
	@JsonView(BasicEvent.class)
	private String month_date;
	@JsonView(BasicEvent.class)
	private int year_date;
	
	@OneToOne
	@JsonView(BasicEvent.class)
	private User owner_id;
	
	//@JsonView(BasicEvent.class)
	@ManyToMany(mappedBy = "eventList")
	@JsonView(MembersEvent.class)
	private List<User> participants_IDs = new ArrayList<>();
	

	public Event() {
	}

	public Event(String name,double price, String info, String place, 
			Date start_date, Date end_date, String main_photo) {
		
		this.name = name;
		this.price = price;
		this.info = info;
		this.place = place;		
		this.start_date = start_date;
		this.end_date = end_date;
		this.main_photo = main_photo;
	}
	
	public Event(String name, String info, double price, String place){		
		this.name = name;
		this.info = info;
		this.price = price;
		this.place = place;
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
	
	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
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

	public void setStart_date(Date start_date){
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date){
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

	public int getDay_date() {
		return day_date;
	}

	public void setDay_date(int day_date) {
		this.day_date = day_date;
	}

	public String getMonth_date() {
		return month_date;
	}

	
	public void setMonth_date(String month_date) {
		this.month_date =  month_date;
	}

	public int getYear_date() {
		return year_date;
	}

	public void setYear_date(int year_date) {
		this.year_date = year_date;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public List<String> getEventImages() {
		return eventImages;
	}

	public void setEventImages(List<String> eventImages) {
		this.eventImages = (ArrayList<String>) eventImages;
	}
	
	public void addNewImageToGallery(String filename){
		this.eventImages.add(filename);
	}
	
}
