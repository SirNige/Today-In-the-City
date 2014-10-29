package com.KNASK.todayinthecitymodel;

public class Location {
	private int locatonID;
	private String locationName;
	private String locationAddress;
	
	
	public int getLocatonID() {
		return locatonID;
	}
	public void setLocatonID(int locatonID) {
		this.locatonID = locatonID;
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
	@Override
	public String toString() {
		return this.locationName;
	}
	
	
}
