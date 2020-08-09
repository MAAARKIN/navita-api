package br.com.navita.api.web.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.navita.api.domain.model.Brand;
import br.com.navita.api.domain.model.Patrimony;
import br.com.navita.api.service.PatrimonyService;
import br.com.navita.api.util.GenericConverter;
import br.com.navita.api.web.payload.BrandDTO;
import br.com.navita.api.web.payload.PageableDTO;
import br.com.navita.api.web.payload.PatrimonyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/patrimonies", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = "/patrimonies")
public class PatrimonyController {

	@Autowired
	private PatrimonyService service;

	@ApiOperation(value = "Save a new patrimony")
	@PostMapping
	public ResponseEntity<String> save(@RequestBody @Valid PatrimonyDTO payload) {

		Patrimony patrimony = GenericConverter.mapper(payload, Patrimony.class);
		patrimony = service.save(patrimony);

		return ResponseEntity.created(URI.create(String.format("/%s/%s", "patrimonies", patrimony.getId()))).build();
	}

	@ApiOperation(value = "Find patrimony by id", response = Patrimony.class)
	@GetMapping(value = "/{id}")
	public ResponseEntity<Patrimony> findById(@PathVariable("id") Long id) {

		Patrimony patrimony = service.find(id);

		if (patrimony == null)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(patrimony);
	}

	@ApiOperation(value = "Find all patrimonies", responseContainer = "List", response = Api.class)
	@GetMapping
	public ResponseEntity<?> findAll(@ModelAttribute PatrimonyDTO payload,
			@ModelAttribute PageableDTO pageableDTO) {

		Patrimony patrimony = GenericConverter.mapper(payload, Patrimony.class);
		if (!pageableDTO.isEmpty()) {

			final Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getLimit());
			Page<Patrimony> apiPage = service.list(patrimony, pageable);

			return ResponseEntity.ok(apiPage);
		} else {

			List<Patrimony> result = service.list();
			return ResponseEntity.ok(result);
		}

	}

	@ApiOperation(value = "Update a patrimony")
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody PatrimonyDTO payload) {

		Patrimony patrimony = GenericConverter.mapper(payload, Patrimony.class);
		patrimony = service.update(id, patrimony);

		return ResponseEntity.ok(patrimony);
	}
	
    @ApiOperation(value = "Delete a patrimony")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

         service.delete(id);
         
         return ResponseEntity.noContent().build();
    }
}
