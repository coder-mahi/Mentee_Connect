package com.mahesh.mentee_connect.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mentors")
public class Mentor {

    @Id
    private String id;
    private String name;
    private String email;
    private String expertise;
    
    public String getId() {
    	return id;
    }
    public void setId(String id) {
    	this.id = id;
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
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
    
}
