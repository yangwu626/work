/* File Name: Patient.
 * Course Name: 18F_CST8284 Java
 * Lab Section: 314
 * Student Name: Yang Wu
 * Date: 18/10/2018
 */
package assign3;

//create Patient class.
public class Patient {
	//declare Patient class private fields.
	private String firstName;
	private String lastName;
	private OurDate birthDate;
	private String healthCardNumber;
	// constructor
	public Patient(String firstName, String lastName,String healthCardNumber, OurDate birthDate ) {
		setFirstName(firstName);
		setLastName(lastName);
		setHealthCardNumber(healthCardNumber);
		setBirthDate(birthDate);
	}

	// default constructor.
	public Patient() {
		// chain constructor
		this("unknown", "unknown", "unknown", new OurDate());
	}
	
	// return patient's first name
	public String getFirstName() {
		return firstName;
	}

	// set patient's first name
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// return patient's last name
	public String getLastName() {
		return lastName;
	}

	// set patient's last name
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// get patient's health card #
	public String getHealthCardNumber() {
		return healthCardNumber;
	}

	// set patient's health card #
	public void setHealthCardNumber(String healthCardNumber) {
		if (healthCardNumber.length() != 9 ||
			!healthCardNumber.matches("[0-9]+")) {
			throw new MedicalClinicException("Invalide health card number!", null);
		}
		this.healthCardNumber = healthCardNumber;
	}

	// get patient's birth date
	public OurDate getBirthDate() {
		return birthDate;
	}

	// set patient's birth date
	private void setBirthDate(OurDate birthDate) {
		OurDate now = new OurDate();
		// throw an exception if birth date is invalid (current day or in future)
		if (birthDate.getYear() < 1900 ||
			birthDate.getYear() > now.getYear() ||
			(birthDate.getYear() == now.getYear() && birthDate.getMonth() > now.getMonth()) ||
			(birthDate.getYear() == now.getYear() && birthDate.getMonth() == now.getMonth() && birthDate.getDay() >= now.getDay())) {  		
			throw new MedicalClinicException("Invalide birth date!", null);
		}
		this.birthDate = birthDate;
	}
	
	// Override toString method
	@Override
	public String toString() {
		return "firstName=" + firstName + ",lastName=" + lastName + ", birthDate=" + birthDate.toString() + 
				", healthCard=HealthCard [healthCardNumber=" +  healthCardNumber + "]";
	}
	
	// Override hashCode method
	@Override
	public int hashCode() {
		// if health card number is available, then use it since it's unique.
		if (healthCardNumber != "unknown")
			return healthCardNumber.hashCode();
		// health card # is invalid, then we use String(name + birthDate) to generate a hadhcode.
		String all = firstName + lastName + birthDate.toString();
		return all.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// return false if obj is null or is not instance of Patient.
		if (obj == null || !(obj instanceof Patient ))
			return false;
		// cast it to an instance of Patient
		Patient other = (Patient) obj;
		return birthDate.equals(other.birthDate) &&
			firstName.equals(other.firstName) &&
			healthCardNumber == other.healthCardNumber &&
			lastName.equals(other.lastName);
	}
	
	
	
	
	
}
