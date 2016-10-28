package ie.revenue.rccourse.SpringJdbcTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class SpringUserDb {

	//	properties
	DriverManagerDataSource dmds;
	protected JdbcTemplate jdbcTemplate;
	
	//	get & set methods
	public DriverManagerDataSource getDmds() {
		return dmds;
	}

	@Autowired
	public void setDmds(DriverManagerDataSource dmds) {
		this.dmds = dmds;
		
		jdbcTemplate = new JdbcTemplate(dmds);
	}

	//	constructor(s)
	public SpringUserDb() {
		
	}
	
	public SpringUserDb(DriverManagerDataSource dmds) {
		this.dmds = dmds;
	}
	
	//	other methods
	
	//	get the user with the specified id
	public User getUser(int id) throws SpringUserDbException {
		User user = null;
		String sql = "SELECT * FROM users WHERE id=?";
		
		try {
	        user = (User) jdbcTemplate.queryForObject(
	                sql, new Object[] { id }, 
	                new UserRowMapper());//BeanPropertyRowMapper<User>(User.class));
			
		} catch (Exception ex) {
			throw new SpringUserDbException(ex.getMessage());
		}
		return user;
	}
	
	public List<User> getAll() {
		String sql = "SELECT * FROM users";

		List<User> users = jdbcTemplate.query(sql, 
				new BeanPropertyRowMapper<User>(User.class));
		return users;

	}

	public List<User> find(String search) {
	
		//String sql = "SELECT * from users " +
		//			 "WHERE firstName LIKE '%" + search + "%' " +
		//			 "OR lastName LIKE '%" + search + "%";
		
		String sql = "SELECT * from users " +
					 "WHERE firstName LIKE ? " +
					 "OR lastName LIKE ?";
		
		List<User> users = jdbcTemplate.query(sql,
				new Object[]{"%" + search + "%", "%" + search + "%"},
				new BeanPropertyRowMapper<User>(User.class));
		
		return users;
	}
	
	public void create(User user) throws SpringUserDbException {
	
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.setTableName("users");
		jdbcInsert.setGeneratedKeyName("id");
		
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put("firstName", user.getFirstName());
        parameters.put("lastName", user.getLastName());
        parameters.put("registered", user.isRegistered()?1:0);
        parameters.put("dateOfBirth", user.getDateOfBirth());

		Number id = jdbcInsert.executeAndReturnKey(
				new MapSqlParameterSource(parameters));
		user.setId(id.intValue());
	}
	
	public void delete(int id) {

		//	delete the transactions for the user
		deleteTransactionsForUser(id);
		
		//	delete the user
		String sql = "DELETE FROM users WHERE id = ?";
		jdbcTemplate.update(sql, new Object[]{id});
	}
	
	public void update(User user) {
		
		String sql = "UPDATE users " + 
					 "SET firstName = ?, " +
					 "lastName = ?, " +
					 "registered = ?, " +
					 "dateOfBirth = ? " +
					 "WHERE id = ?";
		jdbcTemplate.update(sql, new Object[]{user.getFirstName(), 
				user.getLastName(), 
				user.isRegistered(), 
				user.getDateOfBirth(), 
				user.getId()});
	}
	
	public List<UserTransaction> getTransactionsForUser(int userId) {
		
		String sql = "SELECT * FROM transactions WHERE userId=?";

		List<UserTransaction> transactions = jdbcTemplate.query(sql, 
				new Object[]{userId},  
				new BeanPropertyRowMapper<UserTransaction>(UserTransaction.class));
		return transactions;
	}

	public UserTransaction createTransaction(UserTransaction userTransaction) {

		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.setTableName("transactions");
		jdbcInsert.setGeneratedKeyName("id");
		
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put("userId", userTransaction.getUserId());
        parameters.put("description", userTransaction.getDescription());
        parameters.put("transactionDate", userTransaction.getTransactionDate());
        parameters.put("amount", userTransaction.getAmount());

		Number id = jdbcInsert.executeAndReturnKey(
				new MapSqlParameterSource(parameters));
		userTransaction.setId(id.intValue());

		return userTransaction;
	}
	
/*	public UserTransaction create(User user, UserTransaction userTransaction) {#
		
		return userTransaction;
	}
*/	
	public void updateTransaction(UserTransaction userTransaction) {
		String sql = "UPDATE transactions " +
					 "SET description = ?, " +
					 "amount = ?, " +
					 "transactionDate = ? " +
					 "WHERE id = ?";
		jdbcTemplate.update(sql, new Object[]{
				userTransaction.getDescription(), 
				userTransaction.getAmount(), 
				userTransaction.getTransactionDate(), 
				userTransaction.getId()
			});
	}
	
	public void deleteTransaction(int transactionId) {
		String sql = "DELETE FROM transactions WHERE id = ?";
		jdbcTemplate.update(sql, new Object[]{transactionId});
	}
	
	public void deleteTransactionsForUser(User user) {
		String sql = "DELETE FROM transactions WHERE userId=?";
		jdbcTemplate.update(sql, new Object[]{user.getId()});
	}
	
	public void deleteTransactionsForUser(int userId) {
		String sql = "DELETE FROM transactions WHERE userId=?";
		jdbcTemplate.update(sql, new Object[]{userId});
	}
	
	public void close() {
		
	}
	
	public static void main(String[] args) {
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext(
				"SpringBeans.xml");
        //ApplicationContext context = 
        //		new AnnotationConfigApplicationContext(AppConfiguration.class);

		DriverManagerDataSource dmds = 
				context.getBean(DriverManagerDataSource.class);
		
		SpringUserDb userDb = context.getBean(SpringUserDb.class);
		
		//SpringUserDb userDb = new SpringUserDb(dmds);
		
		userDb.deleteTransaction(1);
		userDb.deleteTransactionsForUser(54);

		User u = null;
		try {
			u = userDb.getUser(58);
		} catch (SpringUserDbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userDb.deleteTransactionsForUser(u);
		
		
		
		List<UserTransaction>txs = userDb.getTransactionsForUser(57);
		
		for (UserTransaction tx : txs) {
			tx.setAmount(1999.99);
			userDb.updateTransaction(tx);
		}
		
		txs = userDb.getTransactionsForUser(57);
		for (UserTransaction tx : txs) {
			System.out.println(tx);
		}
		
		/*
		User user = new User(-1, "NEW", "USER", true, "2000-01-02");
		try {
			userDb.create(user);
		} catch (SpringUserDbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("NEW USER:" + user.getId());
		
		userDb.delete(57);
		
		//user.setId(47);
		user.setFirstName("CHANGED");
		user.setLastName("CHANGED");
		
		userDb.update(user);
		
		user = null;
		try {
			user = userDb.getUser(57);
			System.out.println(user);
			
		} catch (SpringUserDbException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("User not found");
		}
		
		List<User>users = userDb.getAll();
		
		for (User u : users) {
			System.out.println(u);
		}
		*/
		/*
		
		
		
		user.setFirstName("UPDATED");
		user.setLastName("NAME");
		user.setRegistered(true);
		user.setDateOfBirth("1970-01-13");
		
		userDb.update(user);
		*/
		
		
		
		
		
		//userDb.delete(5);
		
		/*
		User user = new User (-1, "new", "user", false, "2000-01-01");
		
		userDb.create(user);
		*/
		//User user = userDb.getUser(5);
		
		//System.out.println(user);
		
		
		//ArrayList<User> userList = userDb.getAll();
		
		//for (User user : userList) {
			
		//	System.out.println(user);
		//}
		
		userDb.close();
	}

}
