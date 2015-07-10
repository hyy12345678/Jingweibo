package com.hyy.jingweibo.support.utils;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class JWBImageDownloader {

	private static final String TAG = "JWBImageDownloader";

	private HashMap<String, MyAsyncTask> map = new HashMap<String, MyAsyncTask>();

	private Map<String, SoftReference<Bitmap>> imageCaches = new HashMap<String, SoftReference<Bitmap>>();

	/***
	 * 构造函数
	 * 
	 * @param url
	 * @param mImageView
	 * @param path
	 * @param mActivity
	 * @param download
	 */
	public void doImageDownload(String url, ImageView mImageView,
			OnImageDownload download) {

		SoftReference<Bitmap> currBitmap = imageCaches.get(url);
		Bitmap softRefBitmap = null;
		if (currBitmap != null) {
			softRefBitmap = currBitmap.get();
		}

		Bitmap bitmap = null;
		// 在软引用获取失败的情况下才进行文件读取
		if (!(currBitmap != null && mImageView != null && softRefBitmap != null && url
				.equals(mImageView.getTag()))) {
			String imageName = "";
			if (url != null) {
				imageName = JWBPicStorageUtil.getInstance().getImageName(url);
			}

			bitmap = JWBPicStorageUtil.getInstance().readBitmapByFileName(
					imageName, 90, 90);
		}

		// 先从软引用中拿数据
		if (currBitmap != null && mImageView != null && softRefBitmap != null
				&& url.equals(mImageView.getTag())) {
			mImageView.setImageBitmap(softRefBitmap);
		}
		// 软引用中没有，从文件中拿数据
		else if (bitmap != null && mImageView != null
				&& url.equals(mImageView.getTag())) {
			mImageView.setImageBitmap(bitmap);
		}
		// 文件中也没有，此时根据mImageView的tag，即url去判断该url对应的task是否已经在执行，如果在执行，本次操作不创建新的线程，否则创建新的线程。
		else if (url != null && needCreateNewTask(mImageView)) {
			MyAsyncTask task = new MyAsyncTask(url, mImageView, download);
			if (mImageView != null) {

				task.execute();
				// 将对应的url对应的任务存起来
				map.put(url, task);
			}
		}

	}

	/***
	 * 判断是否需要重新创建线程下载图片，如果需要，返回值为true。
	 * 
	 * @param mImageView
	 * @return
	 */
	private boolean needCreateNewTask(ImageView mImageView) {
		// TODO Auto-generated method stub
		boolean b = true;
		if (mImageView != null) {
			String curr_task_url = (String) mImageView.getTag();
			if (isTasksContains(curr_task_url)) {
				b = false;
			}
		}
		return b;
	}

	/***
	 * 检查该url（最终反映的是当前的ImageView的tag，tag会根据position的不同而不同）对应的task是否存在
	 * 
	 * @param curr_task_url
	 * @return
	 */
	private boolean isTasksContains(String curr_task_url) {
		// TODO Auto-generated method stub
		boolean b = false;
		if (map != null && map.get(curr_task_url) != null) {
			b = true;
		}
		return b;
	}

	/**
	 * 删除map中该url的信息，这一步很重要，不然MyAsyncTask的引用会“一直”存在于map中
	 * 
	 * @param url
	 */
	private void removeTaskFormMap(String url) {
		if (url != null && map != null && map.get(url) != null) {
			map.remove(url);
		}
	}

	/***
	 * 异步下载图片的方法
	 * 
	 * @author hyylj
	 * 
	 */
	private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

		private ImageView mImageView;
		private String url;
		private OnImageDownload download;

		public MyAsyncTask(String url, ImageView imageView,
				OnImageDownload download) {
			// TODO Auto-generated constructor stub
			this.url = url;
			this.mImageView = imageView;
			this.download = download;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Bitmap result) {

			// 回调设置图片
			if (download != null) {
				download.onDownloadSucc(result, url);
				// 该url对应的task已经下载完成，从map中将其删除
				removeTaskFormMap(url);
			}

			super.onPostExecute(result);
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			Bitmap data = null;
			if (url != null) {
				try {

					URL c_url = new URL(url);
					InputStream bitmap_data = c_url.openStream();
					data = BitmapFactory.decodeStream(bitmap_data);
					String imageName = JWBPicStorageUtil.getInstance()
							.getImageName(url);

					JWBPicStorageUtil.getInstance().saveBitmapToFileByFileName(
							data, imageName);

					bitmap_data.close();

					imageCaches.put(
							url,
							new SoftReference<Bitmap>(Bitmap
									.createScaledBitmap(data, 90, 90, true)));

				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG,
							e.getMessage() == null ? "exeception happened message is null"
									: e.getMessage());
				}
			}

			// TODO Auto-generated method stub
			return data;
		}
	}
}
