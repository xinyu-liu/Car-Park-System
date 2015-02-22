package carparksystem.entities;

import java.io.File;
import java.util.ArrayList;

/** 
 * This is an entity class storing the 
 * information of staff account.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class StaffAccount {
	private int id;
	private String name;
	private String campusCardNum;
	private String email;
	private String phoneNum;
	private File staffAccountFile = new File("staff_account.txt");
	private DBUtil dbUtil = new DBUtil(staffAccountFile);
	
	/**
	 * This method is used to create a staff account.
	 * 
	 * @param name The name of the staff.
	 * @param campusCardNum The campus card number of the staff.
	 * @param email The e-mail address of the staff.
	 * @param phoneNum The phone number of the staff.
	 * @return StaffAccount An account of a particular staff.
	 */
	public StaffAccount create(String name, String campusCardNum, String email, String phoneNum) {
		StaffAccount sa = new StaffAccount();
		sa.setName(name);
		sa.setCampusCardNum(campusCardNum);
		sa.setEmail(email);
		sa.setPhoneNum(phoneNum);
		int id = dbUtil.insert(toStrings(sa));
		sa.setId(id);
		
		return sa;
	}
	
	/**
	 * This method is used to update the change to the account.
	 */
	public void modify() {
		dbUtil.update(toStrings(this));
	}
	
	/**
	 * This method is used to get a staff account 
	 * information by campus card number.
	 * 
	 * @param campusCardNum The campus card number of the staff.
	 * @return StaffAccount An account of a particular staff.
	 */
	public StaffAccount getByCampusCard(String campusCardNum) {
		return toStaffAccount(dbUtil.get(2, campusCardNum));
	}
	
	/**
	 * This method is used to get all registered staff accounts.
	 * 
	 * @return ArrayList<> All accounts of staff.
	 */
	public ArrayList<StaffAccount> getAll() {
		ArrayList<String[]> list = dbUtil.getAll();
		ArrayList<StaffAccount> saList  
						= new ArrayList<StaffAccount>();		
		for (String[] line : list) {
			saList.add(toStaffAccount(line));
		}
		
		return saList;
	}
	
	/**
	 * This method is used to set staff accounts.
	 * 
	 * @param line The line number of staff account in the file.
	 * @return StaffAccount An account of a staff.
	 */
	private StaffAccount toStaffAccount(String[] line) {
		if (line == null) 
			return null;
		
		StaffAccount sa = new StaffAccount();
		
		sa.setId(Integer.parseInt(line[0]));
		sa.setName(line[1]);
		sa.setCampusCardNum(line[2]);
		sa.setEmail(line[3]);
		sa.setPhoneNum(line[4]);
		
		return sa;
	}
	
	/**
	 * This method is used to store a staff account 
	 * to a file in string format.
	 * 
	 * @param sa The account of a staff.
	 * @return String[] String representation of staff account.
	 */
	private String[] toStrings(StaffAccount sa) {
		String[] line = new String[5];
		line[0] = "" + sa.getId();
		line[1] = sa.getName();
		line[2] = sa.getCampusCardNum();
		line[3] = sa.getEmail();
		line[4] = sa.getPhoneNum();
		
		return line;
	}
	
	// Get and set method
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCampusCardNum() {
		return campusCardNum;
	}
	public void setCampusCardNum(String campusCardNum) {
		this.campusCardNum = campusCardNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}
