package com.wanma.eichong.assets.utils;

import java.io.Serializable;

public class BaseResultDTO implements Serializable {

	private static final long serialVersionUID = -7267660449414705110L;

	protected boolean success = true;
	protected String resultCode;
	protected String errorDetail;

	protected Object obj;

	public BaseResultDTO(){

	}

	public BaseResultDTO(boolean success, String resultCode, String errorDetail){
		this.success = success;
		this.resultCode = resultCode;
		this.errorDetail = errorDetail;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public boolean isFailed() {
		return !this.success;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
