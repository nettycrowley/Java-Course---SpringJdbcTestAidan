package ie.revenue.rccourse.SpringJdbcTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

	//	properties
	protected int id;
	protected String firstName;
	protected String lastName;
	protected boolean registered;
	protected String dateOfBirth;
	
	//	get & set methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	//	constructor(s)
	public User() {
		
	}
	public User(int id, String firstName, String lastName, boolean registered, String dateOfBirth) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.registered = registered;
		this.dateOfBirth = dateOfBirth;
	}

	//	other methods
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", registered=" + registered
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}

	public void display() {
		System.out.println(this);
	}
	
	public String toJson() {
		
		//String json = "{" +
		//			    	"\"id\":" + id + "," +
		//			    	"\"firstName\":\"" + firstName + "\", " +
		//			    	"\"lastName\":\"" + lastName + "\", " +
		//			    	"\"registered\":" + registered + ", " +
		//			    	"\"dateOfBirth\":\"" + dateOfBirth + "\"" +
		//			  "}";
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json;
		try {
			json = objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			json = "{}";
		}
		
		return json;
					    	
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (registered ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (registered != other.registered)
			return false;
		return true;
	}

	public static void main(String[] args) {
		
		
		
		User user = new User(1, "Aidan", "Killeen", false, "13/1/1970");
		
		System.out.println(user.toJson());
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String json = "";
		try {
			json = objectMapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		
		System.out.println(json);

	}

}
