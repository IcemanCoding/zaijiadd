package com.zaijiadd.app.order.dao;

import com.zaijiadd.app.order.dto.GoodsInfoInOrderDTO;
import com.zaijiadd.app.order.dto.OrderDetailDTO;
import com.zaijiadd.app.order.entity.LogisticsTypeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDetailDAO {
	
	/**
	 * 查找所有的物流公司
	 * @return
	 */
	public List<LogisticsTypeEntity> queryLogisticsTypes();
	
	/**
	 * 根据id查找订单详细信息
	 * @param id
	 * @return
	 */
	public OrderDetailDTO lookOrderDetailById(@Param(value="id")Integer id);
	
	/**
	 * 根据订单编号查找订单详细信息
	 * @param orderCode
	 * @return
	 */
	public OrderDetailDTO lookOrderDetailByOrderCode(@Param(value="orderCode")String orderCode);
	
	/**
	 * 查找 某用户下的所有订单
	 * @param params
	 * @return
	 */
	public List<OrderDetailDTO> queryAllOrderByUserId(Map<String, Object> params);
	
	/**
	 * 查看某订单下的所有商品信息
	 * @return
	 */
	public List<GoodsInfoInOrderDTO> lookGoodsInfoByOrderKeyId(@Param("orderKeyId")int orderKeyId);
	
	/**
	 * 查找某商店下的所有订单信息
	 * @param params
	 * @return
	 */
	public List<OrderDetailDTO> queryAllOrderInStore(Map<String, Object> params);
	
	/**
	 * 根据订单状态查看订单
	 * @param params
	 * @return
	 */
	public List<OrderDetailDTO> queryAllOrderByPayStatus(Map<String, Object> params);
	
	/**
	 * 关联出订单下的所有商品信息
	 * @param params
	 * @return
	 */
	public List<GoodsInfoInOrderDTO> joinGoodsInfoByOrderKeyId(Map<String, Object> params);
}
