package com.feng.surveypark.service;

import java.util.List;

public interface BaseService<T> {
		//操作
		public void saveEntity(T t);
		public void updateEntity(T t);
		public void sayeOrUpdateEntity(T t);
		public void deleteEntity(T t);
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
		public List<T> findObjectBySQL(String sql,Object...objects);
		
		//得到全部实体
		public List<T> findAllEntities();
		
	
		
		
}
