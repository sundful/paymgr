/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.security.dao.OrganizationRoleDao.java
 * Class:			OrganizationRoleDao
 * Date:			2013-4-15
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feinno.security.entity.main.OrganizationRole;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  2.0.0
 * @since   2013-4-15 下午4:11:05 
 */

public interface OrganizationRoleDAO extends JpaRepository<OrganizationRole, Long> {
	List<OrganizationRole> findByOrganizationId(Long organizationId);
}
