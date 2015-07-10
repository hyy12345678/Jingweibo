package com.hyy.jingweibo.support.settinghelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 
 * @author hyylj
 * @data 15-5-3
 */
public class SettingHelper {
	private static SharedPreferences.Editor editor = null;
	private static SharedPreferences sharedPreferences = null;

	private SettingHelper() {

	}
	
	

	public static int getSharedPreferences(Context paramContext,
			String paramString, int paramInt) {
		return getSharedPreferencesObject(paramContext).getInt(paramString,
				paramInt);
	}

	public static Boolean getSharedPreferences(Context paramContext,
			String paramString, Boolean paramBoolean) {
		return getSharedPreferencesObject(paramContext).getBoolean(paramString,
				paramBoolean);
	}
	
	public static long getSharedPreferences(Context paramContext, String paramString,
            long paramLong) {
        return getSharedPreferencesObject(paramContext).getLong(paramString, paramLong);
    }

	public static String getSharedPreferences(Context paramContext, String paramString1,
            String paramString2) {
        return getSharedPreferencesObject(paramContext).getString(paramString1, paramString2);
    }


	private static SharedPreferences getSharedPreferencesObject(
			Context paramContext) {
		if (null == sharedPreferences) {
			sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(paramContext);
		}
		return sharedPreferences;
	}

	private static SharedPreferences.Editor getEditorObject(Context paramContext) {
		if (editor == null) {
			editor = PreferenceManager
					.getDefaultSharedPreferences(paramContext).edit();
		}
		return editor;
	}

	public static void setEditor(Context paramContext, String paramString,
			int paramInt) {
		getEditorObject(paramContext).putInt(paramString, paramInt).commit();
	}

	public static void setEditor(Context paramContext, String paramString,
			long paramLong) {
		getEditorObject(paramContext).putLong(paramString, paramLong).commit();
	}

	public static void setEditor(Context paramContext, String paramString,
			Boolean paramBoolean) {
		getEditorObject(paramContext).putBoolean(paramString, paramBoolean)
				.commit();
	}

	public static void setEditor(Context paramContext, String paramString1,
			String paramString2) {
		getEditorObject(paramContext).putString(paramString1, paramString2)
				.commit();
	}

}
