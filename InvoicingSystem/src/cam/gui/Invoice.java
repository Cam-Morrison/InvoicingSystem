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

import cam.business.logic.DataManager;
import cam.business.logic.Transactions;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class Invoice {
	private DataManager data = new DataManager();
	private JTable custTable;
	private JTable orderTable;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel generateInvoice(int id) {
		Transactions order = data.returnTransactions(id);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		
		JLabel IDlabel = new JLabel("Order #" + id);
		IDlabel.setFont(new Font("Arial", Font.BOLD, 12));
		IDlabel.setForeground(new Color(11, 26, 106));
		
		JLabel dateLbl = new JLabel("" + order.getOrderDate());
		dateLbl.setFont(new Font("Arial", Font.BOLD, 12));
		dateLbl.setForeground(new Color(11, 26, 106));
		
		JTextPane staffTxt = new JTextPane();
		staffTxt.setFont(new Font("Arial", Font.PLAIN, 12));
		staffTxt.setText(data.returnStaffInfo(id));
		staffTxt.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		staffTxt.setEditable(false);
		
		JTextPane paymentTxt = new JTextPane();
		paymentTxt.setFont(new Font("Arial", Font.PLAIN, 12));
		paymentTxt.setText("Payment is due on\n" + data.returnPayment(id));
		paymentTxt.setEditable(false);
		paymentTxt.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JTextPane shippingTxt = new JTextPane();
		shippingTxt.setFont(new Font("Arial", Font.PLAIN, 12));
		shippingTxt.setText(data.returnShipping(id));
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
		
		JLabel totalCostLbl = new JLabel("Total cost: £" + order.getTotalCost());
		totalCostLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		totalCostLbl.setFont(new Font("Arial", Font.BOLD, 12));
		totalCostLbl.setForeground(new Color(11, 26, 106));
		
		JScrollPane custScroll = new JScrollPane();
		custScroll.setBorder(null);
		
		JScrollPane orderScroll = new JScrollPane();
		orderScroll.setBorder(null);
		
		JLabel vatLbl = new JLabel("VAT: 20%");
		vatLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		vatLbl.setForeground(new Color(11, 26, 106));
		vatLbl.setFont(new Font("Arial", Font.BOLD, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(orderScroll, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(custScroll, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(IDlabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
							.addComponent(dateLbl)
							.addGap(21))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(staffTxt, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(paymentTxt, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(shippingTxt, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
							.addGap(11))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(staffLbl, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
							.addGap(7)
							.addComponent(paymentLbl, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(shippingLbl, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(totalCostLbl, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
							.addGap(47))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(vatLbl, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
							.addGap(47))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(IDlabel)
						.addComponent(dateLbl))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(custScroll, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(staffLbl, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(paymentLbl, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(shippingLbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(1)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(staffTxt, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
						.addComponent(paymentTxt, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(shippingTxt, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
					.addGap(20)
					.addComponent(orderScroll, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
					.addGap(2)
					.addComponent(vatLbl)
					.addGap(2)
					.addComponent(totalCostLbl)
					.addGap(8))
		);
		
		orderTable = new JTable();
		orderTable.setEnabled(false);
		orderTable.setRowSelectionAllowed(false);
		orderTable.setFont(new Font("Arial", Font.PLAIN, 12));
		orderTable.setFillsViewportHeight(true);
		orderTable.setModel(data.generateTable("Items", id));
		orderTable.getTableHeader().setOpaque(false);
		orderTable.getTableHeader().setBackground(new Color(220,220,220));
		orderTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		orderScroll.setViewportView(orderTable);
		
		custTable = new JTable();
		custTable.setEnabled(false);
		custTable.setRowSelectionAllowed(false);
		custTable.setFillsViewportHeight(true);
		custTable.setModel(data.generateTable("Customers",1));
		custTable.setFont(new Font("Arial", Font.PLAIN, 12));
		custTable.getTableHeader().setOpaque(false);
		custTable.getTableHeader().setBackground(new Color(220,220,220));
		custTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		custScroll.setViewportView(custTable);
		panel.setLayout(gl_panel);
		return panel;
	}
}
