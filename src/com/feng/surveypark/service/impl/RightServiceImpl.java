 package com.feng.surveypark.service.impl;


import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feng.surveypark.dao.BaseDao;
import com.feng.surveypark.domain.security.Right;
import com.feng.surveypark.service.RightService;
import com.feng.surveypark.util.DataUtil;
import com.feng.surveypark.util.StringUtil;
import com.feng.surveypark.util.ValidateUtil;
/**
 * 权限Service实现类
 * @author feng3
 *
 */
@Service("rightService")
public class RightServiceImpl extends BaseServiceImpl<Right> implements RightService{
	//重写DAO
	@Override
	@Resource(name = "rightDao")
	public void setDao(BaseDao<Right> dao) {
		super.setDao(dao);
	}

	/**
	 * 保存更新权限
	 */
	@Override
	public void saveOrUpdateRight(Right model) {
		if (model.getId() == null ) {
			//权限位
			int rightPos = 0 ;
			//权限码
			long rightCode = 1 ;
			
			String hql = "SELECT MAX(r.rightPos),MAX(r.rightCode) FROM Right r "
					+ "WHERE r.rightPos = (SELECT MAX(rr.rightPos) FROM Right rr)";
			//第一位为最大权限位,第二位为最大权限码
			Object [] arr= (Object[]) this.uniqueResult(hql);
			Integer topRightPos = (Integer) arr[0];
			Long topRightCode =  (Long) arr[1];
		
			if (topRightPos == null) {
				rightPos = 0;
				rightCode = 1;
			}else {
				//权限位更新
				rightPos = topRightPos; 
				//权限码是否到达最大值
				if (topRightCode>=(1L << 60)) {
					rightPos = topRightPos+1;
					rightCode = 1;
				}else {
					rightCode = topRightCode << 1;
				}
			}
			  
			model.setRightPos(rightPos);
			model.setRightCode(rightCode);
			
		}
		this.sayeOrUpdateEntity(model);
	}

	/**
	 * 按照URL追加权限
	 */
	@Override
	public void appendRightByURL(String url) {
		String hql = "select count(*) from Right r where r.rightUrl = ?";
		Long count = (Long)this.uniqueResult(hql, url);
		if (count == 0) {
			Right right = new Right();
			right.setRightUrl(url);
			this.saveOrUpdateRight(right);
		}
		
	}

	/**
	 * 批量更改权限
	 */
	@Override
	public void batchUpdateRights(List<Right> allRights) {
		if (ValidateUtil.isValid(allRights)) {
			String hql = "update Right r set r.rightName = ? ,r.common = ? where r.id = ? ";
			for (Right right : allRights) {
				this.batchEntityByHQL(hql, right.getRightName() ,right.isCommon(), right.getId());
			}
		}
		
	}

	/**
	 * 根据给定的id数组返回权限集合
	 */
	@Override
	public List<Right> findRightsInRange(Integer[] ids) {
		if (ValidateUtil.isValid(ids)) {
			String hql = "from Right r where r.id in ("+StringUtil.arr2Str(ids)+")";
			return this.findEntityByHQL(hql);
			
		}
		return null;
	}

	/**
	 * 获得角色没有取的的权限
	 */
	@Override
	public List<Right> findRightsNotRole(Set<Right> rights) {
		if (!ValidateUtil.isValid(rights)) {
			return this.findAllEntities();
		}else {
			String hql = "from Right r where r.id not in ("+DataUtil.extractEntityIds(rights)+")";  
			return this.findEntityByHQL(hql);
		}
	}

	/**
	 * 得到当前最大的权限位
	 */
	@Override
	public int getMaxRightPos() {
		String hql = "select max(r.rightPos) from Right r";
		Integer maxRightPos =  (Integer) this.uniqueResult(hql);
		return maxRightPos == null ? 0:maxRightPos;
	}
	
}
