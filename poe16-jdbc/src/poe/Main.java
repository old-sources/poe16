package poe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
//		bidouille(sc);
		mainLoop(sc);
		System.out.println("Terminé");
		sc.close();
	}

	static final int FIND_ALL = 1;
	static final int FIND_BY  = 2;
	static final int INSERT   = 3;
	static final int DELETE   = 5;

	private static void mainLoop(Scanner sc) {
		PersonDAO dao = new PersonDAO();
		String choice;
		do {
			System.out.println("Faites un choix : ");
			System.out.println(FIND_ALL + " : afficher toutes les personnes");
			System.out.println(FIND_BY  + " : afficher une personne par id");
			System.out.println(INSERT   + " : ajouter  une personne");
			System.out.println(DELETE   + " : retirer  une personne par id");
			System.out.println("Enter : quitter l'application");

			choice = sc.nextLine();
			if ( choice.trim().isEmpty() || false ) break ;

			switch (Integer.parseInt(choice)) {
			case FIND_ALL:
				displayAll(dao);
				break;
			case FIND_BY:
				displayOne(dao, sc);
				break;
			case INSERT:
				insert(dao, sc);
				break;
			case DELETE:
				delete(dao, sc);
				break;
			default:
				System.out.println("choix <" + choice + "> non disponible");
				break;
			}

		} while ( true ) ;
	}

	private static void displayAll(PersonDAO dao) {
		List<Person> list = dao.findAll();
		for (Person person : list) {
			displayPerson(person);
		}
	}

	private static void displayOne(PersonDAO dao, Scanner sc) {
		int id = sc.nextInt();
		Person person = dao.findOne(id);
		displayPerson(person);
	}

	private static void displayPerson(Person person) {
		String id = person.getId() == null ? null : person.getId().toString();
		String birthDate = person.getBirthDate() == null ? null
				: person.getBirthDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		System.out.println(String.join(" | ", 
				id, 
				person.getFirstName(), 
				person.getLastName(),
				birthDate
				));
	}

	private static void insert(PersonDAO dao, Scanner sc) {
		Person person = new Person();

		System.out.println("prénom : ");
		person.setFirstName(sc.nextLine());
		System.out.println("nom : ");
		person.setLastName(sc.nextLine());
		System.out.println("date de naissance : ");
		person.setBirthDate(LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		displayPerson(person);

		dao.insert(person);
		displayPerson(person);
	}

	private static void delete(PersonDAO dao, Scanner sc) {
		int id = sc.nextInt();
		dao.delete(id);
	}

	private static void bidouille(Scanner sc) {
		try {
			sc.nextInt();
		} catch (InputMismatchException ex) {
			System.out.println("format invalide");
		}
		try {
			String str = sc.nextLine();
			Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			System.out.println("format invalide");
		}
		try {
			String str = sc.nextLine();
			java.util.Date date = 
					new SimpleDateFormat("dd/MM/yyyy").parse(str);
		} catch (ParseException e) {
			System.out.println("format invalide");
		}
		try {
			String str = sc.nextLine();
			DateTimeFormatter format = 
					DateTimeFormatter.ofPattern("dd/MM/yyyy");
			java.time.LocalDate date = LocalDate.parse(str, format);
		} catch (InputMismatchException ex) {
			System.out.println("format invalide");
		}
	}

}
