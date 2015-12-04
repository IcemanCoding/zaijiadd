package com.zaijiadd.app.store.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GoodsInfoEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1048505082215772826L;
	
	private Integer id;
	private String goodsID;//产品编号
	private Integer categoryId;//商品类别
	private String goodsName;//商品名
	private String description;//
	private String spec;//商品规格
	private String pic;//商品图片信息
	private String tag;//商品关键词
	private Integer status;//商品状态：-1 删除 1 正常
	private Date createDate;//
	private Date updateDate;//
	private Integer appGoodsId;//
	
	private CategoryInfoEntity categoryInfo;
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getAppGoodsId() {
		return appGoodsId;
	}
	public void setAppGoodsId(Integer appGoodsId) {
		this.appGoodsId = appGoodsId;
	}
	public CategoryInfoEntity getCategoryInfo() {
		return categoryInfo;
	}
	public void setCategoryInfo(CategoryInfoEntity categoryInfo) {
		this.categoryInfo = categoryInfo;
	}

}
