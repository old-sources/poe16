package poe;

public class Person {

	private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	private String firstName;
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	private String lastName;
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	private java.time.LocalDate birthDate;
	public java.time.LocalDate getBirthDate() { return birthDate; }
	public void setBirthDate(java.time.LocalDate birthDate) { this.birthDate = birthDate; }

}
