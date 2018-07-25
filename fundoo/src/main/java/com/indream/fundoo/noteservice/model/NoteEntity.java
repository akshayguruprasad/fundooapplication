package com.indream.fundoo.noteservice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Document(collection = "notes")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "creadtedOn", "lastModified" })
public class NoteEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String _id;

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

    public String get_id() {
	return _id;
    }

    public void set_id(String _id) {
	this._id = _id;
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
	if (label == null) {
	    this.label = new ArrayList<String>();
	}
	return this.label;
    }

    public void setLabel(List<String> label) {
	this.label = label;
    }

}
