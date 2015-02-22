package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import carparksystem.controls.*;
import carparksystem.entities.*;

/** 
 * This is a boundary class providing a graphical interface for
 * administrator operations on account management.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class AccountUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton regBtn, modifyBtn;
	private JTable table;
	
	private String[] columnNames = {"Index",
            "Name",
            "Campus Card",
            "E-mail",
            "Phone"};
	
	private Object[][] data = {};
	
	/**
	 * Constructor to initialize a EntranceUI
	 */
	public AccountUI() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 20, 20, 20));
		
		// declare the accounts table and the layout
		JPanel accountPanel = new JPanel();
		add(accountPanel, BorderLayout.CENTER);
		accountPanel.setBorder(new CompoundBorder(new TitledBorder(null,
				"Staff Accounts", TitledBorder.LEFT, TitledBorder.TOP),
				new EmptyBorder(10, 10, 20, 10)));
		accountPanel.setLayout(new BorderLayout());
		
		// declare the table and the layout
		table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		accountPanel.add(scrollPane, BorderLayout.CENTER);
		
		// declare the buttons and the layout
		JPanel BtnPanel = new JPanel();
		add(BtnPanel, BorderLayout.SOUTH);
		BtnPanel.setLayout(new BoxLayout(BtnPanel, BoxLayout.LINE_AXIS));
		BtnPanel.setBorder(new EmptyBorder(30, 10, 0, 10));
		regBtn = new JButton("Register");
		BtnPanel.add(regBtn);
		regBtn.addActionListener(this);
		BtnPanel.add(Box.createHorizontalGlue());
		modifyBtn = new JButton("Modify");
		BtnPanel.add(modifyBtn);
		modifyBtn.addActionListener(this);
		
		update();
	}
	
	/**
	 * This method is to update the information of 
	 * staff account on the user interface.
	 */
	public void update() {
		AccountControl ac = new AccountControl();
		ArrayList<StaffAccount> saList= ac.viewStaffAccount();	
		int size = saList.size();
		data = new Object[size][5];
		
		// get information of each staff account
		int i = 0;
		for (StaffAccount s : saList) {
			data[i][0] = s.getId();
			data[i][1] = s.getName();
			data[i][2] = s.getCampusCardNum();
			data[i][3] = s.getEmail();
			data[i][4] = s.getPhoneNum();
			i++;
		}
		
		DefaultTableModel model=new DefaultTableModel(data, columnNames);
		table.setModel(model);
	}
	
	
	/**
	 * This method is to show the dialog which is 
	 * used for registering a new staff car.
	 */
	public void showRegAccount() {
		// declare massages of the pop-up
		Object[] message = new Object[8];
		message[0] = "Name: ";
		JTextField nameTF = new JTextField(); 
		message[1] = nameTF;
		message[2] = "Campus card: ";
		JTextField campusCardTF = new JTextField(); 
		message[3] = campusCardTF;
		message[4] = "E-mail: ";
		JTextField emailTF = new JTextField(); 
		message[5] = emailTF;
		message[6] = "Phone: ";
		JTextField phoneTF = new JTextField(); 
		message[7] = phoneTF;
		
		String[] options = { "OK", "Cancel" };
		
		// show pop-up message box
		int result = JOptionPane.showOptionDialog(this, 
					message, // the dialog message array
					"Register", // the title of the dialog window
					JOptionPane.DEFAULT_OPTION, // option type
					JOptionPane.INFORMATION_MESSAGE, // message type
					null, // optional icon, use null to use the default icon
					options, // options string array, will be made into buttons
					options[0] // option that should be made into a default buttons
				);

		switch (result) {
			case 0: // OK
				AccountControl ac = new AccountControl();
				ac.addStaffAccount(nameTF.getText(), 
						campusCardTF.getText(), 
						emailTF.getText(), 
						phoneTF.getText());				
				break;
			case 1: // cancel
				break;
			default:
				break;
		}
	}
	
	/**
	 * This method is to show the dialog which is used 
	 * for modifying the current staff account.
	 */
	public void showModifyAccount() {
		// declare massages of the pop-up
		Object[] message = new Object[10];
		message[0] = "Index: ";
		JTextField idTF = new JTextField(); 
		message[1] = idTF;
		message[2] = "Name: ";
		JTextField nameTF = new JTextField(); 
		message[3] = nameTF;
		message[4] = "Campus card: ";
		JTextField campusCardTF = new JTextField(); 
		message[5] = campusCardTF;
		message[6] = "E-mail: ";
		JTextField emailTF = new JTextField(); 
		message[7] = emailTF;
		message[8] = "Phone: ";
		JTextField phoneTF = new JTextField(); 
		message[9] = phoneTF;
		

		String[] options = { "OK", "Cancel" };

		// show pop-up message box
		int result = JOptionPane.showOptionDialog(this, 
					message, // the dialog message array
					"Modify", // the title of the dialog window
					JOptionPane.DEFAULT_OPTION, // option type
					JOptionPane.INFORMATION_MESSAGE, // message type
					null, // optional icon, use null to use the default icon
					options, // options string array, will be made into buttons
					options[0] // option that should be made into a default buttons
				);

		switch (result) {
			case 0: // OK
				AccountControl ac = new AccountControl();
				try {
					ac.modifyStaffAccount(Integer.parseInt(idTF.getText()),
							nameTF.getText(), 
							campusCardTF.getText(), 
							emailTF.getText(), 
							phoneTF.getText());		
				} catch(Exception e) {
					// show pop-up message box
					JOptionPane.showMessageDialog(this, "Index must be integer!");
				}
				break;
			case 1: // cancel
				break;
			default:
				break;
		}
	}
	
	/**
	 * This method called every time a button generates an event. 
	 * 
	 *@param e  A reference to an ActionEvent object that occurs.
	 */ 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == regBtn) {
			showRegAccount();			
		} else if (e.getSource() == modifyBtn) {
			showModifyAccount();
		}
		
		update();
	}
}
