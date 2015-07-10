package com.hyy.jingweibo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyy.jingweibo.R;
import com.hyy.jingweibo.database.DatabaseManager;
import com.hyy.jingweibo.generator.JWBGeo;
import com.hyy.jingweibo.generator.JWBStatus;
import com.hyy.jingweibo.generator.JWBUser;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.constant.JWBConstants;
import com.hyy.jingweibo.support.constant.JWBWeiboApiType;
import com.hyy.jingweibo.support.keeper.AccessTokenKeeper;
import com.hyy.jingweibo.support.keeper.AccountInfoKeeper;
import com.hyy.jingweibo.support.lib.pulltorefresh.PullToRefreshBase;
import com.hyy.jingweibo.support.lib.pulltorefresh.PullToRefreshListView;
import com.hyy.jingweibo.support.utils.JWBDataTransUtil;
import com.hyy.jingweibo.support.utils.ViewUtility;
import com.hyy.jingweibo.ui.adapter.OldFriendsTimeLineAdapter;
import com.hyy.jingweibo.ui.adapter.StatusListAdapter;
import com.hyy.jingweibo.ui.basefragment.OldAbstractMessageTimeLineFragment;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

public class OldFrendsTimeLineFragment extends
		OldAbstractMessageTimeLineFragment {

	private static final String TAG = "NewFrendsTimeLineFragment";

	protected PullToRefreshListView pullToRefreshListView;

	// protected OldFriendsTimeLineAdapter timeLineAdapter;
	protected StatusListAdapter timeLineAdapter;

	protected TextView empty;

	protected ProgressBar progressBar;

	protected static final int NEW_MSG_LOADER_ID = 1;

	public static OldFrendsTimeLineFragment newInstance() {

		return new OldFrendsTimeLineFragment();
	}

	public PullToRefreshListView getPullToRefreshListView() {
		return pullToRefreshListView;
	}

	public ListView getListView() {
		return pullToRefreshListView.getRefreshableView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.listview_layout, container, false);
		buildLayout(inflater, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		// timeLineAdapter = new OldFriendsTimeLineAdapter(
		// GlobalContext.getInstance(), getListView());

		List<JWBStatus> dummy = new ArrayList<JWBStatus>();
		timeLineAdapter = new StatusListAdapter(this, getListView(), dummy,
				true);
		getListView().setAdapter(timeLineAdapter);

		getLoaderManager().initLoader(NEW_MSG_LOADER_ID, null,
				msgAsyncTaskLoaderCallback);

	}

	protected void buildLayout(LayoutInflater inflater, View view) {
		empty = ViewUtility.findViewById(view, R.id.empty);
		progressBar = ViewUtility.findViewById(view, R.id.progressbar);
		progressBar.setVisibility(View.GONE);
		pullToRefreshListView = ViewUtility.findViewById(view, R.id.listView);

		getListView().setHeaderDividersEnabled(false);
		getListView().setScrollingCacheEnabled(false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		pullToRefreshListView.setOnRefreshListener(listViewOnRefreshListener);

	}

	private PullToRefreshBase.OnRefreshListener<ListView> listViewOnRefreshListener = new PullToRefreshBase.OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {

			loadNewMsg();
		}
	};

	public void loadNewMsg() {

		refresh();

	}

	protected LoaderManager.LoaderCallbacks<List<JWBStatus>> msgAsyncTaskLoaderCallback = new LoaderManager.LoaderCallbacks<List<JWBStatus>>() {

		@Override
		public void onLoaderReset(Loader<List<JWBStatus>> loader) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadFinished(Loader<List<JWBStatus>> loader,
				List<JWBStatus> data) {
			// TODO Auto-generated method stub
			getPullToRefreshListView().onRefreshComplete();

			timeLineAdapter.setList(data);
			timeLineAdapter.notifyDataSetChanged();

		}

		@Override
		public Loader<List<JWBStatus>> onCreateLoader(int id, Bundle args) {

			Loader<List<JWBStatus>> loader = new MessageLoader(
					GlobalContext.getInstance());

			return loader;
		}
	};

	private static class MessageLoader extends AsyncTaskLoader<List<JWBStatus>> {

		public MessageLoader(Context context) {
			super(context);
		}

		@Override
		protected void onStartLoading() {
			// TODO Auto-generated method stub
			super.onStartLoading();
			forceLoad();
		}

		@Override
		public List<JWBStatus> loadInBackground() {
			// TODO Auto-generated method stub

			List<JWBStatus> list = DatabaseManager.getInstance(
					GlobalContext.getInstance()).queryStatusAllByUser(
					AccountInfoKeeper.readAuthInfoToken(
							GlobalContext.getInstance()).getAutherId());
			return list;
		}

	}

	/***
	 * 重新获取微博信息，并刷新列表
	 */
	private void refresh() {
		// TODO Auto-generated method stub
		fireWeiboApi(JWBWeiboApiType.FRIENDS_TIME_LINE);
	}

	@Override
	protected void onRequestListenerCallback(String response) {
		// TODO Auto-generated method stub
		super.onRequestListenerCallback(response);

		if (!TextUtils.isEmpty(response)) {

			// print return message
			Log.i(TAG, response);

			if (response.startsWith("{\"statuses\"")) {
				// 调用 StatusList#parse 解析字符串成微博列表对象
				StatusList statuses = StatusList.parse(response);

				if (statuses != null && statuses.total_number > 0) {
					Toast.makeText(GlobalContext.getInstance(),
							"获取微博信息流成功, 条数: " + statuses.statusList.size(),
							Toast.LENGTH_LONG).show();

					// 转换类型获取存储用list
					List<JWBStatus> jwbStatusList = JWBDataTransUtil
							.transFromStatus2JWBStatus(statuses);

					// 获取StatusList中每个Status的关联Status（如果有的话）
					List<JWBStatus> retweetedStatusList = JWBDataTransUtil
							.digRetweetedStatus(statuses);

					// 获取StatusList中相关User
					List<JWBUser> jwbStatusUserList = JWBDataTransUtil
							.digJWBUser(statuses);

					// 获取关联Status中的User
					List<JWBUser> jwbRetweetedStatusUserList = JWBDataTransUtil
							.digJWBUserFromRetStatusList(statuses);
					
					

					// 存储数据库
					DatabaseManager.getInstance(GlobalContext.getInstance())
							.saveOrUpdateUser(jwbStatusUserList);

					DatabaseManager.getInstance(GlobalContext.getInstance())
							.saveOrUpdateUser(jwbRetweetedStatusUserList);

					DatabaseManager.getInstance(GlobalContext.getInstance())
							.saveOrUpdateStatus(retweetedStatusList);

					DatabaseManager.getInstance(GlobalContext.getInstance())
							.saveOrUpdateStatus(jwbStatusList);

					// refresh weibo list
					getLoaderManager().restartLoader(NEW_MSG_LOADER_ID, null,
							msgAsyncTaskLoaderCallback);

				}

			} else if (response.startsWith("{\"created_at\"")) {
				// 调用 Status#parse 解析字符串成微博对象
				Status status = Status.parse(response);
				Toast.makeText(GlobalContext.getInstance(),
						"发送一送微博成功, id = " + status.id, Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(GlobalContext.getInstance(), response,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean isListViewFling() {
		// TODO Auto-generated method stub
		return false;
	}

}
