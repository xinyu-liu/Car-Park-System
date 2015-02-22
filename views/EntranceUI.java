package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import carparksystem.controls.*;

/** 
 * This is a boundary class providing a graphical interface for
 * customer operations at the entrance.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class EntranceUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private OutFrame outFrame;
	private JButton staffBtn, publicBtn, emergencyBtn;
	private JTextField inputTF, leftCarSpaceTF;
	private JLabel parkingTimeLb;

	/**
	 * Constructor to initialize a EntranceUI.
	 */
	public EntranceUI(OutFrame outFrame) {
		setLayout(new BorderLayout());
		this.outFrame = outFrame;
		 
		// declare the titles and the layout
		JPanel titlePanel = new JPanel();
		add(titlePanel, BorderLayout.NORTH);
		titlePanel.setBorder(new EmptyBorder(5,10,5,10));
		titlePanel.add(new JLabel("<html><font size=\"5\"><b>Entrance</b></font></html>"));
		
		// declare the screens and the layout
		JPanel screenMargin = new JPanel();
		add(screenMargin, BorderLayout.CENTER);
		screenMargin.setLayout(new BoxLayout(screenMargin, BoxLayout.PAGE_AXIS));
		screenMargin.setBorder(new EmptyBorder(5,10,5,10));
		JPanel screenBorder = new JPanel();
		screenMargin.add(screenBorder);
		screenBorder.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JPanel screenPanel = new JPanel();
		screenBorder.add(screenPanel);
		screenPanel.setLayout(new GridLayout(5, 1, 10, 20));
		screenPanel.setBorder(new EmptyBorder(10,10,10,10));
		screenPanel.add(new JLabel("<html>Parking spaces left:</html>", 
				JLabel.CENTER));
		leftCarSpaceTF = new JTextField();
		leftCarSpaceTF.setHorizontalAlignment(JLabel.CENTER);
		screenPanel.add(leftCarSpaceTF);
		leftCarSpaceTF.setEditable(false);
		parkingTimeLb = new JLabel("Normal weekdays: Staff ONLY",
				JLabel.CENTER);
		screenPanel.add(parkingTimeLb);
		screenPanel.add(new JLabel("<html>Public: Press Public button.</html>",
				JLabel.CENTER));
		screenPanel.add(new JLabel("<html>Staff: Input campus card <br/> number, press Staff button.</html>",
				JLabel.CENTER));
		
		// declare the buttons and the layout
		JPanel btnPanel = new  JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setLayout(new  GridLayout(4, 1, 10, 5));
		btnPanel.setBorder(new EmptyBorder(10,15,15,15));
		inputTF = new JTextField();
		btnPanel.add(inputTF);	
		staffBtn = new JButton("Staff");
		btnPanel.add(staffBtn);
		staffBtn.addActionListener(this);
		publicBtn = new JButton("Public");
		btnPanel.add(publicBtn);
		publicBtn.addActionListener(this);
		emergencyBtn = new JButton("Emergency");
		btnPanel.add(emergencyBtn);
		emergencyBtn.addActionListener(this);
		
		update();
	}
	/**
	 * Method to update park space left 
	 * and whether the day is open to public
	 */
	public void update() {
		SettingControl sc = new SettingControl();
		leftCarSpaceTF.setText("" + sc.displaySpaces());
		
		InControl ic = new InControl();
		if (ic.checkNonworkingday()) {
			parkingTimeLb.setText("For Staff and Public use");
			publicBtn.setEnabled(true);
		}
		else {
			parkingTimeLb.setText("Normal weekdays: Staff ONLY");
			publicBtn.setEnabled(false);
		}
	}
	
	
	/**
	 * This method called every time a button generates an event. 
	 * 
	 *@param e  a reference to an ActionEvent object that occurs
	 */ 
	@Override
	public void actionPerformed(ActionEvent e) {
		InControl ic = new InControl();
		
		if (e.getSource() == emergencyBtn) {
			EmergencyControl ec = new EmergencyControl();
			ec.informAdmin();
			// show pop-up message box
			JOptionPane.showMessageDialog(this, "The manager will come to aid immediately.");
		} else if (!ic.checkLeftSpaces()) {
			// show pop-up message box
			JOptionPane.showMessageDialog(this, "Sorry, no park space left");
		} else if (e.getSource() == staffBtn) {
			if (ic.checkStaffAccount(inputTF.getText())) {
				ic.addStaffRecord(inputTF.getText());
				ic.openBarrier();
				// show pop-up message box
				JOptionPane.showMessageDialog(this, "The entrance barrier is open!");
			} else 
				// show pop-up message box
				JOptionPane.showMessageDialog(this, "Campus card number invalid!\r\nPlease register your car first.");
		} else if (!ic.checkBlankTicket()) {
			// show pop-up message box
			JOptionPane.showMessageDialog(this, "Sorry! Out of blank ticket.\r\nPlease press Emergency button for help.");
		} else if (e.getSource() == publicBtn) {
			String publicNum = ic.generatePublicNum();
			ic.addPublicRecord(publicNum);
			ic.openBarrier();
			
			// show pop-up message box
			JOptionPane.showMessageDialog(this, "The entrance barrier is open! " +
					"\n Your public number:" + publicNum);
		}
		
		outFrame.upadate();
	}
}
