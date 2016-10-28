package ie.revenue.rccourse.SpringJdbcTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfiguration {
	
	public @Bean DriverManagerDataSource dataSource() {
		DriverManagerDataSource dmds = new DriverManagerDataSource();

		dmds.setDriverClassName("com.mysql.jdbc.Driver");
        dmds.setUrl("jdbc:mysql://localhost:3306/rccourse");
		dmds.setUsername("root");
		dmds.setPassword("root");
		
		return dmds;
	}

	public @Bean SpringUserDb userDb() {
		
		SpringUserDb userDb = new SpringUserDb();
		
		return userDb;
		
	}
}
