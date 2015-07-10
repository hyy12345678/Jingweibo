package com.hyy.jingweibo.support.constant;

public enum JWBWeiboApiType {
	 
	/* 获取当前登录用户及其所关注用户的最新微博。*/
	FRIENDS_TIME_LINE,
	/* 获取最新的提到登录用户的微博列表，即@我的微博*/
	MENTIONS,
	/* 发布一条新微博 */
	UPDATE,
	/* 上传图片并发布一条新微博 */
	UPLOAD,
	/* 指定一个图片URL地址抓取后上传并同时发布一条新微博，此方法会处理URLencod */
	UPLOAD_URL_TEXT,
	
	
}
