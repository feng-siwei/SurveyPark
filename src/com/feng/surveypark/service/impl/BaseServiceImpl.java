package com.feng.surveypark.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.feng.surveypark.dao.BaseDao;
import com.feng.surveypark.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> dao ; 
	
	//注入dao
	@Resource 
	public void setDao(BaseDao<T> dao) {
		this.dao = dao;
	}

	@Override
	public void saveEntity(T t) {
		dao.sayeEntity(t);

	}

	@Override
	public void updateEntity(T t) {
		dao.updateEntity(t);
	}

	@Override
	public void sayeOrUpdateEntity(T t) {
		dao.sayeOrUpdateEntity(t);
	}

	@Override
	public void deleteEntity(T t) {
		dao.deleteEntity(t);
	}

	@Override
	public void batchEntityByHQL(String hql, Object... objects) {
		dao.batchEntityByHQL(hql, objects);
	}

	@Override
	public T getEntity(Integer id) {
		return dao.getEntity(id);
	}

	@Override
	public T loadEntity(Integer id) {
		return dao.loadEntity(id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		return dao.findEntityByHQL(hql, objects);
	}

}
