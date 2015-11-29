package com.zaijiadd.app.store.controller;

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
import com.zaijiadd.app.common.viewmodel.SupplyRequestViewModel;
import com.zaijiadd.app.store.entity.CategoryInfoEntity;
import com.zaijiadd.app.store.service.CategoryInfoService;

@RequestMapping("/category")
@Controller
public class CategoryInfoController extends BaseController{
	
	@Autowired
	private CategoryInfoService categoryInfoService;
	
	@RequestMapping ( value = "/getCategorys", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> getCategorys(HttpServletRequest request, SupplyRequestViewModel viewmodel) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		//int userType = (int) request.getAttribute(ConstantsForLogin.USER_TYPE);//得到用户类型
		String pId = request.getParameter("parentId");
		int parentId = 0;
		if(pId != null) parentId = Integer.parseInt(pId);
		
		List<CategoryInfoEntity> categoryInfoList = categoryInfoService.queryCategoryInfo(parentId);
		
		resData.put("data", categoryInfoList);
		
		return ContainerUtils.buildResSuccessMap( resData );
	}
	
}
