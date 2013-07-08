/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2013, feinno.com
 * Filename:		com.feinno.pay.service.ChannelService.java
 * Class:			ChannelService
 * Date:			2013-4-21
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.pay.service;

import java.util.List;

import com.feinno.pay.entity.Channel;
import com.feinno.security.exception.ExistedException;
import com.feinno.security.exception.ServiceException;
import com.feinno.security.util.dwz.Page;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> Version 2.0.0
 * @since 2013-4-21 下午7:55:07
 */

public interface ChannelService {

	public static final String SEPARATOR = "|";

	void save(Channel channel) throws ExistedException;;

	void delete(Long id) throws ServiceException;

	Channel get(Long id);

	void update(Channel channel);
	
	List<Channel> findAll();

	List<Channel> findAll(Page page);

	List<Channel> find(Page page, String keywords);

	Channel findChannel(String code);

	/**
	 * 验证签名的合法性
	 * 
	 * @param cp
	 * @param pd
	 * @param sign
	 * @return
	 */
	boolean checkSignLegal(Channel channel, String sign);

	/**
	 * 验证支付金额的合法性
	 * 
	 * @param cp
	 * @param pd
	 * @param sign
	 * @return
	 */
	boolean checkMoneylLegal(Channel channel, String money);

	/**
	 * 得到sessionkey通过请求幻方
	 * 
	 * @param cp
	 * @param pd
	 * @param sign
	 * @return
	 */
	String getSessionKey(Channel channel);

	/**
	 * 得到orderId通过请求幻方
	 * 
	 * @param cp
	 * @param pd
	 * @param sign
	 * @return
	 */
	String getOrderId(Channel channel, String sessionKey, String phone,
			String money);

	/**
	 * 得到resultCode通过请求幻方
	 * 
	 * @param cp
	 * @param pd
	 * @param sign
	 * @return
	 */
	public String getResultCode(Channel channel, String sessionKey,
			String verifyCode, String orderId);

}
