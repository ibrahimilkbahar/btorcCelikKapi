package tr.com.mindworks.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class PropertiesUtil {
	private static HashMap<String, String> result;
	private static PropertiesUtil instance;
	
	private PropertiesUtil() {
	}

	public static synchronized PropertiesUtil init() {
		if (instance == null) {
			instance = new PropertiesUtil();
			result = findAllProperties();
		}
		return instance;
	}
	
	public String getPropertyValue(String code) {
		if(result.containsKey(code)) 
		{
			return result.get(code);
		}	
		return "";
	}
	
	private static HashMap<String, String> findAllProperties() {
		Statement st = null;
		ResultSet rs = null;

		try {
			System.out.println("Sistem Parametreleri yükleniyor.");
			ConnectionProvider.init();
			result = new HashMap<String, String>();
			String query = "SELECT * FROM T_System_Parameter";
			st = ConnectionProvider.getConnection().createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				result.put(rs.getString("Code"),rs.getString("Value"));
			}
			System.out.println("Sistem Parametreleri Yükleme işlemi tamamlandı.");

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getStackTrace().toString());
		} finally {
			try {
				rs.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		 
		return result;

	}
}
