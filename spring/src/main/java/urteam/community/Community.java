package urteam.community;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import urteam.user.*;

@Entity
public class Community {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String info;
	private String country;
	private String city;
	private String sport;
	
	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	@OneToMany(cascade = CascadeType.ALL)
	private List<User> admin_IDs = new ArrayList<>();
	
	@OneToMany
	private List<User> users_IDs = new ArrayList<>();

	public Community() {
	}

	public Community(String name, String info, String sport) {
		this.name = name;
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

	public List<User> getAdmin_IDs() {
		return admin_IDs;
	}

	public void setAdmin_IDs(List<User> admin_IDs) {
		this.admin_IDs = admin_IDs;
	}

	public List<User> getUsers_IDs() {
		return users_IDs;
	}

	public void setUsers_IDs(List<User> users_IDs) {
		this.users_IDs = users_IDs;
	}
	
	@Override
	public String toString() {
		return "Comunidad [id=" + id + ", Nombre=" + name + ", info=" + info + "]";
	}

}
