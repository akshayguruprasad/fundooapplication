package com.indream.fundoo.noteservice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = { "creadtedOn", "lastModified" })
public class NoteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	@NotBlank
	private String title;
	private String contents;
	@ApiModelProperty(hidden = true)
	private Date creadtedOn;
	@ApiModelProperty(hidden = true)
	private Date lastModified;
	@ApiModelProperty(hidden = true)
	private String userId;
	private List<String> collaborators;
	private List<String> label;
	private boolean completed;
	private boolean archived;
	private boolean pinned;
	private boolean trashed;
	private Date reminderDate;
	private List<UrlEntity> entities;

	/**
	 * @return the entities
	 */
	public List<UrlEntity> getEntities() {
		if (entities == null) {
			this.entities = new ArrayList<>();

		}

		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<UrlEntity> entities) {
		this.entities = entities;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	public NoteEntity() {
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public boolean isTrashed() {
		return trashed;
	}

	public void setTrashed(boolean trashed) {
		this.trashed = trashed;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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

	public List<String> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<String> collaborators) {
		this.collaborators = collaborators;
	}

	public List<String> getLabel() {
		return label;
	}

	public void setLabel(List<String> label) {
		this.label = label;
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.model
	 * 
	 * @since 02-Aug-2018
	 *
	 */
	@Override
	public String toString() {
		return "NoteEntity [id=" + id + ", title=" + title + ", contents=" + contents + ", creadtedOn=" + creadtedOn
				+ ", lastModified=" + lastModified + ", userId=" + userId + ", collaborators=" + collaborators
				+ ", label=" + label + ", completed=" + completed + ", archived=" + archived + ", pinned=" + pinned
				+ ", trashed=" + trashed + ", reminderDate=" + reminderDate + "]";
	}

}
