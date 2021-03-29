package cam.business.logic;

class Suppliers {
	private int supplierID;
	private String companyName;
	private String phoneNumber;
	private String email;
	
	Suppliers(int supplierID, String companyName, String phoneNumber, String email){
		this.supplierID = supplierID;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	/**
	 * @return the supplierID
	 */
	public int getSupplierID() {
		return supplierID;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
}
