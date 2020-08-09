package br.com.navita.api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		br.com.navita.api.domain.model.User userFinded = userService.findByEmail(email);
		if (userFinded == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		
		return new User(userFinded.getEmail(), userFinded.getPassword(), new ArrayList<>());
	}
}
