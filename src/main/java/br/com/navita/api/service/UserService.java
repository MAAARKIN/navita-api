package br.com.navita.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.navita.api.domain.model.User;
import br.com.navita.api.domain.repository.UserRepository;
import br.com.navita.api.exception.AlreadyExistException;
import br.com.navita.api.exception.RecordNotFoundException;
import br.com.navita.api.util.GenericConverter;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	public User find(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public User findByEmail(String email) {
		return repository.findByEmail(email).orElse(null);
	}

	public User save(final User user) {
		Example<User> example = Example.of(
				user, 
				ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()));
		boolean exist = repository.exists(example);
		
		if (exist)
			throw new AlreadyExistException("already exist a user with this email");
		
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		
		return repository.save(user);
	}

	public Page<User> list(User customExample, Pageable pageable) {

		Example<User> example = Example.of(customExample,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example, pageable);
	}

	public List<User> list() {

		return repository.findAll();
	}
	
	public User update(Long id, User record) {

		User finded = this.find(id);
		if (finded == null)
			throw new RecordNotFoundException("The record that you trying to update does not exist");

		finded = GenericConverter.mapper(record, finded);

		return repository.save(finded);
	}

	public void delete(Long id) {

		User finded = this.find(id);
		if (finded == null)
			return;

		repository.delete(finded);
	}
}
