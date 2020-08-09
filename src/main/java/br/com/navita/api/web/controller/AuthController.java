package br.com.navita.api.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.navita.api.auth.JwtTokenUtil;
import br.com.navita.api.domain.model.User;
import br.com.navita.api.service.AuthService;
import br.com.navita.api.util.GenericConverter;
import br.com.navita.api.web.payload.JwtDTO;
import br.com.navita.api.web.payload.JwtResponse;
import br.com.navita.api.web.payload.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthService service;

	@ApiOperation(value = "Sign up")
	@PostMapping(value = "/signup")
	public ResponseEntity<?> signup(@RequestBody @Valid UserDTO payload) {

		User user = GenericConverter.mapper(payload, User.class);
		service.signUp(user);

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Sign in")
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtDTO authenticationRequest) throws Exception {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		service.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
}
