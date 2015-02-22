package carparksystem.controls;

import java.util.ArrayList;
import java.util.Date;

import carparksystem.entities.ParkStatus;
import carparksystem.entities.PublicRecord;
import carparksystem.entities.StaffRecord;

/** 
 * This is a control class providing the control for
 * departing the park.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class OutControl {
	
	/**
	 * This method is used to check whether the 
	 * campus card is valid or not.
	 * 
	 * @param compusCardNum The campus card number of the staff.
	 * @return boolean Whether staff can go out.
	 */
	public boolean checkStaffCar(String campusCardNum) {
		StaffRecord sr = new StaffRecord();
		ArrayList<StaffRecord> srList = 
				sr.getByCampusCard(campusCardNum); 
		
		for (StaffRecord s : srList) {
			if (s.getDepartTime() == null) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * This method is used to check whether there is 
	 * tickets left in the ticket box or not.
	 * 
	 * @param publicNum The 3-digit random which are 
	 *                  generated to the public
	 * @return boolean Whether public can go out.
	 */
	public boolean checkTicket(String publicNum) {
		PublicRecord pr = new PublicRecord();
		pr = pr.getByPublicNum(publicNum);
		if (pr == null)       // cannot find the park record
			return false;     // cannot go outside
		
		if (pr.getDepartTime() == null) 
			return true;
		
		return false;
	}
	
	/**
	 * This method is used to record the depart time of staff.
	 * 
	 * @param campusCardNum The campus card number of staff.
	 */
	public void recordStaffExitTime(String campusCardNum) {
		StaffRecord sr = new StaffRecord();
		ArrayList<StaffRecord> srList = 
				sr.getByCampusCard(campusCardNum);
		sr = srList.get(srList.size()-1);
		Date departTime = new  Date(System.currentTimeMillis());
		sr.store(departTime);      // store depart time for staff
	}
		
	/**
	 * This method is used to record the depart time of public.
	 * 
	 * @param publicNum The 3-digit random which are 
	 *                  generated to the public.
	 */
	public void recordPublicExitTime(String publicNum) {
		PublicRecord pr = new PublicRecord();
		pr = pr.getByPublicNum(publicNum);
		Date departTime = new  Date(System.currentTimeMillis());
		pr.store(departTime);   // store depart time for public
	}
	
	/**
	 * This method is used to record the 
	 * parking time of public in hours.
	 * 
	 * @param publicNum The 3-digit random which are 
	 *                  generated to the public.
	 * @return double The parking hours.
	 */
	public double calculateHours(String publicNum) {
		PublicRecord pr = new PublicRecord();
		pr = pr.getByPublicNum(publicNum);         // for a particular public
		
		long between = pr.getDepartTime().getTime() 
				- pr.getEnterTime().getTime();     // get the parking time
		double hours = between/(1000 * 60 * 60.0); // convert minutes to hours
		
		return hours;
	}
	
	/**
	 * This method is used to calculate parking price for public.
	 * 
	 * @param publicNum The 3-digit random which are 
	 *                  generated to the public.
	 * @return double The payment of the public.
	 */
	public double calculatePrice(String publicNum) {
		double hours = calculateHours(publicNum);
		double payment;
		
		//calculate price
		if (hours < 2) 
			payment = 0.5;
		else if (hours < 4)
			payment =  1;
		else if (hours < 8)
			payment =  2;
		else if (hours < 12)
			payment =  3;
		else 
			payment =  5;
		
		PublicRecord pr = new PublicRecord();
		pr = pr.getByPublicNum(publicNum);
		pr.store(payment);    // store payment to the file of public record
		
		return payment;
	}
	
	/**
	 * This method is used to add coins to the payment station.
	 * 
	 * @param price The money which are payed for the public.
	 */
	public void addCoins(double price) {
		ParkStatus ps = new ParkStatus();
		ps.setCoinNum(ps.getCoinNum()+price);	
	}
	
	/**
	 * This method is used to open the exit barrier of the 
	 * park for staff and public to go outside.
	 */
	public void openBarrier() {
		System.out.printf("The exit barrier is open!\n"); // reserve for further connect to the barrier
		ParkStatus ps = new ParkStatus();
		ps.setSpaceNum(ps.getSpaceNum()+1); // add 1 to the remaining space
	}
}
