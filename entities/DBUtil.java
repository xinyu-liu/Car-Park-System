package carparksystem.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/** 
 * This is an entity class mainly used 
 * for file reading and writing.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class DBUtil {
	private File file;
	private ArrayList<String[]> list;
	
	/**
	 * Constructor to initialize a DBUtil
	 * 
	 * @param file
	 */
	public DBUtil(File file) {
		this.file = file;
		if (file.exists()) {
			list = fileReader();
		} else { 
			try {
				file.createNewFile();
			} catch (Exception e) {
				System.out.printf("Can't create the file!\n");
				e.printStackTrace();
				System.exit(1);
			}
			list = new ArrayList<String[]>();
		}
	}
	
	/**
	 * This method is used to insert a line to the file.
	 * 
	 * @param line The line.
	 * @return int The number of the line.
	 */
	public int insert(String[] line) {
		int id = Integer.parseInt(line[0]);
		
		id = list.size() + 1;
		line[0] = "" + id;
		list.add(line);
		fileWriter(list);
		
		return id;
	}
	
	/**
	 * This method is used to update a line of the file.
	 * 
	 * @param line The line.
	 */
	public void update(String[] line) {
		try {
			int id = Integer.parseInt(line[0]);
				if ( id > 0 && id <= list.size()) {
					list.set(id-1, line);
					fileWriter(list);
				} else 
					System.out.printf("Can't find record!\n");
		} catch (Exception e) {
			System.out.printf("Wrong ID!\n");
		}
	}
	
	/**
	 * This method is used to read from the file.
	 * 
	 * @param column The column of the data in the file.
	 * @param value The String representation of the data.
	 * @return String[] The data which want to read.
	 */
	public String[] get(int column, String value) {
		String[] line = null;
		
		for (String[] nextLine : list) {
			if (nextLine[column].equals(value)) {
				line = nextLine;
				break;
			}
		}
		
		return line;
	}
	
	/**
	 * This method is used to query data in the file.
	 * 
	 * @param column The column of the data in the file.
	 * @param value The String representation of the data.
	 * @return String[] The data which want to query.
	 */
	public ArrayList<String[]> query(int column, String value) {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		
		for (String[] nextLine : list) {
			if (nextLine[column].equals(value)) {
				lines.add(nextLine);
			}
		}
		
		return lines;
	}
	
	/**
	 * This method is used to get all data in the file.
	 * 
	 * @return ArrayList<> All the data in the file.
	 */
	public ArrayList<String[]> getAll() {
		return this.list;
	}
	
	/**
	 * This method is used for file reader.
	 * 
	 * @return ArrayList<> The data which is read in the file.
	 */
	public ArrayList<String[]> fileReader() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			boolean hasNext = true;
			
			while (hasNext) {
				String nextLine = br.readLine();
				if (nextLine != null ) {
					String[] nextLineAsTokens = nextLine.split("\\|");
			        if (nextLineAsTokens != null)
			        	list.add(nextLineAsTokens);
				} else {
					hasNext = false;
				}
			}	
			
			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.printf("Can't read the file!\n");
			e.printStackTrace();
			System.exit(1);
		}
		
		return list;
	}
	
	/**
	 * This method is used for file writer.
	 * 
	 * @param list The list of the data.
	 */
	public void fileWriter(ArrayList<String[]> list) {
		try {
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			
			for (String[] line : list) {	
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < line.length; i++) {	
					if (i != 0) {
						sb.append("|");
					}
					String nextElement = line[i];
					if (nextElement.length() > 0)	
						sb.append(nextElement);
				}
				sb.append("\r\n");
				pw.write(sb.toString());
			}
			
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			System.out.printf("Can't write the file!\n");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
