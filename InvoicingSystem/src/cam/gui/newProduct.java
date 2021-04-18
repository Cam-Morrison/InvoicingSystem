package cam.gui;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import cam.business.logic.DataManager;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Author: Cameron Morrison 569530
//Second year graded unit project. 
 
public class NewProduct {
	private JFrame frmNewProduct;
	private JTextField nameField;
	private JTextField descField;
	private JTextField priceField;
	private JTextField quantityField;
	private JLabel warningLbl;
	private DataManager data = new DataManager();
	private JTextField companyNameField;
	private JTextField phoneField;
	private JTextField emailField;
	private String productName;
	private String desc;
	private double price;
	private int quantity;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	JFrame addProduct(){
		frmNewProduct = new JFrame();
		frmNewProduct.getContentPane().setBackground(new Color(255, 255, 255));
		frmNewProduct.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		frmNewProduct.setFont(new Font("Arial", Font.PLAIN, 12));
		frmNewProduct.setIconImage(Toolkit.getDefaultToolkit().getImage(NewProduct.class.getResource("/cam/gui/icon.png")));
		frmNewProduct.setTitle("New product");
		frmNewProduct.setResizable(false);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		JLabel nameLbl = new JLabel("Product name");
		nameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		nameLbl.setFont(new Font("Arial", Font.BOLD, 16));
		nameLbl.setForeground(new Color(11, 26, 106));
		
		descField = new JTextField();
		descField.setColumns(10);
		
		JLabel descLbl = new JLabel("Description");
		descLbl.setHorizontalAlignment(SwingConstants.CENTER);
		descLbl.setFont(new Font("Arial", Font.BOLD, 16));
		descLbl.setForeground(new Color(11, 26, 106));
		
		priceField = new JTextField();
		priceField.setColumns(10);
		
		quantityField = new JTextField();
		quantityField.setColumns(10);
		
		JLabel priceLbl = new JLabel("Price");
		priceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		priceLbl.setFont(new Font("Arial", Font.BOLD, 16));
		priceLbl.setForeground(new Color(11, 26, 106));
		
		JLabel quantityLbl = new JLabel("Quantity (1-20)");
		quantityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		quantityLbl.setFont(new Font("Arial", Font.BOLD, 16));
		quantityLbl.setForeground(new Color(11, 26, 106));
		
		warningLbl = new JLabel("");
		warningLbl.setFont(new Font("Arial", Font.BOLD, 16));
		warningLbl.setForeground(Color.RED);
		warningLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Button for existing supplier
		JButton supplierBtn = new JButton("Existing supplier");
		supplierBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If validate form is true
				if(validateForm()) {
					try {
						//Check if supplier exists
						int id = Integer.parseInt(JOptionPane.showInputDialog(frmNewProduct, "Please enter supplier ID"));
						if(data.doesSupplierExist(id)) {
							//Add product (uses REGEX Pattern to remove pound symbol and comma from price double)
							if(data.addProduct(id, nameField.getText(), descField.getText(), Double.parseDouble(priceField.getText().replaceAll("[,|£]", "")), Integer.parseInt(quantityField.getText()))) {
								frmNewProduct.dispose();
								JOptionPane.showMessageDialog(null, "Product added");
							}else {
								warningLbl.setText("Something went wrong. Please restart.");
							}
						} else {
							throw new Exception();
						}
					}catch(Exception err) {
						warningLbl.setText("That is not an existing supplier ID.");
					}
				}
			}
		});
		supplierBtn.setFont(new Font("Arial", Font.PLAIN, 16));
		
		//Button for new supplier button
		JButton newSupplierBtn = new JButton("New supplier");
		newSupplierBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If validate form is true
				if(validateForm()) {
					try {
						productName = nameField.getText();
						desc = descField.getText();
						//uses REGEX Pattern to remove pound symbol and comma from double
						price = Double.parseDouble(priceField.getText().replaceAll("[,|£]", ""));
						System.out.println(price);
						quantity = Integer.parseInt(quantityField.getText());
						//Form to create new supplier
						JPanel panel = supplierForm();
						frmNewProduct.getContentPane().setLayout(new BorderLayout());
						frmNewProduct.getContentPane().removeAll();
						frmNewProduct.getContentPane().add(panel, BorderLayout.CENTER);
						frmNewProduct.revalidate();
						frmNewProduct.repaint();
					}catch(Exception err) {
						warningLbl.setText("Something went wrong, restart program.");
					}
				}
			}
		});
		newSupplierBtn.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(frmNewProduct.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(supplierBtn, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(newSupplierBtn, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
						.addComponent(descLbl, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
						.addComponent(nameLbl, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
						.addComponent(nameField, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
						.addComponent(descField, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(priceLbl, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
									.addGap(35))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(priceField, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(quantityLbl, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
								.addComponent(quantityField, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)))
						.addComponent(warningLbl, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
					.addGap(44))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(17)
					.addComponent(nameLbl)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(descLbl, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(descField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(priceLbl, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(quantityLbl, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(priceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(quantityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addComponent(warningLbl, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(newSupplierBtn, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addComponent(supplierBtn, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(39, Short.MAX_VALUE))
		);
		frmNewProduct.getContentPane().setLayout(groupLayout);
		return frmNewProduct;
	}
	
	//New supplier form
	private JPanel supplierForm() {
		JPanel panel = new JPanel();
	
		JLabel supplierWarningLbl = new JLabel("");
		supplierWarningLbl.setForeground(Color.RED);
		supplierWarningLbl.setHorizontalAlignment(SwingConstants.CENTER);
		supplierWarningLbl.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel nameLbl = new JLabel("Company name");
		nameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		nameLbl.setFont(new Font("Arial", Font.BOLD, 16));
		nameLbl.setForeground(new Color(11, 26, 106));
		
		companyNameField = new JTextField();
		companyNameField.setHorizontalAlignment(SwingConstants.CENTER);
		companyNameField.setFont(new Font("Arial", Font.PLAIN, 16));
		companyNameField.setColumns(10);
		
		JLabel phoneLbl = new JLabel("Phone number");
		phoneLbl.setHorizontalAlignment(SwingConstants.CENTER);
		phoneLbl.setFont(new Font("Arial", Font.BOLD, 16));
		phoneLbl.setForeground(new Color(11, 26, 106));
		
		phoneField = new JTextField();
		phoneField.setHorizontalAlignment(SwingConstants.CENTER);
		phoneField.setFont(new Font("Arial", Font.PLAIN, 16));
		phoneField.setColumns(10);
		
		JLabel emailLbl = new JLabel("Email address");
		emailLbl.setHorizontalAlignment(SwingConstants.CENTER);
		emailLbl.setFont(new Font("Arial", Font.BOLD, 16));
		emailLbl.setForeground(new Color(11, 26, 106));
		
		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setFont(new Font("Arial", Font.PLAIN, 16));
		emailField.setColumns(10);
		
		JButton confirmBtn = new JButton("Confirm listing");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If one of the fields is empty
				if(companyNameField.getText().isBlank() || phoneField.getText().isBlank() || emailField.getText().isBlank()) {
					//Warn users to fill in the field
					supplierWarningLbl.setText("Please fill in all fields.");
				}else {
					//If phone number isn't valid
					if(data.isValidPhone(phoneField.getText()) == false) {
						supplierWarningLbl.setText("Phone number is not in valid format.");
					} else {
						//If email isn't valid
						if(data.isValidEmail(emailField.getText()) == false) {
							supplierWarningLbl.setText("Email is not in valid format.");
						} else {
							 //If add supplier is successful, add product, and update table
							 if(data.addSupplier(companyNameField.getText(), phoneField.getText(), emailField.getText())) {
								 data.addProduct(-1, productName, desc, price, quantity);
								 frmNewProduct.dispose();
								 JOptionPane.showMessageDialog(null, "Successfully added new product and supplier.");
								 Dashboard.updateTable("Suppliers");  
							 }else {
								 supplierWarningLbl.setText("Error please restart program.");
							}
						}
					}
				}
			}
		});
		confirmBtn.setFont(new Font("Arial", Font.BOLD, 16));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(nameLbl, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(63)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(phoneLbl, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
								.addComponent(companyNameField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
								.addComponent(phoneField, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
								.addComponent(emailLbl, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
								.addComponent(emailField, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
								.addComponent(confirmBtn, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
							.addGap(54))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(supplierWarningLbl, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addComponent(nameLbl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(companyNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(phoneLbl)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(phoneField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(23)
					.addComponent(emailLbl, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(emailField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(supplierWarningLbl)
					.addGap(18, 18, Short.MAX_VALUE)
					.addComponent(confirmBtn, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		panel.setLayout(gl_panel);
		return panel;
	}
	
	//Validate product fields contents
	private boolean validateForm() {
		//If one of the fields is empty display error messages
		if(nameField.getText().isBlank() || descField.getText().isBlank() || priceField.getText().isBlank() || quantityField.getText().isBlank()) {
			warningLbl.setText("Please fill in all fields");
			return false;
		}else { 
			//If the name of the product is less than 5 in length or more than 50, display error message
			if(nameField.getText().length() < 5 || nameField.getText().length() > 50) {
				warningLbl.setText("Please make the name field 5-50 characters.");
				return false;
			}else {
				//If the name of the product is less than 5 in length or more than 50, display error message
				if(descField.getText().length() < 10 || descField.getText().length() > 250) {
					warningLbl.setText("Please make the description 10-250 characters.");
					return false;
				}else {
					try {
						//Check if price and quantity can be numeral (uses REGEX Pattern to remove pound symbol and comma from double)
						double testPrice = Double.parseDouble(priceField.getText().replaceAll("[,|£]", ""));
						if(testPrice > 100000) {
							//Display a more appropriate message than "Come on... seriously, in this economy!? You silly goose."
							warningLbl.setText("Price cannot be over £100,000.");
							return false;
						}
						int testQuantity = Integer.parseInt(quantityField.getText());
						//If quantity is less or equal to minimum, quantity is more than max or price is less or equal to minimum, throw exception
						if(testQuantity <= 0 || testQuantity > 20 || testPrice <= 0.00) {
							throw new Exception();
						}
						//Throw error if they can't be parsed
					}catch(Exception e) {
						warningLbl.setText("Price and quantity must be valid values.");
						return false;
					}
				}
			}
		}

		return true;
	}
}
