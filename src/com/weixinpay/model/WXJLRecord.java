package com.weixinpay.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.hq.util.StringUtil;

public class WXJLRecord {
	private String content;
	private String date;
	private String materal;
	private String mileage;
	private String payType;
	private String type;
	private String remark;
	private String mainTainDate;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		if(!StringUtil.isEmpty(date)){
			 DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");  
			 DateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");  
			 try {
	            Date date1 = sdf1.parse(date);
	            date = sdf2.format(date1);
            } catch (ParseException e) {
	            e.printStackTrace();
            }
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMateral() {
		return materal;
	}

	public void setMateral(String materal) {
		this.materal = materal;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMainTainDate() {
		return mainTainDate;
	}

	public void setMainTainDate(String mainTainDate) {
		this.mainTainDate = mainTainDate;
	}

}
