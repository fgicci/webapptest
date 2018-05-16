package com.proquest.interview.phonebook;

import com.proquest.interview.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class PhoneBookImpl implements PhoneBook {

	private Session session;
	private List<Person> people;
	private CriteriaQuery<Person> query;
	private CriteriaBuilder builder;

	public PhoneBookImpl() {
		session = HibernateUtil.getSession();
	}

	@Override
	public void addPerson(Person newPerson) {
		try {
			session.getTransaction().begin();
			session.merge(newPerson);
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		}
	}
	
	@Override
	public Person findPerson(String firstName, String lastName) {
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Person.class);
		Root root = query.from(Person.class);
		query.select(root).where(builder.equal(root.get("name"), (firstName + " " + lastName)));
		Optional<Person> optional = session.createQuery(query).getResultList().stream().findFirst();
		return optional.isPresent() ? optional.get() : null;
	}

	public List<Person> findAll() {
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Person.class);
		Root root = query.from(Person.class);
		query.select(root);
		return session.createQuery(query).getResultList();
	}
	
	public static void main(String[] args) {
		// DatabaseUtil.initDB();  // The entity manager creates the database using hsqldb.
		PhoneBook phoneBook = new PhoneBookImpl();
		// TODO: create person objects and put them in the PhoneBook and database
		phoneBook.addPerson(new Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI"));
		phoneBook.addPerson(new Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI"));

		// TODO: print the phone book out to System.out
		phoneBook.findAll().forEach(System.out::println);
		// TODO: find Cynthia Smith and print out just her entry
		System.out.println(phoneBook.findPerson("Cynthia", "Smith"));
		// TODO: insert the new person objects into the database
		phoneBook.addPerson(new Person("Fernando Gicci Hernandes", "+99 9999 239999", "0 Somewhere in this planet, Europe, Earth"));
	}
}
