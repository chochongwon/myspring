package myspring.batch.model;

import java.io.Serializable;

public class PlayerBean implements Serializable{
	
	private static final long serialVersionUID = -5330794101303450135L;

	private String id;
	
	private String lastName;
	
	private String firstName;
	
	private String position;
	
	private int birthYear;
	
	private int debutYear;
	
	public PlayerBean(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getDebutYear() {
		return debutYear;
	}

	public void setDebutYear(int debutYear) {
		this.debutYear = debutYear;
	}

	@Override
	public String toString() {
		return "PlayerBean [id=" + id + ", lastName=" + lastName
				+ ", firstName=" + firstName + ", position=" + position
				+ ", birthYear=" + birthYear + ", debutYear=" + debutYear + "]";
	}
	
}