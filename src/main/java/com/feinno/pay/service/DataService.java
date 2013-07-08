/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.DataService.java
 * Class:			DataService
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.service;

import java.util.List;

import com.feinno.pay.entity.Data;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> Version 2.0.0
 * @since 2013-4-21 下午7:55:07
 */

public interface DataService {

	void save(Data data) throws ExistedException;

	void delete(Long id) throws ServiceException;
	
	void delete(Data data) throws ServiceException;

	Data get(Long id);

	void update(Data data);

	List<Data> findAll(Page page);
	
	List<Data> find(Page page, String keywords);

	List<Data> findAll();

}
