package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import carparksystem.controls.*;

/** 
 * This is a boundary class providing a graphical interface for
 * customer operations at the Exit.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class ExitUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private OutFrame outFrame;
	private JButton staffBtn, publicBtn, emergencyBtn;
	private JTextField inputTF;
	
	/**
	 * Constructor to initialize a ExitUI.
	 */
	public ExitUI(OutFrame outFrame) {
		setLayout(new BorderLayout());
		this.outFrame = outFrame;
		
		// declare the title and the layout
		JPanel titlePanel = new JPanel();
		add(titlePanel, BorderLayout.NORTH);
		titlePanel.setBorder(new EmptyBorder(5,10,5,10));
		titlePanel.add(new JLabel("<html><font size=\"5\"><b>Exit</b></font></html>"));
		
		// declare the screen and the layout
		JPanel screenMargin = new JPanel();
		add(screenMargin, BorderLayout.CENTER);
		screenMargin.setLayout(new BoxLayout(screenMargin, BoxLayout.PAGE_AXIS));
		screenMargin.setBorder(new EmptyBorder(5,10,5,10));
		JPanel screenBorder = new JPanel();
		screenMargin.add(screenBorder);
		screenBorder.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JPanel screenPanel = new JPanel();
		screenBorder.add(screenPanel);
		screenPanel.setLayout(new GridLayout(3, 1, 10, 30));
		screenPanel.setBorder(new EmptyBorder(10,10,10,10));

		screenPanel.add(new JLabel("<html>For public customer:<br/> Input public number<br/> and press Public button.</html>",
				JLabel.CENTER));
		screenPanel.add(new JLabel("<html>For staff:<br/> Input campus card <br/> number and press Staff <br/> button.</html>",
				JLabel.CENTER));
	
		// declare the button and the layout
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
		
	}
	
	/**
	 * This method called every time a button generates an event. 
	 * 
	 *@param e  A reference to an ActionEvent object that occurs.
	 */ 
	@Override
	public void actionPerformed(ActionEvent e) {
		OutControl oc = new OutControl();
		
		if (e.getSource() == emergencyBtn) {
			EmergencyControl ec = new EmergencyControl();
			ec.informAdmin();
			//show pop-up message box
			JOptionPane.showMessageDialog(this, "The manager will come to aid immediately.");
		} else if (e.getSource() == staffBtn) {
			if (oc.checkStaffCar(inputTF.getText())) {
				oc.recordStaffExitTime(inputTF.getText());
				oc.openBarrier();
				inputTF.setText("");
				//show pop-up message box
				JOptionPane.showMessageDialog(this, "The exit barrier is open!");
			} else
				//show pop-up message box
				JOptionPane.showMessageDialog(this, "Wrong campus card number!");		
		} else if (e.getSource() == publicBtn) {
			if (oc.checkTicket(inputTF.getText())) {
				oc.recordPublicExitTime(inputTF.getText());
				outFrame.setPayment(inputTF.getText());
				outFrame.showPaymentUI();
				inputTF.setText("");
			} else
				//show pop-up message box
				JOptionPane.showMessageDialog(this, "Wrong public number!");
		} 
		
		outFrame.upadate();
	}
}
