package com.zaijiadd.app.store.service;

import java.util.List;

import com.zaijiadd.app.store.entity.CategoryInfoEntity;

public interface CategoryInfoService {
	
	public List<CategoryInfoEntity> queryCategoryInfo(Integer parentId);
	
}
