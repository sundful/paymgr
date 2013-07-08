/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.OrderService.java
 * Class:			OrderService
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

import com.feinno.pay.entity.Order;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> Version 2.0.0
 * @since 2013-4-21 下午7:55:07
 */

public interface OrderService {

	void save(Order order);

	void save(String code, String phone, String money, String orderId);

	void delete(Long id);

	Order get(Long id);

	Order get(String orderId);

	void update(Order order);

	void update(Order order, String resultCode);

	Order checkOrderLegal(String phone, String orderId);

	List<Order> findAll(Page page);

	List<Order> find(Page page, String channel, String orderId, Date fromDate,
			Date toDate);

	List<Order> find(Page page, String channel, boolean status, String orderId,
			Date fromDate, Date toDate);
}
