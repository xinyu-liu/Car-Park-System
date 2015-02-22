package carparksystem.views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import carparksystem.controls.*;

/** 
 * This is a boundary class providing a graphical interface for
 * administrator operations on setting park status.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class ParkStatusUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private OutFrame outFrame;
	private JButton setLeftCardBtn, clearCoinBtn, logoutBtn;
	private JTextField leftCardTF, leftCarSpacesTF, leftCoinTF;

	/**
	 * Constructor to initialize a ParkStatusUI.
	 */
	public ParkStatusUI(OutFrame outFrame) {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 20, 20, 20));
		this.outFrame = outFrame;
		
		// declare the status and the layout
		JPanel statusBorder = new JPanel();
		add(statusBorder, BorderLayout.CENTER);
		statusBorder.setBorder(new CompoundBorder(new TitledBorder(null,
				"Park Status", TitledBorder.LEFT, TitledBorder.TOP),
				new EmptyBorder(10, 30, 10, 30)));
		JPanel statusPanel = new JPanel();
		statusBorder.add(statusPanel);
		statusPanel.setLayout(new GridLayout(3,2,20,20));
		statusPanel.add(new JLabel("Tickets: "));
		leftCardTF = new JTextField();
		statusPanel.add(leftCardTF);
		leftCardTF.setEditable(false);
		statusPanel.add(new JLabel("Coins: "));
		leftCoinTF = new JTextField();
		statusPanel.add(leftCoinTF);
		leftCoinTF.setEditable(false);
		statusPanel.add(new JLabel("Park spaces: "));
		leftCarSpacesTF = new JTextField();
		statusPanel.add(leftCarSpacesTF);
		leftCarSpacesTF.setEditable(false);
		
		// declare the buttons and the layout
		JPanel BtnPanel = new JPanel();
		add(BtnPanel, BorderLayout.SOUTH);
		BtnPanel.setLayout(new BoxLayout(BtnPanel, BoxLayout.LINE_AXIS));
		BtnPanel.setBorder(new EmptyBorder(30, 10, 0, 10));
		setLeftCardBtn = new JButton("Set the number of tickets");
		BtnPanel.add(setLeftCardBtn);
		setLeftCardBtn.addActionListener(this);
		BtnPanel.add(Box.createHorizontalGlue());
		clearCoinBtn = new JButton("Clear coin box");
		BtnPanel.add(clearCoinBtn);
		clearCoinBtn.addActionListener(this);
		BtnPanel.add(Box.createHorizontalGlue());
		logoutBtn = new JButton("Logout");
		BtnPanel.add(logoutBtn);
		logoutBtn.addActionListener(this);
		
		update();
	}
	
	/**
	 * This method is to show the update of park 
	 * space, blank tickets and coins.  
	 */
	public void update() {
		SettingControl sc = new SettingControl();
		leftCarSpacesTF.setText("" + sc.displaySpaces());
		leftCardTF.setText("" + sc.displayLeftBlankTicket());
		leftCoinTF.setText("" + sc.diaplayLeftCoin());
	}
	
	/**
	 * This method is to show the ticket number setting dialog.
	 */	 
	public void showSetLeftCards() {
		// Message
		Object[] message = new Object[2];
		message[0] = "Number of tickets: ";
		JTextField cardsTF = new JTextField();
		message[1] = cardsTF;
		
		String[] options = { "OK", "Cancel" };

		int result = JOptionPane.showOptionDialog(this, 
					message, // the dialog message array
					"Set the number of tickets", // the title of the dialog window
					JOptionPane.DEFAULT_OPTION, // option type
					JOptionPane.INFORMATION_MESSAGE, // message type
					null, // optional icon, use null to use the default icon
					options, // options string array, will be made into buttons
					options[0] // option that should be made into a default buttons
				);
		
		switch (result) {
			case 0: // OK
				try {
					int leftCardsNum = Integer.parseInt(cardsTF.getText());
					SettingControl sc = new SettingControl();
					sc.setTicketNum(leftCardsNum);			
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this, "Wrong Input");
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
		if (e.getSource() == setLeftCardBtn) {
			showSetLeftCards();
		} else if (e.getSource() == clearCoinBtn) {
			SettingControl sc = new SettingControl();
			sc.cleanCionNum();
		} else if (e.getSource() == logoutBtn) {
			outFrame.showAdminUI();
		}
		
		update();
	}
}
