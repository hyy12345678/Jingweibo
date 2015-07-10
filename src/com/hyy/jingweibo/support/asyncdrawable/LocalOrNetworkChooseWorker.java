package com.hyy.jingweibo.support.asyncdrawable;

import java.lang.ref.WeakReference;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.hyy.jingweibo.support.file.FileLocationMethod;
import com.hyy.jingweibo.support.file.FileManager;
import com.hyy.jingweibo.support.imageutility.ImageUtility;

public class LocalOrNetworkChooseWorker extends AbstractWorker<String, Integer, Boolean> {

    private String data = "";
    private boolean isMultiPictures = false;

    private WeakReference<ImageView> viewWeakReference;

    private FileLocationMethod method;

    private IJingweiboDrawable IJingweiboDrawable;

    public String getUrl() {
        return data;
    }

    public LocalOrNetworkChooseWorker(ImageView view, String url, FileLocationMethod method,
            boolean isMultiPictures) {

        this.viewWeakReference = new WeakReference<ImageView>(view);
        this.data = url;
        this.method = method;
        this.isMultiPictures = isMultiPictures;
    }

    public LocalOrNetworkChooseWorker(IJingweiboDrawable view, String url,
            FileLocationMethod method,
            boolean isMultiPictures) {

        this(view.getImageView(), url, method, false);
        this.IJingweiboDrawable = view;
        this.isMultiPictures = isMultiPictures;
    }

    @Override
    protected Boolean doInBackground(String... url) {
        String path = FileManager.getFilePathFromUrl(data, method);
        return ImageUtility.isThisBitmapCanRead(path) && TaskCache.isThisUrlTaskFinished(data);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
        ImageView imageView = viewWeakReference.get();
        if (!isMySelf(imageView)) {
            return;
        }

        imageView.setImageDrawable(
                new ColorDrawable(DebugColor.CHOOSE_CANCEL));
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        ImageView imageView = viewWeakReference.get();
        if (!isMySelf(imageView)) {
            return;
        }

        if (result) {
            LocalWorker newTask = null;

            if (IJingweiboDrawable != null) {
                newTask = new LocalWorker(IJingweiboDrawable, getUrl(), method,
                        isMultiPictures);
                PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(newTask);
                IJingweiboDrawable.setImageDrawable(downloadedDrawable);
            } else {
                newTask = new LocalWorker(imageView, getUrl(), method, isMultiPictures);
                PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(newTask);
                imageView.setImageDrawable(downloadedDrawable);
            }

            newTask.executeOnIO();
        } else {

            ReadWorker newTask = null;

            if (IJingweiboDrawable != null) {
                newTask = new ReadWorker(IJingweiboDrawable, getUrl(), method,
                        isMultiPictures);
                PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(newTask);
                IJingweiboDrawable.setImageDrawable(downloadedDrawable);
            } else {
                newTask = new ReadWorker(imageView, getUrl(), method, isMultiPictures);
                PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(newTask);
                imageView.setImageDrawable(downloadedDrawable);
            }

            newTask.executeOnWaitNetwork();
        }
    }
}
