package com.hyy.jingweibo.support.asyncdrawable;

import com.hyy.jingweibo.generator.JWBUser;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

public interface IJingweiboDrawable {
	
	    public void setImageDrawable(Drawable drawable);

	    public void setImageBitmap(Bitmap bm);

	    public ImageView getImageView();

	    public void setProgress(int value, int max);

	    public ProgressBar getProgressBar();

	    public void setGifFlag(boolean value);

	    public void setPressesStateVisibility(boolean value);

	    public void setVisibility(int visibility);

	    public int getVisibility();
	    
	    public void checkVerified(JWBUser user);

	    public void setOnClickListener(View.OnClickListener onClickListener);

	    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener);

	    public void setLayoutParams(ViewGroup.LayoutParams layoutParams);

}
