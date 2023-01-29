package com.sikabox.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "sikabox_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String firstName;
	private String lastName;
	private String country;
	private String nationality;
	private LocalDate dob;
	private String gender;
	private String nationalId;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;
	@UpdateTimestamp
	@Column(insertable = false, updatable = true)
	private LocalDateTime updatedOn;

}
