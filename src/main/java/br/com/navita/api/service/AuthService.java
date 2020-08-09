package br.com.navita.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.navita.api.domain.model.User;

@Service
public class AuthService {

	@Autowired
	private UserService userService;

	public void signUp(User user) {
		userService.save(user);
		//notify by email ?
	}
	
	public void signIn(User user) {
		
	}

}
