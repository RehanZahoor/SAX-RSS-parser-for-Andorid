package com.example.com.rehanzahoor.javarssfeeds;

/**
 * RssItem class to store the items in ArrayList<RssItem>
 * @author RehanZahoor
 *
 */
public class RssItem {
	
	private String title;
	private String description;
	private String link;
	private String pubDate;
	/**
	 * default constructor
	 */
	public RssItem(){
		
	}
	/**
	 * set All given RSS item values in constructor
	 * 
	 * @param title RSS item element
	 * @param description RSS item element
	 * @param link RSS item element
	 * @param pubDate RSS item element
	 */
	public RssItem(String title, String description, String link,
			String pubDate){
		this.title = title;
		this.description = description;
		this.link = link;
		this.pubDate = pubDate;
	}
	
	public String getTitle(){
		return title;		
	}
	public void setTitle(String title){
		this.title= title;
	}
	
	public String getDescription(){
		return description;		
	}
	public void setDescription(String description){
		this.description= description;
	}
	
	public String getLink(){
		return link;		
	}
	public void setLink(String link){
		this.link= link;
	}
	
	public String getpubDate(){
		return pubDate;		
	}
	public void setpubDate(String pubDate){
		this.pubDate= pubDate;
	}
}
