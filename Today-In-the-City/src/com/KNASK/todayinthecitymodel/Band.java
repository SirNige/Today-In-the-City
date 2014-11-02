package com.KNASK.todayinthecitymodel;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;

public class Band implements Serializable {

	private int bandID;
	private String bandName;
	private Genre genre;
	private String description;
	private Bitmap 	bandImage;
	
	public int getBandID() {
		return bandID;
	}
	
	public void setBandID(int bandID) {
		this.bandID = bandID;
	}
	
	public String getBandName() {
		return bandName;
	}
	
	public void setBandName(String bandName) {
		this.bandName = bandName;
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
	
	public Bitmap getBandImage() {
		return bandImage;
	}
	
	public void setBandImage(Bitmap bandImage) {
		this.bandImage = bandImage;
	}
	
	@Override
	public String toString() {
		return bandName;
	}
	

	
}
