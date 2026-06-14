package com.springmart.authuser.entity; 

import jakarta.persistence.*; 
import lombok.*; 

@Entity 
@Data 
public class UserAddress { 
	@Id 
	@GeneratedValue 
	Long id; 
	String city; 
	String state; 
	String country;
}