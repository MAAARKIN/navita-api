package br.com.navita.api.web.payload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PatrimonyDTO {

	@NotNull
    @Size(max = 180, min = 4)
	private String name;
	
	@Size(max = 180)
	private String description;
	
	@NotNull
    private ReferenceIdDTO brand;
}
