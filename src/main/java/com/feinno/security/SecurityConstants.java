/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, feinno.com
 * Filename:		com.feinno.security.SecurityConstants.java
 * Class:			SecurityConstants
 * Date:			2012-8-9
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.feinno.security;


/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * @version  1.1.0
 * @since   2012-8-9 上午10:47:08 
 */

public interface SecurityConstants {
	/**
	 * 登录用户
	 */
	public final static String LOGIN_USER = "login_user";    
	
	/**
	 * 验证码
	 */
	public final static String CAPTCHA_KEY = "captcha_key";
	
	/**
	 * 日志参数
	 */
	public final static String LOG_ARGUMENTS = "log_arguments";
	
	/**
	 * ResponseBody注解返回的mapModel。
	 */
	public final static String MODEL_MAP = "model_map";
}
