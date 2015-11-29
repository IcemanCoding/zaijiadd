package com.zaijiadd.app.user.dao;

import java.util.Map;

import com.zaijiadd.app.user.dto.UserStoreDTO;
import com.zaijiadd.app.user.entity.UserInfoEntity;

public interface UserInfoDAO {
	
	public Integer isUserInfoExist( String zaijiaddid );
	
	public Integer insertUserInfo( String zaijiaddid );
	
	public Integer getUserType( String zaijiaddid );

	public UserInfoEntity loginCheck(Map<String, String> param);
	
	public UserInfoEntity queryByUserId(Integer userId);
	
	/**
	 * 根据用户id查找用户的店铺信息
	 * @param userId
	 * @return
	 */
	public UserStoreDTO getUserStoreByUserId(Integer userId);
}
