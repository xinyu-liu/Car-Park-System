package carparksystem.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/** 
 * This is an entity class storing the information of 
 * non-working-day which are set by the administrator.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class Nonworkingdays {
	// MM-dd-yyyy
	private ArrayList<String> nonworkingdaylist;
	private File nonworkingdayFile = new File("nonworkingday.txt");

	/**
	 * Constructor to initialize a Nonworkingdays
	 */
	public Nonworkingdays() {
		nonworkingdaylist = new ArrayList<String>();	
		if (nonworkingdayFile.exists()) {
			try {
				// Open the file 
				FileReader fr = new FileReader(nonworkingdayFile);
				BufferedReader br = new BufferedReader(fr);
				boolean hasNext = true;
				// Read the file 
				while (hasNext) {
					String nextLine = br.readLine();
					if (nextLine != null ) {
						String date = nextLine;
						nonworkingdaylist.add(date);
					} else {
						hasNext = false;
					}
				}	
				
				// Close the file
				br.close();
				fr.close();
			} catch (Exception e) {
				System.out.printf("Can't read the file!\n");
				e.printStackTrace();
				System.exit(1);
			}
		} else 
			synchronize();
	}
	
	/**
	 * This method is used to make the data is displaying on 
	 * the screen is the same as the data storing in the file.
	 */
	private void synchronize() {
		try {
			// Open the file
			FileWriter fw = new FileWriter(nonworkingdayFile);
			PrintWriter pw = new PrintWriter(fw);

			for (String d : nonworkingdaylist) {	
				pw.write(d + "\r\n");
			}
			
			// Close the file
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			System.out.printf("Can't write the config file!\n");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * This method is used to check whether it 
	 * is non-working-day or not.
	 * 
	 * @param day The current date.
	 * @return boolean Whether it is non-working-day or not.
	 */
	public boolean isNonworkingday(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		for (String str : nonworkingdaylist) {
			String[] tokenLine = str.split("-"); 
			// If the date is holiday, return true
			if (month == Integer.parseInt(tokenLine[0]) &&
					dayOfMonth ==  Integer.parseInt(tokenLine[1]) &&
							year == Integer.parseInt(tokenLine[2]))
				return true;
		}
		
		return false;
	}
	
	/**
	 * This method is used to add a non-working-day by the administrator.
	 * 
	 * @param day The date which want to add to the list.
	 */
	public void addNonworkingday(String day) {
		nonworkingdaylist.add(day);     // add a date to the list
		synchronize();
	}
	
	/**
	 * This method is used to delete a non-working-day by the administrator.
	 * 
	 * @param day The date which want to delete from the list.
	 */
	public void delNonworkingday(String day) {
		for (int i=0; i < nonworkingdaylist.size(); i++) {
			String line = nonworkingdaylist.get(i);
			if (line.equals(day))
				nonworkingdaylist.remove(i);   // delete a date from the list
		}
		synchronize();
	}
	
	/**
	 * This method is used to view the non-working-day list.
	 * @return ArrayList<> The list of non-working-day. 
	 */
	public ArrayList<String> getNonworkingdaylist() {
		return nonworkingdaylist;
	}
}
