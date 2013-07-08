/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.PhoneRegionService.java
 * Class:			PhoneRegionService
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.service;

import java.util.List;

import com.feinno.pay.entity.PhoneRegion;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> Version 2.0.0
 * @since 2013-4-21 下午7:55:07
 */

public interface PhoneRegionService {

	void save(PhoneRegion region);

	void delete(Long id);

	PhoneRegion get(Long id);

	void update(PhoneRegion region);

	List<PhoneRegion> findAll(Page page);

}
