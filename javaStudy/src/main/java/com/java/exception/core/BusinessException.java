package com.java.exception.core;

import com.java.exception.ErrorCode;

/**
 * 业务异常，继承自AppRuntimeException，需要通知用户的异常，必须继承继承此类。
 */
public class BusinessException extends AppRuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
	}

	public BusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BusinessException(String arg0) {
		super(arg0);
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode);
	}
}
