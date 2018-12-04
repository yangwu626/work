
/* File Name: MedicalClinicUserInterface
 * Course Name: 18F_CST8284 Java
 * Lab Section: 314
 * Student Name: Yang Wu
 * Date: 18/10/2018
 */

package assign3;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

public class MedicalClinicUserInterface {

	private static final int ADD_PATIENT = 1;
	private static final int ADD_APPOINTMENT = 2;
	private static final int CANCEL_APPOINTMENT = 3;
	private static final int LIST_APPOINTMENT = 4;
	private static final int QUIT = 5;

	private MedicalClinic clinic;
	private Scanner in;

	public MedicalClinicUserInterface() {
		in = new Scanner(System.in);
		clinic = new MedicalClinic();
	}

	public static void main(String [] args) {
		MedicalClinicUserInterface ui = new MedicalClinicUserInterface();
		ui.menu();
	}

	public void menu() {
		boolean quit = false;

		while(!quit) {
			System.out.println("Please make a choice:\n" +
				"1 Enter a new patient\n" +
				"2 Make an appointment for a patient\n"+
				"3 Cancel an appointment for a patient\n"+
				"4 List all appointments\n"+
				"5 Quit");
			try {
				switch (in.nextInt()) {
				case MedicalClinicUserInterface.ADD_PATIENT:
					addPatient();
					break;
				case MedicalClinicUserInterface.ADD_APPOINTMENT:
					addAppointment();
					break;
				case MedicalClinicUserInterface.CANCEL_APPOINTMENT:	
					cancelAppointment();
				case MedicalClinicUserInterface.LIST_APPOINTMENT:
					listAppointments();
					break;
				case MedicalClinicUserInterface.QUIT:
					quit=true;
	//				System.out.println("Goodbye");
					break;
				case 9: //
					printPatients();
					break;
				default:
					break;
				}
			} 
			catch(MedicalClinicException e) {
				e.printStackTrace();
				System.out.println("Still executing ...");
			}
		}
	}

	// add a new patient
	public void addPatient() {
		// get first name
		System.out.print("Enter first name: ");
		String firstName = in.next();
		// get last name
		System.out.print("Enter last name: ");
		String lastName = in.next();
		System.out.println("");
		// get health card #	
		System.out.print("Enter healthcard number: ");
		String healthCardNumber = in.next();
		
		// get birth date
		System.out.print("Enter birth date DDMMYYYY: ");
		String strBirthDate = in.next();
		
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		
		int birthDay, birthMonth, birthYear;
		// do validation & convertion from String to OurDate
		{
			Date date;
			try {
				date = formatter.parse(strBirthDate);
			}
			catch (ParseException e) {
				System.out.println("invalid date!");
				return ;
		    }
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			birthDay = calendar.get(Calendar.DATE);
			birthMonth = calendar.get(Calendar.MONTH) + 1;
			birthYear = calendar.get(Calendar.YEAR);
		}

		// get patient type
		System.out.println("Enter the type of patient:");
		System.out.println("1. Maternity Patient");
		System.out.println("2. OutPatient");
		System.out.println("3. Regular Patient");
		int patientType = in.nextInt();
		System.out.println("");
		
		if(patientType == 1) {
			// add a Maternity Patient
			System.out.print("Enter due date DDMMYYYY: ");
			String strDueDate = in.next();
			System.out.print("Need nutrition testing: ");
			String strYesNo = in.next();
			
			// parse date
			Date date;
			try {
				date = formatter.parse(strDueDate);
			}
			catch (ParseException e) {
				System.out.println("invalid date!");
				return ;
		    }
			// convert 
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int dueDay = calendar.get(Calendar.DATE);
			int dueMonth = calendar.get(Calendar.MONTH) + 1;
			int dueYear = calendar.get(Calendar.YEAR);
		
			boolean nt = strYesNo == "yes" ? true : false;
			
			clinic.addPatient(firstName, lastName, healthCardNumber, new OurDate(birthDay, birthMonth, birthYear), 
					new OurDate(dueDay, dueMonth, dueYear), nt);			
		} else if (patientType == 2) {
			// add an OutPatient
			System.out.println("Mobility:");
			String strMobility = in.next();
			System.out.println("distance from clinic:");
			double distanceFromClinic = in.nextDouble();
			boolean mobility = 	strMobility == "yes" ? true : false;
			clinic.addPatient(firstName, lastName, healthCardNumber, 
					new OurDate(birthDay, birthMonth, birthYear), mobility, distanceFromClinic);
		} else if (patientType == 3) {
			// Regular a Patient
			clinic.addPatient(lastName, firstName, healthCardNumber, new OurDate(birthDay, birthMonth, birthYear), patientType);
		}
	}

	public void addAppointment() {
		System.out.print("Enter health card number:");
		String healthCardNumber = in.next();

		Patient patient = null;
		// try to find patient from health card number
		{
			Iterator<Patient> i = clinic.getPatients().iterator();
			while(i.hasNext()) {
				Patient p = i.next();
				if (p.getHealthCardNumber().equals(healthCardNumber)) {
					patient = p;
					break;
				}
			}
		}
		// give up if no patient found
		if (patient == null) {
			System.out.println("Invalid health card");
			return;
		}

		// output patient information.
		System.out.println(patient.toString());
		System.out.println("");
		printDoctors();
		
		System.out.print("Enter number for doctor selection:");
		int doctorIndex = in.nextInt();
		int doctorNum = clinic.getDoctors().size();
		// 
		if (doctorIndex <= 0 || doctorIndex > doctorNum) {
			System.out.print("Invalid doctor selected");
			return ;
		}
		
		Doctor doctor = clinic.getDoctors().get(doctorIndex-1);
			
		System.out.print("\nEnter desired appointment date DDMMYYYY:");
		String strDate = in.next();
		// parse date
		int day, month, year;
		{
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");

			Date date;
			try {
				date = formatter.parse(strDate);
			}
			catch (ParseException e) {
				System.out.println("invalid date!");
				return ;
		    }
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			day = calendar.get(Calendar.DATE);
			month = calendar.get(Calendar.MONTH) + 1;
			year = calendar.get(Calendar.YEAR);
		}
		OurDate appointmentDate = new OurDate(day, month, year);
		clinic.addAppointment(patient, doctor, appointmentDate);
		System.out.print("");	
	}
	public void cancelAppointment() {
		System.out.print("Enter health card number:");
		String healthCardNumber = in.next();

		Patient patient = null;
		// try to find patient from health card number
		{
			Iterator<Patient> i = clinic.getPatients().iterator();
			while(i.hasNext()) {
				Patient p = i.next();
				if (p.getHealthCardNumber().equals(healthCardNumber)) {
					patient = p;
					break;
				}
			}
		}
		if (patient == null) {
			System.out.println("no appointment found ");
			return ;
		}
		System.out.println(patient.toString());
		System.out.println("");

		printDoctors();
		System.out.print("Enter number for doctor selection: ");
		int doctorIdx = in.nextInt();
		int doctorNum = clinic.getDoctors().size();
		// 
		if (doctorIdx <= 0 || doctorIdx > doctorNum) {
			System.out.println("Invalid doctor selected");
			return ;
		}
		Doctor doctor = clinic.getDoctors().get(doctorIdx-1);
		
		System.out.print("Enter appointment date DDMMYYYY:");
		String strDate = in.next();
		int day, month, year;
		{
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");

			Date date;
			try {
				date = formatter.parse(strDate);
			}
			catch (ParseException e) {
				System.out.println("invalid date!");
				return ;
		    }
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			day = calendar.get(Calendar.DATE);
			month = calendar.get(Calendar.MONTH) + 1;
			year = calendar.get(Calendar.YEAR);
		}
		OurDate appointmentDate = new OurDate(day, month, year);

		// try to find out a matched appointment
		int appointmentIdx = -1;
		{
			int idx = 0;
			Appointment appoint = new Appointment(patient, doctor, appointmentDate);
			Iterator<Appointment> i = clinic.getAppointments().iterator();
			while(i.hasNext()) {
				Appointment a = i.next();
				if (a.equals(appoint)) {
					appointmentIdx = idx;
					break;
				}
				idx ++;
			}
			
		}
		if (appointmentIdx != -1)
			clinic.cancelAppointment(appointmentIdx);
		System.out.println("");
	}
	//print appointment list.
	public void listAppointments() {
		Iterator<Appointment> i = clinic.getAppointments().iterator();
		while(i.hasNext()) {
			Appointment a = i.next();
			System.out.println("Appointment [" + a.toString() + "]");
		}
		System.out.println("");
	}
	//print doctors list.
	public void printDoctors() {
		int idx = 1;
		Iterator<Doctor> i = clinic.getDoctors().iterator();
		while(i.hasNext()) {
			Doctor d = i.next();
			System.out.println(idx + " " + d.toString());
			idx ++;
		}
	}
	public void printPatients() {
		Iterator<Patient> i = clinic.getPatients().iterator();
		while(i.hasNext()) {
			Patient p = i.next();
			System.out.println( "Patient [" + p.toString() + "]");
		}
		System.out.println("");
	}
	
}
