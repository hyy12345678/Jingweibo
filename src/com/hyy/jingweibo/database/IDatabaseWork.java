package com.hyy.jingweibo.database;

import java.util.List;

import com.hyy.jingweibo.generator.DaoSession;
import com.hyy.jingweibo.generator.JWBAccount;
import com.hyy.jingweibo.generator.JWBAccountDao;
import com.hyy.jingweibo.generator.JWBDownloadPicsDao;
import com.hyy.jingweibo.generator.JWBStatus;
import com.hyy.jingweibo.generator.JWBStatusDao;
import com.hyy.jingweibo.generator.JWBUser;
import com.hyy.jingweibo.generator.JWBUserDao;

public interface IDatabaseWork {
	
	/***
	 * return Session for db
	 * @return
	 */
	Object getDB();
	
	/***
	 * status更新／保存
	 */
	public void saveOrUpdateStatus(List<JWBStatus> statusList);
	
	/***
	 * 获取所有微博
	 * @return
	 */
	public List<JWBStatus> queryStatusAll();
	
	
	/***
	 * 获取当前登录用户在本地存储的所有微博
	 * @return
	 */
	public List<JWBStatus> queryStatusAllByUser(String userId);
	
	/***
	 * 获取指定ID的微博
	 * @return
	 */
	public JWBStatus queryStatusById(Long id);
	
	/***
	 * user 更新／保存
	 * @param userList
	 */
	public void saveOrUpdateUser(List<JWBUser> userList);
	
	/***
	 * 获取DaoSession
	 * @return
	 */
	public DaoSession getDaoSession();
	
	/***
	 * 获取StatusDao
	 * @return
	 */
	public JWBStatusDao getStatusDao();
	
	/***
	 * 获取UserDao
	 * @return
	 */
	public JWBUserDao getUserDao();
	
	/***
	 * 获取AccountDao
	 * @return
	 */
	public JWBAccountDao getAccountDao();
	
	public JWBDownloadPicsDao getDownloadPicsDao();
	
}
