package cam.business.logic;

//Author: Cameron Morrison 569530
//Second year graded unit project. 

class Staff {
	private int staffID;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String job;
	private String passwordHash;
	
	protected Staff(int staffID, String firstName, String lastName, String phoneNumber, String job, String passwordHash){
		this.staffID = staffID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.job = job;	
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the staffID
	 */
	public int getStaffID() {
		return staffID;
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
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @return the Password Hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
	//Used to display relevant information in the staff text area on the invoice panel.
	public String toString() {
		return  "ID: " + getStaffID() + "\n" + getFirstName() + " " + 
				getLastName() + "\n" + getPhoneNumber();
	}
}
