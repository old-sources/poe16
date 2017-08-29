package poe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main2 {

	public static void main(String[] args) {
		//
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			// 1
			//Class<?> driverClass = Class.forName("com.mysql.jdbc.Driver");
			//DriverManager.registerDriver((Driver) driverClass.newInstance());
			// 2 création d'une connexion
			String url = "jdbc:mysql://localhost:3306/imie";
			String user = "root";
			String password = "";
			connection = DriverManager.getConnection(url, user, password);
			// 3 préparation d'une instruction
			String query = " select id, firstname, lastname from person ";
			statement = connection.prepareStatement(query);
			// 4 execution de l'instruction
			statement.execute();

			result = statement.executeQuery();
			// 5 obtention des résultats
			while (result.next()) {
				System.out.println("id : " + result.getInt("id"));
				System.out.println("prénom : " + result.getString("firstName"));
				System.out.println(result.getString("lastName"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 6 fermeture des résultats
			if (result != null) { try { result.close(); } catch (SQLException ex) {} }
			// 7 fermeture de l'instruction
			if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
			// 8 fermeture de la connexion
			if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
		}
	}

}
