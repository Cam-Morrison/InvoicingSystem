package cam.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import cam.business.logic.DataManager;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.CompoundBorder;
import javax.swing.table.TableCellRenderer;
import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.UIManager;

//Author: Cameron Morrison 569530
//Second year graded unit project. 

public class dashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFrame mainFrame;
	private static JTable table;
	private static JPanel eastPanel;
	private static JLabel title;
	private static JScrollPane scrollPane;
	private static JFrame productForm;
	private static JFrame updateForm;
	private static JFrame orderForm;
	private static JPanel invoice;
	private static JPanel overview;
	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	private static DataManager data = new DataManager();

	@SuppressWarnings("serial")
	public void createDashboard() {
		mainFrame = new JFrame();
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(dashboard.class.getResource("/cam/gui/icon.png")));
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setForeground(Color.WHITE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override //Default closing event
			public void windowClosing(WindowEvent e){
				mainFrame.dispose();
				System.exit(0);
			}
		});
		mainFrame.setTitle("Invoicing system");
		mainFrame.setBounds(100, 100, 739, 487);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    eastPanel = new JPanel();
	    eastPanel.setBorder(new CompoundBorder());
	    eastPanel.setBackground(Color.WHITE);
	    scrollPane = new JScrollPane();
	    
	    //Creation of JTable
	    table = new JTable() { //Custom conditional formatting 
	    	 public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
	        	Component comp = super.prepareRenderer(renderer, row, col);
	        	//If the the column is "quantity"
	        	 if (table.getColumnName(col).compareToIgnoreCase("Quantity") == 0) {
            		int fieldValue = 0;
	            	try {
	            		fieldValue = (int) table.getModel().getValueAt(row, 4);
	            	}catch(Exception e) {
	            		return comp;
	            	} //If cell value is between 0-5 then colour yellow for stock warning.
	            	if(fieldValue <= 5 && fieldValue > 0) {
	                    comp.setBackground(new Color(255,255,0));
	                    comp.setForeground(Color.black);
	                  //If cell value is more than 5 then colour green
	                } else if(fieldValue > 5) { 
	                	comp.setBackground(new Color(124,252,0));
	                	comp.setForeground(Color.black);
	                	//If cell value is 0 then colour red to show importance.
	                } else if(fieldValue == 0){
	                    comp.setBackground(new Color(139,0,0));
	                    comp.setForeground(Color.white);
	                } //If column is "Status"
	        	 }else if(table.getColumnName(col).compareToIgnoreCase("Status") == 0) {
	        		int status = 1;
	            	try { 
	            		status = (int) table.getModel().getValueAt(row, 6);
	            	}catch(Exception e) {
	            		return comp;
	            	} //Colour green for available
	        		if(status > 0) {
	                    comp.setBackground(new Color(124,252,0));
	                    comp.setForeground(Color.black);
	                  //Colour red for unavailable
	                } if(status == 0) {
	                    comp.setBackground(new Color(139,0,0));
	                    comp.setForeground(Color.white);
	                } 
	            }else { //else default black text, white background
	                comp.setForeground(Color.black);
	                comp.setBackground(Color.white);
	            }
	            return comp;
	    	 }
	    };
	    table.setEnabled(false);
	    table.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
	    table.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	    table.setFont(new Font("Arial", Font.PLAIN, 12));
	    table.setModel(data.generateTable("", 0));
	    table.setFillsViewportHeight(true);
	    table.getTableHeader().setOpaque(false);
	    table.getTableHeader().setBackground(new Color(11, 26, 106));
	    table.getTableHeader().setForeground(Color.white);
	    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
	    scrollPane.setViewportView(table);

		//WestPanel where buttons are located
		JPanel westPanel = new JPanel();
		westPanel.setIgnoreRepaint(true);
		westPanel.setBackground(new Color(11, 26, 106));
			
		//Button to display information
		JButton displayBtn = new JButton("Display");
		displayBtn.setIgnoreRepaint(true);
		displayBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		displayBtn.setFocusTraversalKeysEnabled(false);
		displayBtn.setFocusable(false);
		displayBtn.setFocusPainted(false);
		displayBtn.setFont(new Font("Arial", Font.BOLD, 16));
		displayBtn.setForeground(new Color(11, 26, 106));
		displayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent  e) {
				//Closes any already open dialogs.
				closeFrames();
				//A list of drop down options.
				Object options[] = {"Transactions", "Customers", "Suppliers", "Products", "Administrators", "Shipping", "Invoice"};
		        Object selectionObject = JOptionPane.showInputDialog(mainFrame, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		        //if user didn't click cancel
		        if(!(selectionObject == null)) { 
		        	String selectionString = selectionObject.toString();
		        	try { //Try to remove invoice panel if its there
		        		eastPanel.remove(invoice);
		        	}catch(Exception ex){	
		        	} finally { //Always run:
			        	if(selectionString.equals("Invoice")) { 
			        		boolean valid = false;
			        		int ans = 0;
			        		do {
				        		try { //Displays transactions so user can cross reference order ID before generating invoice
									table.setModel(data.generateTable("Transactions", 0));
									title.setText("Transactions");		
					        		ans = Integer.parseInt(JOptionPane.showInputDialog(mainFrame,"Please enter an order ID to display invoice.",JOptionPane.INFORMATION_MESSAGE));
					        		if(data.isOrderID(ans) == true) {
					        			valid = true;
					        		}else {
					        			JOptionPane.showMessageDialog(mainFrame, ans + " is not an existing order ID.");
					        		}
				        		}catch(java.lang.NumberFormatException error) {
				        			JOptionPane.showMessageDialog(mainFrame, "Please enter an intenger value.");
				        			break;
				        		}
			        		}while(valid != true);
			        		//If valid is true then add invoice panel
				        	if(valid == true) {
				        		restoreTable();
				        		Invoice inv = new Invoice();
				        		eastPanel.remove(scrollPane);
				        		eastPanel.remove(title);
				        		eastPanel.setLayout(new BorderLayout());
				        		invoice = inv.generateInvoice(ans);
				        		eastPanel.add(invoice, BorderLayout.CENTER);
				        		mainFrame.setSize((int)size.getWidth()/2, (int)size.getHeight()/2);
				        		mainFrame.setLocationRelativeTo(null);
				        	} 
			        	}else { //Else invoice cannot have been the selected option so generate the table
							table.setModel(data.generateTable(selectionString, 0));
							title.setText(selectionString);		
							restoreTable();
			        	}
		        	}
		        }
			}
		});
		displayBtn.setBackground(Color.WHITE);
		
		//Button to create new orders
		JButton orderBtn = new JButton("Create order");
		orderBtn.setIgnoreRepaint(true);
		orderBtn.setFocusable(false);
		orderBtn.setFocusTraversalKeysEnabled(false);
		orderBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		orderBtn.setFocusPainted(false);
		orderBtn.setFont(new Font("Arial", Font.BOLD, 16));
		orderBtn.setForeground(new Color(11, 26, 106));
		orderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Display product table
				updateTable("Products");
				//Add order form
				NewOrder orderPage = new NewOrder();
				orderForm = orderPage.clientInfoForm();
				orderForm.setVisible(true);
				orderForm.setSize((int)size.getWidth() / 4, (int)(size.getHeight() / 2.5));
				orderForm.setResizable(false);
				orderForm.setLocationRelativeTo(null);
			}
		});
		orderBtn.setBackground(Color.WHITE);
		
		//Products button
		JButton productBtn = new JButton("New product");
		productBtn.setIgnoreRepaint(true);
		productBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		productBtn.setFocusTraversalKeysEnabled(false);
		productBtn.setFocusable(false);
		productBtn.setFocusPainted(false);
		productBtn.setFont(new Font("Arial", Font.BOLD, 16));
		productBtn.setForeground(new Color(11, 26, 106));
		productBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable("Suppliers");
				newProduct productPage = new newProduct();
				productForm = productPage.addProduct();
				productForm.setVisible(true);
				productForm.setSize((int)size.getWidth() / 4, (int)(size.getHeight() / 2.5));
				productForm.setResizable(false);
				productForm.setLocationRelativeTo(null);
			}
		});
		productBtn.setBackground(Color.WHITE);
		
		//Update button
		JButton updateBtn = new JButton("Update product");
		updateBtn.setIgnoreRepaint(true);
		updateBtn.setFocusable(false);
		updateBtn.setFocusTraversalKeysEnabled(false);
		updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updateBtn.setForeground(new Color(11, 26, 106));
		updateBtn.setFocusPainted(false);
		updateBtn.setFont(new Font("Arial", Font.BOLD, 16));
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//Display products table
					updateTable("Products");
					UpdateForm up = new UpdateForm();
					updateForm = up.generateForm();
					updateForm.setVisible(true);
					updateForm.setSize((int)size.getWidth() / 4, (int)(size.getHeight() / 2.5));
					updateForm.setResizable(false);
					updateForm.setLocationRelativeTo(null);
				}
		});
		updateBtn.setBackground(Color.WHITE);
		
		//Button to create report
		JButton reportBtn = new JButton("Create report");
		reportBtn.setIgnoreRepaint(true);
		reportBtn.setFocusTraversalKeysEnabled(false);
		reportBtn.setFocusable(false);
		reportBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		reportBtn.setFocusPainted(false);
		reportBtn.setFont(new Font("Arial", Font.BOLD, 16));
		reportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Close existing frames 
				closeFrames();
				Object options[] = {"Operation overview", "Order before date", "Low inventory products"};
		        Object selection = JOptionPane.showInputDialog(mainFrame, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		        if(selection != null) {
		        	try {
		        		eastPanel.remove(invoice);
		        	}catch(Exception ex){};	
		        	if(selection.equals("Operation overview")) {
		        		mainFrame.setResizable(false);
		        		eastPanel.remove(scrollPane);
		        		eastPanel.remove(title);
		        		eastPanel.setLayout(new BorderLayout());
		        		overview = financialOverview();
		        		eastPanel.add(overview, BorderLayout.CENTER);
		        		mainFrame.setSize((int)size.getWidth()/2, (int)size.getHeight()/2);
		        		mainFrame.setLocationRelativeTo(null);
		        		mainFrame.revalidate();
		        		mainFrame.repaint();
		        	} else if(selection.equals("Order before date")) {
						//Date picker frame
						DatePicker jd = new DatePicker();
						String message ="Search for all orders placed before:\n";
						Object[] params = {message,jd};
						JOptionPane.showConfirmDialog(mainFrame,params, "Filter by date", JOptionPane.PLAIN_MESSAGE);
						java.sql.Date date = null;	
		
				    	try {
				    		date = java.sql.Date.valueOf(jd.getDate());
				    	} catch(Exception ex){};
				    	
						try {
			        		eastPanel.remove(invoice);
			        	}catch(Exception ex){};	
	
				    	if(date != null) { 
				    		//Return orders before date 
					        if(data.ordersBeforeDate(date) == true) {
								table.setModel(data.generateTable("Date", 0));
								title.setText("Transactions before " + date);
								//Adds tables back
								restoreTable();
					        }else {
					        	JOptionPane.showMessageDialog(mainFrame, "There are no orders before selected date.");
					        }
				    	}
			        } else { 
			        	//Show table contents for low stock
						table.setModel(data.generateTable("LowStock", 0));
						title.setText("Low inventory products");
						restoreTable();
			        }
		        }
		}});
		reportBtn.setBackground(Color.WHITE);
		reportBtn.setForeground(new Color(11, 26, 106));
		
		//Windows builder layout
		GroupLayout groupLayout = new GroupLayout(mainFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(westPanel, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(eastPanel, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(westPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(eastPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
		);
		//Title bar for east panel 
		title = new JLabel("Transactions");
		title.setBackground(Color.WHITE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(11, 26, 106));
		title.setFont(new Font("Arial", Font.BOLD, 16));
		GroupLayout gl_eastPanel = new GroupLayout(eastPanel);
		gl_eastPanel.setHorizontalGroup(
			gl_eastPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_eastPanel.createSequentialGroup()
					.addGroup(gl_eastPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(title, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_eastPanel.setVerticalGroup(
			gl_eastPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_eastPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(title)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
		);
		eastPanel.setLayout(gl_eastPanel);
		
		//Print button that lets users print and save the contents of the page
		JButton printBtn = new JButton("Print page");
		printBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		printBtn.setForeground(new Color(11, 26, 106));
		printBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  PrinterJob pj = PrinterJob.getPrinterJob();
				  PageFormat pf = pj.defaultPage();
				  pj.setJobName(" Print Component ");
				  //Set page format landscape by default
				  pf.setOrientation(PageFormat.LANDSCAPE);
				  pj.setPrintable (new Printable() {    
					  
				    public int print(Graphics pg, PageFormat pf, int pageNum){
				      if (pageNum > 0){
				    	  return Printable.NO_SUCH_PAGE;
				      }
				      Graphics2D g2 = (Graphics2D) pg;
				      g2.translate(pf.getImageableX(), pf.getImageableY());
				      eastPanel.paint(g2);
				      return Printable.PAGE_EXISTS;
				    }
				  }, pf);
				  if (pj.printDialog() == false)
				  return;

				  try {
				        pj.print();
				  } catch (PrinterException ex) {
				        JOptionPane.showMessageDialog(mainFrame, "Cannot print page.");
				  }
			}
		});
		printBtn.setIgnoreRepaint(true);
		printBtn.setFont(new Font("Arial", Font.BOLD, 16));
		printBtn.setFocusable(false);
		printBtn.setFocusTraversalKeysEnabled(false);
		printBtn.setFocusPainted(false);
		printBtn.setBackground(Color.WHITE);
		GroupLayout gl_westPanel = new GroupLayout(westPanel);
		gl_westPanel.setHorizontalGroup(
			gl_westPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_westPanel.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_westPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(updateBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(printBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(reportBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(productBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(orderBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(displayBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
					.addGap(38))
		);
		gl_westPanel.setVerticalGroup(
			gl_westPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_westPanel.createSequentialGroup()
					.addGap(20)
					.addComponent(displayBtn, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(orderBtn, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(productBtn, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(updateBtn, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(reportBtn, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(printBtn, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		westPanel.setLayout(gl_westPanel);
		mainFrame.getContentPane().setLayout(groupLayout);
	   
		//Position JFrame in middle of monitor
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	//Method to add table and title back to east panel
	private static void restoreTable() {
		try { //to remove overview page if its there
			eastPanel.remove(overview);
		}catch(Exception e) {};
		try { //to remove invoice page if its there
			eastPanel.remove(invoice);
		}catch(Exception e) {};
		//Unlock mainframe size
		mainFrame.setResizable(true);
		//Reconfigure the layout
		GroupLayout gl_eastPanel = new GroupLayout(eastPanel);
		gl_eastPanel.setHorizontalGroup(
			gl_eastPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_eastPanel.createSequentialGroup()
					.addGroup(gl_eastPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(title, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_eastPanel.setVerticalGroup(
			gl_eastPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_eastPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(title)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
		);
		eastPanel.setLayout(gl_eastPanel);

		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	//Try to close any frames that could be open before opening another
	private static void closeFrames() {
		try {
			productForm.dispose();
		}catch(Exception e) {};
		try {
			updateForm.dispose();
		}catch(Exception er) {};
		try {
			orderForm.dispose();
		}catch(Exception err) {};
	}
	
	//Restore table and update the tables model
	static void updateTable(String choice) {
		closeFrames();
		restoreTable();
		title.setText(choice);	
		table.setModel(data.generateTable(choice, 0));
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private JPanel financialOverview() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		
		JLabel financeTitle = new JLabel("Financial overview for UK operations");
		financeTitle.setBackground(Color.WHITE);
		financeTitle.setHorizontalAlignment(SwingConstants.LEFT);
		financeTitle.setFont(new Font("Arial", Font.BOLD, 20));
		financeTitle.setForeground(new Color(11, 26, 106));
		
		JLabel totalSalesLbl = new JLabel("Total revenue from domestic sales: \u00A3" + data.totalSales());
		totalSalesLbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalSalesLbl.setFont(new Font("Arial", Font.BOLD, 16));
		totalSalesLbl.setForeground(new Color(11, 26, 106));
		
		JLabel totalVatLbl = new JLabel("Total Value Added Tax: \u00A3" + data.totalVAT());
		totalVatLbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalVatLbl.setFont(new Font("Arial", Font.BOLD, 16));
		totalVatLbl.setForeground(new Color(11, 26, 106));
		
		JLabel totalProductsSoldLbl = new JLabel("Total products sold: " + data.totalSold());
		totalProductsSoldLbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalProductsSoldLbl.setFont(new Font("Arial", Font.BOLD, 16));
		totalProductsSoldLbl.setForeground(new Color(11, 26, 106));
		
		JLabel totalCustomersLbl = new JLabel("Total customers:" + data.totalUsers());
		totalCustomersLbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalCustomersLbl.setFont(new Font("Arial", Font.BOLD, 16));
		totalCustomersLbl.setForeground(new Color(11, 26, 106));
		
		JLabel totalSuppliersLbl = new JLabel("Total suppliers: " + data.totalSuppliers());
		totalSuppliersLbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalSuppliersLbl.setFont(new Font("Arial", Font.BOLD, 16));
		totalSuppliersLbl.setForeground(new Color(11, 26, 106));
		
		JLabel totalStaffLbl = new JLabel("Number of sales representatives: " + data.totalStaff());
		totalStaffLbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalStaffLbl.setFont(new Font("Arial", Font.BOLD, 16));
		totalStaffLbl.setForeground(new Color(11, 26, 106));
		
		JLabel totalOrdersLbl = new JLabel("Total orders: " + data.totalOrders());
		totalOrdersLbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalOrdersLbl.setFont(new Font("Arial", Font.BOLD, 16));
		totalOrdersLbl.setForeground(new Color(11, 26, 106));
		
		JLabel activeProductsLbl = new JLabel("Number of products listed: " + data.activeProducts());
		activeProductsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		activeProductsLbl.setFont(new Font("Arial", Font.BOLD, 16));
		activeProductsLbl.setForeground(new Color(11, 26, 106));
		
		JLabel disabledProducts = new JLabel("Number of products delisted: " + data.delistedProducts());
		disabledProducts.setHorizontalAlignment(SwingConstants.LEFT);
		disabledProducts.setFont(new Font("Arial", Font.BOLD, 16));
		disabledProducts.setForeground(new Color(11, 26, 106));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addComponent(totalVatLbl, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addComponent(activeProductsLbl, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addComponent(disabledProducts, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addComponent(totalStaffLbl, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addComponent(totalProductsSoldLbl, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addComponent(totalCustomersLbl, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addComponent(totalSuppliersLbl, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addComponent(totalOrdersLbl, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(58)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(financeTitle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
								.addComponent(totalSalesLbl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
							.addGap(41))) 
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(financeTitle, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
					.addGap(43)
					.addComponent(totalSalesLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(totalVatLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(activeProductsLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(disabledProducts, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(totalStaffLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(totalProductsSoldLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(totalCustomersLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(totalSuppliersLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(totalOrdersLbl, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
					.addGap(106))
		);
		panel.setLayout(gl_panel);
		return panel;
	}
}