package carparksystem.views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import carparksystem.entities.Admin;

/** 
 * This is a boundary class providing a graphical interface for
 * administrator login.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class AdminUI  extends JPanel implements ActionListener  {
	private static final long serialVersionUID = 1L;
	private OutFrame outFrame;
	private JTextField usernameTF;
	private JPasswordField pwdPF;
	private JButton loginBtn, clearBtn;
	
	/**
	 * Constructor to initialize a AdminUI.
	 */
	public AdminUI(OutFrame outFrame) {
		setLayout(new BorderLayout());
		this.outFrame = outFrame;
		
		// declare the screen and the layout
		JPanel screenMargin = new JPanel();
		add(screenMargin, BorderLayout.CENTER);
		screenMargin.setLayout(new BoxLayout(screenMargin, BoxLayout.PAGE_AXIS));
		screenMargin.setBorder(new EmptyBorder(50,30,10,30));
		JPanel screenBorder = new JPanel();
		screenMargin.add(screenBorder);
		screenBorder.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JPanel screenPanel = new JPanel();
		screenBorder.add(screenPanel);
		screenPanel.setLayout(new BoxLayout(screenPanel, BoxLayout.PAGE_AXIS));
		screenPanel.setBorder(new EmptyBorder(40,20,10,20));
		screenPanel.add(new JLabel("<html><font size=\"10\">Welcome to the Car" +
				"<br/> park system!</font></html>"));
		screenPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		screenPanel.add(new JLabel("Version 1.0"));
		
		// declare the login and the layout
		JPanel loginPanel = new JPanel();
		add(loginPanel, BorderLayout.SOUTH);
		loginPanel.setLayout(new BorderLayout());
		loginPanel.setBorder(new EmptyBorder(10, 150, 15, 150)); 
		
		JPanel inputPanel = new JPanel();
		loginPanel.add(inputPanel, BorderLayout.CENTER);
		inputPanel.setLayout(new GridLayout(3, 2, 30, 20));
		inputPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
		
		inputPanel.add(new JLabel("User name :"));
		usernameTF =  new JTextField();
		inputPanel.add(usernameTF);
		inputPanel.add(new JLabel("Password :"));
		pwdPF = new JPasswordField();
		inputPanel.add(pwdPF);
		
//		JPanel btnPanel = new JPanel();
//		loginPanel.add(btnPanel, BorderLayout.SOUTH);
//		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		loginBtn = new JButton("Login");
		inputPanel.add(loginBtn);
		loginBtn.addActionListener(this);
//		btnPanel.add(Box.createHorizontalGlue());
		clearBtn = new JButton("Clear");
		inputPanel.add(clearBtn);
		clearBtn.addActionListener(this);
	}
	
	/**
	 * This method called every time a button generates an event. 
	 * 
	 *@param e  A reference to an ActionEvent object that occurs.
	 */ 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBtn) {
			Admin ad = new Admin();
			if (ad.login(usernameTF.getText(),
					new String(pwdPF.getPassword()))) 
				outFrame.showMainCtl();
			else
				// show pop-up message box
				JOptionPane.showMessageDialog(this, "Wrong username or password!");
		} else if (e.getSource() == clearBtn) {
			// set the text of the testfield
			usernameTF.setText("");
			pwdPF.setText("");
		}
	}
}
