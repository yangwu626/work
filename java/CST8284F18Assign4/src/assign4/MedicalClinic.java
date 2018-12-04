/* File Name: MedicalClinic
 * Course Name: 18F_CST8284 Java
 * Lab Section: 314
 * Student Name: Yang Wu
 * Date: 18/10/2018
 */
package assign4;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

// MedicalClinic class.
public class MedicalClinic implements Serializable{
	/**
	 * first version
	 */
	private static final long serialVersionUID = 1L;
	// predefined max values.  
	static final int MAXAPPOINTMENTS =5;
	static final int MAXPATIENTS = 5;
	static final int MAXDOCTORS = 5;
	
	// array list of appointments, patients and docotors.
	private ArrayList<Appointment> appointments;
	private ArrayList<Patient> patients;
	private ArrayList<Doctor> doctors;

	private int numberAppointments;
	private int numberPatients;
	private int numberDoctors;

	// default constructor
	public MedicalClinic() {
		// alloc arraylist of appointments, patients and doctors
		appointments = new ArrayList<Appointment>();
		patients = new ArrayList<Patient>();
		doctors = new ArrayList<Doctor>();
		// initialize numbers of appointment/patients/docotrs to 0
		numberAppointments = 0;
		numberPatients = 0;
		numberDoctors = 0;
		
		// add doctors
		doctors.add(new Doctor("Singh", "Vikash", "endocrinologist"));
		doctors.add(new Doctor("Miller", "Susan", "cardiologist"));
		doctors.add(new Doctor("Do", "Thanh", "neurologist"));
		doctors.add(new Doctor("DaSilva", "Francois", "internist"));
		doctors.add(new Doctor("Chin", "Judy", "Family Physician"));
			
		numberDoctors = MedicalClinic.MAXDOCTORS;
		
	}
	// add a new patient
	public void addPatient(String lastName, String firstName, String healthCardNumber,
						   OurDate birthDate, int patientType) {
		// skip add patient if patients reach to the max value.
		if (patients.size() >= MedicalClinic.MAXPATIENTS)
			return ;
		
		// skip adding if the patient already exist based on health card #
		for(int i=0; i<patients.size(); i++){
			if (patients.get(i).getHealthCardNumber().equals(healthCardNumber))
				return ;
		}
		Patient patient = null;
		switch(patientType) {
			case 1: // maternity patient
				patient = new MaternityPatient(firstName, lastName, healthCardNumber, birthDate, new OurDate(), false);
				break;
			case 2: // Out patient
				patient = new OutPatient(firstName, lastName, healthCardNumber, birthDate, -1, false);
				break;
			case 3: // regular patient
				patient = new Patient(firstName, lastName, healthCardNumber, birthDate);
				break;
			default:
				break;
		}
		if(patient != null) {
			patients.add(patient);
			// update number of patients
			numberPatients = patients.size();
		}
	}
	// overloaded function. add OutPatient
	public void addPatient(String lastName, String firstName, String healthCardNumber, 
			OurDate	date, boolean mobility, double distance) {
		// skip add patient if patients reach to the max value.
		if (patients.size() >= MedicalClinic.MAXPATIENTS)
			return ;
		
		// skip adding if the patient already exist based on health card #
		for(int i=0; i<patients.size(); i++) {
			if (patients.get(i).getHealthCardNumber().equals(healthCardNumber))
				return ;
		}
		
		Patient patient = new OutPatient(firstName, lastName, healthCardNumber, date, distance, mobility);
		if(patient != null) {
			patients.add(patient);
			// update number of patients
			numberPatients = patients.size();
		}
	}

	// overloaded function. add MaternityPatient
	public void addPatient(String lastName, String firstName, String healthCardNumber, 
			OurDate	date, OurDate dueDate, boolean nutritionTesting) {

		// skip add patient if patients reach to the max value.
		if (patients.size() >= MedicalClinic.MAXPATIENTS)
			return ;
		
		// skip adding if the patient already exist based on health card #
		for(int i=0; i<patients.size(); i++) {
			if (patients.get(i).getHealthCardNumber().equals(healthCardNumber))
				return ;
		}
		Patient patient = new MaternityPatient(firstName, lastName, healthCardNumber, date, dueDate, nutritionTesting);
		if(patient != null) {
			patients.add(patient);
			// update number of patients
			numberPatients = patients.size();
		}
	}
	
	// add a new appointment
	public void addAppointment(Patient patient, Doctor doctor, OurDate date) {
		if (appointments.size() < MedicalClinic.MAXPATIENTS)
			appointments.add(new Appointment(patient, doctor, date));
		// update number of appointments.
		numberAppointments = appointments.size();
	}
	
	// cancel an appointment
	public void cancelAppointment(int index) {
		if (index >= 0 && index < appointments.size())
			appointments.remove(index);
	}
	// get number of appointments
	public int getNumberAppointments() {
		return appointments.size();
	}
	// get number of patienets
	public int getNumberPatients() {
		return patients.size();
	}
	// get number of doctors
	public int getNumberDoctors() {
		return doctors.size();
	}
	// get max value of appointments
	public int getMaxAppointments() {
		return MedicalClinic.MAXAPPOINTMENTS;
	}
	// get max value of patients
	public int getMaxPatients() {
		return MedicalClinic.MAXPATIENTS;
	}
	// get max value of doctors
	public int getMaxDoctors() {
		return MedicalClinic.MAXDOCTORS;
	}
	// get doctor list
	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}
	// get patient list
	public ArrayList<Patient> getPatients() {
		return patients;
	}
	// get appointment list
	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}
	
	// Serialize Patient data  
	public void filePatientsOut() {
    	try
        {    
            //Saving of object in a file 
            FileOutputStream file = new FileOutputStream("PatientData.ser"); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
              
            // write serialization data of Patient objects 
            out.writeObject(this.patients); 
              
            out.close(); 
            file.close(); 
              
            System.out.println("Patients data has been serialized"); 
        } 
        catch(IOException ex) 
        { 
        	ex.printStackTrace();
            System.out.println("IOException is caught"); 
        } 
	}

	// Deserialize Patient data
	public void filePatientsIn() {
	    try
        {    
	    	// open file & load object from stream
	    	FileInputStream file = new FileInputStream("PatientData.ser"); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Reading Patients from a file 
            this.patients = (ArrayList<Patient>)in.readObject(); 
              
            // close file & object parser
            in.close(); 
            file.close(); 
              
            System.out.println("Patients data has been deserialized "); 
        } 
        // catch io exceptions, for example, if there is no file PatientData.ser    
        catch(IOException ex) 
        { 
            System.out.println("Failed to open file PatientData.ser"); 
        	ex.printStackTrace();
        } 
        // catch exceptions in parsing Object data  
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("File PatientData.ser contains invalid data"); 
        	ex.printStackTrace();
        } 		
	}
}
