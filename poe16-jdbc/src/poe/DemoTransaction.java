package poe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DemoTransaction {

	public static void main(String[] args) throws SQLException {
		Connection co = null;
		Statement ps = null;
		ResultSet rs = null;
		try {
			String url = "jdbc:mysql://localhost:3306/imie?zeroDateTimeBehavior=convertToNull";
			String user = "root";
			String password = "";
			co = DriverManager.getConnection(url, user, password);
			co.setAutoCommit(false); // tx begin
			ps = co.createStatement();
			ps.execute("DELETE FROM PERSON WHERE ID=1");
			ps.execute("DELETE FROM PERSON WHERE ID=2");
			co.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			co.rollback();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException ex) {} }
			if (ps != null) { try { ps.close(); } catch (SQLException ex) {} }
			if (co != null) { try { co.close(); } catch (SQLException ex) {} }
		}
	}

}
