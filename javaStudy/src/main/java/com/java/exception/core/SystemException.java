package com.java.exception.core;


import com.java.exception.ErrorCode;

/**
 * 系统异常，与用户无关的异常都必须继承自此。
 */
public class SystemException extends AppRuntimeException {

	private static final long serialVersionUID = 1L;

	public SystemException() {
		super();
	}

	public SystemException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SystemException(String arg0) {
		super(arg0);
	}

	public SystemException(ErrorCode errorCode) {
		super(errorCode);
	}
}
