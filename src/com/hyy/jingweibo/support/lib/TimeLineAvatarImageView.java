package com.hyy.jingweibo.support.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hyy.jingweibo.generator.JWBUser;
import com.hyy.jingweibo.support.asyncdrawable.IJingweiboDrawable;
import com.hyy.jingweibo.support.constant.JWBConstants;
import com.hyy.jingweibo.support.utils.Utility;

public class TimeLineAvatarImageView extends PerformanceImageView implements
		IJingweiboDrawable {

	private Paint paint = new Paint();

	private boolean showPressedState = true;
	private boolean pressed = false;

	private int vType = JWBConstants.V_TYPE_NONE;

	public TimeLineAvatarImageView(Context context) {
		this(context, null);
	}

	public TimeLineAvatarImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TimeLineAvatarImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initLayout(context);
	}

	protected void initLayout(Context context) {
		setPadding(Utility.dip2px(5), Utility.dip2px(5), Utility.dip2px(5),
				Utility.dip2px(5));
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		// TODO Auto-generated method stub
		super.setImageDrawable(drawable);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO Auto-generated method stub
		super.setImageBitmap(bm);
	}

	@Override
	public ImageView getImageView() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void setProgress(int value, int max) {
		// TODO Auto-generated method stub

	}

	@Override
	public ProgressBar getProgressBar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGifFlag(boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPressesStateVisibility(boolean value) {
		// TODO Auto-generated method stub
		if (showPressedState == value) {
			return;
		}
		showPressedState = value;
		invalidate();
	}

	@Override
	public void checkVerified(JWBUser user) {
		
		if (user != null && user.getVerified()
				&& !TextUtils.isEmpty(user.getVerified_reason())) {
			if(user.getVerified_type() == JWBConstants.V_TYPE_PERSONAL){
				verifiedPersonal();
			}else{
				verifiedEnterprise();
			}
		}
		else{
			reset();
		}
	}
	
	private void verifiedPersonal() {
        if (vType != JWBConstants.V_TYPE_PERSONAL) {
            vType = JWBConstants.V_TYPE_PERSONAL;
            invalidate();
        }
    }

    private void verifiedEnterprise() {
        if (vType != JWBConstants.V_TYPE_ENTERPRISE) {
            vType = JWBConstants.V_TYPE_ENTERPRISE;
            invalidate();
        }
    }

    private void reset() {
        if (vType != JWBConstants.V_TYPE_NONE) {
            vType = JWBConstants.V_TYPE_NONE;
            invalidate();
        }
    }

}
