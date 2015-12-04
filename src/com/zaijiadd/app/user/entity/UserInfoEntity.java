package com.zaijiadd.app.user.entity;

import java.util.Date;
import java.util.List;

public class UserInfoEntity {
	private Integer userId;//user_id;
	private Integer zaijiaaddId;//zaijiadd_id
	private String mobileNumber;//mobile_number
	private Integer userType;//user_type
	private Date createDate;//create_date
	private Date updateDate;//update_date
	private String password;
	private String account;
	private Integer loginiStatus;//登录状态： 0-离线  1-在线
	private String email;
	private String pic;
	private Date lastLoginTime;//last_login_time
	
	private List<UserSendScopeEntity> userSendScopeList;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getZaijiaaddId() {
		return zaijiaaddId;
	}
	public void setZaijiaaddId(Integer zaijiaaddId) {
		this.zaijiaaddId = zaijiaaddId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getLoginiStatus() {
		return loginiStatus;
	}
	public void setLoginiStatus(Integer loginiStatus) {
		this.loginiStatus = loginiStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public List<UserSendScopeEntity> getUserSendScopeList() {
		return userSendScopeList;
	}
	public void setUserSendScopeList(List<UserSendScopeEntity> userSendScopeList) {
		this.userSendScopeList = userSendScopeList;
	}

}
