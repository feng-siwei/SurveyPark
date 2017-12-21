package com.feng.surveypark.struts2.action;

import java.lang.reflect.ParameterizedType;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;


/**
 * 抽象的action超类,
 * 
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>,
		Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1882955904871426390L;

	public T model;
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 实例化model,方法同BaseDaoImpl
	 */
	public BaseAction() {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			@SuppressWarnings("unchecked")
			Class<T> clazz =  (Class<T>) type.getActualTypeArguments()[0];
			model = clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public  T getModel() {
		return model;
	}

}
