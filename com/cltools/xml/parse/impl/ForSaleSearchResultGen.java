package com.cltools.xml.parse.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ForSaleSearchResultGen implements SearchGenerator {

	private SearchAgent agent;
	private SearchResult resultObj;
	
	public static final int SUCCESS = 0;
	public static final int PARSE_ERROR = 1;
	public static final int SAX_ERROR = 2;
	public static final int IOERROR = 3;
	public static final int URLERROR = 4;
	
	public static final int MALFORMURLERROR = 5;
	public static final int URISYNTAXERROR = 6;
	public static final int ENCODEERROR = 7;
	

	private String forSaleString = "sss";
	
	


	/**
	 * default constructor will create search location for general for sale search
	 * 
	 * @param location
	 */
	public ForSaleSearchResultGen(String location) {

		// the 'for sale' generator will send in 'sss' by defaul
		agent = new SearchAgent(location, forSaleString);
	}


	/**
	 * Overloaded constructor to allow search URL to isolate to site section
	 * for example (ata = antiques , bia = bikes )
	 * 
	 * @param location
	 * @param siteSection
	 */
	public ForSaleSearchResultGen(String location, String siteSection) {

		agent = new SearchAgent(location, siteSection);
	}

	
	public ForSaleSearchResultGen(Proxy proxy, String location, String siteSection) {

		agent = new SearchAgent(proxy, location, siteSection);
	}
	

	public ForSaleSearchResultGen(Proxy proxy, String location) {

		agent = new SearchAgent(proxy, location, "sss");
	}
	
	
	
	/**
	 * Method to construct the Craigs List search URL
	 * 
	 * @param query the search term to use in the query string
	 */
	@Override
	public int setSearchTerm(String query) {
		
		try {
		agent.setSearchTerm(query);
		resultObj = new SearchResult();
	
		} catch(MalformedURLException malExc){
			System.out.println(malExc.getMessage());
			return MALFORMURLERROR;
		}catch(URISyntaxException uriExc){
			System.out.println(uriExc.getMessage());
			return URISYNTAXERROR;
		}catch(UnsupportedEncodingException encExc){
			System.out.println(encExc.getMessage());
			return ENCODEERROR;
		}
		
		return SUCCESS;
	}

	
	/**
	 *  method used to perform search operation 
	 *  
	 *  @return int value representing the success code or fault code if exception is thrown
	 */
	@Override
	public int doSearch() {

		// throws IOException, ParserConfigurationException, SAXException

		int parseResult;

		URLConnection connection;
		InputStream inURLStream;

		try {

			if (agent.isHasProxy()) {
				// if the agent has a proxy server set we create it here
				connection = agent.getSearchURL().openConnection(agent.getProxyServer());
			} else {
				connection = agent.getSearchURL().openConnection();
			}

		inURLStream = connection.getInputStream();
		
		//
		//printXMLString(inURLStream);
		
		// call private method to perform DOM parsing
		parseResult = doParseJob(inURLStream);
		//close the inputStream
		inURLStream.close();
		
		} catch (IOException ioExc) {
			System.out.println(ioExc.getMessage());
			return IOERROR;
		}

		return parseResult;
	}

	
	
	/**
	 * helper method to output the entire xml string to system out
	 * 
	 * @param inURLStream
	 * @throws IOException
	 */
	private void printXMLString(InputStream inURLStream) throws IOException {
      BufferedReader in = new BufferedReader(new InputStreamReader(inURLStream));
      String inputLine;
      while ((inputLine = in.readLine()) != null) 
          System.out.println(inputLine);
      in.close();
	}
	
	
	/**
	 * helper method to perform xml parsing from inputStream.
	 * Uses java xml parsing to execute.
	 * 
	 * @param inURLStream
	 * @return code containing the status of the parse job.
	 */
	private int doParseJob(InputStream inURLStream) {

		try {
			// set up document builder to parse xml
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			//attempt to fix encoding issues 
			Reader reader = new InputStreamReader(inURLStream,"UTF-8");			
			InputSource iSourceEncoded = new InputSource(reader);
			iSourceEncoded.setEncoding("UTF-8");
			
			Document doc = dBuilder.parse(iSourceEncoded);
			doc.getDocumentElement().normalize();

			// first get the values in the header for the results
			Element titleEl = (Element) doc.getElementsByTagName("title").item(
					0);
			if (titleEl != null) {
				
				resultObj.setTitle(titleEl.getTextContent());
			}

			Element linkEl = (Element) doc.getElementsByTagName("link").item(0);
			if (linkEl != null) {
				resultObj.setLinkURL(new URL(linkEl.getTextContent()));
			}

			Element rssLinkEl = (Element) doc.getElementsByTagName("dc:source").item(0);
			if (rssLinkEl != null) {
				resultObj.setRssURL(new URL(rssLinkEl.getTextContent()));
			}
			
			Element descriptionEL = (Element) doc.getElementsByTagName("description").item(0);
			if(descriptionEL != null){
				resultObj.setDescription(descriptionEL.getTextContent());
			}
			
			Element typeEL = (Element) doc.getElementsByTagName("dc:type").item(0);
			if(typeEL != null){
				resultObj.setType(typeEL.getTextContent());
			}
			
			Element pubEL = (Element) doc.getElementsByTagName("dc:publisher").item(0);
			if(pubEL != null){
				resultObj.setPublisher(pubEL.getTextContent());
			}
	
			Element createrEL = (Element) doc.getElementsByTagName("dc:creator").item(0);
			if(createrEL != null){
				resultObj.setCreator(createrEL.getTextContent());
			}

			Element langEL = (Element) doc.getElementsByTagName("dc:language").item(0);
			if(langEL != null){
				resultObj.setLang(langEL.getTextContent());
			}
			
			
			
			ArrayList<SearchResultItem> resultItems = new ArrayList<SearchResultItem>();

			// retrieve all item elements and iterate
			NodeList nList = doc.getElementsByTagName("item");

			for (int cur = 0; cur < nList.getLength(); cur++) {
				// current <item> node
				Node nNode = nList.item(cur);

				Element itemElement = (Element) nNode;
				// how to get title
				Node titleNode = itemElement.getElementsByTagName("title")
						.item(0);

				// need to replace hex values to show dollar sign 				
				String titleText = titleNode.getTextContent();
				titleText = titleText.replace("&#x0024;", "$");
				titleNode.setTextContent(titleText);			
				
				// get description
				Node descNode = itemElement.getElementsByTagName("description")
						.item(0);
				// get link
				Node linkNode = itemElement.getElementsByTagName("link")
						.item(0);
				// get data
				Node dateNode = itemElement.getElementsByTagName("dc:date")
						.item(0);
				
				// attempt to get the image node may be missing
				Node imageNode = itemElement.getElementsByTagName("enc:enclosure")
						.item(0);
				
				
				String imageUrl = "";
				String imageType = "";
				
				URL imageURL = null;
				
				// set the image properties if there is one
				if(imageNode != null) {
				NamedNodeMap imageNodeMap = imageNode.getAttributes();
				
				//this will get the image properties
				imageUrl = imageNodeMap.getNamedItem("resource").getTextContent();
				imageType = imageNodeMap.getNamedItem("type").getTextContent();
				
				imageURL = new URL(imageUrl);
				}
				
				
				SearchResultItem currentResultItem = new SearchResultItem(
						titleNode.getTextContent(), descNode.getTextContent(),
						new URL(linkNode.getTextContent()),
						dateNode.getTextContent(), imageURL , imageType);

				resultItems.add(currentResultItem);
			}

			resultObj.setResultItems(resultItems);

			// handle possible parsing errors
		} catch (ParserConfigurationException pcExc) {

			System.out.println(pcExc.getMessage());
			return PARSE_ERROR;

		} catch (SAXException saxExc) {
			//thrown id the dbBuilder can not parse the input stream
			System.out.println(saxExc.getMessage());
			saxExc.printStackTrace();
			return SAX_ERROR;

		} catch (IOException ioExc) {
			//this is thrown if the URL created for the result does not resolve
			System.out.println(ioExc.getMessage());
			return IOERROR;
		}

		// return sucess if we complete parse with no exceptions
		return SUCCESS;
	}

	
	@Override
	public SearchResult getSearchResults() {
		return resultObj;
	}

	
	@Override
	public int refreshSearchResults() {
		return doSearch();
	}
	
	

}
