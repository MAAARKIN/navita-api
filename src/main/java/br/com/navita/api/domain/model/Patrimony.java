package br.com.navita.api.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Table(name = "PATRIMONIES")
@Entity
public class Patrimony {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "REGISTER_NUMBER")
	@Setter(AccessLevel.NONE)
	private String registerNumber;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BRAND_ID")
	private Brand brand;
	
	@PrePersist
    private void callPrePersists() {
         this.registerNumber = UUID.randomUUID().toString();
    }

}
