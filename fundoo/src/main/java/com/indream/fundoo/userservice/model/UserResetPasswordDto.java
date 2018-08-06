/**
 * 
 */
package com.indream.fundoo.userservice.model;

import java.io.Serializable;

/**
 * @author rootuser
 *
 */
public class UserResetPasswordDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    String email;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

}
