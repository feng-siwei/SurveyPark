package com.feng.surveypark.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feng.surveypark.dao.BaseDao;
import com.feng.surveypark.domain.security.Right;
import com.feng.surveypark.domain.security.Role;
import com.feng.surveypark.service.RightService;
import com.feng.surveypark.service.RoleService;
import com.feng.surveypark.util.DataUtil;
import com.feng.surveypark.util.ValidateUtil;
/**
 * 角色实现类
 * @author feng3
 *
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	@Resource
	private RightService rightService ;
	
	//重写getDao方法,注入
	@Override
	@Resource(name = "roleDao")
	public void setDao(BaseDao<Role> dao) {
		super.setDao(dao);
	}
	
	

	/**
	 * 保存/更新角色
	 * @param role 角色
	 * @param ownRightIds 获取权限id的数组
	 */
	@Override
	public void saveOrUpdateRole(Role role, Integer[] ownRightIds) {
		if(!ValidateUtil.isValid(ownRightIds)){
			//角色权限清空
			role.getRights().clear();
		}else {
			List<Right> rights = rightService.findRightsInRange(ownRightIds);
			role.setRights(new HashSet<Right>(rights));
		}
		this.sayeOrUpdateEntity(role);
	}

	/**
	 * 查询不在指定范围类的角色集合
	 * @param roles 已有角色
	 * @return 未有角色
	 */
	@Override
	public List<Role> findRolesNotInRange(Set<Role> roles) {
		if (!ValidateUtil.isValid(roles)) {
			return this.findAllEntities();
		}else {
			String hql = "from Role r where r.id not in ("+DataUtil.extractEntityIds(roles)+")";  
			return this.findEntityByHQL(hql);
		}
	}
	
}
