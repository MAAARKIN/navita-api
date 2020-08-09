package br.com.navita.api.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.navita.api.domain.model.Brand;
import br.com.navita.api.domain.repository.BrandRepository;
import br.com.navita.api.exception.AlreadyExistException;
import br.com.navita.api.exception.RecordNotFoundException;
import br.com.navita.api.util.GenericConverter;

@Service
public class BrandService {

	@Autowired
	private BrandRepository repository;

	public Brand find(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Brand save(final Brand brand) {
		Example<Brand> example = Example.of(
				brand, 
				ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()));
		boolean exist = repository.exists(example);
		
		if (exist)
			throw new AlreadyExistException("already exist a brand, consider change the name");
		
		return repository.save(brand);
	}

	public Page<Brand> list(Brand brandExample, Pageable pageable) {

		Example<Brand> example = Example.of(brandExample,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example, pageable);
	}

	public List<Brand> list() {

		List<Brand> result = repository.findAll();
		result.sort(Comparator.comparing(Brand::getName));
		return result;
	}
	
	public Brand update(Long id, Brand record) {

		Brand finded = this.find(id);
		if (finded == null)
			throw new RecordNotFoundException("The record that you trying to update does not exist");

		finded = GenericConverter.mapper(record, finded);

		return repository.save(finded);
	}

	public void delete(Long id) {

		Brand finded = this.find(id);
		if (finded == null)
			return;

		repository.delete(finded);
	}
}
