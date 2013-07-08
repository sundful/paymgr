/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.security.service.impl.RolePermissionServiceImpl.java
 * Class:			RolePermissionServiceImpl
 * Date:			2013-4-16
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.security.dao.RolePermissionDAO;
import com.feinno.security.entity.main.RolePermission;
import com.feinno.security.service.RolePermissionService;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  2.0.0
 * @since   2013-4-16 下午2:14:10 
 */
@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {
	
	@Autowired
	private RolePermissionDAO rolePermissionDAO;

	/**   
	 * @param rolePermission  
	 * @see com.feinno.security.service.RolePermissionService#save(com.feinno.security.entity.main.RolePermission)  
	 */
	@Override
	public void save(RolePermission rolePermission) {
		rolePermissionDAO.save(rolePermission);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.feinno.security.service.RolePermissionService#get(java.lang.Long)  
	 */
	@Override
	public RolePermission get(Long id) {
		return rolePermissionDAO.findOne(id);
	}

	/**   
	 * @param rolePermission  
	 * @see com.feinno.security.service.RolePermissionService#update(com.feinno.security.entity.main.RolePermission)  
	 */
	@Override
	public void update(RolePermission rolePermission) {
		rolePermissionDAO.save(rolePermission);
	}

	/**   
	 * @param id  
	 * @see com.feinno.security.service.RolePermissionService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		rolePermissionDAO.delete(id);
	}

	/**   
	 * @param roleId
	 * @return  
	 * @see com.feinno.security.service.RolePermissionService#findByRoleId(java.lang.Long)  
	 */
	@Override
	public List<RolePermission> findByRoleId(Long roleId) {
		return rolePermissionDAO.findByRoleId(roleId);
	}

}
