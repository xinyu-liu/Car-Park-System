package carparksystem.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/** 
 * This is an entity class storing the 
 * information of staff record.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class StaffRecord extends Record{
	private String campusCardNum;
	private File staffRecordFile = new File("staff_record.txt");
	private DBUtil dbUtil = new DBUtil(staffRecordFile);
	
	/**
	 * This method is used to add a staff record.
	 * 
	 * @param campusCardNum The campus card number of the staff.
	 * @param enterTime The enter time of the staff.
	 * @return StaffRecord A record of a particular staff.
	 */
	public StaffRecord create(String campusCardNum, Date enterTime) {
		StaffRecord sr = new StaffRecord();
		sr.setCampusCardNum(campusCardNum);
		sr.setEnterTime(enterTime);
		int id = dbUtil.insert(toStrings(sr));
		sr.setId(id);
		
		return sr;
	}

	/**
	 * This method is used to get a staff record by campus card number.
	 * 
	 * @param campusCardNum The campus card number of the staff.
	 * @return ArrayList<> A record of a particular staff.
	 */
	public ArrayList<StaffRecord> getByCampusCard(String campusCardNum) {
		ArrayList<String[]> list = dbUtil.query(1, campusCardNum);
		ArrayList<StaffRecord> srList  
						= new ArrayList<StaffRecord>();		
		for (String[] line : list) {
			srList.add(toStaffRecord(line));
		}
		
		return srList;
	}
	
	/**
	 * This method is used to get all records of staff.
	 * 
	 * @return ArrayList<> All records of staff.
	 */
	public ArrayList<StaffRecord> getAll() {
		ArrayList<String[]> list = dbUtil.getAll();
		ArrayList<StaffRecord> srList  
						= new ArrayList<StaffRecord>();		
		for (String[] line : list) {
			srList.add(toStaffRecord(line));
		}
		
		return srList;
	}
	
	/**
	 * This method is used to store depart time to a file.
	 * 
	 * @param departTime The time departing the park.
	 */
	public void store(Date departTime) {
		setDepartTime(departTime);
		dbUtil.update(toStrings(this));
	}
	
	
	/**
	 * This method is used to set staff records.
	 * 
	 * @param line The line number of staff record in the file.
	 * @return StaffRecord A record of a staff.
	 */
	private StaffRecord toStaffRecord(String[] line) {
		StaffRecord sr = new StaffRecord();
		sr.setId(Integer.parseInt(line[0]));
		sr.setCampusCardNum(line[1]);
		if (line[2].length() > 0)
			sr.setDepartTime(stringToTime(line[2]));
		sr.setEnterTime(stringToTime(line[3]));

		return sr;
	}
	
	/**
	 * This method is used to store a staff record 
	 * to a file in string format.
	 * 
	 * @param sr The record of staff.
	 * @return String[] String representation of staff record.
	 */
	private String[] toStrings(StaffRecord sr) {
		String[] line = new String[4];
		line[0] = "" + sr.getId();
		line[1] = sr.getCampusCardNum();
		if (sr.getDepartTime() != null) 
			line[2] = timeToString(sr.getDepartTime());
		else 
			line[2] = "";
		line[3] = timeToString(sr.getEnterTime());
		
		return line;
	}
	
	// Get and set method
	public String getCampusCardNum() {
		return campusCardNum;
	}

	public void setCampusCardNum(String campusCardNum) {
		this.campusCardNum = campusCardNum;
	}
}
