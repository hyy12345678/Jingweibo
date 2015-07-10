package com.hyy.jingweibo.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hyy.jingweibo.R;
import com.hyy.jingweibo.generator.JWBStatus;
import com.hyy.jingweibo.support.utils.JWBDataTransUtil;
import com.hyy.jingweibo.support.utils.JWBImageDownloader;
import com.hyy.jingweibo.support.utils.OnImageDownload;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.hyy.jingweibo.ui.viewholder.ViewHolder;

;;

public class OldFriendsTimeLineAdapter extends BaseAdapter {
	
	private static final String TAG = "NewFriendsTimeLineAdapter";

	private List<JWBStatus> mList = new ArrayList<JWBStatus>();
	private ListView mListView;
	private Context mContext;
	
	private JWBImageDownloader mDownloader;
	
	public OldFriendsTimeLineAdapter(Context context, ListView listview) {
		// TODO Auto-generated constructor stub
		this.mListView = listview;
		this.mContext = context;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.status_list_view, parent, false);
			holder.statusText = (TextView) convertView
					.findViewById(R.id.status_text);

			holder.statusUserName = (TextView) convertView
					.findViewById(R.id.status_user_name);

			holder.status_post_from = (TextView) convertView
					.findViewById(R.id.status_post_from);
			holder.status_post_time = (TextView) convertView
					.findViewById(R.id.status_post_time);

			holder.status_thumbnail_pic = (ImageView) convertView
					.findViewById(R.id.status_thumbnail_pic);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.statusText.setText(mList.get(position).getText());
		holder.statusUserName
				.setText(mList.get(position).getJWBUser().getName());

		holder.status_post_from.setText(JWBDataTransUtil.transWeiboSource(mList
				.get(position).getSource()));
		holder.status_post_time.setText(JWBDataTransUtil
				.transWeiboDateTime(mList.get(position).getCreated_at()));

		// 处理列表上加载的缩略图
		final String thumbnail_pic_url = mList.get(position).getThumbnail_pic();

		if (StringUtils.isNotEmpty(thumbnail_pic_url)) {
			holder.status_thumbnail_pic.setVisibility(View.VISIBLE);
			holder.status_thumbnail_pic.setTag(thumbnail_pic_url);

			if (mDownloader == null) {
				mDownloader = new JWBImageDownloader();
			}
			// 这句代码的作用是为了解决convertView被重用的时候，图片预设的问题
			holder.status_thumbnail_pic
					.setImageResource(R.drawable.ic_launcher);

			if (mDownloader != null) {
				// 异步下载图片
				mDownloader.doImageDownload(thumbnail_pic_url,
						holder.status_thumbnail_pic, new OnImageDownload() {
							@Override
							public void onDownloadSucc(Bitmap bitmap,
									String c_url) {
								ImageView imageView = (ImageView) mListView
										.findViewWithTag(c_url);
								if (imageView != null) {
									imageView.setImageBitmap(bitmap);
									imageView.setTag("");
								}
							}
						});
			}
		}
		else{
			holder.status_thumbnail_pic.setVisibility(View.GONE);
		}

		return convertView;
	}

	public List<JWBStatus> getList() {
		return mList;
	}

	public void setList(List<JWBStatus> list) {
		this.mList = list;
	}

	
}
