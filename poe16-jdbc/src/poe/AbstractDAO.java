package poe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractDAO {

	protected Connection getConnection() throws SQLException {
		try {
			Properties props = new Properties();
			props.load(ClassLoader.getSystemResourceAsStream("jdbc.properties"));

			// si la connexion ne marche plus avec JAVA EE
			// demandez de l'aide à votre formateur
			// tentez de decommenter les lignes suivantes

			// String driver = props.getProperty("driver");
			// Class<?> driverClass = Class.forName(driver);
			// DriverManager.registerDriver((Driver) driverClass.newInstance());

			String url = props.getProperty("url");
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			Connection connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	protected void close(Connection co, PreparedStatement ps, ResultSet rs) {
		if (rs != null) { try { rs.close(); } catch (SQLException ex) {} }
		if (ps != null) { try { ps.close(); } catch (SQLException ex) {} }
		if (co != null) { try { co.close(); } catch (SQLException ex) {} }
	}

}
