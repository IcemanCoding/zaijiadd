package com.zaijiadd.app.user.dto;

import java.io.Serializable;

public class UserStoreDTO implements Serializable {

	private static final long serialVersionUID = -6484899072933554738L;

	private Integer id;
	
	private Integer userId;
	
	private Integer storeId;
	
	private String storeName;
	
	private String storeAddr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddr() {
		return storeAddr;
	}

	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}
	
}
