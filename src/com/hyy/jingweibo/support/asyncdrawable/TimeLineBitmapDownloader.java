package com.hyy.jingweibo.support.asyncdrawable;

import com.hyy.jingweibo.R;
import com.hyy.jingweibo.generator.JWBStatus;
import com.hyy.jingweibo.generator.JWBUser;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.file.FileLocationMethod;
import com.hyy.jingweibo.support.imageutility.ImageUtility;
import com.hyy.jingweibo.support.lib.MyAsyncTask;
import com.hyy.jingweibo.support.settinghelper.SettingUtility;
import com.hyy.jingweibo.support.utils.ThemeUtility;
import com.hyy.jingweibo.ui.basefragment.OldAbstractTimeLineFragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

public class TimeLineBitmapDownloader {

	private int defaultPictureResId;

	private Handler handler;

	static volatile boolean pauseDownloadWork = false;
	static final Object pauseDownloadWorkLock = new Object();

	static volatile boolean pauseReadWork = false;
	static final Object pauseReadWorkLock = new Object();

	private static final Object lock = new Object();

	private static TimeLineBitmapDownloader instance;

	private TimeLineBitmapDownloader(Handler handler) {
		this.handler = handler;
		this.defaultPictureResId = ThemeUtility
				.getResourceId(R.attr.listview_pic_bg);
	}

	public static TimeLineBitmapDownloader getInstance() {
		synchronized (lock) {
			if (instance == null) {
				instance = new TimeLineBitmapDownloader(new Handler(
						Looper.getMainLooper()));
			}
		}
		return instance;
	}

	public void downContentPic(IJingweiboDrawable view, JWBStatus msg,
			OldAbstractTimeLineFragment fragment) {
		String picUrl;

		 boolean isFling = ((OldAbstractTimeLineFragment) fragment).isListViewFling();

	        if (SettingUtility.getEnableBigPic()) {
	            picUrl = msg.getOriginal_pic();
	            display(view, picUrl, FileLocationMethod.picture_large, isFling, false);
	        } else {
	            picUrl = msg.getThumbnail_pic();
	            display(view, picUrl, FileLocationMethod.picture_thumbnail, isFling, false);
	        }
	}

	public void displayMultiPicture(IJingweiboDrawable view, String picUrl,
			FileLocationMethod method, OldAbstractTimeLineFragment fragment) {

		boolean isFling = ((OldAbstractTimeLineFragment) fragment)
				.isListViewFling();

		display(view, picUrl, method, isFling, true);
	}

	private void display(final IJingweiboDrawable view, final String urlKey,
			final FileLocationMethod method, boolean isFling,
			boolean isMultiPictures) {
		view.getImageView().clearAnimation();

		if (!shouldReloadPicture(view.getImageView(), urlKey)) {
			return;
		}

		final Bitmap bitmap = getBitmapFromMemCache(urlKey);
		if (bitmap != null) {
			view.setImageBitmap(bitmap);
			view.getImageView().setTag(urlKey);
			if (view.getProgressBar() != null) {
				view.getProgressBar().setVisibility(View.INVISIBLE);
			}
			if (view.getImageView().getAlpha() != 1.0f) {
				view.getImageView().setAlpha(1.0f);
			}
			view.setGifFlag(ImageUtility.isThisPictureGif(urlKey));
			cancelPotentialDownload(urlKey, view.getImageView());
		} else {

			if (isFling) {
				view.getImageView().setImageResource(defaultPictureResId);
				if (view.getProgressBar() != null) {
					view.getProgressBar().setVisibility(View.INVISIBLE);
				}
				view.setGifFlag(ImageUtility.isThisPictureGif(urlKey));
				return;
			}

			if (!cancelPotentialDownload(urlKey, view.getImageView())) {
				return;
			}

			final LocalOrNetworkChooseWorker newTask = new LocalOrNetworkChooseWorker(
					view, urlKey, method, isMultiPictures);
			PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(
					newTask);
			view.setImageDrawable(downloadedDrawable);

			// listview fast scroll performance
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {

					if (getBitmapDownloaderTask(view.getImageView()) == newTask) {
						newTask.executeOnNormal();
					}
					return;
				}
			}, 400);
		}
	}
	
	
	

	public void downloadAvatar(ImageView view, JWBUser user,
			OldAbstractTimeLineFragment fragment) {
		boolean isFling = fragment.isListViewFling();
		downloadAvatar(view, user, isFling);
	}

	public void downloadAvatar(ImageView view, JWBUser user, boolean isFling) {

		if (user == null) {
			view.setImageResource(defaultPictureResId);
			return;
		}

		String url;
		FileLocationMethod method;
		if (SettingUtility.getEnableBigAvatar()) {
			url = user.getAvatar_large();
			method = FileLocationMethod.avatar_large;
		} else {
			url = user.getProfile_image_url();
			method = FileLocationMethod.avatar_small;
		}
		displayImageView(view, url, method, isFling, false);
	}

	private void displayImageView(final ImageView view, final String urlKey,
			final FileLocationMethod method, boolean isFling,
			boolean isMultiPictures) {
		view.clearAnimation();

		if (!shouldReloadPicture(view, urlKey)) {
			return;
		}

		final Bitmap bitmap = getBitmapFromMemCache(urlKey);
		if (bitmap != null) {
			view.setImageBitmap(bitmap);
			view.setTag(urlKey);
			if (view.getAlpha() != 1.0f) {
				view.setAlpha(1.0f);
			}
			cancelPotentialDownload(urlKey, view);
		} else {

			if (isFling) {
				view.setImageResource(defaultPictureResId);
				return;
			}

			if (!cancelPotentialDownload(urlKey, view)) {
				return;
			}

			final LocalOrNetworkChooseWorker newTask = new LocalOrNetworkChooseWorker(
					view, urlKey, method, isMultiPictures);
			PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(
					newTask);
			view.setImageDrawable(downloadedDrawable);

			// listview fast scroll performance
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {

					if (getBitmapDownloaderTask(view) == newTask) {
						newTask.executeOnNormal();
					}
					return;
				}
			}, 400);
		}
	}

	/**
	 * when user open weibo detail, the activity will setResult to previous
	 * Activity, timeline will refresh at the time user press back button to
	 * display the latest repost count and comment count. But sometimes, weibo
	 * detail's pictures are very large that bitmap memory cache has cleared
	 * those timeline bitmap to save memory, app have to read bitmap from sd
	 * card again, then app play annoying animation , this method will check
	 * whether we should read again or not.
	 */
	private boolean shouldReloadPicture(ImageView view, String urlKey) {
		if (urlKey.equals(view.getTag())
				&& view.getDrawable() != null
				&& view.getDrawable() instanceof BitmapDrawable
				&& ((BitmapDrawable) view.getDrawable() != null && ((BitmapDrawable) view
						.getDrawable()).getBitmap() != null)) {
			// AppLogger.d("shouldReloadPicture=false");
			return false;
		} else {
			view.setTag(null);
			// AppLogger.d("shouldReloadPicture=true");
			return true;
		}
	}

	protected Bitmap getBitmapFromMemCache(String key) {
		if (TextUtils.isEmpty(key)) {
			return null;
		} else {
			return GlobalContext.getInstance().getBitmapCache().get(key);
		}
	}

	private static boolean cancelPotentialDownload(String url,
			ImageView imageView) {
		IPictureWorker bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

		if (bitmapDownloaderTask != null) {
			String bitmapUrl = bitmapDownloaderTask.getUrl();
			if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
				if (bitmapDownloaderTask instanceof MyAsyncTask) {
					((MyAsyncTask) bitmapDownloaderTask).cancel(true);
				}
			} else {
				return false;
			}
		}
		return true;
	}

	private static IPictureWorker getBitmapDownloaderTask(ImageView imageView) {
		if (imageView != null) {
			Drawable drawable = imageView.getDrawable();
			if (drawable instanceof PictureBitmapDrawable) {
				PictureBitmapDrawable downloadedDrawable = (PictureBitmapDrawable) drawable;
				return downloadedDrawable.getBitmapDownloaderTask();
			}
		}
		return null;
	}

}
