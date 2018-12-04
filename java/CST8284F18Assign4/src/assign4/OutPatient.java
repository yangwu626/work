/* File Name: OutPatient
 * Course Name: 18F_CST8284 Java
 * Lab Section: 314
 * Student Name: Yang Wu
 * Date: 18/10/2018
 */
package assign4;
import java.io.Serializable;
import java.util.Objects;
// create Patient's subclass: OutPatient
public class OutPatient extends Patient implements Serializable{

	/**
	 * first version
	 */
	private static final long serialVersionUID = 1L;

	// declare private class fields;
	private double distanceFromClinic;
	private boolean mobility ;

	// constructor
    OutPatient(String firstName, String lastName,String healthCardNumber,
			OurDate birthDate, double distanceFromClinic, boolean mobility) {
		// chain constructor of parent class.
		super(firstName, lastName, healthCardNumber, birthDate);
		// set distance
 		setDistanceFromClinic(distanceFromClinic);
		// set mobility
		setMobility(mobility);
	}
	// default constructor
	private OutPatient() {
		// chain constructor
		this("unknown", "unknown", "unknown", new OurDate(), Double.MAX_VALUE, false);
	}

	// return the distance.
	public double getDistanceFromClinic() {
		return distanceFromClinic;
	}

	// set the distance.
	private void setDistanceFromClinic(double distanceFromClinic) {
		this.distanceFromClinic = distanceFromClinic;
	}

	// return mobility.
	public boolean isMobility() {
		return mobility;
	}

	// set mobility.
	private void setMobility(boolean mobility) {
		this.mobility = mobility;
	}

	// override toString method
	@Override
	public String toString() {
		// append distance & mobility to parent's toString.
		return super.toString() + ", distanceFromClinic=" + distanceFromClinic +
			", mobility=" + mobility;
	}

	// override  hashCode method
	@Override
	public int hashCode() {
		// use Object.hashCode to generate hash code.
		return Objects.hash(super.hashCode(), distanceFromClinic,mobility);
	}

	// override equals method
	@Override
	public boolean equals(Object obj) {
		// return false if obj is null or is not instance of OutPatient.
		if (obj == null || !(obj instanceof OutPatient ))
			return false;
		// return false if failed in parent equals.
		if (!super.equals(obj))
			return false;

		// cast it to OutPatient
		OutPatient other = (OutPatient)obj;

		// the epsilon used in distance comparation,
		// if the two distance is close enough, we treat them as same.
		double DIS_EPSILON = 0.001;
		// return true if both distance and mobility are match,
		// return false in other cases.
		return Math.abs(distanceFromClinic - other.distanceFromClinic) <= DIS_EPSILON &&
			mobility == other.mobility;
	}
}
