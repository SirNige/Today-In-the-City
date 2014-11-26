package com.KNASK.todayinthecitymodel;

import java.io.Serializable;
import java.util.List;


public class Band implements Serializable {
	public Band(){}
	
	public int getBandID() {
		return bandID;
	}

	public void setBandID(int bandID) {
		this.bandID = bandID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}
	
	public boolean equals(Object other) {
		if (this instanceof Band)
			if (other instanceof Band)
				return this.getBandID() == ((Band)other).getBandID();
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	

	private int bandID = -1;
	private String name;
	private int genre;
	private String description;
	
	private List<Show> shows;
}
