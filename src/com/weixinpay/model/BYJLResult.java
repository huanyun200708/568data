package com.weixinpay.model;

import java.util.List;

public class BYJLResult {
	private String order_id;
	private String notify_time;
	private String total_mileage;
	private String number_of_accidents;
	private String car_brand;
	private String result_status;
	private List<String> result_report;
	private String last_time_to_shop;
	private List<BYJLcontent> result_content;
	private List<BYJLdescription> result_description;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	public String getTotal_mileage() {
		return total_mileage;
	}
	public void setTotal_mileage(String total_mileage) {
		this.total_mileage = total_mileage;
	}
	public String getNumber_of_accidents() {
		return number_of_accidents;
	}
	public void setNumber_of_accidents(String number_of_accidents) {
		this.number_of_accidents = number_of_accidents;
	}
	public String getCar_brand() {
		return car_brand;
	}
	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}
	public String getResult_status() {
		return result_status;
	}
	public void setResult_status(String result_status) {
		this.result_status = result_status;
	}
	public List<String> getResult_report() {
		return result_report;
	}
	public void setResult_report(List<String> result_report) {
		this.result_report = result_report;
	}
	public String getLast_time_to_shop() {
		return last_time_to_shop;
	}
	public void setLast_time_to_shop(String last_time_to_shop) {
		this.last_time_to_shop = last_time_to_shop;
	}
	public List<BYJLcontent> getResult_content() {
		return result_content;
	}
	public void setResult_content(List<BYJLcontent> result_content) {
		this.result_content = result_content;
	}
	public List<BYJLdescription> getResult_description() {
		return result_description;
	}
	public void setResult_description(List<BYJLdescription> result_description) {
		this.result_description = result_description;
	}
	
	
}
