package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import carparksystem.controls.*;
import carparksystem.entities.*;

/** 
 * This is a boundary class providing a graphical interface for
 * administrator operations on public record management.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class PublicRecordsUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	//declare the information displayed in the table
	private String[] columnNames = {"Index",
            "Public number",
            "Enter Time",
            "Depart Time",
            "Payment"};
	
	private Object[][] data = {};
	
	/**
	 * Constructor to initialize a PublicRecordsUI.
	 */
	public PublicRecordsUI() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 20, 20, 20));
		
		// display Public Records table
		JPanel recordPanel = new JPanel();
		add(recordPanel, BorderLayout.CENTER);
		recordPanel.setBorder(new CompoundBorder(new TitledBorder(null,
				"Public Records", TitledBorder.LEFT, TitledBorder.TOP),
				new EmptyBorder(10, 10, 20, 10)));
		recordPanel.setLayout(new BorderLayout());
		
		table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		recordPanel.add(scrollPane, BorderLayout.CENTER);
		
		update();
	}
	
	/**
	 * This method is to update the staff record on the user interface.
	 */
	public void update() {
		ReportControl rc = new ReportControl();
		ArrayList<PublicRecord> prList= rc.viewPublicRecords();	
		int size = prList.size();
		data = new Object[size][5];
		
		//get each public record and display
		int i = 0;
		for (PublicRecord pr : prList) {
			data[i][0] = pr.getId();
			data[i][1] = pr.getPublicNum();
			data[i][2] = timeToString(pr.getEnterTime());
			if (pr.getDepartTime() != null)
				data[i][3] = timeToString(pr.getDepartTime());
			else 
				data[i][3] = "N/A";
			data[i][4] = pr.getPayment();
			i++;
		}
		
		DefaultTableModel model=new DefaultTableModel(data, columnNames);
		table.setModel(model);
	}
	
	/**
	 * This method overrides the toString method.
	 * 
	 * @param time  The time of the system.
	 * @return String The string format of time.
	 */
	public String timeToString(Date time) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		return bartDateFormat.format(time);
	}
	
	/**
	 * This method called every time a button generates an event. 
	 * 
	 *@param e  A reference to an ActionEvent object that occurs.
	 */ 
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
