package com.feng.surveypark.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.security.Right;
import com.feng.surveypark.service.RightService;

/**
 * 权限
 * @author feng3
 *
 */
@Controller
@Scope("prototype")
public class RightAction extends BaseAction<Right> {
	private static final long serialVersionUID = 6391443155795320663L;
	 
	private List<Right> allRights ; 
	private Integer rightId;
	
	@Resource
	private RightService rightService;

	/**
	 * 查询全部权限
	 */
	public String findAllRights(){
		this.allRights = rightService.findAllEntities();
		
		return "rightListPage";
	}
	
	
	/**
	 * 转跳添加权限页面
	 */
	
	public String toAddRightPage(){
		return "addRightPage";
	}
	
	/**
	 * 保存更新
	 */
	public String saveOrUpdateRight() {
		rightService.saveOrUpdateRight(model);
		return "findAllRightsAction";
	}
	
	/**
	 * 编辑权限
	 */
	public String editRight() {
		this.model = rightService.getEntity(rightId);
		return "editRightPage";
		
	}
	/**
	 * 删除权限
	 */
	public String deleteRight() {
		Right right = new Right(); 
		right.setId(rightId);
		rightService.deleteEntity(right);
		return "findAllRightsAction";
	}
	/**
	 * 批量更改权限
	 */
	public String batchUpdateRights() {
		rightService.batchUpdateRights(allRights);
		return "findAllRightsAction";
	}
	
	
	
	//_________________________________________________
	public List<Right> getAllRights() {
		return allRights;
	}

	public void setAllRights(List<Right> allRights) {
		this.allRights = allRights;
	}


	public Integer getRightId() {
		return rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	} 
	
	

}
