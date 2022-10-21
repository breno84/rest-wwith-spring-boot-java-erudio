package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
public class PersonServices {
	private final AtomicLong coutner = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public List<Person> findAll() {
		logger.info("Finding all!");
		
		List<Person> persons = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		return persons;
	}

		public Person findById(String id) {
		logger.info("Finding one person!");
		
		Person person = new Person();
		person.setId(coutner.incrementAndGet());
		person.setFirstName("Leoa");
		person.setLastName("Last name ");
		person.setAndress("Gama- DF");
		person.setGender("Male");
		return person;
	}
		
	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(coutner.incrementAndGet());
		person.setFirstName("Person name " + i);
		person.setLastName("Last name " + i);
		person.setAndress("Some andress " + i);
		person.setGender("Male");
		return person;
	}
	
	public Person created(Person person) {
		
		logger.info("Creating one person!");
		return person;
		
	}
	public Person updatad(Person person) {
		
		logger.info("update one person!");
		return person;
		
	}
	
	public void delete(String id) {
		
		logger.info("delete one person!");
	
	}
}
