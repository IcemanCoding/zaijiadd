package com.zaijiadd.app.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zaijiadd.app.authorization.service.AuthorizationService;
import com.zaijiadd.app.common.controller.BaseController;
import com.zaijiadd.app.common.utils.ContainerUtils;
import com.zaijiadd.app.common.utils.ParseUtils;
import com.zaijiadd.app.common.viewmodel.RequestViewmodel;
import com.zaijiadd.app.common.viewmodel.SupplyRequestViewModel;
import com.zaijiadd.app.order.dto.GoodsInfoInOrderDTO;
import com.zaijiadd.app.order.dto.OrderDetailDTO;
import com.zaijiadd.app.order.service.OrderDetailService;
import com.zaijiadd.app.order.service.OrderInfoService;
import com.zaijiadd.app.user.entity.UserInfoEntity;
import com.zaijiadd.app.utils.constants.ConstantsForLogin;
import com.zaijiadd.app.utils.constants.ConstantsForOrder;
import com.zaijiadd.app.utils.constants.ConstantsForPage;
import com.zaijiadd.app.utils.constants.ConstantsForUserType;

@RequestMapping ( "/order" )
@Controller
public class OrderController extends BaseController {

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	/**
	 * Chenzq
	 * 2015.11.06
	 * 生成订单
	 * input	sid、productId
	 */
	@RequestMapping ( value = "/addOrder", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> addOrder( HttpServletRequest request, RequestViewmodel viewmodel ) {

		Map<String, Object> resData = new HashMap<String, Object>();

		/*
		 * 1、上送数据验证
		 */
		String zjtoken = viewmodel.getZjtoken();
		String storeId = viewmodel.getStoreId();
		if ( zjtoken == null || zjtoken.isEmpty() ) {
			return ContainerUtils.buildHeadMap( resData, 0, "zjtoken不能为空" );
		}

		if ( !authorizationService.AuthorizationUserInfo( zjtoken, storeId ) ) {
			return ContainerUtils.buildHeadMap( resData, -1, "用户尚未登录" );
		}
		
		/*
		 * 2、json request convert
		 */
		String productId = "";
		JSONObject jsonRequest = ParseUtils.loadJsonPostRequest( request );
		if ( jsonRequest == null ) {
			productId = request.getParameter( "productId" ) + "";
		}
		productId = jsonRequest.getString( "productId" );
//		String transAmount = jsonRequest.getString( "transAmount" );
//		String subject = jsonRequest.getString( "subject" );
		
//		resData = orderInfoService.buildOrderInfo( viewmodel.getZaijiaddId(), productId, ConstantsForOrder.TRANS_CODE_RECHARGE, transAmount, subject );
		resData = orderInfoService.buildOrderInfo( viewmodel.getZaijiaddId(), productId, ConstantsForOrder.TRANS_CODE_RECHARGE );
		
		if ( resData == null ) {
			return ContainerUtils.buildHeadMap( resData, 0, "系统异常" );
		}
		
		return ContainerUtils.buildResSuccessMap( resData );

	}

	/**
	 * 查看某订单详情
	 * @param orderId
	 * @return
	 */
	@RequestMapping ( value = "/lookOrderDetailById", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> lookOrderDetailById(@RequestParam(required=true, value="orderId")int orderId) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		OrderDetailDTO orderDetail = orderDetailService.lookOrderDetailById(orderId);
		
		resData.put("data", orderDetail);
		
		return ContainerUtils.buildResultMap( resData );
	}
	
	@RequestMapping ( value = "/lookOrderDetailByOrderCode", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> lookOrderDetailByOrderCode(@RequestParam(required=true, value="orderCode")String orderCode) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		OrderDetailDTO orderDetail = orderDetailService.lookOrderDetailByOrderCode(orderCode);
		
		resData.put("data", orderDetail);
		
		return ContainerUtils.buildResultMap( resData );
	}
	
	/**
	 * 获取当前用户下的所有订单信息
	 * pageNo: 当前第几页 必须
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/lookAllOrder", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> lookAllOrder(HttpServletRequest request, SupplyRequestViewModel viewmodel) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		UserInfoEntity userInfo = getCurrentLoginUserInfoInSession(request, viewmodel);
		int userId = userInfo.getUserId();
		
		String pNo = request.getParameter(ConstantsForPage.PAGE_NO);//第几页
		int pageNo = 1;
		if(pNo != null) pageNo = Integer.parseInt(pNo);
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put(ConstantsForPage.PAGE_SIZE, ConstantsForPage.PER_PAGE_SIZE);
		params.put(ConstantsForPage.START, ConstantsForPage.PER_PAGE_SIZE * (pageNo-1));
		
		List<OrderDetailDTO> orderDetails = orderDetailService.queryAllOrderByUserId(params);
		
		resData.put("data", orderDetails);
		
		return ContainerUtils.buildResultMap( resData );
	}
	
	/**
	 * 查看某店铺下的所有订单信息
	 * 供应商特有
	 * @param supplyStoreId
	 * @param request
	 * @return
	 */
	@RequestMapping ( value = "/lookAllOrderInStore", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> lookAllOrderInStore(@RequestParam("supplyStoreId")int supplyStoreId, HttpServletRequest request) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		int userType = (int) request.getAttribute(ConstantsForLogin.USER_TYPE);//得到用户类型
		
		if(userType != ConstantsForUserType.SUPPLY_STORE_OWNER){
			return ContainerUtils.buildResMap(resData, -1, "您没有权限!");
		}
		
		String pNo = request.getParameter(ConstantsForPage.PAGE_NO);//第几页
		int pageNo = 1;
		
		if(pNo != null) pageNo = Integer.parseInt(pNo);
		
		List<OrderDetailDTO> orderDetails = orderDetailService.queryAllOrderInStore(supplyStoreId, pageNo);
				
		resData.put("data", orderDetails);
		
		return ContainerUtils.buildResultMap( resData );
	}
	/**
	 * 查看某订单下所有商品详情
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/lookAllGoodsInOrder", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> lookAllGoodsInOrder(@RequestParam("orderKeyId")int orderKeyId) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		List<GoodsInfoInOrderDTO> orderGoodsList = orderDetailService.lookGoodsInfoByOrderKeyId(orderKeyId);
		
		resData.put("data", orderGoodsList);
		
		return ContainerUtils.buildResultMap( resData );
	}
}
