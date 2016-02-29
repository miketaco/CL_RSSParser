package com.cltools.xml.parse.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

public class SearchAgent {

	
	private String searchTerm;
	private URL searchURL;
//	private String searchURLString = "https://newyork.craigslist.org/search/sss?format=rss&amp;query=";
	private String searchURLString;	
	
	private SearchResult searchResults;
	
	private boolean hasProxy = false;

	
	private String location;
	private String siteArea;
	
	Proxy proxyServer;
	
	
	/**
	 * Default constructor takes location and site area
	 * 
	 * @param loc
	 * @param area
	 */
	public SearchAgent(String loc, String area)  {
		location = loc;
		siteArea = area;
	}

	/**
	 * constructor to create search string with proxy
	 * 
	 * @param proxy
	 * @param loc
	 * @param area
	 */
	public SearchAgent(Proxy proxy, String loc, String area)  {
		this.hasProxy = true;
		proxyServer = proxy;
		location = loc;
		siteArea = area;
	}
	

	/**
	 * method to create the URL string used to request the search results.
	 * will take the search term used.
	 * 
	 * @param searchTerm
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 * @throws UnsupportedEncodingException
	 */
	public void setSearchTerm(String searchTerm) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {
		this.searchTerm = searchTerm;
		
		//use stringbuilder to create url string from parameters
		StringBuilder urlStrBld = new StringBuilder("https://");
		urlStrBld.append(location);
		urlStrBld.append(".craigslist.org/search/");
		urlStrBld.append(siteArea);
		urlStrBld.append("?format=rss&amp;query=");
		urlStrBld.append( URLEncoder.encode(searchTerm, "UTF-8"));
		
		
		//searchURLString = "https://" + location + ".craigslist.org/search/"  + siteArea + "?format=rss&amp;query=" + URLEncoder.encode(searchTerm, "UTF-8");
			
//		searchURLString += URLEncoder.encode(searchTerm, "UTF-8");
		
		searchURL = new URL(urlStrBld.toString());
		
	}


	public String getSearchTerm() {
		return searchTerm;
	}



	public URL getSearchURL() {
		return searchURL;
	}


	public void setSearchURL(URL searchURL) {
		this.searchURL = searchURL;
	}


	public String getSearchURLString() {
		return searchURLString;
	}


	public void setSearchURLString(String searchURLString) {
		this.searchURLString = searchURLString;
	}


	public SearchResult getSearchResults() {
		return searchResults;
	}


	public void setSearchResults(SearchResult searchResults) {
		this.searchResults = searchResults;
	}


	public boolean isHasProxy() {
		return hasProxy;
	}


	public void setHasProxy(boolean hasProxy) {
		this.hasProxy = hasProxy;
	}

	public Proxy getProxyServer() {
		return proxyServer;
	}

	public void setProxyServer(Proxy proxyServer) {
		this.proxyServer = proxyServer;
	}





}
