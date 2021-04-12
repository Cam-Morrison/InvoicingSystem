package cam.business.logic;

//Author: Cameron Morrison 569530
//Second year graded unit project. 

class Clients {
	private String firstName;
	private String lastName;
	private int customerId;
	private String email;
	private String streetAddress;
	private String city;
	private String zipCode;
	private String country;
	
	Clients(String firstName, String lastName, int customerId, String email, String streetAddress, String city, String zipCode, String country){
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerId = customerId;
		this.email = email;
		this.streetAddress = streetAddress;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
}
