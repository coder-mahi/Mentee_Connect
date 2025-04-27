package com.mahesh.mentee_connect.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "admin")
@Data
public class Admin {
	@Id
    private ObjectId id;
	private String name;
	private String email;
	private String password;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id  = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
