package com.hyy.jingweibo.support.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class JWBPicStorageUtil {

	private static final String TAG = "JWBPicStorageUtil.class";

	public static final String STORAGE_PATH = Environment
			.getExternalStorageDirectory() + "/JWB/pics/";

	private static JWBPicStorageUtil util = null;

	private JWBPicStorageUtil() {

	}

	public static JWBPicStorageUtil getInstance() {
		if (null == util) {
			util = new JWBPicStorageUtil();
		}

		return util;
	}

	/**
	 * SDCARD是否存
	 */
	public boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/***
	 * Save Bitmap to a file in SD card. (to default location "STORAGE_PATH")
	 * 
	 * @param bitmap
	 * @param _file
	 * @return saved file path
	 * @throws IOException
	 */
	public String saveBitmapToFileByFileName(Bitmap bitmap, String fileName)
			throws IOException {
		String filePath = STORAGE_PATH + fileName;
		return saveBitmapToFileByFilePath(bitmap, filePath);

	}

	/***
	 * save bitmap according to the path which user defined
	 * 
	 * @param bitmap
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String saveBitmapToFileByFilePath(Bitmap bitmap, String filePath)
			throws IOException {
		BufferedOutputStream os = null;
		try {

			String _file = filePath;

			File file = new File(_file);

			int end = _file.lastIndexOf(File.separator);
			String _fileDirectory = _file.substring(0, end);
			File fileDirectory = new File(_fileDirectory);
			if (!fileDirectory.exists()) {
				fileDirectory.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

			return _file;

		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
		}

	}

	/***
	 * 根据filepath获取图片的bitmap
	 * 
	 * @param filePath
	 * @param outWidth
	 * @param outHeight
	 * @return
	 */
	public Bitmap readBitmapByFilePath(String filePath, int outWidth,
			int outHeight) {
		FileInputStream fs = null;
		BufferedInputStream bis = null;

		File fileTest = new File(filePath);
		if (!fileTest.exists()) {
			return null;
		}
		try {

			fs = new FileInputStream(filePath);
			bis = new BufferedInputStream(fs);
			BitmapFactory.Options option = setBitmapoption(filePath, outWidth,
					outHeight);
			return BitmapFactory.decodeStream(bis, null, option);

		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.getMessage());

		} finally {

			try {

				bis.close();
				fs.close();

			} catch (Exception e2) {
				// TODO: handle exception

				Log.e(TAG, e2.getMessage());
			}

		}

		return null;
	}

	/***
	 * return bitmap from reading file (file must under the default location
	 * "STORAGE_PATH")
	 * 
	 * @param outWidth
	 *            :output width
	 * @param outHeight
	 *            :output height
	 * @return
	 */
	public Bitmap readBitmapByFileName(String fileName, int outWidth,
			int outHeight) {

		String filePath = STORAGE_PATH + fileName;

		return readBitmapByFilePath(filePath, outWidth, outHeight);
	}

	/***
	 * reset width,height option for Bitmap
	 * 
	 * @param file
	 * @param width
	 * @param height
	 * @return
	 */
	private static BitmapFactory.Options setBitmapoption(String file,
			int width, int height) {

		BitmapFactory.Options option = new BitmapFactory.Options();
		// flag for just decode bounds
		option.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(file, option);
		int outWidth = option.outWidth;
		int outHeight = option.outHeight;
		option.inDither = false; // dont do 24 to 16
		option.inPreferredConfig = Bitmap.Config.RGB_565;
		option.inSampleSize = 1;
		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = (outWidth / width + outHeight / height) / 2;
			option.inSampleSize = sampleSize;
		}

		option.inJustDecodeBounds = false;
		return option;

	}

	/***
	 * 根据url得到图片名
	 * 
	 * @param url
	 * @return
	 */
	public String getImageName(String url) {
		String imageName = "";
		if (url != null) {
			imageName = url.substring(url.lastIndexOf(File.separator) + 1);
		}
		return imageName;
	}

	
	// /***
	// * 根据url得到图片得type
	// *
	// * @param url
	// * @return
	// */
	// public String getImageType(String url) {
	// String imageType = "";
	// try {
	// if (url != null) {
	// String temp = url.substring(0, url.lastIndexOf(File.separator));
	// imageType = temp
	// .substring(temp.lastIndexOf(File.separator) + 1);
	// }
	// } catch (Exception e) {
	// Log.e("eeee", url);
	// }
	//
	// return imageType;
	// }
	

}
