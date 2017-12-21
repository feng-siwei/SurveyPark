package com.feng.surveypark.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.security.Right;
import com.feng.surveypark.domain.security.Role;
import com.feng.surveypark.service.RightService;
import com.feng.surveypark.service.RoleService;

/**
 * 角色action
 * @author feng3
 *
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	private static final long serialVersionUID = 4801260877970478552L;
	
	private List<Role> allRoles;
	//角色没有的权限集合
	private List<Right> noOwnRights;
	//获取权限id的数组
	private Integer[] ownRightIds;
	//角色ID
	private Integer roleId;
	
	@Resource
	private RoleService roleService;
	@Resource
	private RightService rightService;
	

	/**
	 * 查询所有角色
	 */
	public String findAllRoles() {
		this.allRoles = roleService.findAllEntities();
		return "roleListPage";
	}
	
	/**
	 * 添加新角色
	 */
	public String toAddRolePage() {
		this.noOwnRights = rightService.findAllEntities(); 
		return "addRolePage";
	}
	
	/**
	 * 保存或修改角色
	 * @return
	 */
	public String saveOrUpdateRole() {
		roleService.saveOrUpdateRole(model , ownRightIds);
		return "findAllRolesAction";
	}

	/**
	 * 编辑权限
	 */
	public String editRole() {
		model =  this.roleService.getEntity(roleId);
		this.noOwnRights = rightService.findRightsNotRole(model.getRights());
		return "editRolePage";
		
	}
	
	/**
	 * 删除权限
	 */
	public String deleteRole() {
		Role role  = new Role();
		role.setId(roleId);
		roleService.deleteEntity(role);
		return "findAllRolesAction";
	}
	
	
	
	
	
	//________________________________________________
	
	public Integer[] getOwnRightIds() {
		return ownRightIds;
	}

	public void setOwnRightIds(Integer[] ownRightIds) {
		this.ownRightIds = ownRightIds;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}

	public List<Right> getNoOwnRights() {
		return noOwnRights;
	}

	public void setNoOwnRights(List<Right> noOwnRights) {
		this.noOwnRights = noOwnRights;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
