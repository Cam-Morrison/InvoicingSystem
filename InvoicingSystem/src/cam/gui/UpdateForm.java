package cam.gui;

import javax.swing.JFrame;
import java.awt.Window.Type;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;

import com.mysql.cj.xdevapi.Table;

import cam.business.logic.DataManager;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class UpdateForm {
	private JTextField idField;
	private JTextField quantityField;
	private JTextField descField;
	private JTextField priceField;
	private DataManager data = new DataManager();

	/**
	 * @wbp.parser.entryPoint
	 */
	public JFrame generateForm(){
		JFrame frmUpdateProduct = new JFrame();
		frmUpdateProduct.getContentPane().setBackground(new Color(255, 255, 255));
		frmUpdateProduct.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmUpdateProduct.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		frmUpdateProduct.setTitle("Update product");
		frmUpdateProduct.setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateForm.class.getResource("/cam/gui/icon.png")));
		frmUpdateProduct.setType(Type.POPUP);
		frmUpdateProduct.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
            	frmUpdateProduct.dispose();
            	dashboard.updateTable("Products");    	
            }
        });
		
		JLabel productIDtxt = new JLabel("Please enter product ID");
		productIDtxt.setForeground(new Color(11, 26, 106));
		productIDtxt.setFont(new Font("Arial", Font.BOLD, 16));
		productIDtxt.setHorizontalAlignment(SwingConstants.CENTER);
		
		idField = new JTextField();
		idField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		idField.setColumns(10);
		idField.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
				  idField.setText("");
			  }
			});
		JLabel quantityTxt = new JLabel("Enter new quantity");
		quantityTxt.setHorizontalAlignment(SwingConstants.CENTER);
		quantityTxt.setForeground(new Color(11, 26, 106));
		quantityTxt.setFont(new Font("Arial", Font.BOLD, 16));
		
		quantityField = new JTextField();
		quantityField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				quantityField.setText("");
			}
		});
		quantityField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		quantityField.setColumns(10);
		
		JLabel descTitle = new JLabel("Update product description");
		descTitle.setHorizontalAlignment(SwingConstants.CENTER);
		descTitle.setFont(new Font("Arial", Font.BOLD, 16));
		descTitle.setForeground(new Color(11, 26, 106));
		
		descField = new JTextField();
		descField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				descField.setText("");
			}
		});
		descField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		descField.setColumns(10);
		
		JLabel priceTxt = new JLabel("Update product price");
		priceTxt.setHorizontalAlignment(SwingConstants.CENTER);
		priceTxt.setFont(new Font("Arial", Font.BOLD, 16));
		priceTxt.setForeground(new Color(11, 26, 106));
		
		priceField = new JTextField();
		priceField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				priceField.setText("");
			}
		});
		priceField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		priceField.setColumns(10);
		
		JButton cancelBtn = new JButton("Cancel update");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmUpdateProduct.dispose();
			}
		});
		cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelBtn.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel warningLbl = new JLabel("");
		warningLbl.setForeground(Color.RED);
		warningLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		warningLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton updateBtn = new JButton("Update product");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(idField.getText());
					if(data.doesProductExist(id) == false) {
						idField.setText("Incorrect, that is not a valid product ID.");
					} else {
						int quantity = 21;
						String desc = "";
						double price = 0.00;
						if(!descField.getText().isBlank() || !quantityField.getText().isBlank() || !priceField.getText().isBlank()) {
							if(!descField.getText().isBlank()) {
								if(descField.getText().length() < 10 || descField.getText().length() > 250) {
									descField.setText("Description must be between 10 - 250 characters.");
									warningLbl.setText("Description is not valid.");
								}else {
									desc = descField.getText();
								}
							}
						
							if(!quantityField.getText().isBlank()) {
								int fieldQuantity = Integer.parseInt(quantityField.getText());
								if(fieldQuantity < 0 || fieldQuantity > 20) {
									quantityField.setText("Must be 0-20!");
									warningLbl.setText("Quantity must be 0-20");
								}else {
									quantity = fieldQuantity;
								}
							} 
							if(!priceField.getText().isBlank()) {
								price = Double.parseDouble(priceField.getText());
							}
										
							if(data.updateProduct(id, quantity, desc, price)) {
								frmUpdateProduct.dispose();			
								JOptionPane.showMessageDialog(null, "Product " + id + " has been updated.");
								frmUpdateProduct.dispatchEvent(new WindowEvent(frmUpdateProduct, WindowEvent.WINDOW_CLOSING));
							} else { 
								warningLbl.setText("There was an issue, please restart program.");
							}
						}else {
							warningLbl.setText("Please update one or more of the fields.");
						}
					}
				}catch(Exception err) {
					warningLbl.setText("Incorrect format, please enter a number.");
				}
			}
		});
		updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updateBtn.setFont(new Font("Arial", Font.BOLD, 16));
		
		JButton btnRemoveProduct = new JButton("Delist / relist");
		btnRemoveProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int input = Integer.parseInt(idField.getText());
					if(data.doesProductExist(input) == false) {
						idField.setText("Incorrect, that is not a valid product ID.");
					}else {
						data.removeProduct(input);
						frmUpdateProduct.dispatchEvent(new WindowEvent(frmUpdateProduct, WindowEvent.WINDOW_CLOSING));
					}
				}catch(Exception err) {
					idField.setText("Incorrect, please enter a number!");
				} finally {
					frmUpdateProduct.repaint();
				}
			}
		});
		btnRemoveProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRemoveProduct.setFont(new Font("Arial", Font.BOLD, 16));
		
		GroupLayout groupLayout = new GroupLayout(frmUpdateProduct.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(114)
					.addComponent(idField, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
					.addGap(111))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(cancelBtn, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(descField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(descTitle, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(productIDtxt, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(priceTxt, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnRemoveProduct, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
										.addComponent(priceField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(quantityField, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
								.addComponent(quantityTxt, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
								.addComponent(updateBtn, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(warningLbl, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(productIDtxt, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(idField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(descTitle, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(descField, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(warningLbl)
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(priceTxt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(quantityTxt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(quantityField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(priceField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRemoveProduct, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
						.addComponent(updateBtn, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(cancelBtn, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		frmUpdateProduct.getContentPane().setLayout(groupLayout);
		return frmUpdateProduct;
	}
}
