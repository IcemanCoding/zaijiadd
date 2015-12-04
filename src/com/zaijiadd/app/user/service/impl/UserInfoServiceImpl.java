package com.zaijiadd.app.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.zaijiadd.app.user.dao.UserInfoDAO;
import com.zaijiadd.app.user.dto.UserReceiveInfoDTO;
import com.zaijiadd.app.user.dto.UserStoreDTO;
import com.zaijiadd.app.user.entity.UserInfoEntity;
import com.zaijiadd.app.user.entity.UserSendScopeEntity;
import com.zaijiadd.app.user.service.UserInfoService;

public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoDAO userInfoDao;

	@Override
	public Map<String, Object> getUserType( String zaijiaddId ) {
		
		Map<String, Object> res = new HashMap<String, Object>();
		/*
		 * 1、查询用户是否存在
		 * 存在 		return true
		 */
		Integer queryCount = userInfoDao.getUserType( zaijiaddId );
		if ( queryCount == null ) {
			// 用户不存在
			return null;
		} else {
			res.put( "usertype", queryCount );
			return res;
		}
		
	}

	@Override
	public Boolean addUserInfo( UserReceiveInfoDTO dto ) {

		/*
		 * 1、查询用户是否存在
		 * 存在 		return true
		 * 不存在	insert
		 */
		String zaijiaddid = dto.getZaijiaddId();
		Integer queryCount = userInfoDao.isUserInfoExist( zaijiaddid );
		if ( queryCount != null && queryCount > 0 ) {
			return true;
		}
		
		/*
		 * insert userInfo
		 */
		Integer insertCount = userInfoDao.insertUserInfo( zaijiaddid );
		if ( insertCount > 0 ) {
			return true;
		}
		
		return false;
	
	}

	@Override
	public UserInfoEntity checkLogin(Map<String, String> param) {
		return userInfoDao.loginCheck(param);
	}

	@Override
	public UserInfoEntity queryUserInfoByUserId(Integer userId) {
		return userInfoDao.queryByUserId(userId);
	}

	@Override
	public UserStoreDTO getUserStoreByUserId(Integer userId) {
		return userInfoDao.getUserStoreByUserId(userId);
	}

	@Override
	public int updatePassword(Integer userId, String password) {
		Map<String, Object> param = new HashMap<String, Object> ();
		param.put("password", password);
		param.put("userId", userId);
		return userInfoDao.updatePassword(param);
	}

	@Override
	public List<UserSendScopeEntity> getUserSendScope(Integer userId){
		return userInfoDao.getUserSendScope(userId);
	}
	
	@Override
	public int addUserSendScope(UserSendScopeEntity userSendScope){
		return userInfoDao.addUserSendScope(userSendScope);
	}
	
	@Override
	public int updateUserSendScope(UserSendScopeEntity userSendScope){
		return userInfoDao.updateUserSendScope(userSendScope);
	}
}
