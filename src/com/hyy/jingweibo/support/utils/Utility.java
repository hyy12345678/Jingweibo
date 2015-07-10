package com.hyy.jingweibo.support.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hyy.jingweibo.BuildConfig;
import com.hyy.jingweibo.generator.JWBAccount;
import com.hyy.jingweibo.generator.JWBGeo;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.keeper.AccountInfoBean;
import com.hyy.jingweibo.support.keeper.AccountInfoKeeper;
import com.hyy.jingweibo.support.lib.MyAsyncTask;
import com.hyy.jingweibo.support.settinghelper.SettingUtility;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class Utility {

	private Utility() {
		// Forbidden being instantiated.
	}

	public static boolean isJB() {
		return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean isJB1() {
		return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	public static boolean isJB2() {
		return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
	}

	public static boolean isKK() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}

	public static boolean isL() {
		return Build.VERSION.SDK_INT >= 20;
	}

	public static boolean isTokenValid(JWBAccount account) {
		return !TextUtils.isEmpty(account.getAccess_token())
				&& (account.getExpires_in() == 0 || (System.currentTimeMillis()) < account
						.getExpires_in());
	}

	public static int dip2px(int dipValue) {
		float reSize = GlobalContext.getInstance().getResources()
				.getDisplayMetrics().density;
		return (int) ((dipValue * reSize) + 0.5);
	}

	public static float sp2px(int spValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				GlobalContext.getInstance().getResources().getDisplayMetrics());
	}

	public static boolean isDebugMode() {
		return BuildConfig.DEBUG;
	}

	public static void forceRefreshSystemAlbum(String path) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		String type = options.outMimeType;

		MediaScannerConnection.scanFile(GlobalContext.getInstance(),
				new String[] { path }, new String[] { type }, null);
	}

	public static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnected();
	}

	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSystemRinger(Context context) {
		AudioManager manager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		return manager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
	}

	public static String encodeUrl(Map<String, String> param) {
		if (param == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		Set<String> keys = param.keySet();
		boolean first = true;

		for (String key : keys) {
			String value = param.get(key);
			// pain...EditMyProfileDao params' values can be empty
			if (!TextUtils.isEmpty(value) || key.equals("description")
					|| key.equals("url")) {
				if (first) {
					first = false;
				} else {
					sb.append("&");
				}
				try {
					sb.append(URLEncoder.encode(key, "UTF-8")).append("=")
							.append(URLEncoder.encode(param.get(key), "UTF-8"));
				} catch (UnsupportedEncodingException e) {

				}
			}
		}

		return sb.toString();
	}

	public static void closeSilently(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException ignored) {

			}
		}
	}

	public static void printStackTrace(Exception e) {
		if (BuildConfig.DEBUG) {
			e.printStackTrace();
		}
	}

}
