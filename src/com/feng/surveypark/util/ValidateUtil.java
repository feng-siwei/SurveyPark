package com.feng.surveypark.util;

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.feng.surveypark.domain.User;
import com.feng.surveypark.domain.security.Right;
import com.feng.surveypark.struts2.action.BaseAction;
import com.feng.surveypark.struts2.action.UserAware;

/**
 * 校验工具类
 */
public class ValidateUtil {
	
	/**
	 * 判断字符有效性
	 */
	public static boolean isValid(String str){
		if (str == null || "".equals(str.trim())) {
			return false ; 
		}
		return true;
	}
	
	/**
	 * 判断集合有效性
	 * @param col 集合
	 * @return 
	 */
	public static boolean isValid(Collection<?> col){
		if (col == null || col.isEmpty()) {
			return false;	
		}
		return true;
	}
	/**
	 * 判断数组有效性
	 */
	public static boolean isValid(Object[] objects) {
		if (objects == null || objects.length==0) {
			return false;			
		}
		return true;
	}
	
	
	/**
	 * 权限验证
	 * @param nameSpace 
	 * @param actionName 
	 * @param request 
	 * @param action 可以为null
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public static boolean hasRight(String nameSpace,String actionName,
			HttpServletRequest request,BaseAction action) {
		//对nameSpace和actionName预处理
		if (!isValid(nameSpace) || "/".equals(nameSpace)) {
			nameSpace = "";
		}
		if (actionName.contains("?")) {
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		
		String url = nameSpace +"/"+actionName;
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		//获取全部权限map
		Map<String, Right> map = (Map<String, Right>) servletContext.getAttribute("all_rights_map");
		Right right = map.get(url);
	
//		System.out.println(url);
//		System.out.println(right.getRightName());
		
		User user = (User) session.getAttribute("user");
//		如果权限位空或者权限位公共资源放行
		if (right == null || right.isCommon()) {
			return true;
		}else {
			if(user == null) {
				return false;	
			}else {
				//实现userAware用户关注接口注入,注意action为空的情况
				if (action != null && action instanceof UserAware) {
					((UserAware) action).setUser(user);
				}
				//权限
				if (user.isSuperAdmin()) {
					return true;
				}else {
					if (user.hasRight(right)) {
						return true;
					}
					return false;
				}
				
			}
		}
		
	}
	
}
