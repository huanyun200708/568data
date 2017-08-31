package cn.com.hq.vo;

import java.sql.Time;

public class OnboardInfoVO {

	public OnboardInfoVO() {
	}

	private String id;
	private String accountid;
	private String accountname;
	private String appointtime;
	private String onboardtime;
	private String onboardaddress;
	private String appointstatus;
	private String onboardstatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getOnboardtime() {
		return onboardtime;
	}

	public void setOnboardtime(String onboardtime) {
		this.onboardtime = onboardtime;
	}

	public String getOnboardaddress() {
		return onboardaddress;
	}

	public void setOnboardaddress(String onboardaddress) {
		this.onboardaddress = onboardaddress;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAppointtime() {
		return appointtime;
	}

	public void setAppointtime(String appointtime) {
		this.appointtime = appointtime;
	}

	public String getAppointstatus() {
		return appointstatus;
	}

	public void setAppointstatus(String appointstatus) {
		this.appointstatus = appointstatus;
	}

	public String getOnboardstatus() {
		return onboardstatus;
	}

	public void setOnboardstatus(String onboardstatus) {
		this.onboardstatus =onboardstatus;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	@Override
	public String toString() {
		return "id=" + id + "accountname=" + accountname + "onboardtime="
				+ onboardtime.toString() + "onboardaddress=" + onboardaddress;
	}

}
