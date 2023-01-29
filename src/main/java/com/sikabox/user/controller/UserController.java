package com.sikabox.user.controller;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sikabox.user.dto.UserDto;
import com.sikabox.user.entity.User;
import com.sikabox.user.service.impl.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final ModelMapper mapper;

	@GetMapping
	public ResponseEntity<?> getUsers() {
		return userService.getUsers();
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDto user) {
		return userService.createUser(mapper.map(user, User.class));

	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> updateUser(@RequestBody UserDto user, @PathVariable("id") UUID id) throws Exception {
		return userService.updateUser(mapper.map(user, User.class), id);
	}

	@GetMapping("/pagination")
	public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "3") int pageSize) {
		return userService.getAllUsersPaginated(offset, pageSize);
	}

	@GetMapping("/{nationalId}")
	public ResponseEntity<?> getUserByNationalId(@PathVariable("nationalId") String id) throws Exception {
		return userService.getUserByNationalId(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") UUID id) throws Exception {
		return userService.deleteUserById(id);
	}

}
