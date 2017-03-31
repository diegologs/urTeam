package urteam.community;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import urteam.event.Event.BasicEvent;
import urteam.news.News;
import urteam.user.User;

@Entity
public class Community {
	
	
	public interface BasicCommunity{}
	
	@Id
	@JsonView(BasicCommunity.class)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String communityId = "aleatorio";

	@JsonView(BasicCommunity.class)
	private String name;
	@JsonView(BasicCommunity.class)
	private String info;
	private String country;	
	private String city;
	@JsonView(BasicCommunity.class)
	private String sport;
	private String main_photo;
	
	@OneToOne
	private User owner_id;
	
	
	@Basic
	private ArrayList<String> communityImages = new ArrayList();
	
	@JsonIgnore
	@OneToMany
	private List<News> news;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "communityList")
	private List<User> communityUsers = new ArrayList<>();
	
	

	public Community() {
	}

	public Community(String name, String info, String sport, String main_photo) {
		this.name = name;
		this.info = info;
		this.sport = sport;
		this.main_photo = main_photo;
	}	
	
	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	
	public String getMain_photo() {
		return main_photo;
	}

	public void setMain_photo(String main_photo) {
		this.main_photo = main_photo;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public List<User> getUsers_IDs() {
		return communityUsers;
	}

	public void setUsers_IDs(List<User> users_IDs) {
		this.communityUsers = users_IDs;
	}
	
	@Override
	public String toString() {
		return "Comunidad [id=" + id + ", Nombre=" + name + ", info=" + info + "]";
	}

	public void addNews(News news2) {
		news.add(news2);
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}


	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	
	public void addImage(String filename){
		this.communityImages.add(filename);
		
	}

	public List<User> getCommunityUsers() {
		return communityUsers;
	}

	public void setCommunityUsers(List<User> communityUsers) {
		this.communityUsers = communityUsers;
	}
	
	public ArrayList<String> getCommunityImages() {
		return communityImages;
	}

	public void setCommunityImages(ArrayList<String> communityImages) {
		this.communityImages = communityImages;
	}
	
	public User getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(User owner_id) {
		this.owner_id = owner_id;
	}
}

