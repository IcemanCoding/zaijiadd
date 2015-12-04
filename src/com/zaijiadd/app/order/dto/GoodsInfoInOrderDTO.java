package com.zaijiadd.app.order.dto;

import java.io.Serializable;

public class GoodsInfoInOrderDTO implements Serializable {

	private static final long serialVersionUID = 1160712044112563423L;

	private Integer id;
	private String goodsID;//产品编号
	private Integer categoryId;//商品类别
	private String goodsName;//商品名
	private String description;//
	private String spec;//商品规格
	private String pic;//商品图片信息
	private String tag;//商品关键词
//	private Integer goodsStatus;//商品状态：-1 删除 1 正常
//	private Date createDate;//
//	private Date updateDate;//
//	private Integer appGoodsId;//
	
	private Integer goodsInfoId;//产品id
	private Double price;
	private Integer stock;//库存有量
//	private Integer status;//产品状态 -1:下架 1:在售
//	private Integer userStoreId;//供应商 商店id
	private Integer saleCount;
	
	private String storeName;//商店名称
	private String storeAddr;//商店地址
	
	
	private Integer count;//此订单中 该物品的总量
	private String categoryCode;//商品类型简称
	private String categoryName;//商品类型名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
	public Integer getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
