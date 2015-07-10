package com.hyy.jingweibo.ui.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Splitter;
import com.hyy.jingweibo.R;
import com.hyy.jingweibo.generator.JWBStatus;
import com.hyy.jingweibo.generator.JWBUser;
import com.hyy.jingweibo.support.asyncdrawable.IJingweiboDrawable;
import com.hyy.jingweibo.support.asyncdrawable.IPictureWorker;
import com.hyy.jingweibo.support.asyncdrawable.PictureBitmapDrawable;
import com.hyy.jingweibo.support.asyncdrawable.TimeLineBitmapDownloader;
import com.hyy.jingweibo.support.file.FileLocationMethod;
import com.hyy.jingweibo.support.lib.MyAsyncTask;
import com.hyy.jingweibo.support.lib.TimeLineAvatarImageView;
import com.hyy.jingweibo.support.lib.TimeTextView;
import com.hyy.jingweibo.support.settinghelper.SettingUtility;
import com.hyy.jingweibo.support.utils.ThemeUtility;
import com.hyy.jingweibo.support.utils.Utility;
import com.hyy.jingweibo.support.utils.ViewUtility;
import com.hyy.jingweibo.ui.basefragment.OldAbstractTimeLineFragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public abstract class AbstractAppListAdapter extends BaseAdapter {

	protected List<JWBStatus> bean;
	protected Fragment fragment;
	protected LayoutInflater inflater;
	protected ListView listView;
	protected boolean showOriStatus = true;

	protected int checkedBG;
	protected int defaultBG;

	public static final int NO_ITEM_ID = -1;

	public AbstractAppListAdapter(Fragment fragment, ListView listView,
			List<JWBStatus> bean, boolean showOriStatus) {

		if (showOriStatus) {
			listView.setDivider(null);
		}

		this.bean = bean;
		this.inflater = fragment.getActivity().getLayoutInflater();
		this.listView = listView;

		this.fragment = fragment;
		this.showOriStatus = showOriStatus;

		defaultBG = fragment.getResources().getColor(R.color.transparent);
		checkedBG = ThemeUtility.getColor(R.attr.listview_checked_color);
	}

	private List<JWBStatus> getList() {
		return bean;
	}

	public void setList(List<JWBStatus> list) {
		this.bean = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (getList() != null) {
			return getList().size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position >= 0 && getList() != null && getList().size() > 0
				&& position < getList().size()) {
			return getList().get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (getList() != null && getList().get(position) != null
				&& getList().size() > 0 && position < getList().size()) {
			return Long.valueOf(getList().get(position).getId());
		} else {
			return NO_ITEM_ID;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = initNormalLayout(parent);
			holder = buildHolder(convertView);

			// configViewFont(holder);
			// bindViewData(holder, position);
			// bindOnTouchListener(holder);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		configViewFont(holder);
		bindViewData(holder, position);
		bindOnTouchListener(holder);

		return convertView;
	}

	private void configViewFont(ViewHolder holder) {

		int prefFontSizeSp = SettingUtility.getFontSize();
		float currentWidgetTextSizePx;

		currentWidgetTextSizePx = holder.time.getTextSize();

		if (Utility.sp2px(prefFontSizeSp - 3) != currentWidgetTextSizePx) {
			holder.time.setTextSize(prefFontSizeSp - 3);
			if (holder.source != null) {
				holder.source.setTextSize(prefFontSizeSp - 3);
			}
		}

		currentWidgetTextSizePx = holder.content.getTextSize();

		if (Utility.sp2px(prefFontSizeSp) != currentWidgetTextSizePx) {
			holder.content.setTextSize(prefFontSizeSp);
			holder.username.setTextSize(prefFontSizeSp);
			holder.repost_content.setTextSize(prefFontSizeSp);
		}

		if (holder.repost_count != null) {
			currentWidgetTextSizePx = holder.repost_count.getTextSize();
			if (Utility.sp2px(prefFontSizeSp - 5) != currentWidgetTextSizePx) {
				holder.repost_count.setTextSize(prefFontSizeSp - 5);
			}
		}

		if (holder.comment_count != null) {
			currentWidgetTextSizePx = holder.comment_count.getTextSize();
			if (Utility.sp2px(prefFontSizeSp - 5) != currentWidgetTextSizePx) {
				holder.comment_count.setTextSize(prefFontSizeSp - 5);
			}
		}

	}

	protected abstract void bindOnTouchListener(ViewHolder holder);

	protected abstract void bindViewData(ViewHolder holder, int position);

	private View initNormalLayout(ViewGroup parent) {
		return inflater.inflate(R.layout.timeline_listview_item_layout, parent,
				false);
	}

	private ViewHolder buildHolder(View convertView) {

		ViewHolder holder = new ViewHolder();
		holder.username = ViewUtility.findViewById(convertView, R.id.username);
		TextPaint tp = holder.username.getPaint();
		if (tp != null) {
			tp.setFakeBoldText(true);
		}

		holder.content = ViewUtility.findViewById(convertView, R.id.content);
		holder.repost_content = ViewUtility.findViewById(convertView,
				R.id.repost_content);
		holder.time = ViewUtility.findViewById(convertView, R.id.time);
		holder.avatar = (TimeLineAvatarImageView) convertView
				.findViewById(R.id.avatar);

		holder.repost_content_pic = (IJingweiboDrawable) convertView
				.findViewById(R.id.repost_content_pic);
		holder.repost_content_pic_multi = ViewUtility.findViewById(convertView,
				R.id.repost_content__pic_multi);

		holder.content_pic = holder.repost_content_pic;
		holder.content_pic_multi = holder.repost_content_pic_multi;

		holder.listview_root = ViewUtility.findViewById(convertView,
				R.id.listview_root);
		// holder.repost_layout = ViewUtility.findViewById(convertView,
		// R.id.repost_layout);
		holder.repost_flag = ViewUtility.findViewById(convertView,
				R.id.repost_flag);
		holder.count_layout = ViewUtility.findViewById(convertView,
				R.id.count_layout);
		holder.repost_count = ViewUtility.findViewById(convertView,
				R.id.repost_count);
		holder.comment_count = ViewUtility.findViewById(convertView,
				R.id.comment_count);
		holder.timeline_gps = ViewUtility.findViewById(convertView,
				R.id.timeline_gps_iv);
		holder.timeline_pic = ViewUtility.findViewById(convertView,
				R.id.timeline_pic_iv);
		// holder.replyIV = ViewUtility.findViewById(convertView, R.id.replyIV);
		holder.source = ViewUtility.findViewById(convertView, R.id.source);
		return holder;

	}

	protected void buildAvatar(IJingweiboDrawable view, int position,
			final JWBUser user) {
		view.setVisibility(View.VISIBLE);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(getActivity(),
				// UserInfoActivity.class);
				// intent.putExtra("token",
				// GlobalContext.getInstance().getSpecialToken());
				// intent.putExtra("user", user);
				// getActivity().startActivity(intent);
			}
		});
		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// UserDialog dialog = new UserDialog(user);
				// dialog.show(fragment.getFragmentManager(), "");
				return true;
			}
		});
		view.checkVerified(user);
		buildAvatar(view.getImageView(), position, user);
	}

	protected void buildAvatar(ImageView view, int position, final JWBUser user) {
		String image_url = user.getProfile_image_url();
		if (!TextUtils.isEmpty(image_url)) {
			view.setVisibility(View.VISIBLE);
			TimeLineBitmapDownloader.getInstance().downloadAvatar(view, user,
					(OldAbstractTimeLineFragment) fragment);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	protected void buildPic(final JWBStatus msg, final IJingweiboDrawable view,
			int position) {
		if (SettingUtility.isEnablePic()) {
			view.setVisibility(View.VISIBLE);
			// view.setOnClickListener(new View.OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// ImageView imageView = view.getImageView();
			// AnimationRect rect = AnimationRect.buildFromImageView(imageView);
			// ArrayList<AnimationRect> animationRectArrayList
			// = new ArrayList<AnimationRect>();
			// animationRectArrayList.add(rect);
			//
			// Intent intent = GalleryAnimationActivity
			// .newIntent(msg, animationRectArrayList, 0);
			// getActivity().startActivity(intent);
			// }
			// });
			buildPic(msg, view);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	private void buildPic(final JWBStatus msg, IJingweiboDrawable view) {
		view.setVisibility(View.VISIBLE);
		TimeLineBitmapDownloader.getInstance().downContentPic(view, msg,
				(OldAbstractTimeLineFragment) fragment);
	}

	protected void buildMultiPic(final JWBStatus msg,
			final GridLayout gridLayout) {
		if (SettingUtility.isEnablePic()) {
			gridLayout.setVisibility(View.VISIBLE);

			final int count = countPic(msg);
			for (int i = 0; i < count; i++) {
				final IJingweiboDrawable pic = (IJingweiboDrawable) gridLayout
						.getChildAt(i);
				pic.setVisibility(View.VISIBLE);
				if (SettingUtility.getEnableBigPic()) {
					TimeLineBitmapDownloader.getInstance().displayMultiPicture(
							pic, getPicByIndex(msg, i),
							FileLocationMethod.picture_large,
							(OldAbstractTimeLineFragment) fragment);
				} else {
					TimeLineBitmapDownloader.getInstance().displayMultiPicture(
							pic, getPicByIndex(msg, i),
							FileLocationMethod.picture_thumbnail,
							(OldAbstractTimeLineFragment) fragment);
				}

				// final int finalI = i;
				// pic.setOnClickListener(new View.OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// ArrayList<AnimationRect> animationRectArrayList = new
				// ArrayList<AnimationRect>();
				// for (int i = 0; i < count; i++) {
				// final IWeiciyuanDrawable pic = (IWeiciyuanDrawable)
				// gridLayout
				// .getChildAt(i);
				// ImageView imageView = (ImageView) pic;
				// if (imageView.getVisibility() == View.VISIBLE) {
				// AnimationRect rect = AnimationRect
				// .buildFromImageView(imageView);
				// animationRectArrayList.add(rect);
				// }
				// }
				//
				// Intent intent = GalleryAnimationActivity.newIntent(msg,
				// animationRectArrayList, finalI);
				// getActivity().startActivity(intent);
				// }
				// });
			}

			if (count < 9) {
				ImageView pic;
				switch (count) {
				case 8:
					pic = (ImageView) gridLayout.getChildAt(8);
					pic.setVisibility(View.INVISIBLE);
					break;
				case 7:
					for (int i = 8; i > 6; i--) {
						pic = (ImageView) gridLayout.getChildAt(i);
						pic.setVisibility(View.INVISIBLE);
					}
					break;
				case 6:
					for (int i = 8; i > 5; i--) {
						pic = (ImageView) gridLayout.getChildAt(i);
						pic.setVisibility(View.GONE);
					}

					break;
				case 5:
					for (int i = 8; i > 5; i--) {
						pic = (ImageView) gridLayout.getChildAt(i);
						pic.setVisibility(View.GONE);
					}
					pic = (ImageView) gridLayout.getChildAt(5);
					pic.setVisibility(View.INVISIBLE);
					break;
				case 4:
					for (int i = 8; i > 5; i--) {
						pic = (ImageView) gridLayout.getChildAt(i);
						pic.setVisibility(View.GONE);
					}
					pic = (ImageView) gridLayout.getChildAt(5);
					pic.setVisibility(View.INVISIBLE);
					pic = (ImageView) gridLayout.getChildAt(4);
					pic.setVisibility(View.INVISIBLE);
					break;
				case 3:
					for (int i = 8; i > 2; i--) {
						pic = (ImageView) gridLayout.getChildAt(i);
						pic.setVisibility(View.GONE);
					}
					break;
				case 2:
					for (int i = 8; i > 2; i--) {
						pic = (ImageView) gridLayout.getChildAt(i);
						pic.setVisibility(View.GONE);
					}
					pic = (ImageView) gridLayout.getChildAt(2);
					pic.setVisibility(View.INVISIBLE);
					break;
				}
			}
		} else {
			gridLayout.setVisibility(View.GONE);
		}
	}

	public static class ViewHolder {
		TextView username;
		TextView content;
		TextView repost_content;
		TimeTextView time;
		IJingweiboDrawable avatar;
		IJingweiboDrawable content_pic;
		GridLayout content_pic_multi;
		IJingweiboDrawable repost_content_pic;
		GridLayout repost_content_pic_multi;
		ViewGroup listview_root;
		View repost_layout;
		View repost_flag;
		LinearLayout count_layout;
		TextView repost_count;
		TextView comment_count;
		TextView source;
		ImageView timeline_gps;
		ImageView timeline_pic;
		ImageView replyIV;
	}

	protected void interruptPicDownload(IJingweiboDrawable view) {
		Drawable drawable = view.getImageView().getDrawable();
		if (drawable instanceof PictureBitmapDrawable) {
			PictureBitmapDrawable downloadedDrawable = (PictureBitmapDrawable) drawable;
			IPictureWorker worker = downloadedDrawable
					.getBitmapDownloaderTask();
			if (worker != null) {
				((MyAsyncTask) worker).cancel(true);
			}
		}
		view.getImageView().setImageDrawable(null);
	}

	protected void interruptPicDownload(GridLayout gridLayout) {
		for (int i = 0; i < gridLayout.getChildCount(); i++) {
			ImageView iv = (ImageView) gridLayout.getChildAt(i);
			if (iv != null) {
				Drawable drawable = iv.getDrawable();
				if (drawable instanceof PictureBitmapDrawable) {
					PictureBitmapDrawable downloadedDrawable = (PictureBitmapDrawable) drawable;
					IPictureWorker worker = downloadedDrawable
							.getBitmapDownloaderTask();
					if (worker != null) {
						((MyAsyncTask) worker).cancel(true);
					}
					iv.setImageDrawable(null);
				}
			}
		}
	}

	protected List<String> getPicUrlList(JWBStatus msg) {
		String pic_url = msg.getPic_urls();
		Iterable<String> iterable = Splitter.on("]").split(pic_url);
		List<String> splitedList = convertToList(iterable);
		return splitedList;
	}

	protected int countPic(JWBStatus msg) {

		return getPicUrlList(msg).size();
	}

	protected boolean isMultiPic(JWBStatus msg) {

		if (countPic(msg) > 1) {
			return true;
		}

		return false;
	}

	protected List<String> convertToList(Iterable<String> iterable) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		Iterator<String> it = iterable.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}

		return list;
	}

	protected String getPicByIndex(JWBStatus msg, int i) {
		// TODO Auto-generated method stub

		List<String> pics = getPicUrlList(msg);

		return pics.get(i);
	}
}
