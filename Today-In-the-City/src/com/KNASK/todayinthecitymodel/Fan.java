package com.KNASK.todayinthecitymodel;

import java.io.Serializable;
import java.util.List;


public class Fan  implements Serializable {
	public int getFanID() {
		return fanID;
	}

	public void setFanID(int fanID) {
		this.fanID = fanID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	public boolean equals(Object other) {
		if (this instanceof Fan)
			if (other instanceof Fan)
				return this.getFanID() == ((Fan)other).getFanID();
		
		return false;
	}

	private int fanID = -1;
	private String firstName;
	private String lastName;
	private String password;
	
	private List<Show> shows;
}
