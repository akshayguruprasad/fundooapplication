package com.indream.fundoo.noteservice.model;

import java.io.Serializable;
import java.util.Date;

public class  Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private Date issuedAt;
	private String issuer;

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

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/* @purpose
	 *
	 *
	 * @author akshay
	 * @com.indream.fundoo.noteservice.model
	 * @since 03-Aug-2018
	 *
	 */
	@Override
	public String toString() {
		return "Token [name=" + name + ", id=" + id + ", issuedAt=" + issuedAt + ", issuer=" + issuer + "]";
	}

	
	
}
