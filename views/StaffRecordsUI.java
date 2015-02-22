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
 * administrator operations on staff record management.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class StaffRecordsUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton reportBtn;
	private JTable table;
	private JTextField campusCardTF, monthTF;
	
	//declare the information displayed in the table
	private String[] columnNames = {"Index",
            "Campus Card",
            "Enter Time",
            "Depart Time"};
	
	private Object[][] data = {};
	
	/**
	 * Constructor to initialize a StaffRecordsUI.
	 */
	public StaffRecordsUI() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 20, 20, 20));
		
		// Display Staff Records table
		JPanel recordPanel = new JPanel();
		add(recordPanel, BorderLayout.CENTER);
		recordPanel.setBorder(new CompoundBorder(new TitledBorder(null,
				"Staff Records", TitledBorder.LEFT, TitledBorder.TOP),
				new EmptyBorder(10, 10, 20, 10)));
		recordPanel.setLayout(new BorderLayout());
		
		table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		recordPanel.add(scrollPane, BorderLayout.CENTER);
		
		// Declare the button and the layout
		JPanel BtnPanel = new JPanel();
		add(BtnPanel, BorderLayout.SOUTH);
		BtnPanel.setLayout(new GridLayout(1, 5, 10, 10));
		BtnPanel.setBorder(new EmptyBorder(30, 10, 0, 10));
		reportBtn = new JButton("Staff monthly bill");
		BtnPanel.add(reportBtn);
		reportBtn.addActionListener(this);
		BtnPanel.add(new JLabel("Campus Card:"));
		campusCardTF = new JTextField();
		BtnPanel.add(campusCardTF);
		BtnPanel.add(new JLabel("Month (MM-yyyy):"));
		monthTF = new JTextField(); 
		BtnPanel.add(monthTF);
		
		update();
	}
	
	/**
	 * This method is to update the staff record on the user interface.
	 */
	public void update() {
		ReportControl rc = new ReportControl();
		ArrayList<StaffRecord> srList= rc.viewStaffRecords();	
		int size = srList.size();
		data = new Object[size][4];
		
		//get each staff record and display
		int i = 0;
		for (StaffRecord s : srList) {
			data[i][0] = s.getId();
			data[i][1] = s.getCampusCardNum();
			data[i][2] = timeToString(s.getEnterTime());
			if (s.getDepartTime() != null)
				data[i][3] = timeToString(s.getDepartTime());
			else 
				data[i][3] = "N/A";
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
	 *@param e A reference to an ActionEvent object that occurs.
	 */ 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == reportBtn) {
			ReportControl rc = new ReportControl();
			double bills = rc.generateReport(campusCardTF.getText(), 
					monthTF.getText());
			
			//show pop-up message box
			JOptionPane.showMessageDialog(this, "The total bills in " +
					monthTF.getText() + " : " + bills);
		}
	}
}
