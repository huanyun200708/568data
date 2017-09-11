package com.weixinpay.model;

public class TBXXSaveQuote {

	private String Source                ;
	private String CheSun                ;
	private String SanZhe                ;
	private String DaoQiang              ;
	private String SiJi                  ;
	private String ChengKe               ;
	private String BoLi                  ;
	private String HuaHen                ;
	private String SheShui               ;
	private String ZiRan                 ;
	private String BuJiMianCheSun        ;
	private String BuJiMianSanZhe        ;
	private String BuJiMianDaoQiang      ;
	private String BuJiMianChengKe       ;
	private String BuJiMianSiJi          ;
	private String BuJiMianHuaHen        ;
	private String BuJiMianSheShui       ;
	private String BuJiMianZiRan         ;
	private String BuJiMianJingShenSunShi;
	private String HcSanFangTeYue        ;
	private String HcJingShenSunShi      ;
	private String HcXiuLiChang          ;
	private String HcXiuLiChangType      ;
	private String Fybc                  ;
	private String FybcDays              ;
	private String SheBeiSunShi          ;
	private String BjmSheBeiSunShi       ;
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		switch (source) {
			case "1": Source="太平洋";break;
			case "2": Source="平安";break;
			case "4": Source="人保";break;
			case "8": Source="国寿财";break;
			case "16": Source="中华联合";break;
			case "32": Source="大地";break;
			case "64": Source="阳光";break;
			case "128": Source="太平保险";break;
			case "256": Source="华安";break;
			case "512": Source="天安";break;
			case "1024": Source="英大";break;
			case "2048": Source="安盛天平";break;
		}
	}
	public String getCheSun() {
		return CheSun;
	}
	public void setCheSun(String cheSun) {
		CheSun = cheSun;
	}
	public String getSanZhe() {
		return SanZhe;
	}
	public void setSanZhe(String sanZhe) {
		SanZhe = sanZhe;
	}
	public String getDaoQiang() {
		return DaoQiang;
	}
	public void setDaoQiang(String daoQiang) {
		DaoQiang = daoQiang;
	}
	public String getSiJi() {
		return SiJi;
	}
	public void setSiJi(String siJi) {
		SiJi = siJi;
	}
	public String getChengKe() {
		return ChengKe;
	}
	public void setChengKe(String chengKe) {
		ChengKe = chengKe;
	}
	public String getBoLi() {
		return BoLi;
	}
	public void setBoLi(String boLi) {
		BoLi = boLi;
		//	switch (boLi) {
	//		case "0": BoLi="不投保";break;
	//		case "1": BoLi="1国产";break;
	//		case "2": BoLi="进口";break;
	//	}
	}
	public String getHuaHen() {
		return HuaHen;
	}
	public void setHuaHen(String huaHen) {
		HuaHen = huaHen;
	}
	public String getSheShui() {
		return SheShui;
	}
	public void setSheShui(String sheShui) {
		SheShui = sheShui;
	}
	public String getZiRan() {
		return ZiRan;
	}
	public void setZiRan(String ziRan) {
		ZiRan = ziRan;
	}
	public String getBuJiMianCheSun() {
		return BuJiMianCheSun;
	}
	public void setBuJiMianCheSun(String buJiMianCheSun) {
		BuJiMianCheSun = buJiMianCheSun;
	}
	public String getBuJiMianSanZhe() {
		return BuJiMianSanZhe;
	}
	public void setBuJiMianSanZhe(String buJiMianSanZhe) {
		BuJiMianSanZhe = buJiMianSanZhe;
	}
	public String getBuJiMianDaoQiang() {
		return BuJiMianDaoQiang;
	}
	public void setBuJiMianDaoQiang(String buJiMianDaoQiang) {
		BuJiMianDaoQiang = buJiMianDaoQiang;
	}
	public String getBuJiMianChengKe() {
		return BuJiMianChengKe;
	}
	public void setBuJiMianChengKe(String buJiMianChengKe) {
		BuJiMianChengKe = buJiMianChengKe;
	}
	public String getBuJiMianSiJi() {
		return BuJiMianSiJi;
	}
	public void setBuJiMianSiJi(String buJiMianSiJi) {
		BuJiMianSiJi = buJiMianSiJi;
	}
	public String getBuJiMianHuaHen() {
		return BuJiMianHuaHen;
	}
	public void setBuJiMianHuaHen(String buJiMianHuaHen) {
		BuJiMianHuaHen = buJiMianHuaHen;
	}
	public String getBuJiMianSheShui() {
		return BuJiMianSheShui;
	}
	public void setBuJiMianSheShui(String buJiMianSheShui) {
		BuJiMianSheShui = buJiMianSheShui;
	}
	public String getBuJiMianZiRan() {
		return BuJiMianZiRan;
	}
	public void setBuJiMianZiRan(String buJiMianZiRan) {
		BuJiMianZiRan = buJiMianZiRan;
	}
	public String getBuJiMianJingShenSunShi() {
		return BuJiMianJingShenSunShi;
	}
	public void setBuJiMianJingShenSunShi(String buJiMianJingShenSunShi) {
		BuJiMianJingShenSunShi = buJiMianJingShenSunShi;
	}
	public String getHcSanFangTeYue() {
		return HcSanFangTeYue;
	}
	public void setHcSanFangTeYue(String hcSanFangTeYue) {
		HcSanFangTeYue = hcSanFangTeYue;
	}
	public String getHcJingShenSunShi() {
		return HcJingShenSunShi;
	}
	public void setHcJingShenSunShi(String hcJingShenSunShi) {
		HcJingShenSunShi = hcJingShenSunShi;
	}
	public String getHcXiuLiChang() {
		return HcXiuLiChang;
	}
	public void setHcXiuLiChang(String hcXiuLiChang) {
		HcXiuLiChang = hcXiuLiChang;
	}
	public String getHcXiuLiChangType() {
		return HcXiuLiChangType;
	}
	public void setHcXiuLiChangType(String hcXiuLiChangType) {
		HcXiuLiChangType = hcXiuLiChangType;
		switch (hcXiuLiChangType) {
		case "-1": HcXiuLiChangType="不投保";break;
		case "0": HcXiuLiChangType="国产";break;
		case "1": HcXiuLiChangType="进口";break;
	}
	}
	public String getFybc() {
		return Fybc;
	}
	public void setFybc(String fybc) {
		Fybc = fybc;
	}
	public String getFybcDays() {
		return FybcDays;
	}
	public void setFybcDays(String fybcDays) {
		FybcDays = fybcDays;
	}
	public String getSheBeiSunShi() {
		return SheBeiSunShi;
	}
	public void setSheBeiSunShi(String sheBeiSunShi) {
		SheBeiSunShi = sheBeiSunShi;
	}
	public String getBjmSheBeiSunShi() {
		return BjmSheBeiSunShi;
	}
	public void setBjmSheBeiSunShi(String bjmSheBeiSunShi) {
		BjmSheBeiSunShi = bjmSheBeiSunShi;
	}
	
	

}
