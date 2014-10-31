package com.framework.base;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.healthslife.db.DataBaseHelper;
import com.healthslife.music.MusicUtil;
import com.healthslife.services.UDPListenerService;
import com.healthslife.setting.AppSetting;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BaseApplication extends Application {
	private static BaseApplication mInstance;
	private MyApplicationManager manager;
	private AppSetting mAppSetting;
	@Override
	public void onCreate() {
		mInstance = this;
		manager = new MyApplicationManager(this);
		
		mAppSetting = new AppSetting(getApplicationContext());
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.build();
		ImageLoader.getInstance().init(config);
		
		MusicUtil.initPlayer(this);
		
		
		if(mAppSetting.getRemoteListen()){
			Log.v("service", "open");
			Intent i = new Intent(this,UDPListenerService.class);
			startService(i);
		}
		super.onCreate();
	}
	public BaseApplication getInstance() {
		return mInstance;
	}
	
	
}
