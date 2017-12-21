package com.feng.surveypark.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.User;
import com.feng.surveypark.domain.security.Role;
import com.feng.surveypark.service.RoleService;
import com.feng.surveypark.service.UserService;

/**
 * 用户授权action
 * @author 冯思伟
 *
 */
@Controller
@Scope("prototype")
public class UserAuthorizeAction extends BaseAction<User> {
	private static final long serialVersionUID = 6613402081245748876L;
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	private List<User> allUsers;
	private Integer userId;
	//没有的权限集合
	private List<Role> noOwnRoles;
	//授权极限集合id
	private Integer[] ownRoleIds;
	/**
	 * 返回全部用户
	 */
	public String findAllUsers() {
		this.allUsers = userService.findAllEntities();
		return "userAuthorizeListPage";
	} 
	
	/**
	 * 编辑授权
	 */
	public String editAuthorize() {
		this.model = userService.getEntity(userId);
		noOwnRoles = roleService.findRolesNotInRange(model.getRoles());	 
		return "usertAuthorizePage";
	}
	
	/**
	 * 修改授权
	 */
	public String updateAuthorize() {
		userService.updateAuthorize(model,ownRoleIds);
		return "findAllUsersAction";
		
	}
	
	public String clearAuthorize() {
		userService.clearAuthorize(userId);
		return "findAllUsersAction";
	}
	
	
	
//--------------------------------------------------------------------------------------------------------	
	
	
	    
	
	public Integer[] getOwnRoleIds() {
		return ownRoleIds;
	}

	public void setOwnRoleIds(Integer[] ownRoleIds) {
		this.ownRoleIds = ownRoleIds;
	}

	public List<Role> getNoOwnRoles() {
		return noOwnRoles;
	}

	public void setNoOwnRoles(List<Role> noOwnRoles) {
		this.noOwnRoles = noOwnRoles;
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}