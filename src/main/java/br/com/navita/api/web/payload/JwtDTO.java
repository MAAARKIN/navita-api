package br.com.navita.api.web.payload;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtDTO implements Serializable {

	private static final long serialVersionUID = 1238511259417111504L;
	private String email;
	private String password;

}
