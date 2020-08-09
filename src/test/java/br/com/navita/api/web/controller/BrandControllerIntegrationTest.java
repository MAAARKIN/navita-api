package br.com.navita.api.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.navita.api.ApiApplication;
import br.com.navita.api.domain.model.Brand;
import br.com.navita.api.web.payload.JwtDTO;
import br.com.navita.api.web.payload.UserDTO;

@SpringBootTest(classes = ApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BrandControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String token;

	@BeforeEach
	public void authorization() {

		UserDTO user = new UserDTO();
		user.setEmail("m.filhow@gmail.com");
		user.setName("Marcos Filho");
		user.setPassword("password");

		ResponseEntity<String> responseSignup = this.restTemplate
				.postForEntity("http://localhost:" + port + "/auth/signup", user, String.class);

		assertEquals(HttpStatus.OK.value(), responseSignup.getStatusCodeValue());

		JwtDTO auth = new JwtDTO();
		auth.setEmail("m.filhow@gmail.com");
		auth.setPassword("password");
		ResponseEntity<String> responseSignin = this.restTemplate
				.postForEntity("http://localhost:" + port + "/auth/signin", auth, String.class);

		assertEquals(HttpStatus.OK.value(), responseSignin.getStatusCodeValue());

		String body = responseSignin.getBody();
		try {
			JSONObject json = new JSONObject(body);
			this.token = json.getString("jwttoken");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(token);
	}

	@Test
	public void testAddBrand() {
		Brand brand = new Brand();
		brand.setName("Navita");

		HttpHeaders headers = new HttpHeaders();
		System.out.println(token);
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Brand> request = new HttpEntity<>(brand, headers);
		ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/brands",
				request, String.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
	}
}
