package com.indream.fundoo.noteservice.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class LabelEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String labelName = "";
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LabelEntity() {
	}

	public LabelEntity(String labelName) {
		this.labelName = labelName;
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

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
