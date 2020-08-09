package br.com.navita.api.web.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceIdDTO implements Serializable {

	private static final long serialVersionUID = -4886285666249604229L;

	private Long id;
}
