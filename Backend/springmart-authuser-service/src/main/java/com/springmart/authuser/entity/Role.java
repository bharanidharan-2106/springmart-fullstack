package com.springmart.authuser.entity; 

import jakarta.persistence.*; 
import lombok.*; 

@Entity 
@Data 
public class Role {
	@Id @GeneratedValue 
	Long id; 
	String roleName; 
	String description;
}