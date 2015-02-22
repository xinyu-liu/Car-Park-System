package carparksystem.controls;

import java.util.ArrayList;
import carparksystem.entities.StaffAccount;

/** 
 * This is a control class providing the control for
 * managing staff account.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class AccountControl {
	
	/**
	 * This method is to view all the staff account 
	 * which are already in the system.
	 * 
	 * @return ArrayList<> All the staff account information.
	 */
	public ArrayList<StaffAccount> viewStaffAccount() {
		StaffAccount sa = new StaffAccount();
		return sa.getAll();     // get details of staff accounts
	}
	
	/**
	 * This method is to add a new staff 
	 * account to the park system.
	 * 
	 * @param name The name of the staff.
	 * @param campusCardNum The campus number of the staff.
	 * @param email The e-mail address of the staff.
	 * @param phoneNum The phone number of the staff.
	 */
	public void addStaffAccount(String name, String campusCardNum, String email, String phoneNum) {
		StaffAccount sa = new StaffAccount();
		sa.create(name, campusCardNum, email, phoneNum);      // create staff account
	}
	
	/**
	 * This method is to modify the account information.
	 * 
	 * @param id The index of the account.
	 * @param name The name of the staff.
	 * @param campusCardNum The campus number of the staff.
	 * @param email The e-mail address of the staff.
	 * @param phoneNum The phone number of the staff.
	 */
	public void modifyStaffAccount(int id, String name, String campusCardNum, String email, String phoneNum) {
		StaffAccount sa = new StaffAccount();
		sa.setId(id);
		sa.setName(name);
		sa.setCampusCardNum(campusCardNum);
		sa.setEmail(email);
		sa.setPhoneNum(phoneNum);
		sa.modify();         // modify staff account
	}
}
