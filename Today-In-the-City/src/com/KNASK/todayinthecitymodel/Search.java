package com.KNASK.todayinthecitymodel;

import java.util.Date;

public class Search {
	public enum SearchType {Show, Location}
	
	public Date getBeforeDate() {
		return beforeDate;
	}

	public Date getAfterDate() {
		return afterDate;
	}

	public int[] getGenres() {
		return genres;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public double getWithinDistance() {
		return withinDistance;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	private Search(SearchType searchType, Date beforeDate, Date afterDate, int[] genres, double lat, double lon, double withinDistance) {
		this.searchType = searchType;
		this.beforeDate = beforeDate;
		this.afterDate = afterDate;
		this.genres = genres;
		this.lat = lat;
		this.lon = lon;
		this.withinDistance = withinDistance;
	}
	
	private Date beforeDate;
	private Date afterDate;
	private int[] genres;
	private double lat = 99;
	private double lon = 99;
	private double withinDistance;
	private SearchType searchType;
	
	public static class SearchBuilder {
		public SearchBuilder create(SearchType thisType) {
			return new SearchBuilder(thisType);
		}
		
		public SearchBuilder beforeDate(Date beforeDate) {
			this.beforeDate = beforeDate;
			
			return this;
		}
		
		public SearchBuilder afterDate(Date afterDate) {
			this.afterDate = afterDate;
			
			return this;
		}
		
		public SearchBuilder genres(int[] genres) {
			this.genres = genres;
			
			return this;
		}
		
		public SearchBuilder lat(double lat) {
			this.lat = lat;
			
			return this;
		}
		
		public SearchBuilder lon(double lon) {
			this.lon = lon;
			
			return this;
		}
		
		public SearchBuilder withinDistance(double withinDistance) {
			this.withinDistance = withinDistance;
			
			return this;
		}
		
		public Search build() {
			return new Search(searchType, beforeDate, afterDate, genres, lat, lon, withinDistance);
		}
		
		private SearchBuilder(SearchType thisType) {
			this.searchType = thisType;
		}
		
		private Date beforeDate;
		private Date afterDate;
		private int[] genres;
		private double lat = 99;
		private double lon = 99;
		private double withinDistance;
		private SearchType searchType;
	}
}
