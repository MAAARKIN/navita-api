package br.com.navita.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navita.api.domain.model.Patrimony;

public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {

}
