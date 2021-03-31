package cam.business.logic;

class Products {
	private int productId;
	private String name;
	private double price;
	private int stockQuantity;
	private int supplierId;
	
	Products(int productId, String name, double price, int stockQuantity, int supplierId){
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.supplierId = supplierId;
	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the stockQuantity
	 */
	public int getStockQuantity() {
		return stockQuantity;
	}

	/**
	 * @return the supplierId
	 */
	public int getSupplierId() {
		return supplierId;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param stockQuantity the stockQuantity to set
	 */
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

}
