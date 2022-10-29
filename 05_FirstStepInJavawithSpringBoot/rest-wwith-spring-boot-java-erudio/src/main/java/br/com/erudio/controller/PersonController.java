package br.com.erudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.erudio.model.Person;
import br.com.erudio.model.vo.LoginVO;
import br.com.erudio.services.PersonServices;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	
	@Autowired
	private PersonServices service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	@GetMapping(value = "email/{email}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findByEmail(@PathVariable(value = "email") String email) {
		return service.findByEmail(email);
	}
	
	@GetMapping(value = "/recover")
	public String getSecurityQuestion(@RequestParam String email) {
		Person person = service.findByEmail(email);
		return person.getSecurityQuestion();
	}
	
	@PostMapping(value = "/recover")
	public String resetResponse(@RequestParam String email,@RequestParam String response) {
		Person person = service.findByEmail(email);
		if(!person.getSecurityResponse().equals(response)) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Resposta nao compativel\n");
		}
		int min = 100000;
		int max = 999999;		 
		String generatedPassword = String.valueOf((int) ( (long) min + Math.random() * ((long)max - min + 1))) ;
		person.setPassword(generatedPassword);
		service.updated(person);
		return generatedPassword;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person) {
		
		return service.created(person);
	}
		
	@PostMapping(value = "/login")
	public boolean doLogin(@RequestBody LoginVO login) {
		Person person = service.findByEmail(login.getEmail());
		if(login.getPassword().equals(person.getPassword())) {
			return true;			
		}
		return false;
	}	
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person update(@RequestBody Person person) {
		return service.updated(person);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}