/**
 * create on 2012-2-21
 */
package com.feinno.pay.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.feinno.pay.entity.BackMsg;
import com.feinno.security.util.dwz.JsonUtil;
import com.feinno.security.util.dwz.StringUtil;
import com.feinno.security.util.dwz.XmlUtil;

/**
 * @author 孙维维(sundful)
 * 
 */
public class BaseController {
	private static final Log logger = LogFactory.getLog(BaseController.class);

	/**
	 * 构造返回的消息，并给cp返回
	 * 
	 * @param response
	 * @param code
	 * @param msg
	 */
	public void resMsg(HttpServletResponse response, String code, String format) {
		String content = genResMsg(code, null, format);
		responseContent(response, content);
	}

	/**
	 * 构造返回的消息，并给cp返回
	 * 
	 * @param response
	 * @param code
	 * @param msg
	 * @param orderId
	 */
	public void resMsg(HttpServletResponse response, String code,
			String orderId, String format) {
		String content = genResMsg(code, orderId, format);
		responseContent(response, content);
	}

	/**
	 * 返回指定的内容
	 * 
	 * @param response
	 * @param content
	 */
	private void responseContent(HttpServletResponse response, String content) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(content);
			out.flush();
			if (logger.isDebugEnabled()) {
				logger.debug("[back to cp]=====>\n" + content);
			}
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * 生成response的xml
	 * 
	 * @param code
	 * @param msg
	 * @param orderId
	 * @return
	 */
	public static String genResMsg(String code, String orderId, String format) {
		if ("json".equalsIgnoreCase(format)) {
			BackMsg msg = new BackMsg();
			if (!StringUtil.isEmpty(code)) {
				msg.setResultCode(code);
			}
			if (!StringUtil.isEmpty(orderId)) {
				msg.setOrderid(orderId);
			}
			return JsonUtil.toJson(msg);

		} else {
			XmlUtil obj = new XmlUtil("response");
			if (!StringUtil.isEmpty(code)) {
				obj.addSubXmlObject(new XmlUtil("resultCode", code));
			}
			if (!StringUtil.isEmpty(orderId)) {
				obj.addSubXmlObject(new XmlUtil("orderid", orderId));
			}
			return obj.toFormatXml();
		}
	}
}
