package com.dm.location;

import java.io.Serializable;
import java.util.Date;

import android.location.Location;
import android.location.LocationManager;
import android.os.Parcelable;

public class DMLocation implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2777841249349166026L;
	double latitude;
	double longitude;

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public long getTime() {
		return time;
	}

	double altitude;
	long time;
	String provider;
	float accuracy;

	public String getProvider() {
		return provider;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public float getSpeed() {
		return speed;
	}

	float speed;

	private DMLocation() {
	}
	
	public DMLocation(double lat,double lng){
		Location location = new Location(LocationManager.GPS_PROVIDER);
		//error: override the useful information.
		init(location);
		location.setLatitude(lat);
		location.setLongitude(lng);
	}

	public DMLocation(Location location) {
		init(location);
	}
	private void init(Location location){
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
		time = location.getTime();
		provider = location.getProvider();
		accuracy = location.getAccuracy();
		speed = location.getSpeed();
	}

	public Location getLocation() {
		Location location = new Location(getProvider());
		location.setAccuracy(getAccuracy());
		location.setAltitude(getAltitude());
		location.setLongitude(getLongitude());
		location.setLatitude(getLatitude());
		location.setSpeed(getSpeed());
		location.setTime(getTime());
		return location;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("time:");
		Date date = new Date(time);
		sb.append(date.toLocaleString());
		sb.append("latitude:");
		sb.append(latitude);
		sb.append("longitude:");
		sb.append(longitude);
		sb.append("altitude:");
		sb.append(altitude);
		sb.append("provider:");
		sb.append(provider);
		return sb.toString();
	}
}
