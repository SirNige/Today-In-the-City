package com.KNASK.todayinthecitymodel;

import java.io.Serializable;

public class Location implements Serializable {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public boolean equals(Object other) {
		if (this instanceof Location)
			if (other instanceof Location)
				return this.getLocationID() == ((Location)other).getLocationID();
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	

	private int locationID = -1;
	private String name;
	private String address;
	private double lat;
	private double lon;
}
