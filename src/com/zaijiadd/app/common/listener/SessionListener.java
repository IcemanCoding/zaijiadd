package com.zaijiadd.app.common.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import com.zaijiadd.app.common.utils.SessionContext;

/**
 * session监听器
 * @author chensl
 *
 */
public class SessionListener {
	//public static Map<String, HttpSession> userMap = new HashMap<String, HttpSession>();  
	private   SessionContext myc = SessionContext.getInstance();  
	  
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {  
		myc.addSession(httpSessionEvent.getSession());  
	}  
	  
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {  
		HttpSession session = httpSessionEvent.getSession();  
		myc.delSession(session);  
	}  
}
