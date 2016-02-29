package com.cltools.xml.parse.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * 
 * @author mtacopino
 *
 */
public interface SearchGenerator {

	public int setSearchTerm(String term);

	public int doSearch();

	public SearchResult getSearchResults();

	public int refreshSearchResults();

}
