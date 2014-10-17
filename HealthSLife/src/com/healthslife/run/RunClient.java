package com.healthslife.run;

import android.content.Context;
import android.location.Location;
import android.os.SystemClock;

import com.dm.location.DMLocation;
import com.dm.location.DMLocationClient;
import com.dm.location.DMLocationClient.OnLocationChangeListener;
import com.dm.location.DMLocationClientOption;
import com.dm.location.DMLocationUtils;
import com.healthslife.run.dao.RunSetting;

public class RunClient {
	private Context mContext;
	private DMLocationClient mClient;
	// private ArrayList<DMLocation> locationList;
	private RunSetting mSetting;
	private long startTime;
	private long endTime;
	private boolean isStop = false;
	private OnLocationChangeListener outListener;
	private DMLocation startLocation;
	private DMLocation currentLocation;

	private float distance;
	private long calorie;

	public RunClient(Context context) {
		mClient = new DMLocationClient(context);
		DMLocationClientOption option = new DMLocationClientOption();
		option.setInterval(1000);
		option.setPriority(DMLocationClientOption.GPS_FIRST);
		mClient.setOption(option);
	}

	public void init() {
		mClient.start();
	}

	public void start() {
		startTime = SystemClock.elapsedRealtime();
		mClient.setLocationListener(mListener);
	}

	public float getDistance() {
		if (startLocation == null || currentLocation == null) {
			return 0;
		}
		return distance;
	}

	public long getDuration() {
		long duration = 0;
		if (isStop) {
			duration = endTime - startTime;
		} else {
			duration = SystemClock.elapsedRealtime() - startTime;
		}
		return duration;
	}

	protected void onLocationChanged(DMLocation loation) {
		long millis = this.getDuration();
		float meter = this.getDistance();

		// System.out.println("dur  " + millis + "dis " + meter);
	}

	public void stop() {
		isStop = true;
		endTime = SystemClock.elapsedRealtime();
		mClient.stop();
	}

	public void setOnLocationChangeListener(OnLocationChangeListener listener) {
		outListener = listener;
	}

	private OnLocationChangeListener mListener = new OnLocationChangeListener() {
		@Override
		public void onLocationChanged(DMLocation location) {
			if (startLocation == null) {
				startLocation = location;
			}
			float meters = 0;
			
			if (currentLocation != null && location != null) {
				meters = DMLocationUtils.distanceBetween(location, currentLocation);
				if (meters > currentLocation.getAccuracy()/3) {
					distance += meters;
					long interval =Math.abs( location.getTime() -currentLocation.getTime())  ;
					calorie += calcCalorie(meters, interval);
					currentLocation = location;
				}
			} else {
				currentLocation = location;
			}
			RunClient.this.onLocationChanged(location);
			if (outListener != null) {
				outListener.onLocationChanged(location);
			}
		}
	};
	private static float calorieCon1 = 1;
	private static float calorieCon2 = 1;

	private static long calcCalorie(float distance, long time) {
		float speed = distance / (time / 1000f);
		int calorie = (int) (distance * calorieCon1 + distance * speed * calorieCon2);
		return calorie;
	}

	public long getCalorie() {
		return calorie;
	}
	public void unBind(){
		mClient.unBind();
	}

}
