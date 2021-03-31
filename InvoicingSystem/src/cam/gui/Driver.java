package cam.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cam.business.logic.DataManager;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class Driver {
	private DataManager data;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Driver driver = new Driver();
		driver.start();
	}
	
	//Controls the flow of the program
	private void start() {
		data = new DataManager();
		login();
	}
		
	//Login user interface
	private void login() {
		
		JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Driver.class.getResource("/cam/gui/icon.png")));
		frame.setBackground(Color.WHITE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.setTitle("Invoicing system");

		JLabel userLabel = new JLabel("Staff ID");
		userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		JTextField passwordText = new JTextField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setFocusPainted(false);
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.BLUE);
		loginButton.setMnemonic(KeyEvent.VK_ENTER);
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
		loginButton.setBounds(100, 80, 160, 25);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		} 
		javax.swing.SwingUtilities.updateComponentTreeUI(loginButton);
		panel.add(loginButton);
		loginButton.addActionListener((ActionListener) new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(userText.getText());
					if(data.validateLogin(id, passwordText.getText()) == false){
						throw new Exception();
					}else {
						frame.dispose();
						dashboard dashboard = new dashboard();
					}
				}catch(Exception error) {
					JOptionPane.showMessageDialog(null, "Incorrect login");
				}
			}
		});
		
		frame.getContentPane().add(panel);
		frame.setMinimumSize(new Dimension(300, 150));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getRootPane().setDefaultButton(loginButton);
	}
	
	private void displayInformation(int option) {
		
	}
	
	private void createInvoice() {
		
	}
	
	private void createOrder() {
		
	}
	
	private void manageProducts() {
		
	}
	
	//Reusable dialog to confirm the users decision 
	public boolean confirmDecisionDialog() {
		int dialogResult = JOptionPane.showConfirmDialog(null, "Please confirm your decision.", "Confirmation", JOptionPane.YES_NO_OPTION);
		if(dialogResult == 0) {
			return true;
		} else {
			return false;
		} 
	}

}
