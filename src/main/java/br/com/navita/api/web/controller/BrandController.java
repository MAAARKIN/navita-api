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
import br.com.navita.api.service.BrandService;
import br.com.navita.api.util.GenericConverter;
import br.com.navita.api.web.payload.BrandDTO;
import br.com.navita.api.web.payload.PageableDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/brands", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = "/brands")
public class BrandController {

	@Autowired
	private BrandService service;
	
	@ApiOperation(value = "Save a new brand")
    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid BrandDTO payload) {

		Brand brand = GenericConverter.mapper(payload, Brand.class);
		brand = service.save(brand);

        return ResponseEntity.created(URI.create(String.format("/%s/%s", "brands", brand.getId()))).build();
    }
	
	@ApiOperation(value = "Find brand by id", response = Brand.class)
	@GetMapping(value = "/{id}")
	public ResponseEntity<Brand> findById(@PathVariable("id") Long id) {

		Brand brand = service.find(id);

		if (brand == null)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(brand);
	}
	
    @ApiOperation(value = "Find all brands", responseContainer = "List", response = Api.class)
    @GetMapping
    public ResponseEntity<?> findAll(@ModelAttribute BrandDTO payload, @ModelAttribute PageableDTO pageableDTO) {

    	Brand brand = GenericConverter.mapper(payload, Brand.class);
        if (!pageableDTO.isEmpty()) {

            final Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getLimit());
            Page<Brand> apiPage = service.list(brand, pageable);

            return ResponseEntity.ok(apiPage);
        } else {

            List<Brand> result = service.list();
            return ResponseEntity.ok(result);
        }

    }
    
    @ApiOperation(value = "Update a brand")
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody BrandDTO payload) {

		Brand brand = GenericConverter.mapper(payload, Brand.class);
		brand = service.update(id, brand);

		return ResponseEntity.ok(brand);
	}
	
    @ApiOperation(value = "Delete a brand")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

         service.delete(id);
         
         return ResponseEntity.noContent().build();
    }
}
