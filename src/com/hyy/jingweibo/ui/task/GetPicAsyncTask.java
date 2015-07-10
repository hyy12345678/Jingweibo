package com.hyy.jingweibo.ui.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.keeper.AccessTokenKeeper;
import com.hyy.jingweibo.support.keeper.AccountInfoBean;
import com.hyy.jingweibo.support.keeper.AccountInfoKeeper;
import com.hyy.jingweibo.support.utils.JWBPicStorageUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/***
 * Async Task get pic from Internet
 * 
 * @author hyylj
 * 
 */
public class GetPicAsyncTask extends AsyncTask<String, Integer, Bitmap> {

	private static final String TAG = "GetPicAsyncTask";

	private Oauth2AccessToken mAccessToken = AccessTokenKeeper
			.readAccessToken(GlobalContext.getInstance());

	private ImageView img;

	public GetPicAsyncTask(ImageView img) {
		// TODO Auto-generated constructor stub
		this.img = img;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		String path = params[0];
		URL url = null;
		Bitmap m = null;
		HttpURLConnection conn = null;

		try {
			url = new URL(path);

			conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() == 200) {
				InputStream ins = conn.getInputStream();
				m = BitmapFactory.decodeStream(ins);
				ins.close();
				conn.disconnect();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		img.setImageBitmap(result);
		restoreAutherImg(mAccessToken.getUid(), result);
		
//		AccountInfoBean token = new AccountInfoBean();
//		token.setAutherProfile(restoreAutherImg(mAccessToken.getUid(), result));
//		AccountInfoKeeper.writeAuthInfoToken(GlobalContext.getInstance(), token);
	}

	/***
	 * 保存登录用户头像
	 * 
	 * @param uid
	 * @param in
	 * @return 保存地址
	 */
	private String restoreAutherImg(String uid, Bitmap in) {

		// if externalMemory is not available,toast it
		if (!JWBPicStorageUtil.getInstance().externalMemoryAvailable()) {
			Toast.makeText(GlobalContext.getInstance(),
					"sd card is not available", Toast.LENGTH_SHORT).show();
		} else {
			try {
				return JWBPicStorageUtil.getInstance()
						.saveBitmapToFileByFileName(in, uid + ".jpg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.getMessage());
			}
		}

		return null;

	}

}
