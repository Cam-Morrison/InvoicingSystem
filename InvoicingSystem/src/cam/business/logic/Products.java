package cam.business.logic;

//Author: Cameron Morrison 569530
//Second year graded unit project. 

class Products {
	private int productId;
	private String name;
	private double price;
	private int stockQuantity;
	private int supplierId;
	private int avaliable;
	private String description;
	
	Products(int productId, String name, double price, int stockQuantity, int supplierId, int avaliable, String description){
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.supplierId = supplierId;
		this.avaliable = avaliable;
		this.description = description;
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
	 * @return the availability of product
	 */
	public int getAvaliable() {
		return avaliable;
	}
	
	/**
	 * @return the string of description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param delist or relist product
	 */
	public void swapAvaliable() {
		if(this.avaliable == 1) {
			this.avaliable = 0;
		} else {
			this.avaliable = 1;
		}
	}
	
	/**
	 * @param price the price to set
	 */
	protected void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param update stock quantity
	 */
	protected void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	/**
	 * @param update description
	 */
	protected void setDesc(String desc) {
		this.description = desc;
	}
	

}
