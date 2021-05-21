package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	// able to use default BCryptPasswordEncoder.encode()
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		log.info("Username is set with: ", username);

		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}

	// create new user with username, password and confirmedPassword:
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		// initialize new user
		// use createUserRequest to get username and set new user by username:
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		log.info("Username is set with: ", createUserRequest.getUsername());

		// initialize new cart
		// save new cart into Cart Repository:
		Cart cart = new Cart();
		cartRepository.save(cart);
		// set new cart to user:
		user.setCart(cart);
		log.info("User cart set with: ", user.getCart());

		// validation of user input request
		// check if user entered password has length less than 8
		// or user entered password does not match with confirmedPassword
		// then, return bad request response:
		if(createUserRequest.getPassword().length() < 8 ||
				!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())){
			log.warn("Your password is either less than 8 or passwords mismatch");
			return ResponseEntity.badRequest().build();
		}

		// use BCrypt password hash algorithm to encode user entered password
		// then, set user's password with the encoded password:
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		log.info("Password is set with: ", createUserRequest.getPassword());

		userRepository.save(user);
		return ResponseEntity.ok(user);
	}
	
}
