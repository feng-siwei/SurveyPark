package com.feng.surveypark.dao;

import java.util.List;

/**
 * 接口
 */
public interface BaseDao<T> {
	//操作
	//存储实体
	public void saveEntity(T t);
	//更新实现
	public void updateEntity(T t);
	//没有就保存,存在就更新
	public void saveOrUpdateEntity(T t);
	//删除实体
	public void deleteEntity(T t);
	//通过HQL语句进行复杂操作
	public void batchEntityByHQL(String hql,Object...objects);
	//通过原生SQL实现
	public void executeSQL(String sql,Object...objects);
	
	//查询
	public T getEntity(Integer id);
	public T loadEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
	//单值检索(只有一个结果)
	public Object uniqueResult(String hql, Object...objects);
	//通过原生SQL查询
	public List<T> findObjectBySQL(String sql ,Object...objects);
}
