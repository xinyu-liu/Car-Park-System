package carparksystem.controls;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import carparksystem.entities.*;

/** 
 * This is a control class providing the 
 * control for entering the park.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class InControl {
	
	/**
	 * This method is used to check whether there are 
	 * remaining space or not.
	 * 
	 * @return boolean Whether staff and public can park.
	 */
	public boolean checkLeftSpaces() {
		ParkStatus ps = new ParkStatus();
		if (ps.getSpaceNum() > 0)          // there are remaining spaces
			return true;
		else 
			return false;
	}
	
	/**
	 * This method is used to check whether it is the 
	 * day which public can park or not.
	 * 
	 * @return boolean Whether public can park.
	 */
	public boolean checkNonworkingday() {
		Date time = new  Date(System.currentTimeMillis());
		Nonworkingdays nwd = new Nonworkingdays();
		
		// check if it is weekends or holidays or summer time
		if (nwd.isNonworkingday(time) ||
				isWeekend(time)) 
			return true;          
		else
			return false;
	}
	
	/**
	 * The method is used to check whether it is weekday or weekend.
	 * 
	 * @param date The parking day.
	 * @return boolean Whether public can park.
	 */
	private boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);  // get the day of a week
		
		if (dayOfWeek == Calendar.SATURDAY 
				|| dayOfWeek == Calendar.SUNDAY)    // today is weekend
			return true;
		else 
			return false;
	}
	
	/**
	 * This method is used to check whether there is 
	 * tickets left in the ticket box or not.
	 * 
	 * @return boolean Whether public can park.
	 */
	public boolean checkBlankTicket() {
		ParkStatus ps = new ParkStatus();
		if (ps.getTicketNum() > 0)    // there are tickets left in the ticket box
			return true;
		else
			return false;
	}
	
	/**
	 * This method is used to check whether the 
	 * staff account is valid or not.
	 * 
	 * @param campusCardNum The campus card number of the staff.
	 * @return boolean Whether staff can park.
	 */
	public boolean checkStaffAccount(String campusCardNum) {
		StaffAccount sa = new StaffAccount();
		if (sa.getByCampusCard(campusCardNum) == null)   // account has not register
			return false;
		else 
			return true;	
	}
	
	/**
	 * This method is used to generate a 3-digit 
	 * random number for public.
	 * 
	 * @return String String representation of the public number.
	 */
	public String generatePublicNum() {
		Random rand = new Random();
		int temp = Math.abs(rand.nextInt());
		return "" + (temp%900 + 100);       // generate a 3-digit random number
	}
	
	/**
	 * This method is used to add staff record of enter time.
	 * 
	 * @param campusCardNum The campus card number of the staff.
	 */
	public void addStaffRecord(String campusCardNum) {
		StaffRecord sr = new StaffRecord();
		Date enterTime = new  Date(System.currentTimeMillis());
		sr.create(campusCardNum, enterTime);   // create a record for staff
	}
	
	/**
	 * This method is used to add public record of enter time.
	 * 
	 * @param publicNum The 3-digit random which are 
	 *                  generated to the public.
	 */
	public void addPublicRecord(String publicNum) {
		PublicRecord pr = new PublicRecord();
		Date enterTime = new  Date(System.currentTimeMillis());
		pr.create(publicNum, enterTime);     // create a record for public
		
		ParkStatus ps = new ParkStatus();
		ps.setTicketNum(ps.getTicketNum()-1);   // subtract 1 to the tickets left
	}
	
	/**
	 * This method is used to open the entrance barrier of the 
	 * park for staff and public to go inside.
	 */
	public void openBarrier() {
		System.out.printf("The entrance barrier is open!\n");    // reserve for further connect to the barrier
		ParkStatus ps = new ParkStatus();
		ps.setSpaceNum(ps.getSpaceNum()-1);   // subtract 1 to the remaining space
	}
}
