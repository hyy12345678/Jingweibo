package com.hyy.jingweibo.support.keeper;

/***
 * 保存用户信息的实体bean
 * @author hyylj
 *
 */
public class AccountInfoBean {
	
	//用户ID
	private String autherId;
	
	//用户名
	private String autherName;
	
	//用户头像地址
	private String autherProfile;
	
	//用户描述
	private String autherDescription;

	public String getAutherName() {
		return autherName;
	}

	public void setAutherName(String autherName) {
		this.autherName = autherName;
	}

	public String getAutherProfile() {
		return autherProfile;
	}

	public void setAutherProfile(String autherProfile) {
		this.autherProfile = autherProfile;
	}

	public String getAutherDescription() {
		return autherDescription;
	}

	public void setAutherDescription(String autherDescription) {
		this.autherDescription = autherDescription;
	}

	public String getAutherId() {
		return autherId;
	}

	public void setAutherId(String autherId) {
		this.autherId = autherId;
	}
	
	
	
	
}
