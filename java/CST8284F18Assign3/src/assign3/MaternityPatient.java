
/* File Name: MaternityPatient
 * Course Name: 18F_CST8284 Java
 * Lab Section: 314
 * Student Name: Yang Wu
 * Date: 18/10/2018
 */
package assign3;

import java.util.Objects;

//create Patient's subclass: MaternityPatient
public class MaternityPatient extends Patient{
	
	// declare private class fields;
	private OurDate dueDate;
	private boolean nutritionTesting ;

	// constructor
	MaternityPatient(String firstName, String lastName,String healthCardNumber, 
			OurDate birthDate, OurDate dueDate, boolean nutritionTesting) {
		// chain parent constructor
		super(firstName,lastName, healthCardNumber, birthDate);
		setDueDate(dueDate);
		setNutritionTesting(nutritionTesting); 
	}
	// default constructor
	MaternityPatient() {
		// chain constructor
		this("unknown", "unknown", "unknown", new OurDate(), new OurDate(), false);
	}
	
	// return dueDate
	public OurDate getDueDate() {
		return dueDate;
	}

	// set dueDate
	private void setDueDate(OurDate dueDate) {
		OurDate now = new OurDate();
		// throw an exception if birth date is invalid (current day or in future)
		if (dueDate.getYear() < now.getYear() ||
			(dueDate.getYear() == now.getYear() && dueDate.getMonth() > now.getMonth()) ||
			(dueDate.getYear() == now.getYear() && dueDate.getMonth() == now.getMonth() && dueDate.getDay() < now.getDay())) {  		
			throw new MedicalClinicException("Invalide due date!", null);
		}
		this.dueDate = dueDate;
	}

	// return nutritionTesting
	public boolean isNutritionTesting() {
		return nutritionTesting;
	}

	// set nutritionTesting
	private void setNutritionTesting(boolean nutritionTesting) {
		this.nutritionTesting = nutritionTesting;
	}

	// override toString method
	@Override
	public String toString() {
		return super.toString() + ",dueDate:" + dueDate.toString() + ",nutritionTesting:" + nutritionTesting ;
	}

	// override hashCode method
	@Override
	public int hashCode() {
		// use Object.hashCode to generate hash code.
		return Objects.hash(super.hashCode(), dueDate, nutritionTesting);
	}

	// override equals method
	@Override
	public boolean equals(Object obj) {
		// return false if obj is null or is not instance of OutPatient.
		if (obj == null || !(obj instanceof MaternityPatient ))
			return false;
		// return false if failed in parent equals.
		if (!super.equals(obj))
			return false;

		// cast it to OutPatient
		MaternityPatient other = (MaternityPatient)obj;
		// return true if both dueDate and nutritionTesting are equal.
		// return false in other cases.
		return dueDate.equals(other.dueDate) &&
			nutritionTesting == other.nutritionTesting;
	}
	
	
}
	
	
