package infopublica.connection;

import java.net.URI;

import org.junit.Test;

import static org.junit.Assert.*;

public class PostgresqlConnectionTest {

	@Test
	public void testConnection() {
		try {
		System.setProperty("DATABASE_URL", "postgres://mutfjskyzodbgy:F-fsWlJsdeDlBg-caaPrwoRuxO@ec2-23-23-126-39.compute-1.amazonaws.com:5432/dcn7ebmm696n6r");
		URI dbUri = new URI(System.getProperty("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    int port = dbUri.getPort();

	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath();
	    System.out.println(username);
	    System.out.println(password);
	    System.out.println(port);
	    System.out.println(dbUrl);
	    assertNotNull(username);
	    assertNotNull(password);
	    assertNotNull(port);
	    assertNotNull(dbUrl);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
