package carparksystem.controls;

import java.util.ArrayList;
import carparksystem.entities.*;

/** 
 * This is a control class providing the control for
 * setting some park status.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class SettingControl {
	
	/**
	 * This method is to view the list of non-working-day 
	 * which are set by the user.
	 *
	 *@return String Representation of a Non-working-day list.
	 */
	public ArrayList<String> viewNonworkingday() {
		Nonworkingdays nwd = new Nonworkingdays();
		return nwd.getNonworkingdaylist();
	}
	
	/**
	 * This method is to add a non-working-day to the system by 
	 * the user, in these days, public can go inside the park.
	 * 
	 * @param day The non-working-day.
	 */
	public void addNonworkingday(String day) {
		Nonworkingdays nwd = new Nonworkingdays();
		nwd.addNonworkingday(day);
	}
	
	/**
	 * This method is to delete a non-working-day 
	 * to the system by the user.
	 * 
	 * @param day The non-working-day.
	 */
	public void delNonworkingday(String day) {
		Nonworkingdays nwd = new Nonworkingdays();
		nwd.delNonworkingday(day);
	}
	
	/**
	 * This method is used to fill up tickets 
	 * by the administrator.
	 * 
	 * @param num The number of the tickets.
	 */
	public void setTicketNum(int num) {
		ParkStatus ps = new ParkStatus();
		ps.setTicketNum(num);
	}
	
	/**
	 * This methods is used to take all the coins in 
	 * the coin box by the administrator.
	 */
	public void cleanCionNum() {
		ParkStatus ps = new ParkStatus();
		ps.setCoinNum(0);
	}
	
	/**
	 * This method is used to display the remaining 
	 * spaces in the park.
	 * 
	 * @return int The remaining space.
	 */
	public int displaySpaces() {
		ParkStatus ps = new ParkStatus();
		return ps.getSpaceNum();
	}
	
	/**
	 * This method is used to display the number of 
	 * tickets left in the ticket box.
	 * 
	 * @return int The number of the tickets left.
	 */
	public int displayLeftBlankTicket() {
		ParkStatus ps = new ParkStatus();
		return ps.getTicketNum();
	}
	
	/**
	 * This method is used to display the coins 
	 * left in the coin box.
	 * 
	 * @return double The coins in the coin box.
	 */
	public double diaplayLeftCoin() {
		ParkStatus ps = new ParkStatus();
		return ps.getCoinNum();
	}
}
