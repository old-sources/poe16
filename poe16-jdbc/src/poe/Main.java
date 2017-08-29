package poe;

import java.util.List;

public class Main {

	public static void main(String[] args) {

		PersonDAO dao = new PersonDAO();

		Person p = new Person();
		p.setFirstName("john f");
		p.setLastName("kennedy");

		dao.insert(p);

		List<Person> list = dao.findAll();

		System.out.println(list.size());

		for (Person person : list) {
			System.out.println(person.getId());
			System.out.println(person.getFirstName());
			System.out.println(person.getLastName());
		}

		Person p2 = dao.findOne(-1);

		
		
		System.out.println(p2.getFirstName());
	}

}
