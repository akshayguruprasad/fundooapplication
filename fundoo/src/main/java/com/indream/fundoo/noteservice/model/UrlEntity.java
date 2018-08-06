/**
 * 
 */
package com.indream.fundoo.noteservice.model;

import java.io.Serializable;

/**
 * @author bridgeit
 *
 */
public class UrlEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String title;
	private String detail;
	private String image;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/*
	 * @purpose
	 *
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.noteservice.model
	 * 
	 * @since 04-Aug-2018
	 *
	 */
	@Override
	public String toString() {
		return "UrlEntity [url=" + url + ", title=" + title + ", detail=" + detail + ", image=" + image + "]";
	}

}
