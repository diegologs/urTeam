package urteam;

import java.util.List;

import urteam.community.Community;
import urteam.event.Event;
import urteam.user.User;

public class Search {
	
	private List<User> users;
	private List<Community> communities;
	private List<Event> events;
	
	
	
	
	public Search() {
		super();
	}
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Community> getCommunities() {
		return communities;
	}
	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	
	
}
