package ie.revenue.rccourse.SpringJdbcTest;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) 
    		throws SQLException {

    	User user = new User();
    	user.setId(rs.getInt("id"));
    	user.setFirstName(rs.getString("firstName"));
    	user.setLastName(rs.getString("lastName"));
    	user.setRegistered(rs.getInt("registered")==1);
    	user.setDateOfBirth(rs.getString("dateOfBirth"));

        return user;
    }
}
