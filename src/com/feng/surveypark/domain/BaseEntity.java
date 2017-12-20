package com.feng.surveypark.domain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * 这是一个所有共有实体的父类
 * @author 冯思伟
 *
 */
public abstract class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -810734175998110236L;
	
	//protected对子类和同包下的类开放
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		String className = this.getClass().getSimpleName();
		buffer.append(className+":{");
		//取出ID
		buffer.append("id:"+this.getId()+", ");
		//取出所有属性  getFields 是取piblic的  getDeclaredFields 是所有
		Field[] fields = this.getClass().getDeclaredFields();
		try {
			Class ftype = null;
			Object fvalue = null;
			for (Field field : fields) {
				ftype = field.getType();
				//设置共有权限,否则私有的无法访问
				field.setAccessible(true);
				if ((ftype.isPrimitive()
						|| ftype == String.class
						|| ftype == Integer.class
						|| ftype == Long.class
						|| ftype == Date.class)
						&& !Modifier.isStatic(field.getModifiers())
						&& field.get(this) != null
						) {
					
					//得到属性值
					fvalue = field.get(this);
					buffer.append(field.getName()+":"+fvalue+", ");
				}
			}					
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer.append("}");
		return buffer.toString();
	}
	
	
	
}
