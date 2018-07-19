package com.indream.fundoo.userservice.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_details")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;


	private ObjectId id;
	private String email;
	private String userName;
	private String mobile;
	private String password;
	private boolean isActive;

	public UserEntity() {}

	public UserEntity(String email, String userName, String mobile, String password, boolean isActive) {
		super();
		this.email = email;
		this.userName = userName;
		this.mobile = mobile;
		this.password = password;
		this.isActive = isActive;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	

}
