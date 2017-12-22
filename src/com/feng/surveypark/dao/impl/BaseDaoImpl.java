package com.feng.surveypark.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;






import com.feng.surveypark.dao.BaseDao;

@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Resource
	private SessionFactory sf;
	
	private Class<T> clazz ;
	
	public BaseDaoImpl (){
		//getGenericSuperclass()获得带有泛型的父类
		//Type是 Java 编程语言中所有类型的公共高级接口。
		//它们包括原始类型、参数化类型、数组类型、类型变量和基本类型
		//ParameterizedType参数化类型，即泛型
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz =  (Class<T>) type.getActualTypeArguments()[0];
		
		
	}

	@Override
	public void saveEntity(T t) {
		sf.getCurrentSession().save(t);
	}

	@Override
	public void updateEntity(T t) {
		sf.getCurrentSession().update(t);
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		sf.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public void deleteEntity(T t) {
		sf.getCurrentSession().delete(t);
	}

	@Override
	public void batchEntityByHQL(String hql, Object... objects) {
		Query query = sf.getCurrentSession().createQuery(hql);
		for (int i = 0 ; i<objects.length ; i++) {
			query.setParameter(i, objects[i]);
		}
		query.executeUpdate();
	}

	//通过原生SQL实现
	@Override
	public void executeSQL(String sql, Object... objects) {
		SQLQuery query = sf.getCurrentSession().createSQLQuery(sql);
		for (int i = 0 ; i<objects.length ; i++) {
			query.setParameter(i, objects[i]);
		}
		query.executeUpdate();
		
	}

	
	@Override
	public T getEntity(Integer id) {
		
		return (T) sf.getCurrentSession().get(clazz, id);
	}

	@Override
	public T loadEntity(Integer id) {
		return (T) sf.getCurrentSession().load(clazz, id);
	}

	
	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		Query query = sf.getCurrentSession().createQuery(hql);
		for (int i = 0 ; i<objects.length ; i++) {
			query.setParameter(i, objects[i]);
		}
		
		return query.list();
	}
	
	//单值检索
	@Override
	public Object uniqueResult(String hql, Object...objects){
		Query query = sf.getCurrentSession().createQuery(hql);
		for (int i = 0 ; i<objects.length ; i++) {
			query.setParameter(i, objects[i]);
		}
		
		return query.uniqueResult();
	}

	/**
	 * 通过原生SQL查询
	 */
	@Override
	public List<T> findObjectBySQL(String sql, Object... objects) {
		SQLQuery query = sf.getCurrentSession().createSQLQuery(sql);
		for (int i = 0 ; i<objects.length ; i++) {
			query.setParameter(i, objects[i]);
		}
		/*添加实体,因为sql查询出的是对象数组(一个个的字符串数组)
		 * 添加实体后可以对象数组转化为实体
		 */
		query.addEntity(clazz);
		return query.list();
	}


	
}
