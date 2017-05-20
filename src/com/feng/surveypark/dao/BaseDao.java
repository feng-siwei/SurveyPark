package com.feng.surveypark.dao;

import java.util.List;

/**
 * 接口
 */
public interface BaseDao<T> {
	//操作
	public void sayeEntity(T t);
	public void updateEntity(T t);
	public void sayeOrUpdateEntity(T t);
	public void deleteEntity(T t);
	public void batchEntityByHQL(String hql,Object...objects);

	//查询
	public T getEntity(Integer id);
	public T loadEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
}
