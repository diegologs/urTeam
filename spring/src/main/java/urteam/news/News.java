package urteam.news;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

@Entity

public class News {
	
	
	public interface BasicNews{}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(BasicNews.class)
	private long id;
	@JsonView(BasicNews.class)
	private String title;
	@JsonView(BasicNews.class)
	private String text;
	
		
	public News(String title, String text){
		this.title = title;
		this.text = text;
		
	}
	
	public News(){
		
	}
	
}