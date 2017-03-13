package urteam.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import urteam.community.*;
import urteam.community.Community;
import urteam.event.Event;
import urteam.stats.Stats;

@Entity
@Table(name = "user_profile")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String generatedId = "aleatorio";
	private String username;
	private String surname;
	private String nickname;
	private String passwordHash;
	private String email;

	@Column(columnDefinition = "TEXT")
	private String bio = "";
	private String city;
	private String country;
	private String score = "0";
	private String avatar = "avatar";
	
	@ElementCollection(fetch = FetchType.EAGER) 
	private List<String> roles;

	@ManyToMany
	private List<User> following = new ArrayList<>();

	@ManyToMany(mappedBy = "following")
	private List<User> followers = new ArrayList<>();

	@ManyToMany
	private List<Community> communityList = new ArrayList<>();
	
	@ManyToMany
	private List<Event> eventList = new ArrayList<>();

	@OneToMany
	private List<Stats> sportStats = new ArrayList<>();
	
	public User() {}

	public User(String username, String surname, String nickname, String password, String email, String bio,
			String score, String city, String country,String avatar, String... roles) {
		this.username = username;
		this.surname = surname;
		this.nickname = nickname;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		this.email = email;
		this.bio = bio;
		this.city = city;
		this.country = country;
		this.score = score;
		this.avatar = avatar;
		this.roles = new ArrayList<>(Arrays.asList(roles));
		
		
	}
	
	public User(String username, String surname, String nickname, String password, String email,
			String city, String country) {
		this.username = username;
		this.surname = surname;
		this.nickname = nickname;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		this.email = email;
		this.city = city;
		this.country = country;
		this.roles = new ArrayList<>(Arrays.asList("USER_ROLE"));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String name) {
		this.username = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String password) {
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void addFollowing(User following) {
		this.following.add(following);
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void addFollower(User follower) {
		this.followers.add(follower);
	}

	public int getNumberOfFollower() {
		return this.followers.size();
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Community> getCommunityList() {
		return communityList;
	}

	public void addCommunity(Community community) {
		this.communityList.add(community);
	}

	public void removeCommunity(Community community) {
		this.communityList.remove(community);
		
	}
	
	
	public List<Stats> getSportStats() {
		return sportStats;
	}

	public void addStat(Stats stats){
		this.getSportStats().add(stats);
	}

	public void removeEvent(Event event) {
		this.eventList.remove(event);
		
	}

	public void addEvent(Event event) {
		this.eventList.add(event);
		
	}
	
	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

}
