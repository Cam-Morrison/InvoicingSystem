package cam.business.logic;

class InvoiceItem {
	private int purchaseQuantity;
	private double discount;
	private int orderId;
	private int productId;
	private double purchasePrice;
	
	InvoiceItem(int purchaseQuantity, double discount, int orderId, int productId, double purchasePrice){
		this.purchaseQuantity = purchaseQuantity;
		this.discount = discount;
		this.orderId = orderId;
		this.productId = productId;	
		this.purchasePrice = purchasePrice;
	}
	
	/**
	 * @return the purchaseQuantity
	 */
	public int getPurchaseQuantity() {
		return purchaseQuantity;
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
	/**
	 * @return the purchasePrice
	 */
	public double getPurchasePrice() {
		return purchasePrice;
	}
}
