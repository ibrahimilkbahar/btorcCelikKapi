package tr.com.mindworks.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	private static ConnectionProvider instance;
	private static Connection con;

	private ConnectionProvider() {
	}

	public static synchronized ConnectionProvider init() {
		if (instance == null) {
			instance = new ConnectionProvider();
		}

		try {
			if (con == null || con.isClosed() || !con.isValid(1000)) {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/btorc?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey", "btorc", "1qaz2WSX");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return instance;
	}
	public static synchronized Connection getConnection() {
		return con;
	}
}
