/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.dao.RegionDAO.java
 * Class:			RegionDAO
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.feinno.pay.entity.Region;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a>
 * @version 2.0.0
 * @since 2013-4-21 下午7:53:36
 */

public interface RegionDAO extends JpaRepository<Region, Long>,
		JpaSpecificationExecutor<Region> {

}
