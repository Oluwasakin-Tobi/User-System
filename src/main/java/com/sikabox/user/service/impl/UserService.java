package com.sikabox.user.service.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.sikabox.user.entity.User;

public interface UserService {

	ResponseEntity<?> createUser(User user);

	ResponseEntity<?> getUsers();

	ResponseEntity<?> updateUser(User user, UUID id) throws Exception;

	ResponseEntity<?> getAllUsersPaginated(int offset, int pageSize);

	ResponseEntity<?> getUserByNationalId(String id) throws Exception;

	ResponseEntity<?> deleteUserById(UUID id);

}
