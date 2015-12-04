package com.zaijiadd.app.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zaijiadd.app.common.controller.BaseController;
import com.zaijiadd.app.common.utils.ContainerUtils;
import com.zaijiadd.app.common.utils.SessionContext;
import com.zaijiadd.app.common.viewmodel.SupplyRequestViewModel;
import com.zaijiadd.app.user.entity.UserInfoEntity;
import com.zaijiadd.app.user.entity.UserSendScopeEntity;
import com.zaijiadd.app.user.service.UserInfoService;
import com.zaijiadd.app.utils.constants.ConstantsForLogin;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping ( value = "/login", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> userLogin(HttpSession session,HttpServletRequest request/*, SupplyRequestViewModel viewmodel*/) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		String loginName = request.getParameter("loginName");
		
		String loginPass = request.getParameter("loginPass");
		
//		int userTypeId = viewmodel.getUserTypeId();
//		String userSessionId = viewmodel.getUserSessionId();
		
		Map<String,String> param = new HashMap<String,String>();
		param.put(ConstantsForLogin.LOGIN_NAME, loginName);
		param.put(ConstantsForLogin.LOGIN_PASS, loginPass);
		
		UserInfoEntity resUserInfo = userInfoService.checkLogin(param);
		
		if(null == resUserInfo){
			return ContainerUtils.buildHeadMap( resData, 0, "登录名或密码不正确" );
		}else{
			
			List<UserSendScopeEntity> usseList = userInfoService.getUserSendScope(resUserInfo.getUserId());
			resUserInfo.setUserSendScopeList(usseList);
			
			resData.put("userInfo", resUserInfo);
			
			//保存到session
			session.setAttribute(ConstantsForLogin.SESSION_NAME_LOGIN_RESULT, resUserInfo);
			//session.setMaxInactiveInterval(60);
			
			resData.put(ConstantsForLogin.SUPPLY_SESSION_ID, session.getId());
			
			SessionContext myc= SessionContext.getInstance();  
			myc.addSession(session);
			
			return ContainerUtils.buildResSuccessMap(resData);
		}
	}
	
	@RequestMapping ( value = "/updatePass", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> updatePass(HttpServletRequest request, SupplyRequestViewModel viewmodel) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		//得到用户信息
		UserInfoEntity userInfo = getCurrentLoginUserInfoInSession(getUserSessionId(request));
		int userId = userInfo.getUserId();
		String mobileNumber = userInfo.getMobileNumber();
		
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		
		if(oldPass.equals(newPass)){
			return ContainerUtils.buildHeadMap(resData, -1, "新密码不能与原密码一致 " );
		}
		
		Map<String,String> param = new HashMap<String,String>();
		param.put(ConstantsForLogin.LOGIN_NAME, mobileNumber);
		param.put(ConstantsForLogin.LOGIN_PASS, oldPass);
		
		UserInfoEntity resUserInfo = userInfoService.checkLogin(param);
		
		if(null == resUserInfo){
			return ContainerUtils.buildHeadMap(resData, -1, "原密码不正确" );
		}
		
		if(userInfoService.updatePassword(userId, newPass) > 0){
			return ContainerUtils.buildResSuccessMap(resData);
		}
		
		return ContainerUtils.buildResultNoKnowErrMap(resData);
	}
	
	@RequestMapping ( value = "/loginout", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> loginout(HttpSession session, HttpServletRequest request) {
		Map<String, Object> resData = new HashMap<String, Object>();
		
		SessionContext myc= SessionContext.getInstance();  
		myc.delSession(session);
		
		return ContainerUtils.buildResultMap(resData);
	}
	
}
