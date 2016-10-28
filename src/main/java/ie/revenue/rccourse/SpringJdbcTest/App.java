package ie.revenue.rccourse.SpringJdbcTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext(
				"SpringBeans.xml");

		DriverManagerDataSource dmds = 
				context.getBean(DriverManagerDataSource.class);
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dmds);
		
		String sql = "INSERT INTO users " +
					 "(firstName, lastName, registered, dateOfBirth)"
				+ "VALUES(?, ?, ?, ?)";
		
		//jdbcTemplate.update(sql, new Object[] { 
		//		"new", "user", 0, "2000-01-02"
		//}); 
		
        sql = "SELECT * FROM users WHERE ID = ?";
 
        //jdbcTemplate = new JdbcTemplate(dmds);

        int id = 59; 
        		
        User user = (User) jdbcTemplate.queryForObject(
                sql, new Object[] { id }, new UserRowMapper());

      
        sql = "SELECT * FROM users";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        
        ArrayList<User> users = new ArrayList<User>();
        for (Map row : rows) {

        	User u = new User();
        	u.setId(Integer.parseInt(String.valueOf(row.get("id"))));
        	u.setFirstName(String.valueOf(row.get("firstName")));
        	u.setLastName(String.valueOf(row.get("lastName")));
        	u.setRegistered((Integer)row.get("registered")==1);
        	
        	if (row.get("dateOfBirth") != null) {
        		u.setDateOfBirth(row.get("dateOfBirth").toString());
        	}
        	
        	users.add(u);
        }
      
        for (User u : users) {
        	System.out.println(u);
        }
        
        
        
        
        
        /*
        
        sql = "SELECT * FROM users";
        List<Object> users = jdbcTemplate.queryForList(sql, null, new UserRowMapper());
        
        for (Object u : users) {
        	System.out.println((User)u);
        }
        
        System.out.println(user);
		
		
		*/
		
		
		

		/*
		try {
			Connection conn = dmds.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM users");
			
			while (rs.next()) {
				
				System.out.println(rs.getString("firstName"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
    }
}
