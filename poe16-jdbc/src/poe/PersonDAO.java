package poe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO extends AbstractDAO {

	private static final String FIND_ALL = " select id, firstname, lastname, birthdate from person ";
	private static final String FIND_ONE = " select id, firstname, lastname, birthdate from person where id = ? ";
	private static final String INSERT   = " insert into person ( firstname, lastname, birthdate ) values ( ? , ? , ? ) ";
	private static final String UPDATE   = " update      person set firstname = ? , lastname = ? , birthdate = ? where id = ? ";
	private static final String DELETE   = " delete from person where id = ? ";

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
				Person p = mapRow(rs);
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
			Person p = mapRow(rs);
			return p;
			// } else {
			// throw new RuntimeException("Personne non trouvee pour l'id : " + id);
			// // ou bien
			// return null;
			// }
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, rs); }
	}

	private Person mapRow(ResultSet rs) throws SQLException {
		Person p = new Person();
		p.setId(rs.getInt("id"));
		p.setFirstName(rs.getString("firstName"));
		p.setLastName(rs.getString("lastName"));
		if ( rs.getDate("birthDate") != null ) {
			p.setBirthDate(rs.getDate("birthDate").toLocalDate());
		}
		return p;
	}

	public void insert(Person p) {
		Connection co = null;
		PreparedStatement ps = null;
		ResultSet keys = null;
		try {
			co = getConnection();
			ps = co.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			setValues(ps, p, false);
			ps.execute();
			// obtention de l'id apres insertion (si besoin)
			keys = ps.getGeneratedKeys();
			keys.next();
			p.setId(keys.getInt(1));
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, keys); }
	}

	public void update(Person p) {
		Connection co = null;
		PreparedStatement ps = null;
		try {
			co = getConnection();
			ps = co.prepareStatement(UPDATE);
			setValues(ps, p, true);
			ps.execute();
		} catch (SQLException ex) { throw new RuntimeException(ex);
		} finally { close(co, ps, null); }
	}

	private void setValues(PreparedStatement ps, Person p, boolean update) throws SQLException {
		int ii = 1;
		ps.setString(ii++, p.getFirstName());
		ps.setString(ii++, p.getLastName());
		ps.setDate(ii++, java.sql.Date.valueOf(p.getBirthDate()));
		if (update) {
			ps.setInt(ii++, p.getId());
		}
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

}
