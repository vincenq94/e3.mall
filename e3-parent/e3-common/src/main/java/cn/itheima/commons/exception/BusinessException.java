package cn.itheima.commons.exception;

public class BusinessException extends RuntimeException {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private String code;
	private String goBackUrl;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, String goBackUrl) {
		super(message);
		this.goBackUrl = goBackUrl;
	}

	public BusinessException(String code, String message, String goBackUrl) {
		super(message);
		this.code = code;
		this.goBackUrl = goBackUrl;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(Throwable cause, String goBackUrl) {
		super(cause);
		this.goBackUrl = goBackUrl;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message, Throwable cause, String goBackUrl) {
		super(message, cause);
		this.goBackUrl = goBackUrl;
	}

	public BusinessException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BusinessException(String code, String message, Throwable cause, String goBackUrl) {
		super(message, cause);
		this.code = code;
		this.goBackUrl = goBackUrl;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGoBackUrl() {
		return this.goBackUrl;
	}

	public void setGoBackUrl(String goBackUrl) {
		this.goBackUrl = goBackUrl;
	}
}
