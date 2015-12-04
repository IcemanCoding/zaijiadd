package com.zaijiadd.app.order.dto;

import java.io.Serializable;
import java.util.List;

public class OrderDetailDTO implements Serializable {

	private static final long serialVersionUID = 1998640125892426061L;

	private Integer id;//
	private String orderCode;//order_id,订单编号
	private Integer userId;//
	private String transCode;//
	private String productId;//
	private Double amount;//总金额
	private Integer transStatus;//订单状态
	private String createDate;//下单时间
	private String updateDate;//修改时间
	private Double discountAmount;//优惠金额
	private Double transExpenses;//运费
	private Integer logisticsTypeId;//物流公司 类型id
	private String logisticsCode;//物流单号
	private String presendingTime;//预计送到时间
	private Integer payStatus;//订单支付状态 0: 未支付 1:已支付
	private Integer supplyStoreId;//此订单属于哪个供应商  商店Id
	private String payTime;//支付时间
	
	private String logisticsName;//物流公司公称
	private String logisticsShortName;//物流公司俗称
	
	private String mobileNumber;
	private String account;
	private String storeName;
	private String storeAddr;
	
	private String receiveAddress;
	private String receiveMobile;
	private String receiveRealName;
	private String receivePostCode;
	
	private List<GoodsInfoInOrderDTO> goodsInfoInOrderList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Double getTransExpenses() {
		return transExpenses;
	}
	public void setTransExpenses(Double transExpenses) {
		this.transExpenses = transExpenses;
	}
	public Integer getLogisticsTypeId() {
		return logisticsTypeId;
	}
	public void setLogisticsTypeId(Integer logisticsTypeId) {
		this.logisticsTypeId = logisticsTypeId;
	}
	public String getLogisticsCode() {
		return logisticsCode;
	}
	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	public String getPresendingTime() {
		return presendingTime;
	}
	public void setPresendingTime(String presendingTime) {
		this.presendingTime = presendingTime;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getSupplyStoreId() {
		return supplyStoreId;
	}
	public void setSupplyStoreId(Integer supplyStoreId) {
		this.supplyStoreId = supplyStoreId;
	}
	public String getLogisticsName() {
		return logisticsName;
	}
	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	public String getLogisticsShortName() {
		return logisticsShortName;
	}
	public void setLogisticsShortName(String logisticsShortName) {
		this.logisticsShortName = logisticsShortName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public List<GoodsInfoInOrderDTO> getGoodsInfoInOrderList() {
		return goodsInfoInOrderList;
	}
	public void setGoodsInfoInOrderList(List<GoodsInfoInOrderDTO> goodsInfoInOrderList) {
		this.goodsInfoInOrderList = goodsInfoInOrderList;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getReceiveMobile() {
		return receiveMobile;
	}
	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}
	public String getReceiveRealName() {
		return receiveRealName;
	}
	public void setReceiveRealName(String receiveRealName) {
		this.receiveRealName = receiveRealName;
	}
	public String getReceivePostCode() {
		return receivePostCode;
	}
	public void setReceivePostCode(String receivePostCode) {
		this.receivePostCode = receivePostCode;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
}
