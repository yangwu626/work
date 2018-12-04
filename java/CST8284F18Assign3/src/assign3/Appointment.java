/* File Name: Appointment
 * Course Name: 18F_CST8284 Java
 * Lab Section: 314
 * Student Name: Yang Wu
 * Date: 18/10/2018
 */
package assign3;
import java.util.Objects;

// Appointment
public class Appointment {
	Doctor doctor;
	Patient patient;
	OurDate appointmentDate;

	// constructor
	public Appointment(Patient patient, Doctor doctor, OurDate appointmentDate) {
		setPatient(patient);
		setDoctor(doctor);
		setAppointmentDate(appointmentDate);
	}

	// default constructor
	public Appointment() {
		// chain constructor
		this(new Patient(), new Doctor(), new OurDate());
	}
	// return doctor
	public Doctor getDoctor() {
		return doctor;
	}

	// set doctor
	private void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	//	return patient;
	public Patient getPatient() {
		return patient;
	}

	//	set patient;
	private void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	//	get appointmentDate
	public OurDate getAppointmentDate() {
		return appointmentDate;
	}

	//	set appointmentDate
	private void setAppointmentDate(OurDate appointmentDate) {
		OurDate now = new OurDate();
		int dy = appointmentDate.getYear() - now.getYear();
		int dm = appointmentDate.getMonth() - now.getMonth();
		int dd = appointmentDate.getDay() - now.getDay();
		
		if (dy < 0 ||
			(dy == 0 && dm < 0) ||
			(dy == 0 && dm == 0 && dd <= 0)) { 
			throw new MedicalClinicException("Invalide appointment date!", null);
		}
		this.appointmentDate = appointmentDate;
	}

 	// override toString method
	@Override
	public String toString() {
		// append distance & mobility to parent's toString.
		return "appointments=" + appointmentDate.toString() +
			", " + doctor.toString() + ", patient=Patient[" + patient.toString() + "]";
	}

	// override  hashCode method
	@Override
	public int hashCode() {
		// use Object.hashCode to generate hash code.
		return Objects.hash(doctor, patient, appointmentDate);
	}

	// override equals method
	@Override
	public boolean equals(Object obj) {
		// return false if obj is null or is not instance of OutPatient.
		if (obj == null || !(obj instanceof Appointment))
			return false;
		// cast it to Appointment
		Appointment other = (Appointment)obj;
		// return true if doctor/patient/appointmentDate are all matched to other.
		// return false in any other case.
		return 	doctor.equals(other.doctor) &&
			patient.equals(other.patient) &&
			appointmentDate.equals(other.appointmentDate);
	}
	
}
