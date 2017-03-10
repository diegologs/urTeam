package urteam.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_profile")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String username;
	private String surname;
	private String nickname;
	private String password;
	private String email;
	
	@Column(columnDefinition = "TEXT")
	private String bio;
	private String city;
	private String country;
	private String score;
	private String avatar;
	private String role;

	// @OneToMany(mapped)
	// private List<User> friendList;

	public User() {
	}

	public User(String username, String surname, String nickname, String password, String email, String bio,
			String score, String city, String country) {
		this.username = username;
		this.surname = surname;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.bio = bio;
		this.city = city;
		this.country = country;
		this.score = score;
		this.avatar = "nada";
		this.role = "nada";
		// this.friendList = new ArrayList<>();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// public List<User> getFriendList() {
	// return friendList;
	// }
	//
	// public void setFriendList(ArrayList<User> friendList) {
	// this.friendList = friendList;
	// }

}
