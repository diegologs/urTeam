package urteam.news;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import urteam.community.Community;

@Entity

public class News {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String text;
	
	@ManyToOne
	 private Community community;
	
}