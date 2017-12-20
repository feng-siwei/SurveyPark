package com.feng.surveypark.service.impl;



import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feng.surveypark.dao.BaseDao;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.domain.security.Role;
import com.feng.surveypark.service.RoleService;
import com.feng.surveypark.service.UserService;
import com.feng.surveypark.util.DataUtil;
import com.feng.surveypark.util.StringUtil;
import com.feng.surveypark.util.ValidateUtil;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	/**
	 * 重写userDao,覆盖
	 */
	
	@Override
	@Resource(name="userDao")
	public void setDao(BaseDao<User> dao) {
		super.setDao(dao);
	}
	
	@Resource
	private RoleService roleService;
	

	/**
	 * 判断邮箱是否占用
	 */
	@Override
	public boolean isRegisted(String email) {
		String hql = "from User u where u.email = ?";
		List<User> list = this.findEntityByHQL(hql, email);
		return ValidateUtil.isValid(list);
	}

	/**
	 * 密码加密
	 */
	
	@Override
	public void saveEntity(User t) {
		t.setPassword(DataUtil.md5(t.getPassword()));
		super.saveEntity(t);
	}


	/**
	 * 登入验证
	 */
	@Override
	public User validateLoginInfo(String email, String password) {
		String hql = "from User u where u.email = ? and u.password = ?";
		password = DataUtil.md5(password);
		List<User> list = this.findEntityByHQL(hql, email,password);
		return ValidateUtil.isValid(list)? list.get(0) : null;
	}


	/**
	 * 修改用户授权
	 */
	@Override
	public void updateAuthorize(User user, Integer[] ownRoleIds) {
		//将数据同步补全
		User uu = this.getEntity(user.getId());
		if (!ValidateUtil.isValid(ownRoleIds)) {
			uu.getRoles().clear();
		}else {
			String hql = "from Role r where r.id in ("+StringUtil.arr2Str(ownRoleIds)+")";
			List<Role> roles = roleService.findEntityByHQL(hql);
			uu.setRoles(new HashSet<Role>(roles));
		}
		
	}

	/**
	 * 清除用户授权
	 */
	@Override
	public void clearAuthorize(Integer userId) {
		this.getEntity(userId).getRoles().clear();
	}
	
	
	
}
