package com.sikabox.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
	
	private UUID id;
	private String firstName;
	private String lastName;
	private String country;
	private String nationality;
	private LocalDate dob;
	private String gender;
	private String nationalId;
	private LocalDateTime createdOn;

}
