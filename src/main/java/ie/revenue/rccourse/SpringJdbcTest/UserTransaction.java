package ie.revenue.rccourse.SpringJdbcTest;

import java.sql.Date;

public class UserTransaction {

	//	properties
	protected int id;
	protected int userId;
	protected String description;
	protected String transactionDate;
	protected double amount;
	
	//	get & set methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	//	constructor(s)
	public UserTransaction(){
		
	}
	
	public UserTransaction(int id, int userId, 
			String description, String transactionDate, 
			double amount) {
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.transactionDate = transactionDate;
		this.amount = amount;
	}

	//	other methods
	@Override
	public String toString() {
		return "UserTransaction [id=" + id + ", userId=" + userId + ", description=" + description
				+ ", transactionDate=" + transactionDate + ", amount=" + amount + "]";
	}


	public static void main(String[] args) {

		UserTransaction ut = new UserTransaction(1, 54, 
				"Opening balance", "2016-10-19", 99.99);
		
		System.out.println(ut);
	}

}
