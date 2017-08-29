package poe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

	private static final String FIND_ALL = " select id, firstname, lastname from person ";
	private static final String FIND_ONE = " select id, firstname, lastname from person where id = ? ";
	private static final String INSERT   = " insert into person ( firstname, lastname ) values ( ? , ? ) ";
	private static final String UPDATE   = " update      person set firstname = ? , lastname = ? where id = ? ";
	private static final String DELETE   = " delete from person where id = ? ";

	private Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/imie";
		String user = "root";
		String password = "";
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	public List<Person> findAll() {
		Connection co = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			co = getConnection();
			ps = co.prepareStatement(FIND_ALL);
			rs = ps.executeQuery();
			List<Person> list = new ArrayList<>();
			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("id"));
				p.setFirstName(rs.getString("firstName"));
				p.setLastName(rs.getString("lastName"));
				list.add(p);
			}
			return list;
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, rs); }
	}

	public Person findOne(int id) {
		Connection co = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			co = getConnection();
			ps = co.prepareStatement(FIND_ONE);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			// if (rs.next()) {
			Person p = new Person();
			p.setId(rs.getInt("id"));
			p.setFirstName(rs.getString("firstName"));
			p.setLastName(rs.getString("lastName"));
			return p;
			// } else {
			// throw new RuntimeException("Personne non trouvée pour l'id : " + id);
			// // ou bien
			// return null;
			// }
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, rs); }
	}

	/*

	public void insert(Person p) {
		Connection co = null;
		PreparedStatement ps = null;
		try {
			co = getConnection();
			ps = co.prepareStatement(INSERT);
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.execute();
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, null); }
	}

	public void update(Person p) {
		Connection co = null;
		PreparedStatement ps = null;
		try {
			co = getConnection();
			ps = co.prepareStatement(UPDATE);
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setInt(3, p.getId());
			ps.execute();
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, null); }
	}

	*/

	public void insert(Person p) { save(p, false); }

	public void update(Person p) { save(p, true); }

	public void save(Person p, boolean update) {
		Connection co = null;
		PreparedStatement ps = null;
		try {
			co = getConnection();
			ps = co.prepareStatement(update ? UPDATE : INSERT);
			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			if (update) {
				ps.setInt(3, p.getId());
			}
			ps.execute();
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, null); }
	}

	public void delete(int id) {
		Connection co = null;
		PreparedStatement ps = null;
		try {
			co = getConnection();
			ps = co.prepareStatement(DELETE);
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, null); }
	}

	private void close(Connection co, PreparedStatement ps, ResultSet rs) {
		if (rs != null) { try { rs.close(); } catch (SQLException ex) {} }
		if (ps != null) { try { ps.close(); } catch (SQLException ex) {} }
		if (co != null) { try { co.close(); } catch (SQLException ex) {} }
	}

}
