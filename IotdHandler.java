package com.example.com.rehanzahoor.javarssfeeds;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import android.text.Html;
import android.util.Log;

/**
 * class to read the given RSS feed. RSS feed is given by setURL or 
 * constructor. The parsed RSS items are read by calling getRssItems().
 * Have to call processFeed() before getting Rss items.
 * @author RehanZahoor
 *
 */
public class IotdHandler extends DefaultHandler {
	
	private String url="";
	private boolean inTitle = false;
	private boolean inDescription = false;
	private boolean inItem = false;
	private boolean inLink = false;
	private boolean inPubDate = false;
	private StringBuffer title = new StringBuffer();
	private StringBuffer description = new StringBuffer();
	private StringBuffer link = new StringBuffer();
	private StringBuffer pubDate = new StringBuffer();
	XMLReader reader;
	ArrayList<RssItem> rssItems = new ArrayList<>();	
	StringBuffer buf_description = new StringBuffer();
	boolean notFirstTime = false;
	
	public IotdHandler(String url){
		super();
		this.url = url;
	}
	
	/**
	 * The method processes the given RSS feed and user class can
	 * get the parsed RSS items by calling getRssItems()
	 * @throws IOException if unable to read the given url 
	 */
	public void processFeed() throws IOException{
		try {
			Log.i("Inside ","ProcessFeed Method");
			SAXParserFactory factory =
			SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			reader = parser.getXMLReader();
			reader.setContentHandler(this);
			InputStream inputStream = new URL(url).openStream();
			reader.parse(new InputSource(inputStream));			
		} catch (IOException e) {
			Log.e("IOException", e.toString());
			throw e;
		} catch(SAXException saxe){
			Log.e("SAXException", saxe.toString());
		} catch(ParserConfigurationException e){
			Log.e("ParserConfigurationException", e.toString());
		}
	}
	
	/**
	 * DefaultHandler Method required to be implemented for processing
	 * on any tags
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
			
			if (localName.startsWith("item")) { 
				inItem = true;
				rssItems.add(new RssItem());
				}else if (inItem) {				
					if (localName.equals("title")) { 
						inTitle = true; 
					}else { 
						inTitle = false; 
					}
					if (localName.equals("description")) {
						inDescription = true;						
					}else { 
						inDescription = false;
					}
					if (localName.equals("link")) {
						inLink = true; 
					}else { 
						inLink = false; 
					}
					if (localName.equals("pubDate")) {
						inPubDate = true; 
					}else { 
						inPubDate = false; 
					}	
				}
			
	}
	/**
	 * DefaultHandler Method required to be implemented for processing
	 * on any tags
	 */
	public void characters(char ch[], int start, int length) {
		String chars = new String(ch).substring(start, start + length);
		if (inTitle) { 
			String buf = new String(chars);
			title.append(Html.fromHtml(buf));			
		}
		if (inDescription) {
			String buf = new String(chars);
			description.append(Html.fromHtml(buf));			
		}
		if (inLink) {
			String buf = new String(chars);
			link.append(Html.fromHtml(buf));			
		}
		if (inPubDate) {
			String buf = new String(chars);
			pubDate.append(Html.fromHtml(buf));			
		}
	}
	
	/**
	 * DefaultHandler Method required to be implemented for processing
	 * on end of document
	 */
	public void endDocument (){
		
	}
	
	/**
	 * DefaultHandler Method required to be implemented for processing
	 * on end of element
	 */
	public void endElement (String uri, String localName, String qName){
		
		if(localName.equals("title") && (title.length()>1)){
			rssItems.get(rssItems.size()-1).setTitle(title.toString());
			Log.i("Titles", title.toString());
			title = new StringBuffer();  
		}
		if(localName.equals("description") && (description.length()>1)){
			rssItems.get(rssItems.size()-1).setDescription(description.toString());
			description = new StringBuffer();
		}
		if(localName.equals("link") && (link.length()>1)){
			rssItems.get(rssItems.size()-1).setLink(link.toString());
			Log.i("Links", link.toString() + link.length());
			link = new StringBuffer();
		}
		if(localName.equals("pubDate") && (pubDate.length()>1)){
			rssItems.get(rssItems.size()-1).setpubDate(pubDate.toString());
			Log.i("pubDate", pubDate.toString() + pubDate.length());
			link = new StringBuffer();
		}		
	}
	
	/**
	 * Method to get the parsed RssItems in the form of an ArrayList of
	 * RssItems. ArrayList returned is arranged in order in which Rss Items
	 * appear in the RSS page 
	 * @return
	 */
	public ArrayList<RssItem> getRssItems(){
		return rssItems;		
	}
	
	/**
	 * Method to set URL of the RSS page to be parsed
	 * @param url
	 */
	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	 * Method to get the URL of the RSS page that is set to be parsed by
	 * this class
	 * @return
	 */
	public String getUrl(){
		return url;
	}
}