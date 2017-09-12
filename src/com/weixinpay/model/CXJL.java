package com.weixinpay.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixinpay.test.Data;

public class CXJL {

	private CXJLResult result;
	private String reason;
	private String error_code;
	public CXJLResult getResult() {
		return result;
	}
	public void setResult(CXJLResult result) {
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
	
	public static String queryResult(HttpServletRequest request,OrderInfo order,int memberLevel){
		 	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		 	String queryResult = "";

			//1、先把查询结果缓存到订单记录表里，等待用户付款成功后在返回给用户展示
			String licenseNo = request.getParameter("licenseNo");
			String frameNo = request.getParameter("frameNo");
			String cxjlurl = QueryAppKeyLib.chuxianjiluQueryUrl+"key="+QueryAppKeyLib.chuxianjiluQueryAppKey;
			if(!StringUtil.isEmpty(licenseNo)){
				cxjlurl = cxjlurl+"&licenseNo="+licenseNo;
			}
			if(!StringUtil.isEmpty(frameNo)){
				cxjlurl = cxjlurl+"&frameNo="+frameNo;
			}
			HttpGet cxjhttpGet = new HttpGet(cxjlurl);
			try {
				HttpClient cxjhttpClient = SSLUtil.getHttpClient();
		        HttpResponse cxjles = cxjhttpClient.execute(cxjhttpGet);
		        HttpEntity cxjentity = cxjles.getEntity();
		        String cxjlesult = EntityUtils.toString(cxjentity, "UTF-8");
		        queryResult = cxjlesult;
		        CXJL cxjl = gson.fromJson(queryResult, CXJL.class);
		        if(!"0".equals(cxjl.error_code)){
		        	return "{\"errormassage\":\""+cxjl.reason+"\"}";
		        }
				//queryResult = Data.CXJL.replaceAll("\\s+", " ");
		        //queryResult = "{\"reason\":\"1:没有查到理赔记录\",\"result\":null,\"error_code\":228201}";
System.out.println("QueryResult : "+queryResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//2、设置订单标题和价格
			order.setBody("The vehicle accident records query");
			if(memberLevel==0){
				String chuxianjiluQueryPrice_normal = PropertiesUtils.getPropertyValueByKey("chuxianjiluQueryPrice_normal");
				order.setTotal_fee(Integer.valueOf(chuxianjiluQueryPrice_normal));//设置价格
			}else if(memberLevel==1){
				String chuxianjiluQueryPrice_middle = PropertiesUtils.getPropertyValueByKey("chuxianjiluQueryPrice_middle");
				order.setTotal_fee(Integer.valueOf(chuxianjiluQueryPrice_middle));//设置价格
			}else if(memberLevel==2){
				String chuxianjiluQueryPrice_high = PropertiesUtils.getPropertyValueByKey("chuxianjiluQueryPrice_high");
				order.setTotal_fee(Integer.valueOf(chuxianjiluQueryPrice_high));//设置价格
			}
		return queryResult;
	 }
}
