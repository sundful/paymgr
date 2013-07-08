/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, feinno.com
 * Filename:		com.feinno.security.service.LogEntityService.java
 * Class:			LogEntityService
 * Date:			2013-5-3
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.feinno.security.entity.main.LogEntity;
import com.feinno.security.log.LogLevel;
import com.feinno.security.util.dwz.Page;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  2.1.0
 * @since   2013-5-3 下午5:07:53 
 */

public interface LogEntityService {
	void save(LogEntity logEntity);
	
	LogEntity get(Long id);
	
	void update(LogEntity logEntity);
	
	void delete(Long id);
	
	List<LogEntity> findByLogLevel(LogLevel logLevel, Page page);
	
	List<LogEntity> findAll();
	
	List<LogEntity> findByExample(Specification<LogEntity> specification, Page page);
}
