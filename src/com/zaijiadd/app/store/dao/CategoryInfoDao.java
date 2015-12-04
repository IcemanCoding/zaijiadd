package com.zaijiadd.app.store.dao;

import java.util.List;

import com.zaijiadd.app.store.entity.CategoryInfoEntity;

/**
 * 商品分类 类
 * @author chensl
 *
 */
public interface CategoryInfoDao {

	public List<CategoryInfoEntity> queryCategoryInfo(Integer parentId);
	
}
