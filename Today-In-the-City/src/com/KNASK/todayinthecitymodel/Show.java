package com.KNASK.todayinthecitymodel;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show implements Serializable {
	public Show () {
		genreMap = new HashMap<Integer, String>();
		genreMap.put(0, "Alternative/Indie");
		genreMap.put(1, "Blues");
		genreMap.put(2, "Christian/Gospel");
		genreMap.put(3, "Classical");
		genreMap.put(4, "Country");
		genreMap.put(5, "Dance/Eletronic");
		genreMap.put(6, "Folk");
		genreMap.put(7, "Hip-Hop/Rap");
		genreMap.put(8, "Jazz");
		genreMap.put(9, "Metal");
		genreMap.put(10, "New Age");
		genreMap.put(11, "Pop");
		genreMap.put(12, "R&B/Soul");
		genreMap.put(13, "Reggae");
		genreMap.put(14, "Rock");
		genreMap.put(15, "Seasonal");
		genreMap.put(16, "Vocal/Easy Listening");
		genreMap.put(17, "World");
	}
	
	public int getShowID() {
		return showID;
	}

	public void setShowID(int showID) {
		this.showID = showID;
	}

	public int getFanID() {
		return fanID;
	}

	public void setFanID(int fanID) {
		this.fanID = fanID;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public int getGenre() {
		return genre;
	}

	public void setGenre(int genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public List<Band> getBands() {
		return bands;
	}

	public void setBands(List<Band> bands) {
		this.bands = bands;
	}

	public boolean equals(Object other) {
		if  (this instanceof Show)
			if (other instanceof Show)
				return this.getShowID() == ((Show)other).getFanID();
		
		return false;
	}
	
	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Override
	public String toString() {
		return name;
	}

	private int showID = -1;
	private int fanID;
	private int locationID;
	private String name;
	private Date date;
	private String cost;
	private int genre;
	private String description;
	private String 	contactEmail;
	private String 	contactPhone;
	private String 	webSite;
	

	private Location location;
	private List<Band> bands;
	private static Map<Integer, String> genreMap;
}