package com.zaijiadd.app.order.service;

import java.util.Map;

public interface OrderInfoService {
	
	public Map<String, Object> buildOrderInfo( String zaijiaddId, String productId, String transCode, String transAmount, String subject );

	public Map<String, Object> buildOrderInfo( String zaijiaddId, String productId, String transCode );

	/**
	 * 修改订单状态
	 * @param params
	 */
	public void updatePayStatusByOrderKeyId(int orderKeyId, int updataPayStatusTo);
	
	/**
	 * 
	 * @param orderKeyId
	 * @param updataPayStatusTo
	 * @param lTypeId 物流公司id
	 * @param lCode   物流单号
	 */
	public void updatePayStatusByOrderKeyId(int orderKeyId, int updataPayStatusTo, int lTypeId, String lCode);
}
