package com.indream.fundoo.noteservice.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "labels")
public class LabelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _id;
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

    public String get_id() {
	return _id;
    }

    public void set_id(String _id) {
	this._id = _id;
    }

    public String getLabelName() {
	return labelName;
    }

    public void setLabelName(String labelName) {
	this.labelName = labelName;
    }

}
