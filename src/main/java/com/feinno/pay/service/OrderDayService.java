/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.OrderDayService.java
 * Class:			OrderDayService
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.service;

import java.util.Date;
import java.util.List;

import com.feinno.pay.entity.OrderDay;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> Version 2.0.0
 * @since 2013-4-21 下午7:55:07
 */

public interface OrderDayService {

	void delete(Long id);

	OrderDay get(Long id);

	void update(OrderDay orderDay);

	List<OrderDay> findAll(Page page);

	List<OrderDay> find(Page page, String channel, Date fromDate, Date toDate);

}
