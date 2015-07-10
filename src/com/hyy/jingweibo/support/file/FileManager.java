package com.hyy.jingweibo.support.file;

import java.io.File;
import java.io.IOException;

import com.hyy.jingweibo.R;
import com.hyy.jingweibo.database.DownloadPicturesDBTask;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.debug.AppLogger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

public class FileManager {

	private static final String PICTURE_CACHE = "picture_cache";
	
	
	public static String getFilePathFromUrl(String url,
			FileLocationMethod method) {

		if (!isExternalStorageMounted()) {
			return "";
		}

		if (TextUtils.isEmpty(url)) {
			return "";
		}

		return DownloadPicturesDBTask.get(url);
	}

	public static String generateDownloadFileName(String url) {

		if (!isExternalStorageMounted()) {
			return "";
		}

		if (TextUtils.isEmpty(url)) {
			return "";
		}

		String path = String.valueOf(url.hashCode());
		String result = getSdCardPath() + File.separator + PICTURE_CACHE
				+ File.separator + path;
		if (url.endsWith(".jpg")) {
			result += ".jpg";
		} else if (url.endsWith(".gif")) {
			result += ".gif";
		}
		if (!result.endsWith(".jpg") && !result.endsWith(".gif")
				&& !result.endsWith(".png")) {
			result = result + ".jpg";
		}

		return result;
	}

	/**
	 * install weiciyuan, open app and login in, Android system will create
	 * cache dir. then open cache dir (/sdcard
	 * dir/Android/data/org.qii.weiciyuan) with Root Explorer, uninstall
	 * weiciyuan and reinstall it, the new weiciyuan app will have the bug it
	 * can't read cache dir again, so I have to tell user to delete that cache
	 * dir
	 */
	private static volatile boolean cantReadBecauseOfAndroidBugPermissionProblem = false;

	public static String getSdCardPath() {
		if (isExternalStorageMounted()) {
			File path = GlobalContext.getInstance().getExternalCacheDir();
			if (path != null) {
				return path.getAbsolutePath();
			} else {
				if (!cantReadBecauseOfAndroidBugPermissionProblem) {
					cantReadBecauseOfAndroidBugPermissionProblem = true;
					final Activity activity = GlobalContext.getInstance()
							.getActivity();
					if (activity == null || activity.isFinishing()) {
						GlobalContext.getInstance().getUIHandler()
								.post(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(
												GlobalContext.getInstance(),
												R.string.please_deleted_cache_dir,
												Toast.LENGTH_SHORT).show();
									}
								});

						return "";
					}
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							new AlertDialog.Builder(activity)
									.setTitle(R.string.something_error)
									.setMessage(
											R.string.please_deleted_cache_dir)
									.setPositiveButton(
											R.string.ok,
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {

												}
											}).show();
						}
					});
				}
			}
		} else {
			return "";
		}

		return "";
	}
	
	
	public static File createNewFileInSDCard(String absolutePath) {
        if (!isExternalStorageMounted()) {
            AppLogger.e("sdcard unavailiable");
            return null;
        }

        if (TextUtils.isEmpty(absolutePath)) {
            return null;
        }

        File file = new File(absolutePath);
        if (file.exists()) {
            return file;
        } else {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
                AppLogger.d(e.getMessage());
                return null;
            }
        }
        return null;
    }
	
	
	

	public static boolean isExternalStorageMounted() {

		boolean canRead = Environment.getExternalStorageDirectory().canRead();
		boolean onlyRead = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED_READ_ONLY);
		boolean unMounted = Environment.getExternalStorageState().equals(
				Environment.MEDIA_UNMOUNTED);

		return !(!canRead || onlyRead || unMounted);
	}

}
