/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, feinno.com
 * Filename:		com.feinno.security.service.UserService.java
 * Class:			UserService
 * Date:			2012-8-7
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security.service;

import java.util.List;

import com.feinno.security.entity.main.User;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.util.dwz.Page;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  1.1.0
 * @since   2012-8-7 下午3:03:59 
 */

public interface UserService {
	
	User get(String username);
	
	List<User> find(Page page, String name);

	void update(User user);

	void save(User user) throws ExistedException;

	User get(Long id);

	void delete(Long id) throws ServiceException;

	List<User> findAll(Page page);
}
