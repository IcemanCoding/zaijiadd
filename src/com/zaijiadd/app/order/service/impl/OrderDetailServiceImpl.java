package com.zaijiadd.app.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.zaijiadd.app.order.dao.OrderDetailDAO;
import com.zaijiadd.app.order.dto.GoodsInfoInOrderDTO;
import com.zaijiadd.app.order.dto.OrderDetailDTO;
import com.zaijiadd.app.order.service.OrderDetailService;
import com.zaijiadd.app.utils.constants.ConstantsForPage;

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
	
}
