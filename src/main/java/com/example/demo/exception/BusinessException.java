package com.example.demo.exception;

/**
 * @ClassName: com.example.demo.exception.BusinessException
 * @Description: TODO(自定义的业务异常)
 * @date 2017年7月27日 下午1:29:33
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5656216210816870423L;

	private String errorMsg;

	public BusinessException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
