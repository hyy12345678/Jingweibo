package com.hyy.jingweibo.database;

import android.content.Context;

public class DatabaseManager {
	
	private static IDatabaseWork dbwork = null;
	
	private DatabaseManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static IDatabaseWork getInstance(Context context){
		if(null == dbwork){
			initDatabaseManager(context);
		}
		return dbwork;
	}
	
	public static void initDatabaseManager(Context context){
		if(null == dbwork && null != context){
			dbwork = new DataBaseWorkGreenDao(context);
		}
	}
}
