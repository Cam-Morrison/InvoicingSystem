package cam.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.JTextPane;

public class Invoice {
	private JTable custTable;
	private JTable orderTable;
	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel generateInvoice(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		
		JLabel IDlabel = new JLabel("Order Id");
		IDlabel.setFont(new Font("Arial", Font.BOLD, 12));
		IDlabel.setForeground(new Color(11, 26, 106));
		
		JLabel dateLbl = new JLabel("Order Date");
		dateLbl.setFont(new Font("Arial", Font.BOLD, 12));
		dateLbl.setForeground(new Color(11, 26, 106));
		
		custTable = new JTable();
		custTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JTextPane staffTxt = new JTextPane();
		staffTxt.setText("Sales SalesSalesSales\r\nSalesSalesSalesSalesSales\r\nSalesSalesSalesSalesSales");
		staffTxt.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		staffTxt.setEditable(false);
		
		JTextPane paymentTxt = new JTextPane();
		paymentTxt.setText("paymentpaymentpayment\r\npaymentpaymentpayment\r\npaymentpaymentpayment\r\npaymentpaymentpayment");
		paymentTxt.setEditable(false);
		paymentTxt.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JTextPane shippingTxt = new JTextPane();
		shippingTxt.setText("ShippingShippingShipping\r\nShippingShippingShipping\r\nShippingShippingShipping\r\nShippingShippingShipping");
		shippingTxt.setEditable(false);
		shippingTxt.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JLabel staffLbl = new JLabel("Sales representative");
		staffLbl.setHorizontalAlignment(SwingConstants.CENTER);
		staffLbl.setFont(new Font("Arial", Font.BOLD, 12));
		staffLbl.setForeground(new Color(11, 26, 106));
		
		JLabel paymentLbl = new JLabel("Payment");
		paymentLbl.setFont(new Font("Arial", Font.BOLD, 12));
		paymentLbl.setHorizontalAlignment(SwingConstants.CENTER);
		paymentLbl.setForeground(new Color(11, 26, 106));
		
		JLabel shippingLbl = new JLabel("Shipping");
		shippingLbl.setHorizontalAlignment(SwingConstants.CENTER);
		shippingLbl.setFont(new Font("Arial", Font.BOLD, 12));
		shippingLbl.setForeground(new Color(11, 26, 106));
		
		orderTable = new JTable();
		orderTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JLabel totalCostLbl = new JLabel("Total cost");
		totalCostLbl.setFont(new Font("Arial", Font.BOLD, 12));
		totalCostLbl.setForeground(new Color(11, 26, 106));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(custTable, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(IDlabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 369, Short.MAX_VALUE)
							.addComponent(dateLbl)
							.addGap(21))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(orderTable, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(staffLbl, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
										.addComponent(staffTxt, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(paymentLbl, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
										.addComponent(paymentTxt, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(shippingLbl, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
										.addComponent(shippingTxt, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 420, Short.MAX_VALUE)
									.addComponent(totalCostLbl, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
							.addGap(11))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(IDlabel)
						.addComponent(dateLbl))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(custTable, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(staffLbl, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(paymentLbl, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(shippingLbl, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(shippingTxt)
						.addComponent(paymentTxt)
						.addComponent(staffTxt, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(orderTable, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(totalCostLbl)
					.addGap(20))
		);
		panel.setLayout(gl_panel);
		return panel;
	}
}
