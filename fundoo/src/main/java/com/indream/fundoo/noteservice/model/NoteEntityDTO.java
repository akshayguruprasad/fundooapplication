package com.indream.fundoo.noteservice.model;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;

public final  class NoteEntityDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObjectId id;
	private String title;
	private String contents;
	private Date creadtedOn;
	private Date lastModified;
	private String userId;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getCreadtedOn() {
		return creadtedOn;
	}
	public void setCreadtedOn(Date creadtedOn) {
		this.creadtedOn = creadtedOn;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
