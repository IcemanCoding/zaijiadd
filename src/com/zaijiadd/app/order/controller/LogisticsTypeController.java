package com.zaijiadd.app.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zaijiadd.app.common.controller.BaseController;
import com.zaijiadd.app.common.utils.ContainerUtils;
import com.zaijiadd.app.common.viewmodel.RequestViewmodel;
import com.zaijiadd.app.order.entity.LogisticsTypeEntity;
import com.zaijiadd.app.order.service.OrderDetailService;

@Controller
public class LogisticsTypeController extends BaseController {
	@Autowired
	private OrderDetailService orderDetailService;
	
	/**
	 * 获取所有物流信息
	 * @param request
	 * @param viewmodel
	 * @return
	 */
	@RequestMapping ( value = "/getLogisticsTypes", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> addOrder( HttpServletRequest request, RequestViewmodel viewmodel ) {

		Map<String, Object> resData = new HashMap<String, Object>();

		List<LogisticsTypeEntity> logisticsTypes = orderDetailService.queryLogisticsTypes();
		resData.put("data", logisticsTypes);
		return ContainerUtils.buildResultMap( resData );
	}

}
