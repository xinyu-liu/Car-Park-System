package carparksystem.controls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import carparksystem.entities.*;

/** 
 * This is a control class providing the control for
 * generating monthly report.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class ReportControl {
	
	/**
	 * This method is used to view all the records of staff.
	 * 
	 * @return ArrayList<> All the staff records.
	 */
	public ArrayList<StaffRecord> viewStaffRecords() {
		StaffRecord sr = new StaffRecord();
		return sr.getAll();   // get all the staff record
	}
	
	/**
	 * This method is used to view all the records of public.
	 * 
	 * @return ArrayList<> All the public records.
	 */
	public ArrayList<PublicRecord> viewPublicRecords() {
		PublicRecord pr = new PublicRecord();
		return pr.getAll();   // get all the public record
	}
		
	/**
	 * This method is used for generate monthly report for staff.
	 * 
	 * @param campusCardNum The campus card number for staff.
	 * @param month The monthly report.
	 * @return double The overall payment of a particular staff in a month.
	 */
	public double generateReport(String campusCardNum,  String month) {
		int parkingDay = 0;
		StaffRecord sr = new StaffRecord();
		ArrayList<StaffRecord> srList = sr.getByCampusCard(campusCardNum);
		
		Date lastDepartTime = null;
		for (StaffRecord s : srList) {
			// only pick the complete record 
			if (s.getDepartTime() == null) {
				continue;
			}
			
			// only pick the records for a particular month
			if (toMonth(s.getDepartTime()).equals(month)) {
				// calculate the parking days
				parkingDay += 
						getIntervalDay(s.getEnterTime(), s.getDepartTime());
				
				// if park more than once within a day, only charge once
				if (lastDepartTime != null && toDay(s.getEnterTime()).equals(toDay(lastDepartTime)))		
					parkingDay--;
					
				lastDepartTime = s.getDepartTime();
			}	
		}	
		return parkingDay * 1;  // calculate the price
	}
		
	/**
	 * This method is used to store a string of a particular day.
	 * 
	 * @param time A date format.
	 * @return String String representation of a particular day.
	 */
	private String toDay(Date time) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		return bartDateFormat.format(time);
	}
	
	/**
	 * This method is used to store a string of a particular month.
	 * 
	 * @param time A date format.
	 * @return String String representation of a particular month.
	 */
	private String toMonth(Date time) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-yyyy");
		return bartDateFormat.format(time);
	}
	
	/**
	 * This method is used to calculate the parking time.
	 * 
	 * @param enterTime The enter time of the car.
	 * @param departTime The depart time of the car
	 * @return int The parking time.
	 */
	private int getIntervalDay(Date enterTime, Date departTime) {
		Calendar cNow = Calendar.getInstance();
	    Calendar cReturnDate = Calendar.getInstance();
	    cNow.setTime(enterTime);   // set the enter time
	    cReturnDate.setTime(departTime);   // set the depart time
	    setTimeToMidnight(cNow);
	    setTimeToMidnight(cReturnDate);
	    long todayMs = cNow.getTimeInMillis();
	    long returnMs = cReturnDate.getTimeInMillis();
	    long intervalMs = todayMs - returnMs;
	    return (int) (intervalMs / (1000 * 86400)) + 1;    // return time in hour
	}
	
	/**
	 * This method is used to adjust the time.
	 * 
	 * @param calendar The Calendar format.
	 */
	private void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}
	
	/**
	 * This method is used to send monthly 
	 * report to the QM financial system.
	 */
	public void sendReport() {
		// reserve for future connection to financial system
		System.out.printf("The report has been sent out!");  
	}
}
