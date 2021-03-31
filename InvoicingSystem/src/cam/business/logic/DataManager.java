package cam.business.logic;

import java.awt.Desktop;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;


public class DataManager {

	//Instance variables
	private final Pattern namePattern = Pattern.compile("^[A-Z][a-z]+$");
	private final Pattern emailPattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
	private static ArrayList<Transactions> orderList = new ArrayList<Transactions>();
	private static ArrayList<Clients> clientList = new ArrayList<Clients>();
	private static ArrayList<Suppliers> supplierList = new ArrayList<Suppliers>();
	private static  ArrayList<Products> productList = new ArrayList<Products>();
	private static ArrayList<Staff> staffList = new ArrayList<Staff>();
	
	//Constructor
	public DataManager(){
	}
	
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
				
				//Hashing algorithm 
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
				} catch (NoSuchAlgorithmException e) {return false;}

				//Validate login password hash - check hash equals hash stored in database
				if(hash.toString().equals(staffList.get(staffID - 1).getPasswordHash())) {
					loadDatabase(2);
					return true;
				}
			}
		}
		return false;
	}
	//hash 40bd001563085fc35165329ea1ff5c5ecbdbbeef, pass 123, staff id 1
	//hash 5f6955d227a320c7f1f6c7da2a6d96a851a8118f, pass 321, staff id 2
	
	//Checks productList for product
	public boolean doesProductExist(int id) {
		for(Products p : productList) {
			if(p.getProductId() == id) {
				return true;
			}
		}
		return false;
	}
	
	//Updates product in list
	public boolean updateProduct(int id) {
		if(doesProductExist(id) == true) {
			//TODO
		}
		return false;
	}
	
	//Removes product from list
	public boolean removeProduct(int id) {
		if(doesProductExist(id) == true) {
			for(Products p : productList) {
				if(p.getProductId() == id) {
					productList.remove(p);
					return true;
				}
			}
		}
		return false;
	}
	
	//Creates a new product
	public boolean addProduct(/*String double int int string*/) {
		//TODO
		return false;
	}
	
	//Creates a new order on clients behalf 
	public boolean newOrder(/*String String String String String String String int int int */) {
		//TODO
		return false;
	}
	
	// Method to verify the authenticity of user's entered name
	public boolean isValidName(String name) {
		return namePattern.matcher(name).matches();
	}

	// Method to verify the authenticity of user's entered email
	public boolean isValidEmail(String email) {
		return emailPattern.matcher(email).matches();
	}
	
	//Stores newly entered information to database
	private boolean storeDatabase() {
//		String driver = "com.mysql.cj.jdbc.Driver";
//		String url = "jdbc:mysql://localhost:3306/invoicingsystem?&serverTimezone=UTC";
//		String user = "root";
//		String password = "root";

	//	try {
	//		//Creating connection
	//		Connection myConn = DriverManager.getConnection(url, user, password);
	//		Statement myStmt = myConn.createStatement();
	//		String sql = null;
	//		sql = "INSERT INTO `supplier` " + "VALUES (1, 'Samsung', '+44 3232 3132', 'help@samsung.com')";
	//	    myStmt.executeUpdate(sql);			    
	//		sql = "INSERT INTO `product` " + "VALUES (1, 'Samsung s20', 3, 1600.00, 1)";
	//	    myStmt.executeUpdate(sql);						   
	//		sql = "INSERT INTO `order` " + "VALUES (1, '2014-01-28', 60.00, '2014-03-28', 1, 1)";
	//	    myStmt.executeUpdate(sql);									
	//		sql = "INSERT INTO staff " + "VALUES (1, 'Susan', 'Gardner', '+44 1632 960744', 'Sales rep', '40bd001563085fc35165329ea1ff5c5ecbdbbeef')";
	//	    myStmt.executeUpdate(sql);			    
	//		sql = "INSERT INTO staff " + "VALUES (2, 'Cameron', 'Morrison', '+44 3232 960354', 'Technical officer', '5f6955d227a320c7f1f6c7da2a6d96a851a8118f')";
	//	    myStmt.executeUpdate(sql);					
	//		sql = "INSERT INTO customer " + "VALUES (1, 'Cameron', 'Morrison', 'test@gmail.com')";
	//	    myStmt.executeUpdate(sql);
	//		sql = "INSERT INTO address " + "VALUES ('15 lordhome', 'Edinburgh', 'FK3 94J', 'Scotland', 1)";
	//	    myStmt.executeUpdate(sql);
	//		sql = "INSERT INTO `invoice_item` " + "VALUES (1, 'Flagship smartphone 12gb ram', 12.00, 1, 1)";
	//	    myStmt.executeUpdate(sql);			
	//		myConn.close();
			
	//	}catch(SQLException e) {}	
		return false;
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
			
			//Executing statement
			String sql = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			
			switch(option) {
				case 1:
					sql = "SELECT * FROM staff";
				    rs = myStmt.executeQuery(sql);
				    while (rs.next()) {	
				    	Staff staff = new Staff(rs.getInt("staff_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone_number"),  rs.getString("job"), rs.getString("password_hash"));
				    	staffList.add(staff);
				    }
				    break;
				    
				case 2:	
					sql = "SELECT * FROM customer, address";
				    rs = myStmt.executeQuery(sql);
				    while (rs.next()) {	
				    	Clients customer = new Clients(rs.getString("first_name"), rs .getString("last_name"), rs.getInt("customer_id"), rs.getString("email"),  
				    			rs.getString("street_address"), rs.getString("city"), rs.getString("zip_code"), rs.getString("country"));
				    	clientList.add(customer);
				    	System.out.println(clientList.get(0).getCountry());
				    }
				      
				    
					sql = "SELECT * FROM `order`, `shipping` WHERE order_id = order_order_id";
				    rs = myStmt.executeQuery(sql);
				    while (rs.next()) {	 //need to find a way to import invoice_item as arraylist for order id
			    	
				        ArrayList<InvoiceItem> list = new ArrayList<InvoiceItem>();
				        sql = "SELECT * FROM `invoice_item` WHERE order_order_id = " + rs.getInt("order_id");    
				        rs2 = myStmt2.executeQuery(sql);
				        while(rs2.next()){
				        	InvoiceItem item = new InvoiceItem(rs2.getInt("purchase_quantity"), rs2.getString("item_description"), rs2.getDouble("discount"), rs2.getInt("order_order_id"), rs2.getInt("product_product_id"));
				        	list.add(item);
				        }
				      		        
				    	Transactions order = new Transactions(rs.getInt("order_id"), rs.getDate("order_date"), rs.getDouble("total_cost"), rs.getDate("due_date"),  
				    			rs.getInt("customer_customer_id"), rs.getInt("staff_staff_id"), rs.getInt("shipping_id"), rs.getString("shipping_method"),
				    			rs.getDate("estimated_delivery_date"), list);
				    	orderList.add(order); 
				    	
				    	System.out.println(orderList.get(0).getOrderId());
				    	System.out.println(orderList.get(0).getItems().get(0).getItemDescription());
				    }
				    break;
				    				
				case 300: //Used for testing, check what columns are in a table
					
				    sql = "SELECT * FROM `invoice_item`";
				    rs = myStmt.executeQuery(sql);
			       ResultSetMetaData rsMetaData = rs.getMetaData();
			       System.out.println("List of column names in the current table: ");
			       int count = rsMetaData.getColumnCount();
			       for(int i = 1; i<=count; i++) {
			          System.out.println(rsMetaData.getColumnName(i));
			        }
				   break;
			}
		    myConn.close();
		    return true;
		}catch(CommunicationsException e) { //Connection to DB port unavailable
			JOptionPane.showMessageDialog(null, "Error connecting to database. Please free up port 3306. \nYou will now be redirected to a guide by medium.com.", "Communications link failure", JOptionPane.ERROR_MESSAGE);
		    try {
		        Desktop.getDesktop().browse(new URL("https://medium.com/@javatechie/how-to-kill-the-process-currently-using-a-port-on-localhost-in-windows-31ccdea2a3ea").toURI());
		    } catch (Exception ex) {}
			System.exit(0);
		}
		return false;
	} 
	
	//Returns list of client objects
	public static ArrayList<Clients> returnClients(){
		return clientList;
	}

	//Returns list of transactions objects
	public ArrayList<Transactions> returnTransactions(){
		return orderList;
	}
	
	//Returns list of Supplier objects
	public ArrayList<Suppliers> returnSuppliers(){
		return supplierList;
	}
	
	//Returns list of Products objects
	public ArrayList<Products> returnProducts(){
		return productList;
	}
	
	//Returns list of Staff objects
	public ArrayList<Staff> returnStaff(){
		return staffList;
	}

	//Method to generate and return populated table
	public DefaultTableModel generateTable(int option) {
		DefaultTableModel tableModel;
		switch(option) {
			default: //Default Orders table
				Object orderColumns[] = { "ID", "Date", "Customer ID", "Staff ID", "Shipping ID", "Cost"};
				tableModel = new DefaultTableModel(orderColumns, 0);
				for(Transactions t : orderList) {
					tableModel.addRow(new Object[] { t.getOrderId(), t.getOrderDate(),
							t.getCustomerId(), t.getStaffId(), t.getShippingId(), t.getTotalCost()});
				} 
				break;
			case 1: //Clients users table
				Object clientsColumns[] = {"ID", "Name", "Email", "Address", "City", "Zip code", "Country"};
				tableModel = new DefaultTableModel(clientsColumns, 0);
				for(Clients c : clientList) {
					tableModel.addRow(new Object[] { c.getCustomerId(), c.getFirstName() + " " + c.getLastName(),
					c.getEmail(), c.getStreetAddress(), c.getCity(), c.getZipCode(), c.getCountry()});
				}
				break;
			case 2: //Product table
				Object productColumns[] = { "ID", "Name", "Price", "Quantity", "Supplier", "Description"};
				tableModel = new DefaultTableModel(productColumns, 0);
				for(Products p : productList) {
					tableModel.addRow(new Object[] { p.getProductId(), p.getName(), p.getPrice(), 
							p.getStockQuantity(), p.getSupplierId(), p.getDescription()});
				}
				break;
<<<<<<< Updated upstream
			case 3: //Staff table
=======
			case "Administrators": //Staff table
>>>>>>> Stashed changes
				Object staffColumns[] = {"ID", "Name", "Phone number", "Job", "Hash"};
				tableModel = new DefaultTableModel(staffColumns, 0);
				for(Staff s : staffList) {
					tableModel.addRow(new Object[] { s.getStaffID(), s.getFirstName() + " " + s.getLastName(), s.getPhoneNumber(), 
							s.getJob(), s.getPasswordHash()});
				}
				break;
			case 4: //Shipping table
				Object shippingColumns[] = {"ID", "Shipping Method", "Estimated date", "Order ID"};
				tableModel = new DefaultTableModel(shippingColumns, 0);
				for(Transactions t : orderList) {
					tableModel.addRow(new Object[] { t.getShippingId(), t.getShippingMethod(), t.getEstimatedDate(), t.getOrderId()});
				}
				break;
				
		}

		return tableModel;
	} 	
}
