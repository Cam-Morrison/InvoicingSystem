package cam.business.logic;

import java.util.ArrayList;
import java.util.Date;

class Transactions {
	private int orderId;
	private Date orderDate;
	private double totalCost;
	private Date dueDate;
	private int customerId;
	private int staffId;
	private int shippingId;
	private String shippingMethod;
	private Date estimatedDate;
	private ArrayList<InvoiceItem> items;
	
	
	//Constructor to create new Transaction
	Transactions(int orderId, Date orderDate, double totalCost, Date dueDate, int customerId, int staffId, int shippingId,
			String shippingMethod, Date estimatedDate, ArrayList<InvoiceItem> items){
		
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.totalCost = totalCost;
		this.dueDate = dueDate;
		this.customerId = customerId;
		this.staffId = staffId;
		this.shippingId = shippingId;
		this.shippingMethod = shippingMethod;
		this.estimatedDate = estimatedDate;
		this.items = items;
	}
	
	//Getters
	
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}
	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return totalCost;
	}
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * @return the staffId
	 */
	public int getStaffId() {
		return staffId;
	}
	/**
	 * @return the shippingId
	 */
	public int getShippingId() {
		return shippingId;
	}
	/**
	 * @return the shippingMethod
	 */
	public String getShippingMethod() {
		return shippingMethod;
	}
	/**
	 * @return the estimatedDate
	 */
	public Date getEstimatedDate() {
		return estimatedDate;
	}
	/**
	 * @return the invoiceItems
	 */
	public ArrayList<InvoiceItem> getItems() {
		return items;
	}
}
