/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, feinno.com
 * Filename:		com.feinno.security.dao.UserRoleDao.java
 * Class:			UserRoleDao
 * Date:			2012-8-7
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feinno.security.entity.main.UserRole;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  1.1.0
 * @since   2012-8-7 下午5:08:15 
 */

public interface UserRoleDAO extends JpaRepository<UserRole, Long> {
	List<UserRole> findByUserId(Long userId);
}
