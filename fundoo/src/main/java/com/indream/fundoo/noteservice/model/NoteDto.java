package com.indream.fundoo.noteservice.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public final class NoteDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String _id;
    private String title;
    private String contents;
    @ApiModelProperty(hidden = true)
    private Date creadtedOn;
    @ApiModelProperty(hidden = true)
    private Date lastModified;
    @ApiModelProperty(hidden = true)
    private String userId;
    @ApiModelProperty(hidden = true)

    private List<String> collaborators;

    private List<LabelEntity> label;
    @ApiModelProperty(hidden = true)

    private boolean completed;
    private boolean archived;
    private boolean pinned;
    private boolean trashed;
    @ApiModelProperty(hidden = true)
    private Date reminderDate;

    private String reminder;

    public String getReminder() {
	return reminder;
    }

    public void setReminder(String reminder) {
	this.reminder = reminder;
    }

    public Date getReminderDate() {

	try {
	    this.reminderDate = sdf.parse(this.reminder);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
	this.reminderDate = reminderDate;
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

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

    public List<String> getCollaborators() {
	return collaborators;
    }

    public void setCollaborators(List<String> collaborators) {
	this.collaborators = collaborators;
    }

    public List<LabelEntity> getLabel() {
	return label;
    }

    public void setLabel(List<LabelEntity> label) {
	this.label = label;
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

    @Override
    public String toString() {
	return "NoteDto [_id=" + _id + ", title=" + title + ", contents=" + contents + ", creadtedOn=" + creadtedOn
		+ ", lastModified=" + lastModified + ", userId=" + userId + ", collaborators=" + collaborators
		+ ", label=" + label + ", completed=" + completed + ", archived=" + archived + ", pinned=" + pinned
		+ ", trashed=" + trashed + ", reminderDate=" + reminderDate + ", reminder=" + reminder + "]";
    }

}
