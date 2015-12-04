package com.zaijiadd.app.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zaijiadd.app.store.dao.CategoryInfoDao;
import com.zaijiadd.app.store.entity.CategoryInfoEntity;
import com.zaijiadd.app.store.service.CategoryInfoService;

public class CategoryInfoServiceImpl implements CategoryInfoService{

	@Autowired
	private CategoryInfoDao categoryInfoDao;
	
	@Override
	public List<CategoryInfoEntity> queryCategoryInfo(Integer parentId) {
		return categoryInfoDao.queryCategoryInfo(parentId);
	}

}
