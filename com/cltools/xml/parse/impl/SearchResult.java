package com.cltools.xml.parse.impl;

import java.net.URL;
import java.util.ArrayList;

public class SearchResult {

	
	private ArrayList<SearchResultItem> resultItems;
	private String title;
	private URL linkURL;
	private URL rssURL;
	private String lang;
	private String description;
	private String publisher;
	private String creator;
	private String type;
	
	
	
	public SearchResult() {
		super();
	}



	public ArrayList<SearchResultItem> getResultItems() {
		return resultItems;
	}



	public void setResultItems(ArrayList<SearchResultItem> resultItems) {
		this.resultItems = resultItems;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public URL getLinkURL() {
		return linkURL;
	}



	public void setLinkURL(URL urlURL) {
		this.linkURL = urlURL;
	}



	public URL getRssURL() {
		return rssURL;
	}



	public void setRssURL(URL rssURL) {
		this.rssURL = rssURL;
	}



	public String getLang() {
		return lang;
	}



	public void setLang(String lang) {
		this.lang = lang;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getPublisher() {
		return publisher;
	}



	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}



	public String getCreator() {
		return creator;
	}



	public void setCreator(String creator) {
		this.creator = creator;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
}

