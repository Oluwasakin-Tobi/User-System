package com.sikabox.test.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sikabox.user.entity.User;
import com.sikabox.user.repository.UserRepository;
import com.sikabox.user.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	public UserServiceImpl userService;

	@Mock
	public UserRepository userRepository;

	@Test
	public void testAddUser_Successful() {

		User user = getUser();

		when(userRepository.findByNationalId(any())).thenReturn(Optional.empty());

		ResponseEntity<?> response = userService.createUser(user);

		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void testAddUser_NationalIdExists() {

		User user = getUser();

		when(userRepository.findByNationalId(any())).thenReturn(Optional.of(user));

		ResponseEntity<?> response = userService.createUser(user);

		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	public void testGetUsers() {

		User user = getUser();

		ResponseEntity<?> response = userService.getUsers();

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());

	}

	@Test
	public void testUpdateUsers_UserNotFound() throws Exception {

		UUID userId = UUID.randomUUID();

		User user = getUser();

		when(userRepository.findById(any())).thenReturn(Optional.empty());

		ResponseEntity<?> response = userService.updateUser(user, userId);

		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	public void testUpdateUser_Successful() throws Exception {

		UUID userId = UUID.randomUUID();

		User user = getUser();

		User updatedUser = new User();
		updatedUser.setFirstName("Tyler");
		updatedUser.setNationality("Nigerian");
		updatedUser.setCountry("Nigeria");

		when(userRepository.findById(any())).thenReturn(Optional.of(user));

		ResponseEntity<?> response = userService.updateUser(updatedUser, userId);

		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	private User getUser() {
		User user = new User();
		user.setNationalId("9084H3D");
		user.setCountry("Netherlands");
		user.setFirstName("Firster");
		user.setNationality("Dutch");

		return user;
	}

}
