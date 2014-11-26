package com.KNASK.todayinthecitymodel;

import java.io.Serializable;
import java.util.Date;

public class Search implements Serializable {
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
	
	public void setBeforeDate(Date beforeDate) {
		this.beforeDate = beforeDate;
	}

	public void setAfterDate(Date afterDate) {
		this.afterDate = afterDate;
	}

	public void setGenres(int[] genres) {
		this.genres = genres;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setWithinDistance(double withinDistance) {
		this.withinDistance = withinDistance;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	public Search() {super();}

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
	private static final long serialVersionUID = 1L;
	
	public static class SearchBuilder {
		public static SearchBuilder create(SearchType thisType) {
			return new SearchBuilder(thisType);
		}
		
		public SearchBuilder beforeDate(Date beforeDate) {
			if (this.searchType == SearchType.Location)
				return this;
			
			this.beforeDate = beforeDate;
			return this;
		}
		
		public SearchBuilder afterDate(Date afterDate) {
			if (this.searchType == SearchType.Location)
				return this;
			
			this.afterDate = afterDate;
			return this;
		}
		
		public SearchBuilder genres(int[] genres) {
			if (this.searchType == SearchType.Location)
				return this;
			
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
			if ((withinDistance > 100000) || (withinDistance < 0))
				this.withinDistance = 100000;
			
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
		private double withinDistance = 100000;
		private SearchType searchType;
	}
}
