package carparksystem.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/** 
 * This is an entity class storing the 
 * information of public record.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class PublicRecord extends Record {
	private String publicNum;
	private File publicRecordFile = new File("public_record.txt");
	private DBUtil dbUtil = new DBUtil(publicRecordFile);
	
	
	/**
	 * This method is used to add a public record.
	 * 
	 * @param publicNum The 3-digit random public number.
	 * @param enterTime The enter time of the public.
	 * @return PublicRecord A public record.
	 */
	
	public PublicRecord create(String publicNum, Date enterTime) {
		PublicRecord pr = new PublicRecord();
		pr.setPublicNum(publicNum);
		pr.setEnterTime(enterTime);
		int id = dbUtil.insert(toStrings(pr));
		pr.setId(id);

		return pr;
	}

	/**
	 * This method is used to get public record by public number.
	 * 
	 * @param publicNum The 3-digit random public number.
	 * @return PublicRecord A public record.
	 */
	public PublicRecord getByPublicNum(String publicNum) {
		String[] line = dbUtil.get(1, publicNum);
		return toPublicRecord(line);
	}
	
	/**
	 * This method is used to get all records of public.
	 * 
	 * @return ArrayList<> All records of public.
	 */
	public ArrayList<PublicRecord> getAll() {
		ArrayList<String[]> list = dbUtil.getAll();
		ArrayList<PublicRecord> prList  
						= new ArrayList<PublicRecord>();		
		for (String[] line : list) {
			PublicRecord pr = toPublicRecord(line);
			prList.add(pr);
		}
		
		return prList;
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
	 * This method is used to store public payment to a file.
	 * @param payment The money to a public record.
	 */
	public void store(double payment) {
		setPayment(payment);
		dbUtil.update(toStrings(this));
	}
	
	/**
	 * This method is used to set public records.
	 * 
	 * @param line The line number of staff record in the file.
	 * @return PublicRecord A public record.
	 */
	private PublicRecord toPublicRecord(String[] line) {
		PublicRecord pr = new PublicRecord();
		if (line == null)
			return null;
		
		pr.setId(Integer.parseInt(line[0]));
		pr.setPublicNum(line[1]);
		pr.setEnterTime(stringToTime(line[2]));
		if (line[3].length() > 0)
			pr.setDepartTime(stringToTime(line[3]));
		pr.setPayment(Double.parseDouble(line[4]));
		
		return pr;
	}
	
	/**
	 * This method is used to store a staff record 
	 * to a file in string format.
	 * 
	 * @param pr The record of public.
	 * @return String[] String representation of public record.
	 */
	private String[] toStrings(PublicRecord pr) {
		String[] line = new String[5];
		line[0] = "" + pr.getId();
		line[1] = pr.getPublicNum();
		line[2] = timeToString(pr.getEnterTime());
		if (pr.getDepartTime() != null) 
			line[3] = timeToString(pr.getDepartTime());
		else 
			line[3] = "";
		line[4] = "" + pr.getPayment();
		
		return line;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getId() + "||" +  publicNum  + "||" +  getEnterTime() 
				 + "||" +  getDepartTime()  + "||" +  getPayment() + "\n";
	}
	
	
	// Get and set methods
	public String getPublicNum() {
		return publicNum;
	}

	public void setPublicNum(String publicNum) {
		this.publicNum = publicNum;
	}
}
