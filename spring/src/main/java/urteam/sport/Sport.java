package urteam.sport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true)
	private String name;
	
	private double multiplicator;
	
	
	public Sport(){}

	public Sport(String name, float multiplicator) {
		super();
		this.name = name;
		this.multiplicator = multiplicator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMultiplicator() {
		return multiplicator;
	}

	public void setMultiplicator(double d) {
		this.multiplicator = d;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
	

}
