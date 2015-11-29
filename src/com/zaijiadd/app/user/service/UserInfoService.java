package com.zaijiadd.app.user.service;

import java.util.Map;

import com.zaijiadd.app.user.dto.UserReceiveInfoDTO;
import com.zaijiadd.app.user.dto.UserStoreDTO;
import com.zaijiadd.app.user.entity.UserInfoEntity;

public interface UserInfoService {
	
	public Map<String, Object> getUserType( String zaijiaddId );
	
	public Boolean addUserInfo( UserReceiveInfoDTO dto );

	public UserInfoEntity checkLogin(Map<String, String> param);
	
	public UserInfoEntity queryUserInfoByUserId(Integer userId);
	
	/**
	 * 根据用户id查找用户的店铺信息
	 * @param userId
	 * @return
	 */
	public UserStoreDTO getUserStoreByUserId(Integer userId);
}
