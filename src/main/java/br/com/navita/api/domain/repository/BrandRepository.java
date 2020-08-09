package br.com.navita.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navita.api.domain.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
