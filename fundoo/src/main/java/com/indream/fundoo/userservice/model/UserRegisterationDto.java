package com.indream.fundoo.userservice.model;

import java.io.Serializable;

/**
 * @author Akshay
 *
 */
public class UserRegisterationDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String _id;
    private String email;
    private String password;

    public String get_id() {
	return _id;
    }

    public void set_id(String _id) {
	this._id = _id;
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
