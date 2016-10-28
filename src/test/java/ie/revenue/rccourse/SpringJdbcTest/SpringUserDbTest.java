package ie.revenue.rccourse.SpringJdbcTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SpringUserDbTest {

	SpringUserDb userDb;
	
	@Before
	public void setup() throws SpringUserDbException {
	
		ApplicationContext context = 
				new ClassPathXmlApplicationContext(
				"SpringBeans.xml");
 
		DriverManagerDataSource dmds = 
				context.getBean(DriverManagerDataSource.class);
		
		//userDb = new SpringUserDb(dmds);
		userDb = context.getBean(SpringUserDb.class);

		//	we must have at least 1 user for these tests to run
		User user = new User(-1, "TESTER", "MCTESTFACE", true, "2000-01-01");
		userDb.create(user);
		
		//	we must have a few transactions for this user
		UserTransaction ut = new UserTransaction(-1, user.getId(), 
				"Transaction for Testing", "2016-01-01", 100.0);
		userDb.createTransaction(ut);
		
		ut = new UserTransaction(-1, user.getId(), 
				"Second Transaction for Testing", "2016-01-01", -50.0);
		userDb.createTransaction(ut);
		
	}

	@After
	public void tearDown() {

		//	delete the remaining 9 test users
		
		userDb.close();
	}
	
	@Test
	public void testGetUser() throws SpringUserDbException {
		
		List<User> users = userDb.getAll();
		
		User userToFind = users.get(0);
		User user;

		user = userDb.getUser(userToFind.getId());
		
		assertTrue(user.equals(userToFind));

	}
	
	@Test
	public void testCreate() throws SpringUserDbException {
		
		User user = new User(-1, "FirstName", "LastName", false, "2000-01-01");
		
		userDb.create(user);
		
		int id = user.getId();
		
		User check = userDb.getUser(id);
		
		assertTrue(user.equals(check));
	
		
	}
	
	@Test
	public void testDelete() {
		
		List<User> users = userDb.getAll();
		
		User userToDelete = users.get(0);
		
		int beforeCount = users.size();
		
		userDb.delete(userToDelete.getId());
		
		users = userDb.getAll();
		
		int afterCount = users.size();
		
		assertTrue(afterCount == beforeCount - 1);
		
	}
	
	@Test
	public void testDeleteWithTransactions() throws SpringUserDbException {
		
		User user = new User(-1, "TESTER", "MCTESTFACE", false, "2000-01-02");
		
		userDb.create(user);
		
		UserTransaction tx = new UserTransaction(-1, user.getId(), 
				"TEST TX", "2000-01-02", 99.0);
		userDb.createTransaction(tx);
		userDb.delete(user.getId());
		
		List<UserTransaction>txs = userDb.getTransactionsForUser(user.getId());
		assertTrue(txs.size() == 0);
		
	}
	
	@Test
	public void testSearch() throws SpringUserDbException {
		
		User user = new User(-1, "someXXXthing", "someXXXthing", false, "2000-01-01");
		
		userDb.create(user);
		
		List<User> results = userDb.find("XXX");
		
		assertTrue(results.size() > 0);
		
	}
}
