package com.hyy.jingweibo.database;

import java.util.List;

import com.hyy.jingweibo.generator.DaoMaster;
import com.hyy.jingweibo.generator.DaoMaster.DevOpenHelper;
import com.hyy.jingweibo.generator.DaoSession;
import com.hyy.jingweibo.generator.JWBAccountDao;
import com.hyy.jingweibo.generator.JWBDownloadPicsDao;
import com.hyy.jingweibo.generator.JWBStatus;
import com.hyy.jingweibo.generator.JWBStatusDao;
import com.hyy.jingweibo.generator.JWBStatusDao.Properties;
import com.hyy.jingweibo.generator.JWBUser;
import com.hyy.jingweibo.generator.JWBUserDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseWorkGreenDao implements IDatabaseWork {

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private JWBStatusDao statusDao;
	private JWBUserDao userDao;
	private JWBAccountDao accountDao;
	private JWBDownloadPicsDao downloadPicDao;

	public DataBaseWorkGreenDao(Context context) {
		// TODO Auto-generated constructor stub
		DevOpenHelper openHelper = new DevOpenHelper(context, "jingweibo-db",
				null);
		db = openHelper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		statusDao = daoSession.getJWBStatusDao();
		userDao = daoSession.getJWBUserDao();
		accountDao = daoSession.getJWBAccountDao();
		downloadPicDao = daoSession.getJWBDownloadPicsDao();
	}

	@Override
	public Object getDB() {
		// TODO Auto-generated method stub
		return db;
	}

	public void saveOrUpdateStatus(List<JWBStatus> statusList) {
		statusDao.insertOrReplaceInTx(statusList);
	}

	public List<JWBStatus> queryStatusAll() {
		return statusDao.queryBuilder().orderDesc(Properties.Id).list();

	}

	public List<JWBStatus> queryStatusAllByUser(String userId) {

		return statusDao.queryBuilder().orderDesc(Properties.Id)
				.where(Properties.Received_user_id.eq(userId)).list();

	}

	public JWBStatus queryStatusById(Long id) {
		return statusDao.load(id);
	}

	public void saveOrUpdateUser(List<JWBUser> userList) {
		userDao.insertOrReplaceInTx(userList);
	}

	@Override
	public DaoSession getDaoSession() {
		// TODO Auto-generated method stub
		return daoSession;
	}

	@Override
	public JWBStatusDao getStatusDao() {
		// TODO Auto-generated method stub
		return statusDao;
	}

	@Override
	public JWBUserDao getUserDao() {
		// TODO Auto-generated method stub
		return userDao;
	}

	@Override
	public JWBAccountDao getAccountDao() {
		// TODO Auto-generated method stub
		return accountDao;
	}

	@Override
	public JWBDownloadPicsDao getDownloadPicsDao() {
		// TODO Auto-generated method stub
		return downloadPicDao;
	}
	
	
}
