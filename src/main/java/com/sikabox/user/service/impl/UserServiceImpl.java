package com.sikabox.user.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sikabox.user.constants.UserConstants;
import com.sikabox.user.dto.UserDto;
import com.sikabox.user.entity.User;
import com.sikabox.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final ModelMapper mapper;

	@Value("${validId}")
	boolean isValid = true;

	@Override
	public ResponseEntity<?> createUser(User userRequest) {

		boolean nationalIdExist = userRepository.findByNationalId(userRequest.getNationalId()).isPresent();

		if (nationalIdExist) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format(UserConstants.ID_EXISTS, userRequest.getNationalId()));
		}

		boolean validated = validateNationalId(userRequest);

		if (!validated) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format(UserConstants.INVALID_ID, userRequest.getNationalId()));
		}

		try {
			userRepository.save(userRequest);
		} catch (Exception e) {
			log.debug("Database issue: " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserConstants.USER_NOT_CREATED);
		}
		return ResponseEntity.status(HttpStatus.OK).body(userRequest);

	}

	@Override
	public ResponseEntity<?> getUsers() {

		List<User> users = userRepository.findAll(Sort.by("createdOn").descending());
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@Override
	public ResponseEntity<?> updateUser(User user, UUID id) throws Exception {
		User userDB = userRepository.findById(id).orElse(null);

		if (Objects.isNull(userDB)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(UserConstants.USER_NOT_FOUND, id));
		}

		if (nonNull(user.getCountry())) {
			userDB.setCountry(user.getCountry());
		}
		if (nonNull(user.getNationality())) {
			userDB.setNationality(user.getNationality());
		}
		if (nonNull(user.getDob())) {
			userDB.setDob(user.getDob());
		}
		if (nonNull(user.getFirstName())) {
			userDB.setFirstName(user.getFirstName());
		}
		if (nonNull(user.getLastName())) {
			userDB.setLastName(user.getLastName());
		}
		if (nonNull(user.getGender())) {
			userDB.setGender(user.getGender());
		}
		if (nonNull(user.getNationalId())) {
			boolean validated = validateNationalId(user);

			if (!validated) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(String.format(UserConstants.INVALID_ID, user.getNationalId()));
			}
		}

		try {
			userRepository.save(userDB);
		} catch (Exception e) {
			log.debug("Database issue: " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserConstants.USER_NOT_UPDATED);
		}
		return ResponseEntity.status(HttpStatus.OK).body(userDB);
	}

	@Override
	public ResponseEntity<?> getAllUsersPaginated(int offset, int pageSize) {

		Pageable pageable = PageRequest.of(offset, pageSize, Sort.by("createdOn").descending());

		Page<User> users = userRepository.findAll(pageable);

		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@Override
	public ResponseEntity<?> getUserByNationalId(String id) throws Exception {

		User users = userRepository.findByNationalId(id).orElse(null);

		if (isNull(users)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(UserConstants.ID_NOT_FOUND, id));
		}
		return ResponseEntity.status(HttpStatus.OK).body(mapper.map(users, UserDto.class));
	}

	@Override
	public ResponseEntity<?> deleteUserById(UUID id) {
		User users = userRepository.findById(id).orElse(null);

		if (isNull(users)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(UserConstants.USER_NOT_FOUND, id));
		}

		try {
			userRepository.delete(users);
		} catch (Exception e) {
			log.debug("Database issue: " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format(UserConstants.USER_NOT_DELETED, id));
		}

		return ResponseEntity.status(HttpStatus.OK).body(String.format(UserConstants.USER_DELETED, id));
	}

	private boolean validateNationalId(User userDetails) {

		// Make a call to a third party service that validates ID, check if the
		// details( i.e First and Last name) matches and returns true when validated
		// I make an assumption that the ID is valid
		return isValid;
	}

}
