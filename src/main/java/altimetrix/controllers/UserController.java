package altimetrix.controllers;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import altemetrix.constants.RestConstants;
import altimetrix.entities.User;
import altimetrix.exceptions.UserNotFoundException;
import altimetrix.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@PostMapping(name = "/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		userService.saveUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getUserId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/users/{un}")
	public User getUser(@PathVariable("un") String username) {
		return userService.getUser(username);
	}

	@DeleteMapping("/users/{un}")
	public void deleteUser(@PathVariable("un") String username) {
		userService.deleteUser(username);
	}

	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {

		String jwtTocken = "";
		if (username == null || password == null) {
			throw new RuntimeException("user name or password is empty");
		}

		User user = userService.getUser(username);

		if (user == null) {
			throw new UserNotFoundException("User doesn't exist with the username " + username);
		}

		String userPassword = user.getPassword();
		if (!password.equals(userPassword)) {
			throw new RuntimeException("Invalid login. Please check user name or password");
		}

		jwtTocken = Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, RestConstants.SIGNING_KEY).compact();

		return jwtTocken;
	}

}
