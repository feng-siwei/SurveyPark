package com.feng.surveypark.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import com.feng.surveypark.dao.BaseDao;
import com.feng.surveypark.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> dao ; 
	
	private Class<T> clazz ;
	
	@SuppressWarnings("unchecked")
	public BaseServiceImpl(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz =  (Class<T>) type.getActualTypeArguments()[0];
	}
	
	//注入dao
	@Resource 
	public void setDao(BaseDao<T> dao) {
		this.dao = dao;
	}

	@Override
	public void saveEntity(T t) {
		dao.saveEntity(t);

	}

	@Override
	public void updateEntity(T t) {
		dao.updateEntity(t);
	}

	@Override
	public void sayeOrUpdateEntity(T t) {
		dao.saveOrUpdateEntity(t);
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
	public void executeSQL(String sql, Object... objects) {
		dao.executeSQL(sql, objects);
		
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

	//单值检索(只有一个结果)
	@Override
	public Object uniqueResult(String hql, Object... objects) {
		return dao.uniqueResult(hql, objects);
	}
	
	//原生sql查询
	@Override
	public List<T> findObjectBySQL(String sql, Object... objects) {
		return dao.findObjectBySQL(sql, objects);
	}

	//查询全部实体
	@Override
	public List<T> findAllEntities() {
		String hql = "from "+clazz.getSimpleName();
		return this.findEntityByHQL(hql);
	}

	

}
