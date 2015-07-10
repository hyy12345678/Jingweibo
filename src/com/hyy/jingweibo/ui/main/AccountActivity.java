package com.hyy.jingweibo.ui.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyy.jingweibo.R;
import com.hyy.jingweibo.database.DatabaseManager;
import com.hyy.jingweibo.generator.JWBAccount;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.constant.JWBConstants;
import com.hyy.jingweibo.support.keeper.AccessTokenKeeper;
import com.hyy.jingweibo.support.keeper.AccountInfoBean;
import com.hyy.jingweibo.support.keeper.AccountInfoKeeper;
import com.hyy.jingweibo.support.utils.JWBPicStorageUtil;
import com.hyy.jingweibo.support.utils.ThemeUtility;
import com.hyy.jingweibo.support.utils.Utility;
import com.hyy.jingweibo.ui.interfaces.AbstractAppActivity;
import com.hyy.jingweibo.ui.task.GetPicAsyncTask;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;

public class AccountActivity extends AbstractAppActivity implements
		LoaderManager.LoaderCallbacks<List<JWBAccount>> {

	private static final String TAG = "AuthActivity";

	private static final String ACTION_OPEN_FROM_APP_INNER = "com.hyy.jingweibo:accountactivity";
	private static final String ACTION_OPEN_FROM_APP_INNER_REFRESH_TOKEN = "com.hyy.jingweibo:accountactivity_refresh_token";

	// // AccessToken
	// private Oauth2AccessToken mAccessToken;
	//
	// store Auth info
	AuthInfo mAuthInfo;

	// sso
	SsoHandler mSsoHandler;

	private final int LOADER_ID = 0;

	private ListView listView = null;
	private AccountAdapter listAdapter = null;
	private List<JWBAccount> accountList = new ArrayList<JWBAccount>();

	public static Intent newIntent() {
		Intent intent = new Intent(GlobalContext.getInstance(),
				AccountActivity.class);
		intent.setAction(ACTION_OPEN_FROM_APP_INNER);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		init();

		String action = getIntent() != null ? getIntent().getAction() : null;

		if (ACTION_OPEN_FROM_APP_INNER.equals(action)) {
			// empty
		} else {
			// finish current Activity
			jumpToMainTimeLineActivity();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		getActionBar().setTitle(getString(R.string.app_name));

		listAdapter = new AccountAdapter();
		listView = (ListView) findViewById(R.id.lv_account);

		listView.setOnItemClickListener(new AccountListItemClickListener());
		listView.setAdapter(listAdapter);
		listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

		getLoaderManager().initLoader(LOADER_ID, null, this);

	}

	private void jumpToMainTimeLineActivity() {

		Oauth2AccessToken token = AccessTokenKeeper
				.readAccessToken(GlobalContext.getInstance());

		if (null != token && token.isSessionValid()) {
			
			Intent start = NewMainTimeLineActivity.newIntent();
			start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(start);
			finish();
		} else {
			mSsoHandler.authorize(new AuthListener());
		}

	}

	private void init() {
		// TODO Auto-generated method stub

		mAuthInfo = new AuthInfo(GlobalContext.getInstance(),
				JWBConstants.APP_KEY, JWBConstants.REDIRECT_URL,
				JWBConstants.SCOPE);
		mSsoHandler = new SsoHandler(AccountActivity.this, mAuthInfo);
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			Oauth2AccessToken mAccessToken = Oauth2AccessToken
					.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {

				// refresh login user info
				updateLoginUserInfo(mAccessToken);

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(GlobalContext.getInstance(),
						mAccessToken);
				Toast.makeText(GlobalContext.getInstance(),
						R.string.weibosdk_demo_toast_auth_success,
						Toast.LENGTH_SHORT).show();
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(GlobalContext.getInstance(), message,
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(GlobalContext.getInstance(),
					R.string.weibosdk_demo_toast_auth_canceled,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(GlobalContext.getInstance(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
	}

	/***
	 * Update Login user info(profile image,name,etc)
	 */
	private void updateLoginUserInfo(Oauth2AccessToken mAccessToken) {
		// TODO Auto-generated method stub
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			UsersAPI usersApi = new UsersAPI(GlobalContext.getInstance(),
					JWBConstants.APP_KEY, mAccessToken);

			JWBAccount ja = new JWBAccount();
			ja.setId(Long.valueOf(mAccessToken.getUid()));
			ja.setAccess_token(mAccessToken.getToken());
			ja.setExpires_in(mAccessToken.getExpiresTime());

			DatabaseManager.getInstance(GlobalContext.getInstance())
					.getAccountDao().insertOrReplace(ja);

			Log.i(JWBConstants.TAG, "User id is:" + mAccessToken.getUid());

			usersApi.show((Long.valueOf(mAccessToken.getUid())), mListener);
		} else {
			Toast.makeText(GlobalContext.getInstance(),
					R.string.weibosdk_demo_access_token_is_empty,
					Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				Log.i(JWBConstants.TAG, response);
				if (response.startsWith("{\"id\"")) {

					// 解析字符串Auther对象
					User auther = User.parse(response);

					// 保存auth的token写入preference
					AccountInfoBean token = new AccountInfoBean();
					token.setAutherName(auther.name);
					token.setAutherDescription(auther.description);
					token.setAutherId(auther.id);
					AccountInfoKeeper.writeAuthInfoToken(
							GlobalContext.getInstance(), token);

					// 写入DB
					JWBAccount nja = DatabaseManager
							.getInstance(GlobalContext.getInstance())
							.getAccountDao().load(Long.valueOf(auther.id));
					nja.setName(auther.name);
					nja.setDescription(auther.description);
					nja.setProfile(auther.profile_image_url);

					DatabaseManager.getInstance(GlobalContext.getInstance())
							.getAccountDao().update(nja);

					// 刷新Account列表（这里其实也可以不做刷新，因为下面会直接跳转）
					refresh();

					// 跳转到MainTimeLineActivity
					jumpToMainTimeLineActivity();

				} else {
					Toast.makeText(GlobalContext.getInstance(), response,
							Toast.LENGTH_LONG).show();
				}
			}
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

	private void refresh() {
		getLoaderManager().getLoader(LOADER_ID).forceLoad();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.actionbar_menu_accountactivity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_account:
			// 调用微博注册API
			mSsoHandler.authorize(new AuthListener());
			break;
		}
		return true;
	}

	/***
	 * Loader接口回调
	 */
	@Override
	public Loader<List<JWBAccount>> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub

		return new AccountDBLoader(AccountActivity.this, args);
	}

	@Override
	public void onLoadFinished(Loader<List<JWBAccount>> loader,
			List<JWBAccount> data) {
		// TODO Auto-generated method stub
		accountList = data;
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<JWBAccount>> loader) {
		// TODO Auto-generated method stub
		accountList = new ArrayList<JWBAccount>();
		listAdapter.notifyDataSetChanged();
	}

	/***
	 * AccountDbLoader
	 */
	private static class AccountDBLoader extends
			AsyncTaskLoader<List<JWBAccount>> {

		public AccountDBLoader(Context context, Bundle args) {
			super(context);
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}

		public List<JWBAccount> loadInBackground() {
			return DatabaseManager.getInstance(GlobalContext.getInstance())
					.getAccountDao().loadAll();
		}
	}

	/***
	 * AccountAdapter
	 */
	private class AccountAdapter extends BaseAdapter {
		private int checkedBG;
		private int defaultBG;

		public AccountAdapter() {
			defaultBG = getResources().getColor(R.color.transparent);
			checkedBG = ThemeUtility.getColor(AccountActivity.this,
					R.attr.listview_checked_color);
		}

		@Override
		public int getCount() {
			return accountList.size();
		}

		@Override
		public Object getItem(int i) {
			return accountList.get(i);
		}

		@Override
		public long getItemId(int i) {
			return accountList.get(i).getId();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(final int i, View view, ViewGroup viewGroup) {
			ViewHolder holder;
			if (view == null || view.getTag() == null) {
				LayoutInflater layoutInflater = getLayoutInflater();
				View mView = layoutInflater.inflate(
						R.layout.accountactivity_listview_item_layout,
						viewGroup, false);
				holder = new ViewHolder();
				holder.root = mView.findViewById(R.id.listview_root);
				holder.name = (TextView) mView.findViewById(R.id.account_name);
				holder.avatar = (ImageView) mView
						.findViewById(R.id.imageView_avatar);
				holder.tokenInvalid = (TextView) mView
						.findViewById(R.id.token_expired);
				view = mView;
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.root.setBackgroundColor(defaultBG);
			if (listView.getCheckedItemPositions().get(i)) {
				holder.root.setBackgroundColor(checkedBG);
			}

			holder.name.setText(accountList.get(i).getName());

			if (StringUtils.isNotEmpty(accountList.get(i).getProfile())) {

				Bitmap bm = JWBPicStorageUtil.getInstance()
						.readBitmapByFileName(
								accountList.get(i).getId() + ".jpg", 50, 50);

				if (bm == null) {
					GetPicAsyncTask picAsyncTask = new GetPicAsyncTask(
							holder.avatar);
					picAsyncTask.execute(accountList.get(i).getProfile());
				} else {
					holder.avatar.setImageBitmap(bm);
				}

			}

			holder.tokenInvalid.setVisibility(!Utility.isTokenValid(accountList
					.get(i)) ? View.VISIBLE : View.GONE);
			return view;
		}
	}

	/***
	 * AccountAdapter的ViewHolder
	 * 
	 * @author hyylj
	 * 
	 */
	class ViewHolder {
		View root;
		TextView name;
		ImageView avatar;
		TextView tokenInvalid;
	}

	/***
	 * 列表元素点击回调事件监听
	 * 
	 * @author hyylj
	 * 
	 */
	private class AccountListItemClickListener implements
			AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i,
				long l) {
			
			//Rewrite Accecess token
			Oauth2AccessToken token = new Oauth2AccessToken();
			token.setToken(accountList.get(i).getAccess_token());
			token.setUid(String.valueOf(accountList.get(i).getId()));
			token.setExpiresIn(String.valueOf(accountList.get(i)
					.getExpires_in()));

			AccessTokenKeeper.writeAccessToken(GlobalContext.getInstance(),
					token);
			
			if (!token.isSessionValid()) {
				mSsoHandler.authorize(new AuthListener());
				return;
			}
			
			
			//Rewrite Account token
			AccountInfoBean aifb = new AccountInfoBean();
			aifb.setAutherId(String.valueOf(accountList.get(i).getId()));
			aifb.setAutherName(accountList.get(i).getName());
			aifb.setAutherProfile(accountList.get(i).getProfile());
			aifb.setAutherDescription(accountList.get(i).getDescription());
			
			AccountInfoKeeper.writeAuthInfoToken(GlobalContext.getInstance(), aifb);
			

			Intent intent = NewMainTimeLineActivity.newIntent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}

}
