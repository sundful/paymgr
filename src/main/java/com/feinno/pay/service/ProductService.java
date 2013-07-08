/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.ProductService.java
 * Class:			ProductService
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.service;

import java.util.List;

import com.feinno.pay.entity.Product;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> Version 2.0.0
 * @since 2013-4-21 下午7:55:07
 */

public interface ProductService {

	void save(Product product) throws ExistedException;

	void delete(Long id) throws ServiceException;

	Product get(Long id);

	void update(Product product);

	List<Product> findAll(Page page);
	
	List<Product> find(Page page, String keywords);

}
