/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.controller.TaskController.java
 * Class:			TaskController
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.feinno.pay.entity.Channel;
import com.feinno.pay.entity.OrderDay;
import com.feinno.pay.service.ChannelService;
import com.feinno.pay.service.OrderDayService;
import com.feinno.security.shiro.ShiroDbRealm;
import com.feinno.security.util.dwz.DateUtils;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a>
 * @version 2.0.0
 * @since 2013-4-21 下午8:43:54
 */
@Controller
@RequestMapping("/management/paymgr/orderday")
public class OrderDayController {

	@Autowired
	private OrderDayService orderDayService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private Validator validator;

	private static final String LIST = "management/paymgr/orderday/list";
	private static final String CP_LIST = "management/paymgr/orderday/cp_list";

	@RequiresPermissions("OrderDay:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Page page, String channel, String fromDate,
			String toDate, Map<String, Object> map) {
		List<OrderDay> orderDays = null;
		Date start = null;
		Date end = null;
		if (StringUtils.isNotBlank(fromDate)) {
			start = DateUtils.getDate(fromDate, null);
		}
		if (StringUtils.isNotBlank(toDate)) {
			end = DateUtils.getDate(toDate, null);
		}

		orderDays = orderDayService.find(page, channel, start, end);

		List<Channel> channels = this.channelService.findAll();

		map.put("page", page);
		map.put("orderDays", orderDays);
		map.put("channels", channels);
		map.put("channel", channel);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);

		return LIST;
	}

	/**
	 * 给cp合作方展示订单，只显示该cp的订单
	 * 
	 * @param page
	 * @param orderId
	 * @param fromDate
	 * @param toDate
	 * @param map
	 * @return
	 */
	@RequiresPermissions("CPOrderDay:view")
	@RequestMapping(value = "/cplist", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String cpList(Page page, String fromDate, String toDate,
			Map<String, Object> map) {

		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject
				.getPrincipal();
		String channel = shiroUser.getLoginName();

		List<OrderDay> orderDays = null;
		Date start = null;
		Date end = null;
		if (StringUtils.isNotBlank(fromDate)) {
			start = DateUtils.getDate(fromDate, null);
		}
		if (StringUtils.isNotBlank(toDate)) {
			end = DateUtils.getDate(toDate, null);
		}
		orderDays = orderDayService.find(page, channel, start, end);

		map.put("page", page);
		map.put("orderDays", orderDays);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);

		return CP_LIST;
	}
}
