package carparksystem.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/** 
 * This is an entity class storing the 
 * information of administrator.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class Admin {
	private String username = "admin";
	private String password = "admin";
	private File adminFile = new File("admin.txt");
	
	/**
	 * Constructor to initialize an Admin
	 */
	public Admin() {
		if (adminFile.exists()) 
			try {
				//Open the file
				FileReader fr = new FileReader(adminFile);
				BufferedReader br = new BufferedReader(fr);
				username = br.readLine();
				password = br.readLine();
				// Close the file
				br.close();
				fr.close();
			} catch (Exception e) {
				System.out.printf("Can't read the file!\n");
				e.printStackTrace();
				System.exit(1);
			}
		else
			synchronize();
	}
	
	/**
	 * This method is used to check whether the administrator 
	 * logs in to the system successfully.
	 * 
	 * @param username The user name of the administrator.
	 * @param password The password of the administrator.
	 * @return boolean Whether administrator logs in or not.
	 */
	public boolean login(String username, String password) {
		if ( this.username.equals(username) && this.password.equals(password))
			return true;
		return false;
	}
	
	/**
	 * This method is used to make the data is displaying on 
	 * the screen is the same as the data storing in the file.
	 */
	private void synchronize() {
		try {
			// Open the file
			FileWriter fw = new FileWriter(adminFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.write(username + "\r\n");
			pw.write(password + "\r\n");
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
	
	// Get and set methods
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
		synchronize();
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
		synchronize();
	}
}
