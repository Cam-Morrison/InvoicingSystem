package cam.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import cam.business.logic.DataManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Toolkit;

//Author: Cameron Morrison 569530
//Second year graded unit project. 

public class NewOrder {
	
	private DataManager data = new DataManager();
	private JTextField forenameField;
	private JTextField emailField;
	private JTextField surnameField;
	private JTextField streetField;
	private JTextField postcodeField;
	private JTextField countryFIeld;
	private JTextField cityField;
	private JTextField productField;
	private JTextField quantityField;
	private JLabel productWarningLbl;
	private String forename;
	private String surname;
	private String email;
	private String street;
	private String city;
	private String postcode;
	private String country;
	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();	
	private ArrayList<Integer> productIds = new ArrayList<Integer>();
	private ArrayList<Integer> quantities = new ArrayList<Integer>();
	private ArrayList<Double> discounts = new ArrayList<Double>();
	private JTextField discountField;
	

	public JFrame clientInfoForm(){
		JFrame frmCustomerDetails = new JFrame();
		frmCustomerDetails.setResizable(false);
		frmCustomerDetails.setTitle("Enter customer details");
		frmCustomerDetails.setIconImage(Toolkit.getDefaultToolkit().getImage(NewOrder.class.getResource("/cam/gui/icon.png")));
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		
		JLabel warningLbl = new JLabel("");
		warningLbl.setForeground(Color.RED);
		warningLbl.setHorizontalAlignment(SwingConstants.CENTER);
		warningLbl.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel foreNameLbl = new JLabel("Forename");
		foreNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		foreNameLbl.setFont(new Font("Arial", Font.BOLD, 16));
		foreNameLbl.setForeground(new Color(11, 26, 106));
		
		forenameField = new JTextField();
		forenameField.setHorizontalAlignment(SwingConstants.LEFT);
		forenameField.setFont(new Font("Arial", Font.PLAIN, 16));
		forenameField.setColumns(10);
		
		JLabel surnameLbl = new JLabel("Surname");
		surnameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		surnameLbl.setFont(new Font("Arial", Font.BOLD, 16));
		surnameLbl.setForeground(new Color(11, 26, 106));
		
		JLabel emailLbl = new JLabel("Email address");
		emailLbl.setHorizontalAlignment(SwingConstants.CENTER);
		emailLbl.setFont(new Font("Arial", Font.BOLD, 16));
		emailLbl.setForeground(new Color(11, 26, 106));
		
		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.LEFT);
		emailField.setFont(new Font("Arial", Font.PLAIN, 16));
		emailField.setColumns(10);
		
		surnameField = new JTextField();
		surnameField.setHorizontalAlignment(SwingConstants.LEFT);
		surnameField.setFont(new Font("Arial", Font.PLAIN, 16));
		surnameField.setColumns(10);
	
		streetField = new JTextField();
		streetField.setHorizontalAlignment(SwingConstants.LEFT);
		streetField.setFont(new Font("Arial", Font.PLAIN, 16));
		streetField.setColumns(10);
		
		JLabel streetLbl = new JLabel("Street address");
		streetLbl.setHorizontalAlignment(SwingConstants.CENTER);
		streetLbl.setForeground(new Color(11, 26, 106));
		streetLbl.setFont(new Font("Arial", Font.BOLD, 16));
		
		postcodeField = new JTextField();
		postcodeField.setColumns(10);
		
		countryFIeld = new JTextField();
		countryFIeld.setColumns(10);
		
		cityField = new JTextField();
		cityField.setColumns(10);
		
		JLabel cityLbl = new JLabel("City");
		cityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		cityLbl.setForeground(new Color(11, 26, 106));
		cityLbl.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel lblCountry = new JLabel("Country");
		lblCountry.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountry.setForeground(new Color(11, 26, 106));
		lblCountry.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel lblZipCode = new JLabel("ZIP code");
		lblZipCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblZipCode.setForeground(new Color(11, 26, 106));
		lblZipCode.setFont(new Font("Arial", Font.BOLD, 16));
		
		JButton confirmBtn = new JButton("Continue");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If one of the fields is blank
				if(forenameField.getText().isBlank() || surnameField.getText().isBlank() || emailField.getText().isBlank() || 
						streetField.getText().isBlank() || postcodeField.getText().isBlank() || countryFIeld.getText().isBlank() || cityField.getText().isBlank()) {
					//Set warning label
					warningLbl.setText("Please fill in all fields.");
				}else {
					forename = forenameField.getText().toLowerCase().trim();
					forename = forename.substring(0, 1).toUpperCase() + forename.substring(1);
					surname = surnameField.getText().toLowerCase().trim();
					surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
					//If name isn't valid, display error
					if(data.isValidName(forename) == false) {
						warningLbl.setText("Forename is not in a valid format.");
					} else {
						//If name isn't valid, display error
						if(data.isValidName(surname) == false) {
							warningLbl.setText("Surname is not in valid format.");
						} else {
							//If email isn't valid, display error
							email = emailField.getText().trim();
							if(data.isValidEmail(email) == false) {
								warningLbl.setText("Email is not in valid format.");
							} else {
								//If post code isn't valid, display error
								if(data.isValidPostcode(postcodeField.getText()) == false) {
									warningLbl.setText("Please enter a valid UK post code.");
								}else {
									//If country doesn't match one of the 4 UK countries then display error
									if (!countryFIeld.getText().trim().toLowerCase().matches("scotland|england|wales|northern ireland")){
										warningLbl.setText("Please enter a country within the UK.");
									}else {
										//save variables for later, call product select page.
										street = streetField.getText().trim();
										city = cityField.getText().toLowerCase().trim();
										city = city.substring(0, 1).toUpperCase() + city.substring(1);
										postcode = postcodeField.getText().trim();
										country = countryFIeld.getText().toLowerCase().trim();
										country = country.substring(0, 1).toUpperCase() + country.substring(1);
										frmCustomerDetails.dispose();
										productOrderPage();
									}	
								}
								
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
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(warningLbl, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(foreNameLbl, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
								.addComponent(forenameField, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(10)
									.addComponent(surnameField, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
								.addComponent(surnameLbl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(emailField, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addGap(63)
							.addComponent(emailLbl, GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
							.addGap(54))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(confirmBtn, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(streetLbl, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(streetField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(cityField, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(countryFIeld, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(postcodeField, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(cityLbl, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblCountry, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblZipCode, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(foreNameLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(surnameLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(forenameField)
						.addComponent(surnameField, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(emailLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(emailField, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
					.addGap(4)
					.addComponent(streetLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(streetField, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cityLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
						.addComponent(lblCountry, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
						.addComponent(lblZipCode, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
					.addGap(9)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cityField)
						.addComponent(countryFIeld)
						.addComponent(postcodeField))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(warningLbl, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(confirmBtn, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		frmCustomerDetails.getContentPane().add(panel);
		return frmCustomerDetails;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	private void productOrderPage() {
		JFrame frmProduct = new JFrame();
		frmProduct.getContentPane().setBackground(new Color(255, 255, 255));
		frmProduct.setTitle("Product");
		frmProduct.setResizable(false);
		frmProduct.setIconImage(Toolkit.getDefaultToolkit().getImage(NewOrder.class.getResource("/cam/gui/icon.png")));
		
		JLabel productIdLbl = new JLabel("Product ID");
		productIdLbl.setHorizontalAlignment(SwingConstants.CENTER);
		productIdLbl.setFont(new Font("Arial", Font.BOLD, 16));
		productIdLbl.setForeground(new Color(11, 26, 106));
		
		productField = new JTextField();
		productField.setColumns(10);
		
		JLabel quantityLbl = new JLabel("Quantity");
		quantityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		quantityLbl.setFont(new Font("Arial", Font.BOLD, 16));
		quantityLbl.setForeground(new Color(11, 26, 106));
		
		quantityField = new JTextField();
		quantityField.setColumns(10);
		
		productWarningLbl = new JLabel("");
		productWarningLbl.setForeground(Color.RED);
		productWarningLbl.setHorizontalAlignment(SwingConstants.CENTER);
		productWarningLbl.setFont(new Font("Arial", Font.BOLD, 16));
		
		//Confirm purchase
		JButton confirmBtn = new JButton("Complete");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If one of the fields is empty
				if(productField.getText().isBlank() || quantityField.getText().isBlank()) {
					if(productIds.isEmpty()) {
						productWarningLbl.setText("Please add a product to cart.");
					}else { //If there is an item in cart and no text in fields
						createOrder();
					}
				}else { //validate items in field and place order
					if(validateProduct()) {
						createOrder();
					}
				}
			}
			//Place order
			private void createOrder() {
				frmProduct.dispose();
				//Choose shipping method
				String[] shipping = {"1st Class","2nd Class","UK standard delivery"};
				String shippingMethod = (String) JOptionPane.showInputDialog(null, "Please select a shipping method", 
						"Shipping options", JOptionPane.QUESTION_MESSAGE, null, shipping, shipping[2]);
				if(shippingMethod == null) {
					JOptionPane.showMessageDialog(null, "Your order has been cancelled.");
				}else {
					//Record customer, place order
					int customerId = data.addCustomer(forename, surname, email, street, city, postcode, country);
					if(data.newOrder(customerId, productIds, quantities, discounts, shippingMethod)) {
						JOptionPane.showMessageDialog(null, "Your order has been placed.");
						dashboard.updateTable("Transactions");
					}else {
						JOptionPane.showMessageDialog(null, "Something went wrong, please restart the program.");
					}
				}
			}
		});
		//button to add another product to basket
		JButton moreBtn = new JButton("add product");
		moreBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validateProduct()) {
					frmProduct.dispose();
					productOrderPage();
				}
			}
		});
		
		discountField = new JTextField();
		discountField.setColumns(10);

		JLabel discountLbl = new JLabel("Discount (%)");
		discountLbl.setForeground(new Color(11, 26, 106));
		discountLbl.setHorizontalAlignment(SwingConstants.CENTER);
		discountLbl.setFont(new Font("Arial", Font.BOLD, 16));
		GroupLayout groupLayout = new GroupLayout(frmProduct.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(productWarningLbl, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
						.addComponent(productIdLbl, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
						.addComponent(productField, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(moreBtn, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
							.addGap(33)
							.addComponent(confirmBtn, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(quantityLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(quantityField, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
							.addGap(36)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(discountLbl, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
								.addComponent(discountField, Alignment.LEADING))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(productIdLbl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(productField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(quantityLbl, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(discountLbl, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(quantityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(discountField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(productWarningLbl)
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(confirmBtn, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
						.addComponent(moreBtn, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
					.addContainerGap())
		);
		frmProduct.getContentPane().setLayout(groupLayout);
		
		frmProduct.setVisible(true);
		frmProduct.setSize((int)size.getWidth() / 5, (int)(size.getHeight() / 3.5));
		frmProduct.setResizable(false);
		frmProduct.setLocationRelativeTo(null);
	}
	
	//Validates product and adds to basket
	private boolean validateProduct() {
		try {
			//Product ID and quantity fields must be filled in, parse to check they are in right format
			int product = Integer.parseInt(productField.getText());
			int quantity = Integer.parseInt(quantityField.getText());
			double discount;
			//If discount field is empty, make it 0% by default
			if(discountField.getText().isEmpty()) {
				discount = 0.00;
			}else { //If its not empty, check its the right format
				discount = Double.parseDouble(discountField.getText());
			}
			//If product isn't disabled (status 0)
			if(data.forSale(product)) {
				//Get the number of inventory in stock
				int inStock = data.getStockQuantity(product);
				//Check if that item is already in cart
				if(productIds.contains(product)) {
					productWarningLbl.setText("That product is already in your cart.");
				}else {
					//If quantity of purchase is more than stock
					if(quantity > inStock) {
						//Deny service and display error
						productWarningLbl.setText("There are only " + inStock + " in stock.");
					}else {
						//Quantity cannot be less or equal to zero.
						if(quantity <= 0) {
							productWarningLbl.setText("Quantity cannot be 0.");
						}else {
							//Discount cannot be negative or more than 100%
							if(discount < 0.00 || discount > 100.00) {
								productWarningLbl.setText("Discount cannot be negative.");
							}else {
								//Add product info to lists
								productIds.add(product);
								quantities.add(quantity);
								discounts.add(discount);
								return true;				
							}
						}
					}
				}
			}else {
				productWarningLbl.setText("ID does not exist.");
			}
		}catch(Exception err) {
			productWarningLbl.setText("Fields must contain only numbers.");
		}
		return false;
	}
}
