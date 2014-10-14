package com.KNASK.todayinthecity;

import android.graphics.Bitmap;

//To store the event information
public class ShowEvent {
	
	private String 	showDate;
	private Genre 	genre;
	private String 	showTitle;
	private String 	showID;
	private String 	locationName;
	private String 	locationAddress;
	private Integer 	entranceFee;
	private Bitmap 	eventPoster;
	private String 	description;
	private String 	contactEmail;
	private String 	contactPhone;
	private String 	webSite;
	private String 	ageGroup;
	
	public ShowEvent() {

	}
	
	//////////////////////////////////////////////////////////////
	// Setter
	public void SetShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}
	
	public void SetShowDate(String showDate) {
		this.showDate = showDate;
	}
	
	public void SetLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public void SetLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
	
	
	
	//////////////////////////////////////////////////////////////
	// Getter

	public String GetShowTitle() {
		return showTitle;
	}
	
	public String GetShowDate() {
		return showDate;
	}

	public String GetLocationName() {
		return locationName;
	}

	public String GetLocationAddress() {
		return locationAddress;
	}

}
