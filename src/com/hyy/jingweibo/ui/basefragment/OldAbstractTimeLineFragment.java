package com.hyy.jingweibo.ui.basefragment;

import android.util.Log;
import android.widget.Toast;

import com.hyy.jingweibo.R;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.constant.JWBConstants;
import com.hyy.jingweibo.support.constant.JWBWeiboApiType;
import com.hyy.jingweibo.support.keeper.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;

public abstract class OldAbstractTimeLineFragment extends OldAbstractAppFragment {

	private static final String TAG = "OldAbstractTimeLineFragment";

	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;

	/** 用于获取微博信息流等操作的API */
	private StatusesAPI mStatusesAPI;
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 初始化weiboAPI
		initWeiboStuff();
	}

	/***
	 * 初始化weibo相关事项（token，api，etc）
	 */
	private void initWeiboStuff() {
		mAccessToken = AccessTokenKeeper.readAccessToken(GlobalContext
				.getInstance());
		mStatusesAPI = new StatusesAPI(GlobalContext.getInstance(),
				JWBConstants.APP_KEY, mAccessToken);

	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			onRequestListenerCallback(response);
		}

		@Override
		public void onWeiboException(WeiboException e) {
			// TODO Auto-generated method stub
			Log.e(JWBConstants.TAG, e.getMessage());
			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			Toast.makeText(GlobalContext.getInstance(), info.toString(),
					Toast.LENGTH_LONG).show();
		}
	};

	protected void fireWeiboApi(JWBWeiboApiType fireType) {

		if (mAccessToken != null && mAccessToken.isSessionValid()) {

			switch (fireType) {
			case FRIENDS_TIME_LINE:
				mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false,
						mListener);

				break;

			default:
				break;
			}

		} else {
			Toast.makeText(GlobalContext.getInstance(),
					R.string.weibosdk_demo_access_token_is_empty,
					Toast.LENGTH_LONG).show();
		}
	}

	protected void onRequestListenerCallback(String response) {
	};
	

	public abstract boolean isListViewFling();
}
