package com.hyy.jingweibo.support.application;

import com.hyy.jingweibo.database.DatabaseManager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.Display;

public class GlobalContext extends Application {

	// singleton
	private static GlobalContext globalContext = null;

	// image size
	private Activity activity = null;
	private Activity currentRunningActivity = null;

	private DisplayMetrics displayMetrics = null;
	private Handler handler = new Handler();

	// image memory cache
	private LruCache<String, Bitmap> appBitmapCache = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		globalContext = this;
		DatabaseManager.initDatabaseManager(globalContext);

	}

	public static GlobalContext getInstance() {
		return globalContext;
	}
	
	public Handler getUIHandler() {
        return handler;
    }

	private void buildCache() {
		int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
				.getMemoryClass();

		int cacheSize = Math.max(1024 * 1024 * 8, 1024 * 1024 * memClass / 5);

		appBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {

				return bitmap.getByteCount();
			}
		};
	}

	public synchronized LruCache<String, Bitmap> getBitmapCache() {
		if (appBitmapCache == null) {
			buildCache();
		}
		return appBitmapCache;
	}

	public DisplayMetrics getDisplayMetrics() {
		if (displayMetrics != null) {
			return displayMetrics;
		} else {
			Activity a = getActivity();
			if (a != null) {
				Display display = getActivity().getWindowManager()
						.getDefaultDisplay();
				DisplayMetrics metrics = new DisplayMetrics();
				display.getMetrics(metrics);
				this.displayMetrics = metrics;
				return metrics;
			} else {
				// default screen is 800x480
				DisplayMetrics metrics = new DisplayMetrics();
				metrics.widthPixels = 480;
				metrics.heightPixels = 800;
				return metrics;
			}
		}
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Activity getCurrentRunningActivity() {
		return currentRunningActivity;
	}

	public void setCurrentRunningActivity(Activity currentRunningActivity) {
		this.currentRunningActivity = currentRunningActivity;
	}

}
