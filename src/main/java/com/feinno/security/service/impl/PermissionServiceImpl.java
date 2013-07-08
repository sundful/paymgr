/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.security.service.impl.RolePermissionImpl.java
 * Class:			RolePermissionImpl
 * Date:			2013-4-16
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.security.dao.PermissionDAO;
import com.feinno.security.entity.main.Permission;
import com.feinno.security.service.PermissionService;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  2.0.0
 * @since   2013-4-16 下午2:12:41 
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionDAO permissionDAO;

	/**   
	 * @param permission  
	 * @see com.feinno.security.service.PermissionService#save(com.feinno.security.entity.main.Permission)  
	 */
	@Override
	public void save(Permission permission) {
		permissionDAO.save(permission);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.feinno.security.service.PermissionService#get(java.lang.Long)  
	 */
	@Override
	public Permission get(Long id) {
		return permissionDAO.findOne(id);
	}

	/**   
	 * @param permission  
	 * @see com.feinno.security.service.PermissionService#update(com.feinno.security.entity.main.Permission)  
	 */
	@Override
	public void update(Permission permission) {
		permissionDAO.save(permission);
	}

	/**   
	 * @param id  
	 * @see com.feinno.security.service.PermissionService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		permissionDAO.delete(id);
	}
}
