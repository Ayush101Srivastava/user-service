package com.micro.app.userservice.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.app.userservice.entity.User;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private JpaRepository<User, Long> userRepository;

	public UserController(JpaRepository<User, Long> userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<User> listAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping(path = "/{user_id}")
	@ResponseStatus(code = HttpStatus.OK)
	public User getUserById(@PathVariable(name = "user_id") Long id) {
		return userRepository.findById(id).get();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void addUser(@RequestBody @Validated User user) {
		userRepository.saveAndFlush(user);
	}

	@DeleteMapping(path = "/{user_id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteUserById(@PathVariable(name = "user_id") Long id) {
		userRepository.deleteById(id);
	}

	@PutMapping(path = "/{user_id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void updateUser(@PathVariable("user_id") Long id, @RequestBody @Validated User user) {
		User userToUpdate = userRepository.findById(id).get();
		userToUpdate.setUserName(user.getUserName());
		userToUpdate.setPassword(user.getPassword());
		userRepository.flush();
	}
}
