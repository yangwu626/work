/* File Name: OurDate.
 * Course Name: 18F_CST8284 Java
 * Lab Section: 314
 * Student Name: Yang Wu
 * Date: 18/10/2018
 */
package assign4;
import java.io.Serializable;
import java.util.Calendar;

//OurDate class use calendar for calendar date expression and converted.
public class OurDate implements Serializable{

	/**
	 * first version
	 */
	private static final long serialVersionUID = 1L;

	//declaration class fields: CALENDER,day,month and year.
	private final static Calendar CALENDER = Calendar.getInstance();

	private int day;
	private int month;
	private int year;

	// constructor.
	// set initial value from parameters.
	OurDate(int day,int month,int year){
		setYear(year);
		setMonth(month);
		setDay(day);
	}
	// default constructor.
	// use current day/month/year as default value.
	OurDate(){
		// chain constructor
		this( CALENDER.get(Calendar.DAY_OF_MONTH),
			  CALENDER.get(Calendar.MONTH) + 1,
			  CALENDER.get(Calendar.YEAR));
	}

	//getDay method.
	public int getDay() {
		return day;
	}
	//get month method.
	public int getMonth() {
		return month;
	}
	//get year method.
	public int getYear() {
		return year;
	}
	// set Day method.
	private void setDay(int day) {
		boolean leap_year = false; 
		if ((year % 4 == 0 && year % 100 != 0) ||
		    (year % 400 == 0 && year % 3200 != 0)) {
			leap_year = true;
		}
		// up boundary of days for each month
		int up[] = { -1, 31, 28, 31, 30, 31, 30, 31, 31, 30,31, 30, 31 };
		if (leap_year)
			up[2] = 29;
		if (day < 1 || day > up[month]) {
			throw new MedicalClinicException("Invalide day!", null);
		}
		this.day = day;
	}
	// set month method.
	private void setMonth(int month) {
		if (month < 1 || month > 12) {
			throw new MedicalClinicException("Invalide month!", null);
		}
		this.month = month;
	}
	//set year method.
	private void setYear(int year) {
		this.year = year;
	}

	// Override. Specifiy date outline string format.
	@Override
	public String toString() {
		return "day=" + day + ",month="  + month + ",year=" + year;
	}

	// hashCode method.
	@Override
	public int hashCode() {
		// design hashCode algorithm like this:
		// H = (y * 13 + m) * 32 + d
		// so if two hashCode are same, for example H1 == H2,
		// suppose H1 = (y1 * 13 + m1) * 32 + d1
		// suppose H2 = (y2 * 13 + m2) * 32 + d2
		// if H1 == H2 ===> H1/32 == H2/32 ===> d1 == d2
		// also we have (H1-d1)/32 == (H2-d2) ===> y1 * 13 + m1 == y2 * 13 + m2
		// then we have y1 == y2 and m1 == m2.
 		// so we have a perfect hashcode here, there is no collision at all as long d/m/y are in valid range.
		return ((year * 13 + month) * 32 + day);
	}

	// equals method to compare if two dates are equal.
	@Override
	public boolean equals(Object obj) {
		// return false if obj is null or not an instanced of OurDate.
		if(obj == null || !(obj instanceof OurDate))
			return false;
		// instance obj to OurDate and compare.
		OurDate date = (OurDate) obj;
		// return true if all the d/m/y are match.
		return day == date.getDay() &&
			month == date.getMonth() &&
			year == date.getYear();
	}
}
