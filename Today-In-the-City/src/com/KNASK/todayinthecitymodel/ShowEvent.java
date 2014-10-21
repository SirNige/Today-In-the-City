package com.KNASK.todayinthecitymodel;

import java.io.Serializable;

import android.graphics.Bitmap;


//To store the event information
//It will be stored detail information of the show event from database.
public class ShowEvent implements Serializable  {
	
	private String 	showDate;
	private Genre 	genre;
	private String 	showTitle;
	private String 	showID;
	private String 	locationName;
	private String 	locationAddress;
	private Integer entranceFee;
	private Bitmap 	eventPoster;
	private String 	description;
	private String 	contactEmail;
	private String 	contactPhone;
	private String 	webSite;
	private String 	ageGroup;
	
	public ShowEvent() {

	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	public String getShowID() {
		return showID;
	}

	public void setShowID(String showID) {
		this.showID = showID;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public Integer getEntranceFee() {
		return entranceFee;
	}

	public void setEntranceFee(Integer entranceFee) {
		this.entranceFee = entranceFee;
	}

	public Bitmap getEventPoster() {
		return eventPoster;
	}

	public void setEventPoster(Bitmap eventPoster) {
		this.eventPoster = eventPoster;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	
}
