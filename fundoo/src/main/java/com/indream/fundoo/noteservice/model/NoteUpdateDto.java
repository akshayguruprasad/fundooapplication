/**
 * 
 */
package com.indream.fundoo.noteservice.model;

import java.io.Serializable;

/**
 * @author rootuser
 *
 */
public class NoteUpdateDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String title;
    private String content;
    private String userId;

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }
}
