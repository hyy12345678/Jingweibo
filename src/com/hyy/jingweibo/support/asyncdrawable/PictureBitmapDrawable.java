package com.hyy.jingweibo.support.asyncdrawable;

import java.lang.ref.WeakReference;

import com.hyy.jingweibo.R;
import com.hyy.jingweibo.support.utils.ThemeUtility;

import android.graphics.drawable.ColorDrawable;

public class PictureBitmapDrawable extends ColorDrawable {
    private final WeakReference<IPictureWorker> bitmapDownloaderTaskReference;

    public PictureBitmapDrawable(IPictureWorker bitmapDownloaderTask) {
        super(ThemeUtility.getColor(R.attr.listview_pic_bg));
        bitmapDownloaderTaskReference =
                new WeakReference<IPictureWorker>(bitmapDownloaderTask);
    }

    public IPictureWorker getBitmapDownloaderTask() {
        return bitmapDownloaderTaskReference.get();
    }
}
