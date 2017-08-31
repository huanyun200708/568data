package cn.com.hq.entity;

import java.sql.Time;

public class OnboardInfo {

	public OnboardInfo() {
	}

	private String id;
	private String accountid;
	private String onboardtime;
	private String appointtime;
	private String onboardaddress;
	private String appointstatus;
	private String onboardstatus;

	
	
	public OnboardInfo(String id, String accountid, String onboardtime,
			String appointtime, String onboardaddress, String appointstatus,
			String onboardstatus) {
		super();
		this.id = id;
		this.accountid = accountid;
		this.onboardtime = onboardtime;
		this.appointtime = appointtime;
		this.onboardaddress = onboardaddress;
		this.appointstatus = appointstatus;
		this.onboardstatus = onboardstatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
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
		this.onboardstatus = onboardstatus;
	}

	@Override
	public String toString() {
		return "id=" + id + "accountid=" + accountid + "onboardtime="
				+ onboardtime.toString() + "onboardaddress=" + onboardaddress;
	}

}
