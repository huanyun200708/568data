package com.weixinpay.model;

public class OrderInfoView {
	private String userid;
	private String openid;
	private String orderid;
	private int fee;
	private String paytime;
	private String ip;
	private String title;
	private String content;
	private int confirmTime;
	private String queryType;
	private String querycondition;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(int confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQuerycondition() {
		return querycondition;
	}
	public void setQuerycondition(String querycondition) {
		this.querycondition = querycondition;
	}

	
}
