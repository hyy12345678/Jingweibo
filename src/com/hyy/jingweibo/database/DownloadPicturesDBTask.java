package com.hyy.jingweibo.database;

import java.io.File;

import android.text.TextUtils;

import com.hyy.jingweibo.generator.JWBDownloadPics;
import com.hyy.jingweibo.generator.JWBDownloadPicsDao;
import com.hyy.jingweibo.generator.JWBDownloadPicsDao.Properties;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.constant.JWBConstants;
import com.hyy.jingweibo.support.file.FileLocationMethod;

public class DownloadPicturesDBTask {

	private DownloadPicturesDBTask() {

	}

	public static String get(String url) {

		JWBDownloadPicsDao downloadDao = DatabaseManager.getInstance(
				GlobalContext.getInstance()).getDownloadPicsDao();

		JWBDownloadPics dlp = downloadDao.queryBuilder()
				.where(Properties.Url.eq(url)).unique();

		if (dlp == null) {
			return "";
		}
		String path = dlp.getPath();

		if (!TextUtils.isEmpty(path)) {
			dlp.setTime(System.currentTimeMillis());
			downloadDao.update(dlp);
		}

		return path;
	}

	public static void add(String url, String path, FileLocationMethod method) {

		JWBDownloadPicsDao downloadDao = DatabaseManager.getInstance(
				GlobalContext.getInstance()).getDownloadPicsDao();

		JWBDownloadPics dlp = new JWBDownloadPics();

		dlp.setUrl(url);
		dlp.setPath(path);
		dlp.setTime(System.currentTimeMillis());
		dlp.setType(JWBConstants.TYPE_OTHER);
		switch (method) {
		case avatar_small:
		case avatar_large:
			dlp.setType(JWBConstants.TYPE_AVATAR);
			break;
		}

		long size = new File(path).length();
		dlp.setSize(size);
		downloadDao.insertOrReplace(dlp);

	}

}
