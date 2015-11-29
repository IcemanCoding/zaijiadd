package com.zaijiadd.app.common.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * 管理保存session
 * @author chensl
 */
public class SessionContext {
	  
	private static SessionContext instance;  
	private HashMap<String, HttpSession> sessionMap;  
	  
	private SessionContext() {  
		sessionMap = new HashMap<String, HttpSession>();  
	}  
	  
	public static SessionContext getInstance() {  
		if (instance == null) {  
			instance = new SessionContext();  
		}  
		return instance;  
	}  
	  
	public synchronized void addSession(HttpSession session) {  
		if (session != null) {  
			sessionMap.put(session.getId(), session);  
		}  
	}  
	  
	public synchronized void delSession(HttpSession session) {  
		if (session != null) {  
			sessionMap.remove(session.getId());  
		}  
	}  
	  
	public synchronized HttpSession getSession(String session_id) {  
		if (session_id == null) return null;  
			return (HttpSession) sessionMap.get(session_id);  
	}
}
