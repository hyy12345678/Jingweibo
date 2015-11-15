package com.hyy.jingweibo.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hyy.jingweibo.generator.JWBStatus;
import com.hyy.jingweibo.generator.JWBUser;
import com.hyy.jingweibo.support.settinghelper.SettingUtility;

public class StatusListAdapter extends AbstractAppListAdapter {

	private WeakHashMap<ViewHolder, Drawable> bg = new WeakHashMap<ViewHolder, Drawable>();

	private LongSparseArray<Integer> msgHeights = new LongSparseArray<Integer>();
	private LongSparseArray<Integer> msgWidths = new LongSparseArray<Integer>();
	private LongSparseArray<Integer> oriMsgHeights = new LongSparseArray<Integer>();
	private LongSparseArray<Integer> oriMsgWidths = new LongSparseArray<Integer>();

	public StatusListAdapter(Fragment fragment, ListView listView,
			List<JWBStatus> bean, boolean showOriStatus) {
		super(fragment, listView, bean, showOriStatus);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindViewData(ViewHolder holder, int position) {

		Drawable drawable = bg.get(holder);
		if (drawable != null) {
			holder.listview_root.setBackgroundDrawable(drawable);
		} else {
			drawable = holder.listview_root.getBackground();
			bg.put(holder, drawable);
		}

		if (listView.getCheckedItemPosition() == position
				+ listView.getHeaderViewsCount()) {
			holder.listview_root.setBackgroundColor(checkedBG);
		}

		final JWBStatus msg = bean.get(position);

		JWBUser user = msg.getJWBUser();

		if (user != null) {
			holder.username.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(user.getRemark())) {
				holder.username
						.setText(new StringBuilder(user.getScreen_name())
								.append("(").append(user.getRemark())
								.append(")").toString());
			} else {
				holder.username.setText(user.getScreen_name());
			}
			if (!showOriStatus
					&& !SettingUtility.getEnableCommentRepostListAvatar()) {
				holder.avatar.setLayoutParams(new RelativeLayout.LayoutParams(
						0, 0));
			} else {
				buildAvatar(holder.avatar, position, user);
			}
		} else {
			holder.username.setVisibility(View.INVISIBLE);
			holder.avatar.setVisibility(View.INVISIBLE);
		}
		
		////////
		
		
		 if (!TextUtils.isEmpty(msg.getText())) {
	            boolean haveCachedHeight = msgHeights.get(msg.getId()) != null;
	            ViewGroup.LayoutParams layoutParams = holder.content.getLayoutParams();
	            if (haveCachedHeight) {
	                layoutParams.height = msgHeights.get(msg.getId());
	            } else {
	                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
	            }

	            boolean haveCachedWidth = msgWidths.get(msg.getId()) != null;
	            if (haveCachedWidth) {
	                layoutParams.width = msgWidths.get(msg.getId());
	            } else {
	                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
	            }

	            holder.content.requestLayout();
	            holder.content.setText(msg.getText());
	            if (!haveCachedHeight) {
	                msgHeights.append(msg.getId(), layoutParams.height);
	            }

	            if (!haveCachedWidth) {
	                msgWidths.append(msg.getId(), layoutParams.width);
	            }
	        } else {
	            //TimeLineUtility.addJustHighLightLinks(msg);
	            holder.content.setText(msg.getText());
	        }

        holder.time.setTime(msg.getMills());
        if (holder.source != null) {
            holder.source.setText(msg.getSource());
        }

        if (showOriStatus) {
            boolean checkRepostsCount = (msg.getReposts_count() != 0);
            boolean checkCommentsCount = (msg.getComments_count() != 0);
            boolean checkPic = (msg.getPic_urls()!=null
                    || ((msg.getJWBStatus() != null)
                    && (msg.getJWBStatus().getPic_urls() != null)));
            checkPic = (checkPic && !SettingUtility.isEnablePic());
            boolean checkGps = (msg.getJWBGeo() != null);

            if (!checkRepostsCount && !checkCommentsCount && !checkPic && !checkGps) {
                holder.count_layout.setVisibility(View.INVISIBLE);
            } else {
                holder.count_layout.setVisibility(View.VISIBLE);

                if (checkPic) {
                    holder.timeline_pic.setVisibility(View.VISIBLE);
                } else {
                    holder.timeline_pic.setVisibility(View.GONE);
                }

                if (checkGps) {
                    holder.timeline_gps.setVisibility(View.VISIBLE);
                } else {
                    holder.timeline_gps.setVisibility(View.INVISIBLE);
                }

                if (checkRepostsCount) {
                    holder.repost_count.setText(String.valueOf(msg.getReposts_count()));
                    holder.repost_count.setVisibility(View.VISIBLE);
                } else {
                    holder.repost_count.setVisibility(View.GONE);
                }

                if (checkCommentsCount) {
                    holder.comment_count.setText(String.valueOf(msg.getComments_count()));
                    holder.comment_count.setVisibility(View.VISIBLE);
                } else {
                    holder.comment_count.setVisibility(View.GONE);
                }
            }
        }

        holder.repost_content.setVisibility(View.GONE);
        holder.repost_content_pic.setVisibility(View.GONE);
        holder.repost_content_pic_multi.setVisibility(View.GONE);

        holder.content_pic.setVisibility(View.GONE);
        holder.content_pic_multi.setVisibility(View.GONE);

        if (msg.getPic_urls() != null) {
            if (isMultiPic(msg)) {
                buildMultiPic(msg, holder.content_pic_multi);
            } else {
                buildPic(msg, holder.content_pic, position);
            }
        }

        JWBStatus repost_msg = msg.getJWBStatus();

        if (repost_msg != null && showOriStatus) {
            if (holder.repost_layout != null) {
                holder.repost_layout.setVisibility(View.VISIBLE);
            }
            holder.repost_flag.setVisibility(View.VISIBLE);
            //sina weibo official account can send repost message with picture, fuck sina weibo
            if (holder.content_pic.getVisibility() != View.GONE) {
                holder.content_pic.setVisibility(View.GONE);
            }
            buildRepostContent(msg, repost_msg, holder, position);

            if (holder.content_pic_multi != holder.repost_content_pic_multi) {
                interruptPicDownload(holder.content_pic_multi);
            }
            if (holder.repost_content_pic != holder.content_pic) {
                interruptPicDownload(holder.repost_content_pic);
            }
        } else {
            if (holder.content_pic_multi != holder.repost_content_pic_multi) {
                interruptPicDownload(holder.repost_content_pic_multi);
            }
            if (holder.repost_content_pic != holder.content_pic) {
                interruptPicDownload(holder.repost_content_pic);
            }
            if (holder.repost_layout != null) {
                holder.repost_layout.setVisibility(View.GONE);
            }
            holder.repost_flag.setVisibility(View.GONE);
        }

        boolean interruptPic = false;
        boolean interruptMultiPic = false;
        boolean interruptRepostPic = false;
        boolean interruptRepostMultiPic = false;

        if (msg.getPic_urls() != null) {
            if (isMultiPic(msg) ) {
                interruptPic = true;
            } else {
                interruptMultiPic = true;
            }
        }

        if (repost_msg != null && showOriStatus) {

            if (repost_msg.getPic_urls() != null) {
                if (isMultiPic(repost_msg)) {
                    interruptRepostPic = true;
                } else {
                    interruptRepostMultiPic = true;
                }
            }
        }

        if (interruptPic && interruptRepostPic) {
            interruptPicDownload(holder.content_pic);
            interruptPicDownload(holder.repost_content_pic);
        }

        if (interruptMultiPic && interruptRepostMultiPic) {
            interruptPicDownload(holder.content_pic_multi);
            interruptPicDownload(holder.repost_content_pic_multi);
        }

        if (interruptPic && !interruptRepostPic) {
            if (holder.content_pic != holder.repost_content_pic) {
                interruptPicDownload(holder.content_pic);
            }
        }

        if (!interruptPic && interruptRepostPic) {
            if (holder.content_pic != holder.repost_content_pic) {
                interruptPicDownload(holder.repost_content_pic);
            }
        }

        if (interruptMultiPic && !interruptRepostMultiPic) {
            if (holder.content_pic_multi != holder.repost_content_pic_multi) {
                interruptPicDownload(holder.content_pic_multi);
            }
        }

        if (!interruptMultiPic && interruptRepostMultiPic) {
            if (holder.content_pic_multi != holder.repost_content_pic_multi) {
                interruptPicDownload(holder.repost_content_pic_multi);
            }
        }
		
		////////

	}

	@Override
	protected void bindOnTouchListener(ViewHolder holder) {
		// TODO Auto-generated method stub

	}

	private void buildRepostContent(JWBStatus msg, final JWBStatus repost_msg,
			ViewHolder holder, int position) {
		holder.repost_content.setVisibility(View.VISIBLE);
		if (!repost_msg.getId().equals(holder.repost_content.getTag())) {
			boolean haveCachedHeight = oriMsgHeights.get(msg.getId()) != null;
			ViewGroup.LayoutParams layoutParams = holder.repost_content
					.getLayoutParams();
			if (haveCachedHeight) {
				layoutParams.height = oriMsgHeights.get(msg.getId());
			} else {
				layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			}

			boolean haveCachedWidth = oriMsgWidths.get(msg.getId()) != null;
			if (haveCachedWidth) {
				layoutParams.width = oriMsgWidths.get(msg.getId());
			} else {
				layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
			}

			holder.repost_content.requestLayout();
			holder.repost_content.setText(repost_msg
					.getText());

			if (!haveCachedHeight) {
				oriMsgHeights.append(msg.getId(), layoutParams.height);
			}

			if (!haveCachedWidth) {
				oriMsgWidths.append(msg.getId(), layoutParams.width);
			}

			holder.repost_content.setText(repost_msg
					.getText());
			holder.repost_content.setTag(repost_msg.getId());
		}

		if (repost_msg.getPic_urls() != null ) {
			if (isMultiPic(repost_msg)) {
				buildMultiPic(repost_msg, holder.repost_content_pic_multi);
			} else {
				buildPic(repost_msg, holder.repost_content_pic, position);
			}
		}
	}

}
