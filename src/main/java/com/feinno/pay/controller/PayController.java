package com.feinno.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.feinno.pay.entity.Channel;
import com.feinno.pay.entity.Order;
import com.feinno.pay.service.ChannelService;
import com.feinno.pay.service.OrderService;
import com.feinno.security.util.dwz.EmagUtil.StatusCode;
import com.feinno.security.util.dwz.StringUtil;

@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {

	protected static final Logger logger = LoggerFactory
			.getLogger(PayController.class);

	@Autowired
	protected ChannelService channelService;

	@Autowired
	protected OrderService orderService;

	@RequestMapping(value = "/applyforpurchase", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void reqbuy(
			@RequestParam(value = "cp", defaultValue = "", required = true) String cp,
			@RequestParam(value = "phone", defaultValue = "", required = true) String phone,
			@RequestParam(value = "money", defaultValue = "", required = true) String money,
			@RequestParam(value = "sign", defaultValue = "", required = true) String sign,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format,
			HttpServletResponse response, HttpServletRequest request) {

		long startTime = System.currentTimeMillis(); // 获取开始时间

		// 1.验证参数是否有空或者不合法的
		if (StringUtil.isEmpty(cp, phone, money, sign)) {
			error(response, StatusCode.PARAM_ERROR.getCode(),
					StatusCode.PARAM_ERROR.getMsg(), format);
			return;
		}

		// 2.验证商户信息是否合法
		Channel channel = channelService.findChannel(cp);
		if (channel == null) {
			error(response, StatusCode.CHANNEL_ERROR.getCode(),
					StatusCode.CHANNEL_ERROR.getMsg(), format);
			return;
		}

		// 3.验证支付金额是否合法
		if (!channelService.checkMoneylLegal(channel, money)) {
			error(response, StatusCode.MONEY_ERROR.getCode(),
					StatusCode.MONEY_ERROR.getMsg(), format);
			return;
		}

		// 4.验证签名合法性
		if (!channelService.checkSignLegal(channel, sign)) {
			error(response, StatusCode.SIGN_ERROR.getCode(),
					StatusCode.SIGN_ERROR.getMsg(), format);
			return;
		}

		// 5.请求幻方得到sessionkey
		String sessionKey = channelService.getSessionKey(channel);
		if (StringUtil.isEmpty(sessionKey)) {
			error(response, StatusCode.INTERNAL_ERROR.getCode(),
					StatusCode.INTERNAL_ERROR.getMsg(), format);
			return;
		}

		// 6.请求幻方请求支付接口，并且返回订单号
		String orderId = channelService.getOrderId(channel, sessionKey, phone,
				money);
		if (StringUtil.isEmpty(orderId)) {
			error(response, StatusCode.INTERNAL_ERROR.getCode(),
					StatusCode.INTERNAL_ERROR.getMsg(), format);
			return;
		}

		// 7.保存订单，并返回结果给cp
		orderService.save(cp, phone, money, orderId);
		resMsg(response, StatusCode.SUCCESS_INFO.getCode(), orderId, format);

		long endTime = System.currentTimeMillis(); // 获取结束时间
		if (logger.isDebugEnabled()) {
			logger.debug("reqbuy程序运行时间 =====> " + (endTime - startTime) + "ms");
		}
	}

	@RequestMapping(value = "/confirmpurchase", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void verifycode(
			@RequestParam(value = "cp", defaultValue = "", required = true) String cp,
			@RequestParam(value = "phone", defaultValue = "", required = true) String phone,
			@RequestParam(value = "oid", defaultValue = "", required = true) String orderId,
			@RequestParam(value = "verifycode", defaultValue = "", required = true) String verifyCode,
			@RequestParam(value = "sign", defaultValue = "", required = true) String sign,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format,
			HttpServletResponse response, HttpServletRequest request) {

		long startTime = System.currentTimeMillis(); // 获取开始时间

		// 1.验证参数是否有空或者不合法的
		if (StringUtil.isEmpty(cp, phone, orderId, verifyCode, sign)) {
			error(response, StatusCode.PARAM_ERROR.getCode(),
					StatusCode.PARAM_ERROR.getMsg(), format);
			return;
		}

		// 2.验证商户信息是否合法
		Channel channel = channelService.findChannel(cp);
		if (channel == null) {
			error(response, StatusCode.CHANNEL_ERROR.getCode(),
					StatusCode.CHANNEL_ERROR.getMsg(), format);
			return;
		}

		// 3.验证签名合法性
		if (!channelService.checkSignLegal(channel, sign)) {
			error(response, StatusCode.SIGN_ERROR.getCode(),
					StatusCode.SIGN_ERROR.getMsg(), format);
			return;
		}

		// 4.验证订单是否存在
		Order order = this.orderService.checkOrderLegal(phone, orderId);
		if (order == null) {
			error(response, StatusCode.ORDER_ERROR.getCode(),
					StatusCode.ORDER_ERROR.getMsg(), format);
			return;
		}

		// 5.请求幻方得到sessionkey
		String sessionKey = channelService.getSessionKey(channel);
		if (StringUtil.isEmpty(sessionKey)) {
			error(response, StatusCode.INTERNAL_ERROR.getCode(),
					StatusCode.INTERNAL_ERROR.getMsg(), format);
			return;
		}

		// 6.请求幻方得到resultCode
		String resultCode = channelService.getResultCode(channel, sessionKey,
				verifyCode, orderId);
		if (StringUtil.isEmpty(resultCode)) {
			error(response, StatusCode.INTERNAL_ERROR.getCode(),
					StatusCode.INTERNAL_ERROR.getMsg(), format);
			return;
		}

		// 7.更新订单,并且返回结果给cp
		this.orderService.update(order, resultCode);
		resMsg(response, resultCode, format);

		long endTime = System.currentTimeMillis(); // 获取结束时间
		if (logger.isDebugEnabled()) {
			logger.debug("verifycode程序运行时间 =====> " + (endTime - startTime)
					+ "ms");
		}
	}

	public void error(HttpServletResponse response, String code, String msg,
			String format) {
		resMsg(response, code, format);
		logger.error("[" + code + " error]=====>" + msg);
	}

}
