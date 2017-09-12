package com.weixinpay.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

public class TBXX {

	private TBXXResult result;
	private String reason;
	private String error_code;
	public TBXXResult getResult() {
		return result;
	}
	public void setResult(TBXXResult result) {
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
	public void translate(TBXX t){
		t.getResult().getSaveQuote().setSource(t.getResult().getSaveQuote().getSource());
		t.getResult().getSaveQuote().setHcXiuLiChangType(t.getResult().getSaveQuote().getHcXiuLiChangType());
		
		t.getResult().getUserInfo().setCarUsedType(t.getResult().getUserInfo().getCarUsedType());
		t.getResult().getUserInfo().setIdType(t.getResult().getUserInfo().getIdType());
		t.getResult().getUserInfo().setCityCode(t.getResult().getUserInfo().getCityCode());
		t.getResult().getUserInfo().setFuelType(t.getResult().getUserInfo().getFuelType());
		t.getResult().getUserInfo().setProofType(t.getResult().getUserInfo().getProofType());
		t.getResult().getUserInfo().setLicenseColor(t.getResult().getUserInfo().getLicenseColor());
		t.getResult().getUserInfo().setClauseType(t.getResult().getUserInfo().getClauseType());
		t.getResult().getUserInfo().setRunRegion(t.getResult().getUserInfo().getRunRegion());
		t.getResult().getUserInfo().setInsuredIdType(t.getResult().getUserInfo().getInsuredIdType());
		t.getResult().getUserInfo().setHolderIdType(t.getResult().getUserInfo().getHolderIdType());
		t.getResult().getUserInfo().setIsPublic(t.getResult().getUserInfo().getIsPublic());
		
		
	}
	public static String queryResult(HttpServletRequest request,
			OrderInfo order, int memberLevel) {
		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		 	String queryResult = "";
		String licenseNo = request.getParameter("licenseNo");
		String carVin = request.getParameter("carVin");
		String engineNo = request.getParameter("engineNo");
		String renewalCarType = request.getParameter("renewalCarType");
		String tbxxurl = QueryAppKeyLib.toubaoxinxiQueryUrl+"key="+QueryAppKeyLib.toubaoxinxiQueryAppKey;
		if(!StringUtil.isEmpty(licenseNo)){
			tbxxurl = tbxxurl+"&licenseNo="+licenseNo;
		}
		if(!StringUtil.isEmpty(carVin)){
			tbxxurl = tbxxurl+"&carVin="+carVin;
		}
		if(!StringUtil.isEmpty(engineNo)){
			tbxxurl = engineNo+"&type="+engineNo;
		}
		if(!StringUtil.isEmpty(renewalCarType)){
			tbxxurl = tbxxurl+"&renewalCarType="+renewalCarType;
		}
		HttpGet tbxxhttpGet = new HttpGet(tbxxurl);
        //设置请求器的配置
		try {
		HttpClient tbxxhttpClient = SSLUtil.getHttpClient();
	        HttpResponse tbxxres = tbxxhttpClient.execute(tbxxhttpGet);
	        HttpEntity tbxxentity = tbxxres.getEntity();
	        String tbxxresult = EntityUtils.toString(tbxxentity, "UTF-8");
	        System.out.println(tbxxresult);
	        queryResult = tbxxresult;
	       // queryResult = Data.TBXX.replaceAll("\\s+", " ");
	        try {
	        	TBXX tbxx = gson.fromJson(queryResult, TBXX.class);
	        	if(!"0".equals(tbxx.error_code)){
		        	return "{\"errormassage\":\""+tbxx.reason+"\"}";
		        }
		        tbxx.translate(tbxx);
		        queryResult = gson.toJson(tbxx);
			} catch (Exception e) {
				// TODO: handle exception
			}
	        
System.out.println("QueryResult : "+queryResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		order.setBody("Vehicle insurance information query");
		if(memberLevel==0){
			String toubaoxinxiQueryPrice_normal = PropertiesUtils.getPropertyValueByKey("toubaoxinxiQueryPrice_normal");
			order.setTotal_fee(Integer.valueOf(toubaoxinxiQueryPrice_normal));//设置价格
		}else if(memberLevel==1){
			String toubaoxinxiQueryPrice_middle = PropertiesUtils.getPropertyValueByKey("toubaoxinxiQueryPrice_middle");
			order.setTotal_fee(Integer.valueOf(toubaoxinxiQueryPrice_middle));//设置价格
		}else if(memberLevel==2){
			String toubaoxinxiQueryPrice_high = PropertiesUtils.getPropertyValueByKey("toubaoxinxiQueryPrice_high");
			order.setTotal_fee(Integer.valueOf(toubaoxinxiQueryPrice_high));//设置价格
		}
		return queryResult;
	}
	
}
