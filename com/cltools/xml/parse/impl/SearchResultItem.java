package com.cltools.xml.parse.impl;

import java.net.URL;
import java.util.Date;

public class SearchResultItem {

	
	private String title;
	private String description;
	private URL resultURL;
	private String dcDate;
	private URL imageURL;
	private String imageType;
	
	
	
	public SearchResultItem(String title, String description, URL link,
			String dcDate, URL imageURL, String imageType) {
		this.title = title;
		this.description = description;
		this.resultURL = link;
		this.dcDate = dcDate;
		this.imageURL = imageURL;
		this.imageType = imageType;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public URL getResultURL() {
		return resultURL;
	}



	public void setResultURL(URL resultURL) {
		this.resultURL = resultURL;
	}



	public String getDcDate() {
		return dcDate;
	}



	public void setDcDate(String dcDate) {
		this.dcDate = dcDate;
	}



	public URL getImageURL() {
		return imageURL;
	}



	public void setImageURL(URL imageURL) {
		this.imageURL = imageURL;
	}



	public String getImageType() {
		return imageType;
	}



	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	
	
	
}
