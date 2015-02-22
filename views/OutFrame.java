package carparksystem.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/** 
 * This is a boundary class providing an overall application GUI
 *  and scheduling a job for the event-dispatching thread
 *  
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class OutFrame extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	// The preferred size 
	private int PREFERRED_WIDTH = 1100;
	private int PREFERRED_HEIGHT = 550;
	
	private EntranceUI entranceUI;
	private ExitUI exitUI;
	
	private AdminUI   adminUI;
	private AccountUI accountUI;
	private ParkStatusUI parkStatusUI;
	private PaymentUI paymentUI;
	private PublicRecordsUI pulicRecordsUI;
	private StaffRecordsUI staffRecordsUI;
	private JPanel mainCtrlUI, rightUI;
	private JLabel statusField;
	
	private CardLayout cl1;
	private CardLayout cl2;
	
	/**
	 * Constructor to initialize an OutFrame
	 */
	public OutFrame() {
		setTitle("QM Car Park Control System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
		
		// display Entrance UI
		entranceUI = new EntranceUI(this);
		add(entranceUI, BorderLayout.WEST);
		entranceUI.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		// display Exit UI
		rightUI = new JPanel();
		add(rightUI, BorderLayout.EAST);
		rightUI.setLayout(new CardLayout());
		rightUI.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		cl2 = (CardLayout) (rightUI.getLayout());
		
		exitUI = new ExitUI(this);
		rightUI.add(exitUI, "exit");
		
		paymentUI = new PaymentUI(this);
		rightUI.add(paymentUI, "payment");
		
		// display Main Control UI
		mainCtrlUI = new JPanel();
        add(mainCtrlUI, BorderLayout.CENTER); 
        mainCtrlUI.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        mainCtrlUI.setLayout(new CardLayout());
        cl1 = (CardLayout) (mainCtrlUI.getLayout());
		
        // display Admin UI
        adminUI = new AdminUI(this);
        mainCtrlUI.add( adminUI, "admin");
		
        // display Tabs
        JTabbedPane tabPanel = new JTabbedPane(); 
        mainCtrlUI.add(tabPanel, "tabs");
        
        parkStatusUI = new ParkStatusUI(this);
        tabPanel.add("Park Status", parkStatusUI);
        
        accountUI = new AccountUI();
        tabPanel.add("Staff Accounts", accountUI);
        
        staffRecordsUI = new StaffRecordsUI();
        tabPanel.add("Staff Records", staffRecordsUI);
        
        pulicRecordsUI = new PublicRecordsUI();
        tabPanel.add("Public Records", pulicRecordsUI);
        
        // display Status Bar
        statusField = new JLabel("Current Time :", JLabel.CENTER);
		statusField.setBorder(new EmptyBorder(2, 4, 4, 2));

		add(statusField, BorderLayout.SOUTH);
        
		pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 -  this.getSize().width / 2,
				screenSize.height / 2 -  this.getSize().height / 2);
		setVisible(true);
	}
	
	/**
	 * This method is to show the login interface.
	 */
	public void showAdminUI() {
		cl1.show(mainCtrlUI, "admin");
	}
	
	/**
	 * This method is to show the main management interface.
	 */
	public void showMainCtl() {
		cl1.show(mainCtrlUI, "tabs");
	}
	
	/**
	 * This method is to show the payment station interface.
	 */
	public void showPaymentUI() {
		cl2.show(rightUI, "payment");
	}
	
	/**
	 * This method is to show the exit interface.
	 */
	public void showExitUI() {
		cl2.show(rightUI, "exit");
	}
	
	/**
	 * This method is to set the payment of public.
	 * 
	 * @param publicNum The 3-digit number which is generated to public
	 */
	public void setPayment(String publicNum) {
		paymentUI.setPayment(publicNum);
	}
	
	/**
	 * This method is to update the display of the 
	 * information on the user interface.
	 */
	public void upadate() {
		entranceUI.update();
		parkStatusUI.update();
		pulicRecordsUI.update();
		staffRecordsUI.update();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		while(true) {
			statusField.setText( "" + Calendar.getInstance().getTime());
			try {
				Thread.sleep(1000); 
			} catch(Exception e) {
				statusField.setText("Error!");
		   }
		}  
	}
	
	/**
	 *  A main method to run the program
	 *  It creates a new instance of OutFrame to show this application's GUI
	 *  and schedule a job for the event-dispatching thread
	 *  
	 *  @param args This program does not use this parameter.
	 */
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {    
                /* Turn off metal's use of bold fonts */
                UIManager.put("swing.boldMetal", Boolean.FALSE);  
                OutFrame of = new OutFrame();
                Thread thread = new Thread(of);
                thread.start(); 
            }
        });
	}
}
