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

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;

import com.feinno.pay.entity.Channel;
import com.feinno.pay.entity.Order;
import com.feinno.pay.service.ChannelService;
import com.feinno.pay.service.OrderService;
import com.feinno.security.log.Log;
import com.feinno.security.log.LogLevel;
import com.feinno.security.log.LogMessageObject;
import com.feinno.security.log.impl.LogUitl;
import com.feinno.security.shiro.ShiroDbRealm;
import com.feinno.security.util.dwz.AjaxObject;
import com.feinno.security.util.dwz.DateUtils;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a>
 * @version 2.0.0
 * @since 2013-4-21 下午8:43:54
 */
@Controller
@RequestMapping("/management/paymgr/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private Validator validator;

	private static final String UPDATE = "management/paymgr/order/update";
	private static final String LIST = "management/paymgr/order/list";
	private static final String CP_LIST = "management/paymgr/order/cp_list";

	/**
	 * LogMessageObject的ignore用法实例，ignore不会记录日志。
	 */
	@RequiresPermissions("Order:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Order order = orderService.get(id);
		map.put("order", order);

		return UPDATE;
	}

	/**
	 * Log的level用法实例
	 * 1.level分为三层，默认包层（rootLogLevel默认值TRACE），自定义包层（customLogLevel）
	 * ，具体方法层（@Log默认值TRACE）
	 * <p/>
	 * 2.参考顺序：默认包层->自定义包层->具体方法层->LogMessageObject
	 * <p/>
	 * 3.有自定义包层的level等级会忽略默认包层
	 * <p/>
	 * 4.@Log的level大于等于自定义包层或者默认的level会输出日志；小于则不会。
	 */
	@Log(message = "Log的level用法实例，LogLevel.TRACE小于自定义包层LogLevel.INFO，不会输出日志。", level = LogLevel.TRACE)
	@RequiresPermissions("Order:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(Order order) {
		BeanValidators.validateWithException(validator, order);
		orderService.update(order);

		return AjaxObject.newOk("订单修改成功！").toString();
	}

	@RequiresPermissions("Order:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		orderService.delete(id);

		return AjaxObject.newOk("订单删除成功！").setCallbackType("").toString();
	}

	/**
	 * Log的override用法实例 假如override为true，会忽略掉level
	 * 
	 * 批量删除展示
	 */
	@Log(message = "Log的override用法实例，override为true，会忽略掉level。删除了{0}订单。", level = LogLevel.TRACE, override = true)
	@RequiresPermissions("Order:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMany(Long[] ids) {
		String[] titles = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			Order order = orderService.get(ids[i]);
			orderService.delete(order.getId());
		}

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { Arrays.toString(titles) }));
		return AjaxObject.newOk("订单删除成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("Order:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Page page, String channel, String status,
			String orderId, String fromDate, String toDate,
			Map<String, Object> map) {
		boolean orderStatus = true;
		List<Order> orders = null;
		Date start = null;
		Date end = null;
		if (StringUtils.isNotBlank(fromDate)) {
			start = DateUtils.getDate(fromDate, null);
		}
		if (StringUtils.isNotBlank(toDate)) {
			end = DateUtils.getDate(toDate, null);
		}
		if (StringUtils.isNotBlank(status)) {
			orderStatus = new Boolean(status);
		}
		orders = orderService.find(page, channel, orderStatus, orderId, start,
				end);

		List<Channel> channels = this.channelService.findAll();

		map.put("page", page);
		map.put("orders", orders);
		map.put("channels", channels);
		map.put("channel", channel);
		map.put("status", status);
		map.put("orderId", orderId);
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
	@RequiresPermissions("CPOrder:view")
	@RequestMapping(value = "/cplist", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String cpList(Page page, String orderId, String fromDate,
			String toDate, Map<String, Object> map) {

		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject
				.getPrincipal();
		String channel = shiroUser.getLoginName();

		List<Order> orders = null;
		Date start = null;
		Date end = null;
		if (StringUtils.isNotBlank(fromDate)) {
			start = DateUtils.getDate(fromDate, null);
		}
		if (StringUtils.isNotBlank(toDate)) {
			end = DateUtils.getDate(toDate, null);
		}
		orders = orderService.find(page, channel, true, orderId, start, end);

		map.put("page", page);
		map.put("orders", orders);
		map.put("orderId", orderId);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);

		return CP_LIST;
	}
}
