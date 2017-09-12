package com.weixinpay.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixinpay.service.PayService;
import com.weixinpay.test.Data;

import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

public class BYJL {

	private BYJLResult result;
	private String reason;
	private String error_code;
	private static PayService payService = new PayService();   

	public BYJLResult getResult() {
		return result;
	}

	public void setResult(BYJLResult result) {
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
			order.setBody("Vehicle status query");
			String vin = request.getParameter("vin");
			String type = request.getParameter("type");
			String orderurl = QueryAppKeyLib.baoyangOrderUrl+"key="+QueryAppKeyLib.baoyangQueryAppKey+"&vin="+vin;
			String url = QueryAppKeyLib.baoyangQueryUrl+"key="+QueryAppKeyLib.baoyangQueryAppKey;
			order.setQueryCondition("vin="+vin);
			String condition = payService.getBYJLqueryCondition(order.getOpenid(), vin);
	        //设置请求器的配置
			String orderid = "";
			try {
				if("".equals(condition) || condition.indexOf("result")>-1){
					HttpGet httpGet = new HttpGet(orderurl);
					HttpClient httpClient = SSLUtil.getHttpClient();
			        HttpResponse res = httpClient.execute(httpGet);
			        HttpEntity entity = res.getEntity();
			        String clztresult = EntityUtils.toString(entity, "UTF-8");
			        System.out.println(clztresult);
			        queryResult = clztresult;
					BYJLorder border = gson.fromJson(queryResult, BYJLorder.class);
					if(!"0".equals(border.getError_code())){
			        	return "{\"errormassage\":\""+border.getReason()+"\"}";
			        }
					orderid = border.getResult().getOrder_id();
					//等待5秒，等待报告生成
					//Thread.sleep(5000);
				}else{
					orderid = condition.replace("&orderId=", "");
				}
				HttpGet httpGet = new HttpGet(url +"&orderId="+ orderid);
				HttpClient httpClient = SSLUtil.getHttpClient();
				HttpResponse res = httpClient.execute(httpGet);
				HttpEntity entity = res.getEntity();
				String clztresult = EntityUtils.toString(entity, "UTF-8");
		        queryResult = clztresult;
				BYJL b = gson.fromJson(queryResult, BYJL.class);
				order.setQueryCondition("vin="+vin);
				
				if(!"0".equals(b.getError_code())){
					order.setQueryResult("&orderId="+orderid);
					payService.insertFinancePay(order);
		        	return "{\"errormassage\":\""+b.getReason()+"\"}";
		        }
				
				queryResult = gson.toJson(b);
System.out.println("QueryResult : "+queryResult);
			} catch (Exception e) {
				e.printStackTrace();
				return "{\"errormassage\":\"请确认输入数据是否正确\"}";
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
		return queryResult;
	 }
}
