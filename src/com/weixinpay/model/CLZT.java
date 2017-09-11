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

public class CLZT {

	private CLZTResult result;
	private String reason;
	private String error_code;

	public CLZTResult getResult() {
		return result;
	}

	public void setResult(CLZTResult result) {
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

	public void translate(CLZT c) {
		c.getResult().setState(c.getResult().getState());
		c.getResult().setProperties(c.getResult().getProperties());
		c.getResult().setColor(c.getResult().getColor());
		c.getResult().setFuel(c.getResult().getFuel());
		c.getResult().setVehicleType(c.getResult().getVehicleType());

	}
	 public static String queryResult(HttpServletRequest request,OrderInfo order,int memberLevel){
		 	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		 	String queryResult = "";
			order.setBody("Vehicle status query");
			String number = request.getParameter("number");
			String type = request.getParameter("type");
			String clzturl = QueryAppKeyLib.cheliangzhuangtaiQueryUrl+"key="+QueryAppKeyLib.cheliangzhuangtaiQueryAppKey+"&number="+number;
			if(!StringUtil.isEmpty(type)){
				//clzturl = clzturl+"&type="+type;
			}
			HttpGet clzthttpGet = new HttpGet(clzturl);
	        //设置请求器的配置
			try {
				HttpClient clzthttpClient = SSLUtil.getHttpClient();
		        HttpResponse clztres = clzthttpClient.execute(clzthttpGet);
		        HttpEntity clztentity = clztres.getEntity();
		        String clztresult = EntityUtils.toString(clztentity, "UTF-8");
		        System.out.println(clztresult);
		        queryResult = clztresult;
				/*queryResult = Data.CLZT.replaceAll("\\s+", " ");*/
				CLZT c = gson.fromJson(queryResult, CLZT.class);
				if(!"0".equals(c.error_code)){
		        	return "{\"errormassage\":\""+c.reason+"\"}";
		        }
				try {
					 c.translate(c);
				     queryResult = gson.toJson(c);
				} catch (Exception e) {
					// TODO: handle exception
				}
		       
System.out.println("QueryResult : "+queryResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(memberLevel==0){
				String cheliangzhuangtaiQueryPrice_normal = PropertiesUtils.getPropertyValueByKey("cheliangzhuangtaiQueryPrice_normal");
				order.setTotal_fee(Integer.valueOf(cheliangzhuangtaiQueryPrice_normal));//设置价格
			}else if(memberLevel==1){
				String cheliangzhuangtaiQueryPrice_middle = PropertiesUtils.getPropertyValueByKey("cheliangzhuangtaiQueryPrice_middle");
				order.setTotal_fee(Integer.valueOf(cheliangzhuangtaiQueryPrice_middle));//设置价格
			}else if(memberLevel==2){
				String cheliangzhuangtaiQueryPrice_high = PropertiesUtils.getPropertyValueByKey("cheliangzhuangtaiQueryPrice_high");
				order.setTotal_fee(Integer.valueOf(cheliangzhuangtaiQueryPrice_high));//设置价格
			}
order.setTotal_fee(1);//TODO Test Code
		return queryResult;
	 }
}
