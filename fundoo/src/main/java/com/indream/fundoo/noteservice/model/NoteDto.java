package com.indream.fundoo.noteservice.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public final class NoteDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;
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
    private List<String> label;
    @ApiModelProperty(hidden = true)
    private boolean completed;
    private boolean archived;
    private boolean pinned;
    private boolean trashed;
    @ApiModelProperty(hidden = true)
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    
    private List<UrlEntity> entities;
    
    /**
	 * @return the label
	 */
	public List<String> getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(List<String> label) {
		this.label = label;
	}

	/**
	 * @return the entities
	 */
	public List<UrlEntity> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<UrlEntity> entities) {
		this.entities = entities;
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

    public List<String> getCollaborators() {
	return collaborators;
    }

    public void setCollaborators(List<String> collaborators) {
	this.collaborators = collaborators;
    }


    public String getTitle() {
	return title;
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
