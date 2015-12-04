package com.zaijiadd.app.user.interceptors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zaijiadd.app.common.utils.SessionContext;
import com.zaijiadd.app.utils.constants.ConstantsForLogin;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionContext myc= SessionContext.getInstance();  
        
        String userSessionId = request.getHeader(ConstantsForLogin.SUPPLY_SESSION_ID);
        
		HttpSession sess = myc.getSession(userSessionId);
		
        // 判断如果没有对应的session信息，就跳转到登陆页面
        if (sess == null) {  
        	response.setCharacterEncoding("UTF-8");  
            response.setContentType("application/json; charset=utf-8");  
            PrintWriter out = null;  
            try {  
                out = response.getWriter();
                out.append("{\"msg\": \"请先登录\",\"flag\": -1}"); 
                out.flush();
                return false;
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                if (out != null) {  
                    out.close();  
                }  
            }
            
        }  
        return true; 
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
    
}
