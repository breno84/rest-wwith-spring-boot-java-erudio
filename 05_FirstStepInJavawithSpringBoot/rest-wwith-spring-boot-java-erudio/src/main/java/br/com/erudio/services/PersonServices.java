package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResouceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositores.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;

	public List<PersonVO> findAll() {
		logger.info("Finding all!");

		return DozerMapper.parseListObjecto(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {

		logger.info("Finding one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("No records found for this ID!"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO created(PersonVO person) {

		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;		
	}

	public PersonVO updatad(PersonVO person) {

		logger.info("update one person!");

		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResouceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAndress(person.getAndress());
		entity.setPassword(person.getPassword());
		entity.setGender(person.getGender());

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public void delete(Long id) {

		logger.info("delete one person!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("No records found for this ID!"));

		repository.delete(entity);
	}
}
