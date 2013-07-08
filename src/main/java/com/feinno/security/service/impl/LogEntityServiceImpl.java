/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, feinno.com
 * Filename:		com.feinno.security.service.impl.LogEntityServiceImpl.java
 * Class:			LogEntityServiceImpl
 * Date:			2013-5-3
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.security.dao.LogEntityDAO;
import com.feinno.security.entity.main.LogEntity;
import com.feinno.security.log.LogLevel;
import com.feinno.security.service.LogEntityService;
import com.feinno.security.util.dwz.Page;
import com.feinno.security.util.dwz.PageUtils;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  2.1.0
 * @since   2013-5-3 下午5:08:05 
 */
@Service
@Transactional
public class LogEntityServiceImpl implements LogEntityService {
	
	@Autowired
	private LogEntityDAO logEntityDAO;

	/**   
	 * @param logEntity  
	 * @see com.feinno.security.service.LogEntityService#save(com.feinno.security.entity.main.LogEntity)  
	 */
	@Override
	public void save(LogEntity logEntity) {
		logEntityDAO.save(logEntity);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.feinno.security.service.LogEntityService#get(java.lang.Long)  
	 */
	@Override
	public LogEntity get(Long id) {
		return logEntityDAO.findOne(id);
	}

	/**   
	 * @param logEntity  
	 * @see com.feinno.security.service.LogEntityService#update(com.feinno.security.entity.main.LogEntity)  
	 */
	@Override
	public void update(LogEntity logEntity) {
		logEntityDAO.save(logEntity);
	}

	/**   
	 * @param id  
	 * @see com.feinno.security.service.LogEntityService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		logEntityDAO.delete(id);
	}

	/**
	 * 
	 * @param logLevel
	 * @param page
	 * @return  
	 * @see com.feinno.security.service.LogEntityService#findByLogLevel(com.feinno.security.log.LogLevel, com.feinno.security.util.dwz.Page)
	 */
	@Override
	public List<LogEntity> findByLogLevel(LogLevel logLevel, Page page) {
		org.springframework.data.domain.Page<LogEntity> springDataPage = 
				logEntityDAO.findByLogLevel(logLevel, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**   
	 * @return  
	 * @see com.feinno.security.service.LogEntityService#findAll()  
	 */
	@Override
	public List<LogEntity> findAll() {
		return logEntityDAO.findAll();
	}

	/**
	 * 
	 * @param specification
	 * @param page
	 * @return  
	 * @see com.feinno.security.service.LogEntityService#findByExample(org.springframework.data.jpa.domain.Specification, com.feinno.security.util.dwz.Page)
	 */
	@Override
	public List<LogEntity> findByExample(
			Specification<LogEntity> specification, Page page) {
		org.springframework.data.domain.Page<LogEntity> springDataPage = 
				logEntityDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
