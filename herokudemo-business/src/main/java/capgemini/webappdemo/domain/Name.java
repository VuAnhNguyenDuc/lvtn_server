package capgemini.webappdemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Very simple domain object. Contains a name.
 * @author kristian.haugene
 *
 */
@Entity
public class Name {
	
	/*
	 * Fields
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;

	/*
	 * Constructors
	 */
	public Name(String name){
		setName(name);
	}
	public Name(){
	}
	
	/*
	 * Getters and setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
}