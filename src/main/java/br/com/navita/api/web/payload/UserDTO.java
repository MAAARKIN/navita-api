package br.com.navita.api.web.payload;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDTO implements Serializable {

	private static final long serialVersionUID = -2302687018895810410L;
	private String name;
	private String email;
	private String password;
}
