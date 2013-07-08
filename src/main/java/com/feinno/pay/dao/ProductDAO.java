/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.dao.ProductDAO.java
 * Class:			ProductDAO
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.feinno.pay.entity.Product;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a>
 * @version 2.0.0
 * @since 2013-4-21 下午7:53:36
 */

public interface ProductDAO extends JpaRepository<Product, Long>,
		JpaSpecificationExecutor<Product> {

	Page<Product> findByCodeContaining(String code, Pageable pageable);

	Product findByCode(String code);

}
