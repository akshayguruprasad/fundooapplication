/**
 * 
 */
package com.indream.fundoo.userservice.model;

import java.io.Serializable;

/**
 * @author rootuser
 *
 */
public class UserloginDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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

    private String email;
    private String password;
}
