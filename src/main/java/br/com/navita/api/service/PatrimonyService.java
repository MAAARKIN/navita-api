package br.com.navita.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.navita.api.domain.model.Patrimony;
import br.com.navita.api.domain.repository.PatrimonyRepository;
import br.com.navita.api.exception.RecordNotFoundException;
import br.com.navita.api.util.GenericConverter;

@Service
public class PatrimonyService {

	@Autowired
	private PatrimonyRepository repository;

	public Patrimony find(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Patrimony save(final Patrimony patrimony) {
		
		return repository.save(patrimony);
	}

	public Page<Patrimony> list(Patrimony patrimonyExample, Pageable pageable) {

		Example<Patrimony> example = Example.of(patrimonyExample,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example, pageable);
	}

	public List<Patrimony> list() {

		return repository.findAll();
	}

	public Patrimony update(Long id, Patrimony record) {

		Patrimony finded = this.find(id);
		if (finded == null)
			throw new RecordNotFoundException("The record that you trying to update does not exist");

		finded = GenericConverter.mapper(record, finded);

		return repository.save(finded);
	}

	public void delete(Long id) {

		Patrimony finded = this.find(id);
		if (finded == null)
			return;

		repository.delete(finded);
	}
}
