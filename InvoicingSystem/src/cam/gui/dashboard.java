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
import java.sql.Date;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.UIManager;

public class dashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel eastPanel;
	private JTable table;
	private JLabel title;
	private JFrame mainFrame;
	private JScrollPane scrollPane;
	private JFrame productForm;
	private JFrame updateForm;
	private JFrame orderForm;
	private JPanel invoice;
	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	private DataManager data = new DataManager();

	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("serial")
	public void createDashboard() {
		
		mainFrame = new JFrame();
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(dashboard.class.getResource("/cam/gui/icon.png")));
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setForeground(Color.WHITE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				//Closing event
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
	    table = new JTable() {
	    	 public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {

	        	Component comp = super.prepareRenderer(renderer, row, col);
	        	
	        	 if (table.getColumnName(col).compareToIgnoreCase("Quantity") == 0) {
            		int fieldValue = 0;
            		
	            	try {
	            		fieldValue = (int) table.getModel().getValueAt(row, 4);
	            	}catch(Exception e) {
	            		return comp;
	            	}
	            	if(fieldValue <= 5 && fieldValue > 0) {
	                    comp.setBackground(new Color(255,255,0));
	                    comp.setForeground(Color.black);
	                } else if(fieldValue > 5) {
	                	comp.setBackground(new Color(124,252,0));
	                	comp.setForeground(Color.black);
	                } else if(fieldValue == 0){
	                    comp.setBackground(new Color(139,0,0));
	                    comp.setForeground(Color.white);
	                }
	            }else {
	                comp.setForeground(Color.black);
	                comp.setBackground(Color.white);
	            }
	            return comp;
	    	 }
	    };
	    table.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
	    table.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	    table.setEnabled(false);
	    table.setFont(new Font("Arial", Font.PLAIN, 12));
	    table.setModel(data.generateTable("", 0));
	    table.setFillsViewportHeight(true);
	    table.getTableHeader().setOpaque(false);
	    table.getTableHeader().setBackground(new Color(220,220,220));
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
		
		displayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent  e) {
				closeFrames();
				Object options[] = {"Transactions", "Customers", "Suppliers", "Products", "Administrators", "Shipping", "Invoice"};
		        Object selectionObject = JOptionPane.showInputDialog(mainFrame, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		        if(!(selectionObject == null)) {
		        	String selectionString = selectionObject.toString();
		        	try {
		        		eastPanel.remove(invoice);
		        	}catch(Exception ex){	
		        	} finally {
			        	if(selectionString.equals("Invoice")) { 
			        		boolean valid = false;
			        		int ans = 0;
			        		do {
				        		try {
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
			        		
				        	if(valid == true) {
				        		Invoice inv = new Invoice();
				        		eastPanel.remove(scrollPane);
				        		eastPanel.remove(title);
				        		eastPanel.setLayout(new BorderLayout());
				        		invoice = inv.generateInvoice(ans);
				        		eastPanel.add(invoice, BorderLayout.CENTER);
				        		mainFrame.setSize((int)size.getWidth()/2, (int)size.getHeight()/2);
				        		mainFrame.setLocationRelativeTo(null);
				        	}
			        	}else {
							table.setModel(data.generateTable(selectionString, 0));
							title.setText(selectionString);		
			        		eastPanel.add(title);
			        		eastPanel.add(scrollPane);	

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
			        	}
		        		mainFrame.revalidate();
		        		mainFrame.repaint();
			        }
		        }
			}
		});
		displayBtn.setBackground(Color.WHITE);
		
		//Button to display bookings
		JButton orderBtn = new JButton("Create order");
		orderBtn.setIgnoreRepaint(true);
		orderBtn.setFocusable(false);
		orderBtn.setFocusTraversalKeysEnabled(false);
		orderBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		orderBtn.setFocusPainted(false);
		orderBtn.setFont(new Font("Arial", Font.BOLD, 16));
		orderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFrames();
				NewOrder orderPage = new NewOrder();
				orderForm = orderPage.clientInfoForm();
				orderForm.setVisible(true);
				orderForm.setSize((int)size.getWidth() / 4, (int)(size.getHeight() / 2.5));
				orderForm.setResizable(false);
				orderForm.setLocationRelativeTo(null);
			}
		});
		orderBtn.setBackground(Color.WHITE);
		
		//Button to create bookings
		JButton productBtn = new JButton("New product");
		productBtn.setIgnoreRepaint(true);
		productBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		productBtn.setFocusTraversalKeysEnabled(false);
		productBtn.setFocusable(false);
		productBtn.setFocusPainted(false);
		productBtn.setFont(new Font("Arial", Font.BOLD, 16));
		productBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFrames();
				newProduct productPage = new newProduct();
				productForm = productPage.addProduct();
				productForm.setVisible(true);
				productForm.setSize((int)size.getWidth() / 4, (int)(size.getHeight() / 2.5));
				productForm.setResizable(false);
				productForm.setLocationRelativeTo(null);
			}
		});
		productBtn.setBackground(Color.WHITE);
		
		//Button to update rooms
		JButton updateBtn = new JButton("Update product");
		updateBtn.setIgnoreRepaint(true);
		updateBtn.setFocusable(false);
		updateBtn.setFocusTraversalKeysEnabled(false);
		updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updateBtn.setFocusPainted(false);
		updateBtn.setFont(new Font("Arial", Font.BOLD, 16));
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					closeFrames();
					UpdateForm up = new UpdateForm();
					updateForm = up.generateForm();
					updateForm.setVisible(true);
					updateForm.setSize((int)size.getWidth() / 4, (int)(size.getHeight() / 2.5));
					updateForm.setResizable(false);
					updateForm.setLocationRelativeTo(null);
				}
		});
		updateBtn.setBackground(Color.WHITE);
		
		//Button to cancel bookings
		JButton reportBtn = new JButton("Create report");
		reportBtn.setIgnoreRepaint(true);
		reportBtn.setFocusTraversalKeysEnabled(false);
		reportBtn.setFocusable(false);
		reportBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		reportBtn.setFocusPainted(false);
		reportBtn.setFont(new Font("Arial", Font.BOLD, 16));
		reportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFrames();
				Object options[] = {"Order before date", "Low inventory products"};
		        Object selection = JOptionPane.showInputDialog(mainFrame, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		        if(selection != null) {
			        if(selection.equals("Order before date")) {
						//Date entry
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
					        if(data.ordersBeforeDate(date) == true) {
								table.setModel(data.generateTable("Date", 0));
								title.setText("Transactions before " + date);
					        }else {
					        	JOptionPane.showMessageDialog(mainFrame, "There are no orders before selected date.");
					        }
				    	}
			        } else {
						table.setModel(data.generateTable("LowStock", 0));
						title.setText("Low inventory products");
			        }
	        		eastPanel.add(title);
	        		eastPanel.add(scrollPane);	
		        
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
		}});
		
		reportBtn.setBackground(Color.WHITE);
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
		
		JButton printBtn = new JButton("Print page");
		printBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  PrinterJob pj = PrinterJob.getPrinterJob();
				  PageFormat pf = pj.defaultPage();
				  pj.setJobName(" Print Component ");
				  
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
				.addGroup(gl_westPanel.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_westPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(printBtn, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(reportBtn, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(updateBtn, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(productBtn, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(orderBtn, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addComponent(displayBtn, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
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
	
	//Closes any extra frames that could be open before creating another
	private void closeFrames() {
		try {
			productForm.dispose();
		}catch(NullPointerException ex) {};
		try {
			orderForm.dispose();
		}catch(NullPointerException ex) {};
		try {
			updateForm.dispose();
		}catch(NullPointerException ex) {};
	}
}