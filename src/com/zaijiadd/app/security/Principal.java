package com.zaijiadd.app.security;

import com.zaijiadd.app.user.entity.UserInfoEntity;

import java.io.Serializable;

/**
 * 授权用户对象
 */
public class Principal implements Serializable {
    private static final long serialVersionUID = -3474237226178990224L;

    private Integer userId;
    private String account;
    private Integer userType;

    public Principal() {}

	public Principal(UserInfoEntity userInfo) {
        this.userId = userInfo.getUserId();
        this.account = userInfo.getAccount();
        this.userType = userInfo.getUserType();
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
