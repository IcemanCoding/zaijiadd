package com.zaijiadd.app.common.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.zaijiadd.app.common.utils.SessionContext;
import com.zaijiadd.app.common.utils.StringUtils;
import com.zaijiadd.app.common.viewmodel.RequestViewmodel;
import com.zaijiadd.app.common.viewmodel.SupplyRequestViewModel;
import com.zaijiadd.app.user.entity.UserInfoEntity;
import com.zaijiadd.app.utils.constants.ConstantsForLogin;

public class BaseController {

	@ModelAttribute
	public RequestViewmodel buildRequest( HttpServletRequest request ) {
		
		RequestViewmodel viewmodel = new RequestViewmodel();
		
		Enumeration headerNames = request.getHeaderNames();

		while ( headerNames.hasMoreElements() ) {
			String key = ( String ) headerNames.nextElement();
			String value = request.getHeader( key );
			if ( key.equalsIgnoreCase( "zjtoken" ) ) {
				viewmodel.setZjtoken( value );
				String zaijiaddId = StringUtils.splitString( value, "`", -1 );
				viewmodel.setZaijiaddId( zaijiaddId );
			}
			if ( key.equalsIgnoreCase( "storeId" ) ) {
				viewmodel.setStoreId( value );
			}
		}
		
		return viewmodel;
		
	}
	
	@SuppressWarnings("rawtypes")
	@ModelAttribute
	public SupplyRequestViewModel buildSupplyRequest( HttpServletRequest request ) {
		
		SupplyRequestViewModel viewmodel = new SupplyRequestViewModel();
		
		Enumeration headerNames = request.getHeaderNames();

		while ( headerNames.hasMoreElements() ) {
			String key = ( String ) headerNames.nextElement();
			String value = request.getHeader( key );
			if ( key.equalsIgnoreCase( "userSessionId" ) ) {
				viewmodel.setUserSessionId(value);
			}
		}
		
		return viewmodel;
		
	}
	
	@ModelAttribute
	public UserInfoEntity getCurrentLoginUserInfoInSession(HttpServletRequest request, SupplyRequestViewModel viewmodel){
		SessionContext myc= SessionContext.getInstance();  
		HttpSession sess = myc.getSession(viewmodel.getUserSessionId());
		if(null == sess){
			return null;
		}
		UserInfoEntity userInfo = (UserInfoEntity) sess.getAttribute(ConstantsForLogin.SESSION_NAME_LOGIN_RESULT);
		request.setAttribute(ConstantsForLogin.USER_TYPE, userInfo.getUserType());
		return userInfo;
	}
}
