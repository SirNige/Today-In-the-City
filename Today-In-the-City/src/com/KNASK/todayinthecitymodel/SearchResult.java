package com.KNASK.todayinthecitymodel;

import java.io.Serializable;

public class SearchResult implements Serializable {
	public SearchResult(){}
	
	public SearchResult(Search search) {
		this.search = search;
	}
	
	public Search getSearch() {
		return search;
	}
	
	public void setSearch(Search search) {
		this.search = search;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	private Search search;
	private Show show;
	private Location location;
	private double distance;
	private static final long serialVersionUID = 1L;
}
