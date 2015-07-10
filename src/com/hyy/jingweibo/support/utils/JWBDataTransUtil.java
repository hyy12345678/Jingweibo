package com.hyy.jingweibo.support.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.google.common.base.Joiner;
import com.hyy.jingweibo.generator.JWBGeo;
import com.hyy.jingweibo.generator.JWBStatus;
import com.hyy.jingweibo.generator.JWBUser;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.keeper.AccountInfoKeeper;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.openapi.models.User;

/***
 * jingweibo 数据类型转换工具类
 * 
 * @author hyylj
 * 
 */
public class JWBDataTransUtil {

	/***
	 * 转换微博来源为显示字符串
	 * 
	 * @param origin
	 * @return
	 */
	public static String transWeiboSource(String origin) {

		String ret = "";
		if (StringUtils.isNotEmpty(origin)) {
			String[] strs = StringUtils.split(origin, '>');
			ret = StringUtils.substringBefore(strs[1], "<");
		}

		return ret;

	}
	
	public static Long tranWeiboCreateTime(String ori){
		String pattern = "EEE MMM d HH:mm:ss Z yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
		
		
		try {
			Date d = sdf.parse(ori);
			return d.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/***
	 * 转换微博时间为显示字符串
	 * 
	 * @param origin
	 * @return
	 * 
	 */
	public static String transWeiboDateTime(String origin) {

		/*
		 * For example: Wed Mar 24 14:57:34 +0800 2010
		 */
		String pattern = "EEE MMM d HH:mm:ss Z yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);

		try {

			// 转换后的Date对象
			Date d = sdf.parse(origin);

			Date now = new Date();

			boolean isSameDay = DateUtils.isSameDay(d, now);
			boolean isInFuture = d.after(now);
			boolean isYesterday = isYesterDay(d);

			if (isInFuture) {
				// 未来的某个时间
				return "刚刚";

			} else if (isSameDay) {
				// 当天，并且早于当前时间
				String ret = "刚刚";

				if ((now.getHours() - d.getHours()) != 0) {
					// 相差一小时及以上
					ret = (now.getHours() - d.getHours()) + "小时前";
				} else if ((now.getMinutes() - d.getMinutes()) != 0) {
					// 相差一分钟及以上
					ret = (now.getMinutes() - d.getMinutes()) + "分钟前";
				} else if ((now.getSeconds() - d.getSeconds()) != 0) {
					// 相差一秒钟及以上
					ret = (now.getSeconds() - d.getSeconds()) + "秒前";
				}

				return ret;

			} else if (isYesterday) {
				// 在昨天
				return "昨天"
						+ d.getHours()
						+ ":"
						+ ((d.getMinutes() > 9) ? d.getMinutes() : ("0" + d
								.getMinutes()));

			} else {
				// 在前天以及以前
				return (d.getMonth() + 1) + "月" + d.getDate() + "日";
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/***
	 * 判断是否是昨天
	 * 
	 * @param dest
	 * @return
	 */
	private static boolean isYesterDay(Date dest) {

		Date ceilToday = DateUtils.ceiling(new Date(), Calendar.DATE);
		long yesterdayEnd = ceilToday.getTime() - DateUtils.MILLIS_PER_DAY;
		long yesterdayBegin = ceilToday.getTime() - 2
				* DateUtils.MILLIS_PER_DAY;

		if (dest.getTime() > yesterdayBegin && dest.getTime() < yesterdayEnd) {
			return true;
		}

		return false;
	}

	/***
	 * 类型转换 Status->JWBStatus
	 * 
	 * @param statuses
	 * @return List<JWBStatus>
	 */
	public static List<JWBStatus> transFromStatus2JWBStatus(StatusList statuses) {

		List<JWBStatus> jwbStatusList = new ArrayList<JWBStatus>();

		for (Status st : statuses.statusList) {

			JWBStatus jwb = new JWBStatus();
			jwb.setId(Long.valueOf(st.id));
			jwb.setCreated_at(st.created_at);
			jwb.setMid(st.mid);
			jwb.setIdstr(st.idstr);
			jwb.setSource(st.source);
			jwb.setText(st.text);

			jwb.setFavorited(st.favorited);
			jwb.setTruncated(st.truncated);
			jwb.setIn_reply_to_status_id(st.in_reply_to_status_id);
			jwb.setIn_reply_to_user_id(st.in_reply_to_user_id);
			jwb.setIn_reply_to_screen_name(st.in_reply_to_screen_name);

			jwb.setThumbnail_pic(st.thumbnail_pic);
			jwb.setBmiddle_pic(st.bmiddle_pic);
			jwb.setOriginal_pic(st.original_pic);

			/***
			 * TODO GEO相关定义和操作后续补充
			 */

			JWBGeo geo = new JWBGeo();
			if (st.geo != null) {
				geo.setLongitude(st.geo.longitude);
				geo.setLatitude(st.geo.latitude);
				geo.setAddress(st.geo.address);
				geo.setCity(st.geo.city);
				geo.setCity_name(st.geo.city_name);
				geo.setProvince(st.geo.province);
				geo.setProvince_name(st.geo.province_name);
				geo.setPinyin(st.geo.pinyin);
				geo.setMore(st.geo.more);
			} else {
				geo.setLongitude("34.7462190000");
				geo.setLatitude("113.6994100000");
				geo.setAddress("河南省郑州市管城回族区陇海东路");
				geo.setCity("zz");
				geo.setCity_name("郑州");
				geo.setProvince("hn");
				geo.setProvince_name("河南");
				geo.setPinyin("河南郑州");
				geo.setMore("more");
			}

			jwb.setJWBGeo(geo);

			jwb.setStatus_user_id(Long.valueOf(st.user.id));
			if (st.retweeted_status != null) {
				jwb.setRetweeted_status_id(Long.valueOf(st.retweeted_status.id));
			}

			jwb.setReposts_count(st.reposts_count);
			jwb.setComments_count(st.comments_count);
			jwb.setAttitudes_count(st.attitudes_count);
			jwb.setMlevel(st.mlevel);

			// 使用“]”作为分隔符
			jwb.setVisible(Joiner.on("]").skipNulls()
					.join(st.visible.type, st.visible.list_id));

			// 使用“]”作为分隔符
			if (null != st.pic_urls) {
				jwb.setPic_urls(Joiner.on("]").skipNulls().join(st.pic_urls));
			}
			jwb.setReceived_user_id(AccountInfoKeeper.readAuthInfoToken(
					GlobalContext.getInstance()).getAutherId());

			jwb.setMills(tranWeiboCreateTime(st.created_at));
			
			jwbStatusList.add(jwb);

		}

		return jwbStatusList;

	}

	/***
	 * 获取StatusList中每个Status的关联Status（如果有的话）
	 * 
	 * @return
	 */
	public static List<JWBStatus> digRetweetedStatus(StatusList statuses) {

		List<JWBStatus> jwbStatusList = new ArrayList<JWBStatus>();
		for (Status status : statuses.statusList) {
			if (status.retweeted_status != null) {
				Status st = status.retweeted_status;

				JWBStatus jwb = new JWBStatus();
				jwb.setId(Long.valueOf(st.id));
				jwb.setCreated_at(st.created_at);
				jwb.setMid(st.mid);
				jwb.setIdstr(st.idstr);
				jwb.setSource(st.source);
				jwb.setText(st.text);

				jwb.setFavorited(st.favorited);
				jwb.setTruncated(st.truncated);
				jwb.setIn_reply_to_status_id(st.in_reply_to_status_id);
				jwb.setIn_reply_to_user_id(st.in_reply_to_user_id);
				jwb.setIn_reply_to_screen_name(st.in_reply_to_screen_name);

				jwb.setThumbnail_pic(st.thumbnail_pic);
				jwb.setBmiddle_pic(st.bmiddle_pic);
				jwb.setOriginal_pic(st.original_pic);

				/***
				 * TODO GEO相关定义和操作后续补充
				 */
				JWBGeo geo = new JWBGeo();
				if (st.geo != null) {
					geo.setLongitude(st.geo.longitude);
					geo.setLatitude(st.geo.latitude);
					geo.setAddress(st.geo.address);
					geo.setCity(st.geo.city);
					geo.setCity_name(st.geo.city_name);
					geo.setProvince(st.geo.province);
					geo.setProvince_name(st.geo.province_name);
					geo.setPinyin(st.geo.pinyin);
					geo.setMore(st.geo.more);
				}else{
					geo.setLongitude("34.7462190000");
					geo.setLatitude("113.6994100000");
					geo.setAddress("河南省郑州市管城回族区陇海东路");
					geo.setCity("zz");
					geo.setCity_name("郑州");
					geo.setProvince("hn");
					geo.setProvince_name("河南");
					geo.setPinyin("河南郑州");
					geo.setMore("more");
				}
				
				jwb.setJWBGeo(geo);

				jwb.setStatus_user_id(Long.valueOf(st.user.id));

				// 关联Status不会再次关联，这里不解析
				// jwb.setRetweeted_status_id(Long.valueOf(st.retweeted_status.id));

				jwb.setReposts_count(st.reposts_count);
				jwb.setComments_count(st.comments_count);
				jwb.setAttitudes_count(st.attitudes_count);
				jwb.setMlevel(st.mlevel);

				// 使用“]”作为分隔符
				jwb.setVisible(Joiner.on("]").skipNulls()
						.join(st.visible.type, st.visible.list_id));

				// 使用“]”作为分隔符
				if (null != st.pic_urls) {
					jwb.setPic_urls(Joiner.on("]").skipNulls()
							.join(st.pic_urls));
				}

				jwb.setReceived_user_id(AccountInfoKeeper.readAuthInfoToken(
						GlobalContext.getInstance()).getAutherId());

				jwb.setMills(tranWeiboCreateTime(st.created_at));
				
				jwbStatusList.add(jwb);
			}
		}

		return jwbStatusList;

	}

	/***
	 * 获取StatusList中相关User
	 * 
	 * @param user
	 * @return List<JWBUser>
	 */
	public static List<JWBUser> digJWBUser(StatusList statuses) {

		List<JWBUser> jwbUserList = new ArrayList<JWBUser>();
		for (Status status : statuses.statusList) {

			User user = status.user;
			JWBUser jwbuser = new JWBUser();
			jwbuser.setId(Long.valueOf(user.id));
			jwbuser.setIdstr(user.idstr);
			jwbuser.setScreen_name(user.screen_name);
			jwbuser.setName(user.name);
			jwbuser.setProvince(user.province);
			jwbuser.setCity(user.city);
			jwbuser.setLocation(user.location);
			jwbuser.setDescription(user.description);
			jwbuser.setUrl(user.url);
			jwbuser.setProfile_image_url(user.profile_image_url);
			jwbuser.setProfile_url(user.profile_url);
			jwbuser.setDomain(user.domain);
			jwbuser.setWeihao(user.weihao);
			jwbuser.setGender(user.gender);
			jwbuser.setFollowers_count(user.followers_count);
			jwbuser.setFriends_count(user.friends_count);
			jwbuser.setStatuses_count(user.statuses_count);
			jwbuser.setFavourites_count(user.favourites_count);
			jwbuser.setCreated_at(user.created_at);
			jwbuser.setFollowing(user.following);
			jwbuser.setAllow_all_act_msg(user.allow_all_act_msg);
			jwbuser.setGeo_enabled(user.geo_enabled);
			jwbuser.setVerified(user.verified);
			jwbuser.setVerified_type(user.verified_type);
			jwbuser.setRemark(user.remark);
			jwbuser.setAllow_all_comment(user.allow_all_comment);
			jwbuser.setAvatar_large(user.avatar_large);
			jwbuser.setAvatar_hd(user.avatar_hd);
			jwbuser.setVerified_reason(user.verified_reason);
			jwbuser.setFollow_me(user.follow_me);
			jwbuser.setOnline_status(user.online_status);
			jwbuser.setBi_followers_count(user.bi_followers_count);
			jwbuser.setLang(user.lang);
			jwbuser.setStar(user.star);
			jwbuser.setMbtype(user.mbtype);
			jwbuser.setMbrank(user.mbrank);
			jwbuser.setBlock_word(user.block_word);

			jwbUserList.add(jwbuser);

		}

		return jwbUserList;
	}

	/***
	 * 从关联Status列表中获取相关User
	 * 
	 * @param statusList
	 * @return
	 */
	public static List<JWBUser> digJWBUserFromRetStatusList(StatusList statuses) {

		List<JWBUser> jwbUserList = new ArrayList<JWBUser>();
		for (Status status : statuses.statusList) {
			if (status.retweeted_status != null) {
				Status restStatus = status.retweeted_status;

				User user = restStatus.user;
				JWBUser jwbuser = new JWBUser();
				jwbuser.setId(Long.valueOf(user.id));
				jwbuser.setIdstr(user.idstr);
				jwbuser.setScreen_name(user.screen_name);
				jwbuser.setName(user.name);
				jwbuser.setProvince(user.province);
				jwbuser.setCity(user.city);
				jwbuser.setLocation(user.location);
				jwbuser.setDescription(user.description);
				jwbuser.setUrl(user.url);
				jwbuser.setProfile_image_url(user.profile_image_url);
				jwbuser.setProfile_url(user.profile_url);
				jwbuser.setDomain(user.domain);
				jwbuser.setWeihao(user.weihao);
				jwbuser.setGender(user.gender);
				jwbuser.setFollowers_count(user.followers_count);
				jwbuser.setFriends_count(user.friends_count);
				jwbuser.setStatuses_count(user.statuses_count);
				jwbuser.setFavourites_count(user.favourites_count);
				jwbuser.setCreated_at(user.created_at);
				jwbuser.setFollowing(user.following);
				jwbuser.setAllow_all_act_msg(user.allow_all_act_msg);
				jwbuser.setGeo_enabled(user.geo_enabled);
				jwbuser.setVerified(user.verified);
				jwbuser.setVerified_type(user.verified_type);
				jwbuser.setRemark(user.remark);
				jwbuser.setAllow_all_comment(user.allow_all_comment);
				jwbuser.setAvatar_large(user.avatar_large);
				jwbuser.setAvatar_hd(user.avatar_hd);
				jwbuser.setVerified_reason(user.verified_reason);
				jwbuser.setFollow_me(user.follow_me);
				jwbuser.setOnline_status(user.online_status);
				jwbuser.setBi_followers_count(user.bi_followers_count);
				jwbuser.setLang(user.lang);
				jwbuser.setStar(user.star);
				jwbuser.setMbtype(user.mbtype);
				jwbuser.setMbrank(user.mbrank);
				jwbuser.setBlock_word(user.block_word);

				jwbUserList.add(jwbuser);
			}

		}

		return jwbUserList;
	}

}
