package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.*;

import carparksystem.controls.*;

/** 
 * This is a boundary class providing a graphical interface for
 * customer payment at the Exit.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class PaymentUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private OutFrame outFrame;
	private JButton p10Btn, p20Btn, p50Btn,
		            f1Btn, f2Btn, confirmBtn;
	private JTextField timeTF, paymentTF, coinsTF;
	private double payment;
	private double coins;
	
	/**
	 * Constructor to initialize a PaymentUI.
	 */
	public PaymentUI(OutFrame outFrame) {
		setLayout(new BorderLayout());
		this.outFrame = outFrame;
		
		// display the title
		JPanel titlePanel = new JPanel();
		add(titlePanel, BorderLayout.NORTH);
		titlePanel.setBorder(new EmptyBorder(5,10,5,10));
		titlePanel.add(new JLabel("<html><font size=\"5\"><b>Payment Station</b></font></html>"));
		
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
		screenPanel.setLayout(new GridLayout(6, 1, 10, 10));
		screenPanel.setBorder(new EmptyBorder(10,10,10,10));
		screenPanel.add(new JLabel("Parking Time (Hour):",
				JLabel.CENTER));
		timeTF = new JTextField();
		screenPanel.add(timeTF);
		timeTF.setEditable(false);
		screenPanel.add(new JLabel("Payment:",
				JLabel.CENTER));
		paymentTF = new JTextField();
		screenPanel.add(paymentTF);
		paymentTF.setEditable(false);
		screenPanel.add(new JLabel("Your Coins:",
				JLabel.CENTER));
		coinsTF = new JTextField();
		screenPanel.add(coinsTF);
		coinsTF.setEditable(false);
		
		// declare the buttons and layout
		JPanel btnPanel = new  JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setLayout(new  GridLayout(3, 2, 10, 5));
		btnPanel.setBorder(new EmptyBorder(10,15,15,15));
		p10Btn = new JButton("10p");
		btnPanel.add(p10Btn);
		p10Btn.addActionListener(this);
		p20Btn = new JButton("20p");
		btnPanel.add(p20Btn);
		p20Btn.addActionListener(this);
		p50Btn = new JButton("50p");
		btnPanel.add(p50Btn);
		p50Btn.addActionListener(this);
		f1Btn = new JButton("1");
		btnPanel.add(f1Btn);
		f1Btn.addActionListener(this);
		f2Btn = new JButton("2");
		btnPanel.add(f2Btn);
		f2Btn.addActionListener(this);
		confirmBtn = new JButton("OK");
		btnPanel.add(confirmBtn);
		confirmBtn.addActionListener(this);
	}
	
	/**
	 * This method is to display the payment on the 
	 * textfield in Payment Station.
	 * 
	 * @param publicNum The 3-digit random which are generated to the public.
	 */
	public void setPayment(String publicNum) {
		OutControl oc = new OutControl();
		coins = 0;
		coinsTF.setText("");
		payment = oc.calculatePrice(publicNum);
		double hours= oc.calculateHours(publicNum);
		DecimalFormat df=new DecimalFormat("#0.00");
		timeTF.setText("" + df.format(hours));
		paymentTF.setText("" + payment);
	}
	
	/**
	 * This method is to add coins to the payment station.
	 * 
	 * @param coin The number of the coins.
	 */
	public void addCoin(double coin) {
		coins += coin;
		coinsTF.setText("" + coins);
	}
	
	/**
	 * This method called every time a button generates an event. 
	 * 
	 *@param e  A reference to an ActionEvent object that occurs.
	 */ 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == p10Btn) {
			addCoin(0.1);
		} else if (e.getSource() == p20Btn) {
			addCoin(0.2);
		} else if (e.getSource() == p50Btn) {
			addCoin(0.5);
		} else if (e.getSource() == f1Btn) {
			addCoin(1);
		} else if (e.getSource() == f2Btn) {
			addCoin(2);
		} else if (e.getSource() == confirmBtn) {
			if (coins >= payment) {
				OutControl oc = new OutControl();
				oc.addCoins(coins);
				oc.openBarrier();
				//show pop-up message box
				JOptionPane.showMessageDialog(this, "Thanks for your payment!\n The exit barrier is open");
				outFrame.showExitUI();
				outFrame.upadate();
			} else 
				//show pop-up message box
				JOptionPane.showMessageDialog(this, "Your payment is not enough!");
		}
	}
}
