/**
 * 
 */
package com.indream.fundoo.userservice.model;

import java.io.Serializable;

/**
 * @author rootuser
 *
 */
public class UserUpdatePasswordDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private String confirmPassword;

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

    public String getConfirmPassword() {
	return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
    }

}
