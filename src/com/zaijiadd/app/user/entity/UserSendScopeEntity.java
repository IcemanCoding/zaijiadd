package com.zaijiadd.app.user.entity;

public class UserSendScopeEntity {

	private Integer id;
	private Integer userId;
	private String provinceId;
	private String cityId;
	private String townId;
	private String sendScope;
	
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
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getTownId() {
		return townId;
	}
	public void setTownId(String townId) {
		this.townId = townId;
	}
	public String getSendScope() {
		return sendScope;
	}
	public void setSendScope(String sendScope) {
		this.sendScope = sendScope;
	}

}
