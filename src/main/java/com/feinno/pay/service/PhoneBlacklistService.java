/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.PhoneBlacklistService.java
 * Class:			PhoneBlacklistService
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.service;

import java.util.List;

import com.feinno.pay.entity.PhoneBlacklist;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> Version 2.0.0
 * @since 2013-4-21 下午7:55:07
 */

public interface PhoneBlacklistService {

	void save(PhoneBlacklist blacklist);

	void delete(Long id);

	PhoneBlacklist get(Long id);

	void update(PhoneBlacklist blacklist);

	List<PhoneBlacklist> findAll(Page page);

}
