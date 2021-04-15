package cam.business.logic;

import java.awt.Desktop;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

//Author: Cameron Morrison 569530
//Second year graded unit project. 

public class DataManager {

	//Regular expressions 
	private final Pattern namePattern = Pattern.compile("^[A-Z][a-z]+$");
	private final Pattern emailPattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private final Pattern phonePattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$");
    private final Pattern postcodePattern = Pattern.compile("^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$");
    //Lists of objects
	private static ArrayList<Transactions> orderList = new ArrayList<Transactions>();
	private static ArrayList<Clients> clientList = new ArrayList<Clients>();
	private static ArrayList<Suppliers> supplierList = new ArrayList<Suppliers>();
	private static  ArrayList<Products> productList = new ArrayList<Products>();
	private static ArrayList<Staff> staffList = new ArrayList<Staff>();
	//Other instance variables 
	private Date selectedDate;
	private String updateSQL;
	private static int currentStaffMember;
	
	//Validate login credentials 
	public boolean validateLogin(int staffID, String password) throws SQLException {
		//Checks if the program has already loaded in staff 
		if(staffList.isEmpty() == true) {
			//If not load staff table
			if(loadDatabase(1) == false) {
				return false;
			}
		}
		//For number of staff 
		for(Staff admin : staffList) {
			//Check if staff ID is equal to user input
			if(admin.getStaffID() == staffID) {
				
				//SHA-1 hashing algorithm 
				StringBuilder hash = new StringBuilder();
				try {
					MessageDigest sha = MessageDigest.getInstance("SHA-1");
					byte[] hashedBytes = sha.digest(password.getBytes());
					char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
					for (int idx = 0; idx < hashedBytes.length; idx++) {
						byte b = hashedBytes[idx];
						hash.append(digits[(b & 0xf0) >> 4]);
						hash.append(digits[b & 0x0f]);
					}
				} catch (NoSuchAlgorithmException e) {
					return false;
				}

				//Validate login password hash - check hash equals hash stored in database
				if(hash.toString().equals(staffList.get(staffID - 1).getPasswordHash())) {
					loadDatabase(2);
					currentStaffMember  = staffID;
				    return true;
				}
				//hash 40bd001563085fc35165329ea1ff5c5ecbdbbeef, pass 123, staff id 1
				//hash 5f6955d227a320c7f1f6c7da2a6d96a851a8118f, pass 321, staff id 2
			}
		}
		return false;
	}
	
	//Checks productList for product
	public boolean doesProductExist(int id) {
		for(Products p : productList) {
			if(p.getProductId() == id) {
				return true;
			}
		}
		return false;
	}
	
	//Are there any orders before date
	public boolean ordersBeforeDate(Date date) {
		for(Transactions o : orderList) {
			if(o.getOrderDate().before(date)) {
				this.selectedDate = date;
				return true;
			}
		}
		return false;
	}
	
	//Returns the status of the product (for sale / not for sale)
	public boolean forSale(int id) {
		if(doesProductExist(id)) {
			for(Products p : productList) {
				if(p.getProductId() == id && p.getAvaliable() == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Checks supplier exists
	public boolean doesSupplierExist(int id){
		for(Suppliers s : supplierList) {
			if(s.getSupplierID() == id) {
				return true;
			}
		}
		return false;
	}	
	
	//Adds a new supplier
	public boolean addSupplier(String name, String phoneNumber, String email) {
		try {
			int id = supplierList.get(supplierList.size() - 1).getSupplierID();
			id += 1;
			Suppliers s = new Suppliers(id, name, phoneNumber, email);
			supplierList.add(s);
			storeDatabase(4, id);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	//Checks order ID exists
	public boolean isOrderID(int id){
		for(Transactions t : orderList) {
			if(t.getOrderId() == id) {
				return true;
			}
		}
		return false;
	}
	
	//Updates product in list
	public boolean updateProduct(int id, int quantity, String desc, double price) {
		if(doesProductExist(id) == true) {
			int count = 0;
			//Update product class
			if(quantity <= 20 && quantity >= 0) {
				productList.get(id - 1).setStockQuantity(quantity);
				count++;
			}
			if(desc.equals("") == false && desc.length() > 10 && desc.length() < 250) {
				productList.get(id - 1).setDesc(desc);
				count++;
			}
			if(price > 0.01) {
				System.out.println(price);
				productList.get(id - 1).setPrice(price);
				count++;
			}
			if(count >= 1) {
				//Save to storage
				this.updateSQL = "UPDATE product SET quantity = " + productList.get(id - 1).getStockQuantity() + ", description = '" 
						+ productList.get(id - 1).getDescription() + "', price = " + productList.get(id - 1).getPrice() 
						+ " WHERE product_id = " + productList.get(id - 1).getProductId()  + ";"; 
				storeDatabase(2, id);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	//Returns stock count
	public int getStockQuantity(int id) {
		for(Products p : productList) {
			if(p.getProductId() == id) {
				return p.getStockQuantity();
			}
		}
		return 0;
	}
	
	//Removes product from list
	public boolean removeProduct(int id) {
		if(doesProductExist(id) == true) {
			for(Products p : productList) {
				if(p.getProductId() == id) {
					//Change product availability status in class
					p.swapAvaliable();
					//Change product availability status in DB
					storeDatabase(1, id);
					return true;
				}
			}
		}
		return false;
	}
	
	//Creates a new product
	public boolean addProduct(int id, String name, String desc, double price, int quantity) {
		try {
			if(id < 0) {
				id = supplierList.get(supplierList.size() - 1).getSupplierID();
			}
			int maxID = productList.get(productList.size() - 1).getProductId();
			maxID += 1;
			Products p = new Products(maxID, name, price, quantity, id, 1, desc);
			productList.add(p);
			storeDatabase(3, maxID);
			return true;
		}catch(Exception e) {
			return false;
		}	
	}
	
	//Create a new order
	public boolean newOrder(int customerId, ArrayList<Integer> productIds, ArrayList<Integer> quantities, ArrayList<Double> discounts, String shippingMethod) {
		try {
			//Get the next increment in orderID
			int newOrderID = orderList.get(orderList.size() - 1).getOrderId() + 1;
			//Create an ArrayList to store all items 
			ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
			//Populate ArrayList
			for(int i = 0; i < productIds.size(); i++) {
				InvoiceItem item = new InvoiceItem(quantities.get(i), discounts.get(i), newOrderID, productIds.get(i), productList.get(productIds.get(i) - 1).getPrice());
				items.add(item);
			}
			//Get current time
	        long millis=System.currentTimeMillis();  
	        java.sql.Date date = new java.sql.Date(millis);  
	        //Calculate total cost including VAT
	        double totalCost = calculateTotal(quantities, discounts, productIds);
	        //Calculate the expected delivery date based off royal mail average delivery times
	        Calendar deliveryDate = Calendar.getInstance();
	        switch(shippingMethod) {
	        	case "1st Class": //1 day
	        		deliveryDate.add(Calendar.DAY_OF_YEAR,1);
	        		break;
	        	case "2nd Class": //3 days
	        		deliveryDate.add(Calendar.DAY_OF_YEAR,3);
	        		break;
	        	default: //Standard UK delivery 5 days
	        		deliveryDate.add(Calendar.DAY_OF_YEAR,5);
	        		break;
	        }
	        //Date of expected delivery
	        java.sql.Date dueDate = new java.sql.Date(deliveryDate.getTimeInMillis());
	       
	        //Find highest shippingID and add to the increment 
	        int shippingId = 0;
	        for(Transactions t : orderList){
	        	if(t.getShippingId() > shippingId) {
	        		shippingId = t.getShippingId();
	        	}
	        }
	        shippingId += 1;
	        
	        //Create order 
			Transactions order = new Transactions(newOrderID, date, totalCost, date, customerId, DataManager.currentStaffMember, shippingId,
					shippingMethod, dueDate, items);
			orderList.add(order);
			
			//Store to database
			storeDatabase(6, newOrderID);
			
			//Update quantity left in stock
			for(InvoiceItem i : items) {
				int avaliable = productList.get(i.getProductId() - 1).getStockQuantity();
				productList.get(i.getProductId() - 1).setStockQuantity(avaliable - i.getPurchaseQuantity());
				storeDatabase(7, i.getProductId());
			}
			return true;	
		}catch(Exception e){
			return false;
		}
	}
	
	// Method to verify the authenticity of user's entered name
	public boolean isValidName(String name) {
		return namePattern.matcher(name).matches();
	}

	// Method to verify the authenticity of user's entered email
	public boolean isValidEmail(String email) {
		return emailPattern.matcher(email).matches();
	}
	
	//Method to verify the authenticity of the user's entered phone number
	public boolean isValidPhone(String phone) {
		return phonePattern.matcher(phone).matches();
	}
	
	//Method to verify the authenticity of the user's entered UK post code
	public boolean isValidPostcode(String postcode) {
		return postcodePattern.matcher(postcode).matches();
	}
	
	//Stores newly entered information to database
	private boolean storeDatabase(int option, int ID) {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/invoicingsystem?&serverTimezone=UTC";
		String user = "root";
		String password = "root";

		try {
			//Creating connection
			Connection myConn = DriverManager.getConnection(url, user, password);
			Statement myStmt = myConn.createStatement();	
			String sql = null;
			switch(option) {
				case 1: //Remove or reintroduce product
					sql = "UPDATE product SET avaliable = " + productList.get(ID - 1).getAvaliable() + " WHERE product_id = " + (ID);
					myStmt.executeUpdate(sql);
					break;	
				case 2: //Update product details
					sql = this.updateSQL;
					myStmt.executeUpdate(sql);
					break;
				case 3: //add new product
					Products p = productList.get(ID - 1);
					
					sql = "INSERT INTO `product` (product_id, name, quantity, price, supplier_supplier_id, avaliable, description)" +
						  " VALUES (" + p.getProductId() + ", '" + p.getName() + "', " + p.getStockQuantity()
						  + ", " + p.getPrice() + ", " + p.getSupplierId() + ", " + p.getAvaliable()
						  + ", '" + p.getDescription() + "');";
					myStmt.executeUpdate(sql);
					break;
				case 4: //Stores a new supplier
					Suppliers s = supplierList.get(ID - 1);
					sql = "INSERT INTO `supplier` " +
						  "VALUES (" + s.getSupplierID() + ", '" + s.getCompanyName() + "', '" + s.getPhoneNumber()
						  + "', '" + s.getEmail() + "');";
					myStmt.executeUpdate(sql);
					break;
				case 5: //Store new customer
					Clients c = clientList.get(ID - 1);
					sql = "INSERT INTO `customer` "
						+ "VALUES (" + c.getCustomerId() + ", '" + c.getFirstName() + "', '" + c.getLastName() + "', '" + c.getEmail() + "');";
					myStmt.executeUpdate(sql);
					sql = "INSERT INTO `address` "
						+ "VALUES ('" + c.getStreetAddress() + "', '" + c.getCity() + "', '" + c.getZipCode() + "', '" + c.getCountry() + "', " + c.getCustomerId() + ");";
					myStmt.executeUpdate(sql);	
					break;
					
				case 6: //store new transaction details
					
					//Saves order information
					Transactions o = orderList.get(ID - 1);
					sql = "INSERT INTO `order`" 
							+ " VALUES (" + o.getOrderId() + ", '" + o.getOrderDate() + "', " + o.getTotalCost() + ", '" +
							o.getDueDate() + "', " + o.getCustomerId() + ", " + o.getStaffId() + ");";
					myStmt.executeUpdate(sql);
					
					//Saves all the invoice items
					for(InvoiceItem i : orderList.get(ID - 1).getItems()) {
						sql = "INSERT INTO `invoice_item` " +
								  "VALUES (" + i.getPurchaseQuantity() + ", " + i.getDiscount() + ", " + i.getOrderId()
								  + ", " + i.getProductId() + ", " + i.getPurchasePrice() + ");";
						myStmt.executeUpdate(sql);
					}
	
					sql = "INSERT INTO `shipping` "
							+ "VALUES (" + o.getShippingId() + ", '" + o.getShippingMethod() + "', '" + o.getEstimatedDate() + "', " + o.getOrderId() + ");";
					myStmt.executeUpdate(sql);
					break;
					
				case 7: //Update product quantity
					sql = "UPDATE `product` SET quantity = " + productList.get(ID - 1).getStockQuantity() +  " WHERE product_id = " + productList.get(ID - 1).getProductId() + ";";
					myStmt.executeUpdate(sql);
					break;
			}
			myConn.close();	
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}

	//Loads any previous information from database
	private boolean loadDatabase(int option) throws SQLException {
		//Database connection information
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/invoicingsystem?&serverTimezone=UTC";
		String user = "root";
		String password = "root";

		try {
			//Creating connection
			Connection myConn = DriverManager.getConnection(url, user, password);
			Statement myStmt = myConn.createStatement();
			Statement myStmt2 = myConn.createStatement();
			String sql = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			
			switch(option) {
				case 1: //Load in staff information to validate login credentials 
					sql = "SELECT * FROM staff";
				    rs = myStmt.executeQuery(sql);
				    while (rs.next()) {	
				    	Staff staff = new Staff(rs.getInt("staff_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone_number"),  rs.getString("job"), rs.getString("password_hash"));
				    	staffList.add(staff);
				    }
				    break;
				    
				case 2:	//Once login is successful, load in other information from database
					sql = "SELECT * FROM customer, address WHERE customer_id = customer_customer_id";
				    rs = myStmt.executeQuery(sql);
				    while (rs.next()) {	
				    	Clients customer = new Clients(rs.getString("first_name"), rs .getString("last_name"), rs.getInt("customer_id"), rs.getString("email"),  
				    			rs.getString("street_address"), rs.getString("city"), rs.getString("zip_code"), rs.getString("country"));
				    	clientList.add(customer);
				    }
				      
					sql = "SELECT * FROM `order`, `shipping` WHERE order_id = order_order_id";
				    rs = myStmt.executeQuery(sql);
				    while (rs.next()) {
			    	
				        ArrayList<InvoiceItem> list = new ArrayList<InvoiceItem>();
				        sql = "SELECT * FROM `invoice_item` WHERE order_order_id = " + rs.getInt("order_id");    
				        rs2 = myStmt2.executeQuery(sql);
				        while(rs2.next()){
				        	InvoiceItem item = new InvoiceItem(rs2.getInt("purchase_quantity"), rs2.getDouble("discount"),
				        			rs2.getInt("order_order_id"), rs2.getInt("product_product_id"), rs2.getDouble("purchase_price"));
				        	list.add(item);
				        }
				      		        
				    	Transactions order = new Transactions(rs.getInt("order_id"), rs.getDate("order_date"), rs.getDouble("total_cost"), rs.getDate("due_date"),  
				    			rs.getInt("customer_customer_id"), rs.getInt("staff_staff_id"), rs.getInt("shipping_id"), rs.getString("shipping_method"),
				    			rs.getDate("estimated_delivery_date"), list);
				    	orderList.add(order); 
				    }
				    
				    sql = "SELECT * FROM `product`";
				    rs = myStmt.executeQuery(sql);
				    while (rs.next()) {
				    		Products p = new Products(rs.getInt("product_id"), rs.getString("name"), 
				    				rs.getDouble("price"), rs.getInt("quantity"), rs.getInt("supplier_supplier_id"), rs.getInt("avaliable"), rs.getString("description"));
				    		productList.add(p);
				    }

				    sql = "SELECT * FROM `supplier`";
				    rs = myStmt.executeQuery(sql);
				    while (rs.next()) {
				    	Suppliers s = new Suppliers(rs.getInt("supplier_id"), rs.getString("name"), rs.getString("phone"), rs.getString("email"));
				    	supplierList.add(s);
				    }
				    break;
			}
		    myConn.close();
		    return true;
		    
		}catch(CommunicationsException e) { //Connection to mySQL port unavailable; inform user
			JOptionPane.showMessageDialog(null, "Error connecting to database. Please free up port 3306. \nYou will now be redirected to a guide by medium.com.", 
					"Communications link failure", JOptionPane.ERROR_MESSAGE);
		    try { //Open web link on how to fix issue for user
		        Desktop.getDesktop().browse(new URL("https://medium.com/@javatechie/how-to-kill-the-process-currently-using-a-port-on-localhost-in-windows-31ccdea2a3ea").toURI());
		    } catch (Exception ex) {}
			System.exit(0);
		}
		return false;
	} 
	
	//Returns a single orders information
	public Transactions returnTransactions(int id){
		return orderList.get(id - 1); 
	}
	
	//Returns an informative string for the payment box on the invoice panel
	public String returnPayment(int id) {
		return "" + orderList.get(id - 1).getDueDate();
	}
	
	//Returns an informative string for the shipping box on the invoice panel
	public String returnShipping(int id) {
		return orderList.get(id - 1).toString();
	}
	
	//Returns an informative string for the staff box on the invoice panel
	public String returnStaffInfo(int id) {
		int staffId = orderList.get(id - 1).getStaffId();
		return staffList.get(staffId - 1).toString();
	}
	
	//Calculate total cost using each items quantity and discount percentage 
	public double calculateTotal(ArrayList<Integer> quantities, ArrayList<Double> discounts, ArrayList<Integer> productIds) {
		double totalCost = 0;
		double price;
		//For every item associated with order
		for(int i = 0; i < productIds.size(); i++) {
			//invoice item price is equal to price - discount x quantity 
			price = ((productList.get(productIds.get(i) - 1).getPrice()) * quantities.get(i));
			//UK VAT 20%
			price = price + ((price / 100) * 20);
			//Add to total cost 
			totalCost += (price - ((price / 100) * discounts.get(i)));
		} 
		return totalCost;
	}

	//Method to generate and return populated table
	public DefaultTableModel generateTable(String option, int id) {
		DefaultTableModel tableModel;
		switch(option) {
			default: //Default Orders table
				Object orderColumns[] = { "ID", "Date", "Customer ID", "Staff ID", "Shipping ID", "Cost"};
				tableModel = new DefaultTableModel(orderColumns, 0);
				for(Transactions t : orderList) {
						tableModel.addRow(new Object[] { t.getOrderId(), t.getOrderDate(),
						t.getCustomerId(), t.getStaffId(), t.getShippingId(), "£" + t.getTotalCost()});
				} 
				break;
			case "Customers": //Clients users table
				Object clientsColumns[] = {"ID", "Name", "Email", "Address", "City", "Zip code", "Country"};
				tableModel = new DefaultTableModel(clientsColumns, 0);
				if(id == 0) { //If ID = 0, then default option show all users
					for(Clients c : clientList) {
						tableModel.addRow(new Object[] { c.getCustomerId(), c.getFirstName() + " " + c.getLastName(),
						c.getEmail(), c.getStreetAddress(), c.getCity(), c.getZipCode(), c.getCountry()});
					}
				} else { //If ID is specific then only return information for that one ID.
					Clients c = clientList.get(id - 1);
					tableModel.addRow(new Object[] { c.getCustomerId(), c.getFirstName() + " " + c.getLastName(),
					c.getEmail(), c.getStreetAddress(), c.getCity(), c.getZipCode(), c.getCountry()});
				}
				break;
			case "Products": //Product table
				Object productColumns[] = { "ID", "Name", "Price", "Description", "Quantity", "Supplier", "Status"};
				tableModel = new DefaultTableModel(productColumns, 0);
				for(Products p : productList) {
					tableModel.addRow(new Object[] { p.getProductId(), p.getName(), "£" + p.getPrice(),
							p.getDescription(), p.getStockQuantity(), p.getSupplierId(), p.getAvaliable()});
				}
				break;
			case "Administrators": //Staff table
				Object staffColumns[] = {"ID", "Name", "Phone number", "Job", "Hash"};
				tableModel = new DefaultTableModel(staffColumns, 0);
				for(Staff s : staffList) {
					tableModel.addRow(new Object[] { s.getStaffID(), s.getFirstName() + " " + s.getLastName(), s.getPhoneNumber(), 
							s.getJob(), s.getPasswordHash()});
				}
				break;
			case "Shipping": //Shipping table
				Object shippingColumns[] = {"ID", "Shipping Method", "Estimated date", "Order ID"};
				tableModel = new DefaultTableModel(shippingColumns, 0);
				for(Transactions t : orderList) {
					tableModel.addRow(new Object[] { t.getShippingId(), t.getShippingMethod(), t.getEstimatedDate(), t.getOrderId()});
				}
				break;
			case "Suppliers": //Supplier table
				Object supplierColumns[] = {"ID", "Company name", "Phone number", "Email"};
				tableModel = new DefaultTableModel(supplierColumns, 0);
				for(Suppliers s : supplierList) {
					tableModel.addRow(new Object[] { s.getSupplierID(), s.getCompanyName(), s.getPhoneNumber(), s.getEmail()});
				}
				break;
			case "Items": //Items table for invoice page
				Object itemsColumns[] = {"Name", "Quantity", "Description", "Price", "Discount"};
				tableModel = new DefaultTableModel(itemsColumns, 0);
				for(InvoiceItem it : orderList.get(id - 1).getItems()) {
					tableModel.addRow(new Object[] { productList.get(it.getProductId() - 1).getName(), it.getPurchaseQuantity(), 
							productList.get(it.getProductId() - 1).getDescription(), "£" + (it.getPurchasePrice() * it.getPurchaseQuantity()), it.getDiscount() + "%"});
				}
				break;		
			case "Date": //Transactions before date
				Object transactionColumns[] = { "ID", "Date", "Customer ID", "Staff ID", "Shipping ID", "Cost"};
				tableModel = new DefaultTableModel(transactionColumns, 0);
					for(Transactions t : orderList) {
						if(t.getOrderDate().before(this.selectedDate)) {
							tableModel.addRow(new Object[] { t.getOrderId(), t.getOrderDate(),
							t.getCustomerId(), t.getStaffId(), t.getShippingId(), t.getTotalCost()});
					} 
				}
				break;
			case "LowStock": //Products that are low in stock
				Object LowStockColumns[] = { "ID", "Name", "Price", "Description", "Quantity", "Supplier", "Status"};
				tableModel = new DefaultTableModel(LowStockColumns, 0);
					for(Products p : productList) {
						if(p.getStockQuantity() <= 5) {
						tableModel.addRow(new Object[] { p.getProductId(), p.getName(), "£" + p.getPrice(),
								p.getDescription(), p.getStockQuantity(), p.getSupplierId(), p.getAvaliable()});
					}
				}
		}
		return tableModel;
	}

	//Creates or identifies customer
	public int addCustomer(String forename, String surname, String email, String streetAddress, String city, String zipCode, String country) {
		for(Clients c : clientList) {
			if(c.getEmail().equals(email)) {
				//If email exists, return customer ID
				return c.getCustomerId();
			}
		} //Else create new customer and save details
		int id = clientList.get(clientList.size() - 1).getCustomerId() + 1;
		Clients customer = new Clients(forename, surname, id, email, streetAddress, city, zipCode, country);
		clientList.add(customer);
		storeDatabase(5, id);
		return id;
	} 		
	
	//Returns the sales in GBP (already includes discount and VAT amounts)
	public double totalSales(Date date) {
		double total = 0;
		for(Transactions t : orderList) {
			if(date == null) { //If date == null then calculate all time revenue
				total += t.getTotalCost();
			}else { //If order is after the date, add to total
				if(t.getOrderDate().after(date)) {
					total += t.getTotalCost();
				} 
			}
		}
		return total;
	}
	//Returns the total VAT
	public double totalVAT(Date date) {
		double vat = 0;
		for(Transactions t : orderList) {
			if(date == null) { //If date == null then calculate all time VAT
				vat += ((t.getTotalCost() / 100) * 20);
			}else { //If order is after the date, add to total
				if(t.getOrderDate().after(date)) {
					vat += ((t.getTotalCost() / 100) * 20);
				} 
			}
		}
		return vat;
	}
	//Returns the total number of products sold
	public int totalSold() {
		int sold = 0;
		for(Transactions t : orderList) {
			for(InvoiceItem i : t.getItems()) {
				sold += i.getPurchaseQuantity();
			}
		} 
		return sold;
	}
	//Returns the total number of customers
	public int totalUsers() {
		return clientList.size();
	}
	//Returns the total number of suppliers
	public int totalSuppliers() {
		return supplierList.size();
	}
	//Returns the total number of sales
	public int totalStaff() {
		return staffList.size();
	}
	//Returns the total number of orders
	public int totalOrders() {
		return orderList.size();
	}
	//Returns the total number of products currently available for sales
	public int activeProducts() {
		int count = 0;
		for(Products p : productList) {
			if(p.getAvaliable() == 1) {
				count++;
			}
		}
		return count;
	}
	//Returns the total number delisted products
	public int delistedProducts() {
		int count = 0;
		for(Products p : productList) {
			if(p.getAvaliable() == 0) {
				count++;
			}
		}
		return count;
	}
}
