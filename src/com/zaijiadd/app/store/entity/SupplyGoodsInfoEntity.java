package com.zaijiadd.app.store.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SupplyGoodsInfoEntity implements java.io.Serializable{
	private static final long serialVersionUID = -2717420313910911363L;
	
	private Integer id;
	private Integer goodsInfoId;//产品id
	private Double price;
	private Integer stock;//库存有量
	private Integer status;//产品状态 -1:下架 1:在售
	private Integer userStoreId;//供应商 商店id
	private Integer saleCount;
	
	private GoodsInfoEntity goodsInfo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGoodsInfoId() {
		return goodsInfoId;
	}
	public void setGoodsInfoId(Integer goodsInfoId) {
		this.goodsInfoId = goodsInfoId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUserStoreId() {
		return userStoreId;
	}
	public void setUserStoreId(Integer userStoreId) {
		this.userStoreId = userStoreId;
	}
	public Integer getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}
	public GoodsInfoEntity getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(GoodsInfoEntity goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

}
