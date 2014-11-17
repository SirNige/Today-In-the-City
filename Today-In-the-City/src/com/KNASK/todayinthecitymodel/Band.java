package com.KNASK.todayinthecitymodel;

import java.io.Serializable;
import java.util.List;
import android.media.Image;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Band implements Serializable {
	public Band(){}
	
	public int getBandID() {
		return bandID;
	}

	public void setBandID(int bandID) {
		this.bandID = bandID;
	}

	public String getName() {
		return bandName;
	}

	public void setName(String name) {
		this.bandName = name;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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
	
	@Override
	public String toString() {
		return bandName;
	}

	private int bandID = -1;
	private String bandName;
	private Genre genre;
	private String description;
	private Image image;
	
	private List<Show> shows;
}

