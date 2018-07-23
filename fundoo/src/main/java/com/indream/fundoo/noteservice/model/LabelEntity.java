package com.indream.fundoo.noteservice.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="labels")
public class LabelEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private ObjectId id;

	private String labelName;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
