package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.ResouceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositores.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public List<Person> findAll() {
		logger.info("Finding all!");
		
		return repository.findAll();
	}

		public Person findById(Long id) {
		logger.info("Finding one person!");
		
		return repository.findById(id).orElseThrow(() -> new ResouceNotFoundException("No records found for this ID!"));
	}
		
	public Person created(Person person) {
		
		logger.info("Creating one person!");
		return repository.save(person);		
	}
	
	public Person updatad(Person person) {
		
		logger.info("update one person!");		
		
		var entity = repository.findById(person.getId()).orElseThrow(()-> new ResouceNotFoundException("No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAndress(person.getAndress());
		entity.setGender(person.getGender());
		
		return repository.save(person);
	}
	
	public void delete(Long id) {
		
		logger.info("delete one person!");
		var entity = repository.findById(id).orElseThrow(()-> new ResouceNotFoundException("No records found for this ID!"));
		
		repository.delete(entity);
	}
}
