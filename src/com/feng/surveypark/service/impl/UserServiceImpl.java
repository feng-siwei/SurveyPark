package com.feng.surveypark.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feng.surveypark.dao.BaseDao;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.UserService;
import com.feng.surveypark.util.DataUtil;
import com.feng.surveypark.util.ValidateUtil;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements
		UserService {

	/**
	 * 重新userDao,覆盖注解(non-Javadoc)
	 */
	
	@Override
	@Resource(name="userDao")
	public void setDao(BaseDao<User> dao) {
		// TODO Auto-generated method stub
		super.setDao(dao);
	}
	

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

	
	
	
	


}
