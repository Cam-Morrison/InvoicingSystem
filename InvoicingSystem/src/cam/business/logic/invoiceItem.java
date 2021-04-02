package cam.business.logic;

class InvoiceItem {
	private int purchaseQuantity;
	private String itemDescription;
	private double discount;
	private int orderId;
	private int productId;
	
	InvoiceItem(int purchaseQuantity, String itemDescription, double discount, int orderId, int productId){
		this.purchaseQuantity = purchaseQuantity;
		this.itemDescription = itemDescription;
		this.discount = discount;
		this.orderId = orderId;
		this.productId = productId;	
	}
	
	/**
	 * @return the purchaseQuantity
	 */
	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}
	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
}
