package com.indream.fundoo.noteservice.model;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Document(collection = "user_notes")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "creadtedOn", "lastModified" })
public class NoteEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	private String title;
	private String contents;
	@ApiModelProperty(hidden = true)
	private Date creadtedOn;
	@ApiModelProperty(hidden = true)
	private Date lastModified;
	@ApiModelProperty(hidden = true)
	private String userId;

	public NoteEntity(String title, String contents, Date creadtedOn, Date lastModified, String userId) {
		super();
		this.title = title;
		this.contents = contents;
		this.creadtedOn = creadtedOn;
		this.lastModified = lastModified;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public NoteEntity() {
	}

	public NoteEntity(String title, String contents, Date creadtedOn, Date lastModified) {
		this.title = title;
		this.contents = contents;
		this.creadtedOn = creadtedOn;
		this.lastModified = lastModified;
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

}
