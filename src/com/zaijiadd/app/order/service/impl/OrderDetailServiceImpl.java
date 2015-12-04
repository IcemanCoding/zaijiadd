package com.zaijiadd.app.order.service.impl;

import com.zaijiadd.app.order.dao.OrderDetailDAO;
import com.zaijiadd.app.order.dto.GoodsInfoInOrderDTO;
import com.zaijiadd.app.order.dto.OrderDetailDTO;
import com.zaijiadd.app.order.entity.LogisticsTypeEntity;
import com.zaijiadd.app.order.service.OrderDetailService;
import com.zaijiadd.app.utils.constants.ConstantsForPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailDAO orderDetailDao;

	@Override
	public OrderDetailDTO lookOrderDetailById(Integer id) {
		return orderDetailDao.lookOrderDetailById(id);
	}

	@Override
	public OrderDetailDTO lookOrderDetailByOrderCode(String orderCode) {
		return orderDetailDao.lookOrderDetailByOrderCode(orderCode);
	}

	@Override
	public List<OrderDetailDTO> queryAllOrderByUserId(Map<String, Object> params) {
		return orderDetailDao.queryAllOrderByUserId(params);
	}

	/**
	 * 查看某订单下的所有商品信息
	 * @return
	 */
	public List<GoodsInfoInOrderDTO> lookGoodsInfoByOrderKeyId(@Param("orderKeyId")int orderKeyId){
		return orderDetailDao.lookGoodsInfoByOrderKeyId(orderKeyId);
	}

	/**
	 * 查看某商店中的所有订单信息 
	 * @author chensl
	 * @param storeId-商店id, 
	 * @param pageNo:加载第几页数据
	 */
	@Override
	public List<OrderDetailDTO> queryAllOrderInStore(int storeId, int pageNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ConstantsForPage.PAGE_SIZE, ConstantsForPage.PER_PAGE_SIZE);
		params.put(ConstantsForPage.START, ConstantsForPage.PER_PAGE_SIZE * (pageNo-1));
		params.put("supplyStoreId", storeId);
		return orderDetailDao.queryAllOrderInStore(params);
	}

	/**
	 * 根据订单状态查看订单
	 * @param params
	 * @return
	 */
	@Override
	public List<OrderDetailDTO> queryAllOrderByPayStatus(Integer payStatus, int pageNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ConstantsForPage.PAGE_SIZE, ConstantsForPage.PER_PAGE_SIZE);
		params.put(ConstantsForPage.START, ConstantsForPage.PER_PAGE_SIZE * (pageNo-1));
		params.put("payStatus", payStatus);
		return orderDetailDao.queryAllOrderByPayStatus(params);
	}

	@Override
	public List<GoodsInfoInOrderDTO> joinGoodsInfoByOrderKeyId(Map<String, Object> params) {
		return orderDetailDao.joinGoodsInfoByOrderKeyId(params);
	}

	@Override
	public List<LogisticsTypeEntity> queryLogisticsTypes() {
		return orderDetailDao.queryLogisticsTypes();
	}
	
}
