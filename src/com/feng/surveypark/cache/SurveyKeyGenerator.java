package com.feng.surveypark.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

import com.feng.surveypark.util.DataUtil;
import com.feng.surveypark.util.ValidateUtil;

/**
 * 自定义缓存key生成器
 * @author 冯思伟
 *
 */
public class SurveyKeyGenerator implements KeyGenerator {

	/**
	 * 
	 */
	@Override
	public Object generate(Object arg0, Method arg1, Object... arg2) {
		String targHashCode = arg0.getClass().getSimpleName()+"["+arg0.hashCode()+"]";
		String mname = arg1.getName();
		String key = null ;
		StringBuffer buffer = new StringBuffer();
		if (ValidateUtil.isValid(arg2)) {
			for (Object p : arg2) {
				buffer.append(p.toString()+",");
			}
			key = targHashCode +"."+mname+"("+DataUtil.md5(buffer.toString())+")";
			return key;
		}
		key = targHashCode +"."+mname+"()";
		return key;
	}

}
