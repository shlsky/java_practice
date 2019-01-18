package com.java.exception.core;


import com.java.exception.ErrorCode;

import java.io.Serializable;

/**
 * 应用运行时异常，继承自RuntimeException，增加错误码字段，系统中所有的自定义异常都必须从此类中
 * 或此类的子类继承。在实际开发中，编码人员只需要从BusinessException或SystemException继承。
 */
public class AppRuntimeException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	/**异常编码*/
	private Integer errCode;
	/**异常信息*/
	private String message;

	public AppRuntimeException() {
		super();
	}
	public AppRuntimeException(String arg0) {
		super(arg0);
		this.message = arg0;
	}
	public AppRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.message = arg0;
	}

	public AppRuntimeException(ErrorCode errorCode) {
		this.errCode = errorCode.code;
		this.message = errorCode.msg;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
