/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, feinno.com
 * Filename:		com.feinno.security.shiro.IncorrectCaptchaException.java
 * Class:			IncorrectCaptchaException
 * Date:			2012-8-7
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.feinno.security.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 
 * @author <a href="mailto:sundful@gmail.com">sundful</a> Version 1.1.0
 * @since 2012-8-7 上午9:22:21
 */

public class IncorrectCaptchaException extends AuthenticationException {
	/** 描述  */
	private static final long serialVersionUID = 6146451562810994591L;

	public IncorrectCaptchaException() {
		super();
	}

	public IncorrectCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCaptchaException(String message) {
		super(message);
	}

	public IncorrectCaptchaException(Throwable cause) {
		super(cause);
	}

}
