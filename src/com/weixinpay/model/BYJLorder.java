package com.weixinpay.model;


public class BYJLorder {

	private BYJLOrderResult result;
	private String reason;
	private String error_code;


	public BYJLOrderResult getResult() {
		return result;
	}

	public void setResult(BYJLOrderResult result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

}
