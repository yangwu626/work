/* File Name: Doctor
 * Course Name: 18F_CST8284 Java
 * Lab Section: 314
 * Student Name: Yang Wu
 * Date: 18/10/2018
 */
package assign4;

import java.io.Serializable;

// Create Doctor class.
public class Doctor implements Serializable {
	/**
	 * first version
	 */
	private static final long serialVersionUID = 1L;
	//declare Doctor class fields: firstName:String, lastName:String, specialty:String.
	private String firstName;
	private String lastName;
	private String specialty;
	
	// default constructor
	Doctor() {
		// chain constructor
		this("unknown", "unknown", "unknown");
	}
	
	// Create Doctor  constructor firstName:String, lastName:String, specialty:String.
	Doctor(String firstName, String lastName, String specialty){
		setFirstName(firstName);
		setLastName(lastName);
		setSpecialty(specialty);
	}
	//getFirstName method.
	public String getFirstName() {
		return firstName;
	}
	//getLastName method.
	public String getLastName() {
		return lastName;
	}
	//getSpecialty method.
	public String getSpecialty() {
		return specialty;
	}
	//setFirstName method.
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	//setLastName method.
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	//setSpecialty method.
	private void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Override
	public String toString() {
		return ("doctor=" + getFirstName() + "," + getLastName() + ", " + getSpecialty());
	}
	
	//Override hashCode method.
	@Override
	public int hashCode() {
		// mix firstName,lastName and specialty together using string hashCode.
		String all = firstName + ":" + lastName + ":" + specialty;
		return all.hashCode();
	}

	// Override equals method
	@Override
	public boolean equals(Object obj) {
		// return false if obj is null or is not an instance of Doctor
		if (obj == null || !(obj instanceof Doctor ))
			return false;
		// cast to Doctor
		Doctor doctor = (Doctor) obj;
		// return true if all members are match, else return false.
		return firstName.equals(doctor.getFirstName()) &&
			lastName.equals(doctor.getLastName()) &&
			specialty.equals(doctor.getSpecialty());
	}
}
