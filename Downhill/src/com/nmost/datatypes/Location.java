package com.nmost.datatypes;

public class Location {

	private String mName;
	private double longitude;
	private double latitutde;

	public Location(String name, double longi, double lati) {
		setName(name);
		setLong(longi);
		setLat(lati);
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public double getLong() {
		return longitude;
	}

	public void setLong(double mLength) {
		this.longitude = mLength;
	}

	public double getLat() {
		return latitutde;
	}

	public void setLat(double mDeltaY) {
		this.latitutde = mDeltaY;
	}

}
