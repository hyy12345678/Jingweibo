package com.hyy.jingweibo.support.keeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/***
 * 该类定义了需要保存的登录用户信息
 * 
 * @author hyylj
 * 
 */
public class AccountInfoKeeper {

	private static final String PREFERENCES_NAME = "com_jingweibo_auther_info";

	// extends to auth info
	private static final String KEY_AUTH_ID = "auth_id";
	private static final String KEY_AUTH_PROFILE = "auth_profile";
	private static final String KEY_AUTH_NAME = "auth_name";
	private static final String KEY_AUTH_DESCRIPTION = "auth_description";

	public static void writeAuthInfoToken(Context context,AccountInfoBean token) {
	
		if(null == context || null == token){
			return;
		}
		
		SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = sp.edit();
		
		if(null != token.getAutherId()){
			editor.putString(KEY_AUTH_ID, token.getAutherId());
		}
		if(null != token.getAutherName()){
			editor.putString(KEY_AUTH_NAME, token.getAutherName());
		}
		
		if(null != token.getAutherProfile()){
			editor.putString(KEY_AUTH_PROFILE, token.getAutherProfile());
		}
		
		if(null != token.getAutherDescription()){
			editor.putString(KEY_AUTH_DESCRIPTION, token.getAutherDescription());
		}
		
		editor.commit();
		
	}
	
	public static AccountInfoBean readAuthInfoToken(Context context){
		
		if(null == context){
			return null;
		}
		
		SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		AccountInfoBean token = new AccountInfoBean();
		
		token.setAutherId(sp.getString(KEY_AUTH_ID, ""));
		token.setAutherName(sp.getString(KEY_AUTH_NAME, ""));
		token.setAutherProfile(sp.getString(KEY_AUTH_PROFILE, ""));
		token.setAutherDescription(sp.getString(KEY_AUTH_DESCRIPTION, ""));
		
		return token;
	}
	
	public static void clear(Context context){
		if(null == context){
			return;
		}
		
		SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor ed =sp.edit();
		ed.clear();
		ed.commit();
		
	}

}
