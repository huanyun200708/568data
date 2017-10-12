package com.weixinpay.model;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixinpay.service.MyRunnable;
import com.weixinpay.service.PayService;

public class BYJL {

	private BYJLResult result;
	private String reason;
	private String error_code;
	private static PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(BYJL.class);
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
	
	public static void setOrderFee(HttpServletRequest request,OrderInfo order,int memberLevel){
		if(memberLevel==0){
			String cheliangzhuangtaiQueryPrice_normal = PropertiesUtils.getPropertyValueByKey("cheliangbaoyangQueryPrice_normal");
			order.setTotal_fee(Integer.valueOf(cheliangzhuangtaiQueryPrice_normal));//设置价格
		}else if(memberLevel==1){
			String cheliangzhuangtaiQueryPrice_middle = PropertiesUtils.getPropertyValueByKey("cheliangbaoyangQueryPrice_middle");
			order.setTotal_fee(Integer.valueOf(cheliangzhuangtaiQueryPrice_middle));//设置价格
		}else if(memberLevel==2){
			String cheliangzhuangtaiQueryPrice_high = PropertiesUtils.getPropertyValueByKey("cheliangbaoyangQueryPrice_high");
			order.setTotal_fee(Integer.valueOf(cheliangzhuangtaiQueryPrice_high));//设置价格
		}
	}

	public static String queryResult(HttpServletRequest request,String orderId){
		 	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		 	OrderInfo order = payService.getQueryOrderByorderId(orderId);
		 	String queryResult = "";
			String vin = request.getParameter("vin");
			String orderurl = QueryAppKeyLib.baoyangOrderUrl+"key="+QueryAppKeyLib.baoyangQueryAppKey+"&vin="+vin;
			String url = QueryAppKeyLib.baoyangQueryUrl+"key="+QueryAppKeyLib.baoyangQueryAppKey;
			order.setQueryCondition("&vin="+vin);
			//order.setOpenid("oUm4A0UA7pG6t-TQUVsLQqRppNl8");
			Map<String,String> resultMap = payService.getBYJLqueryCondition(order.getOpenid(), vin);
			String condition = resultMap.get("condition");
	        //设置请求器的配置
			String orderid = "";
			try {
				if(StringUtil.isEmpty(condition) || condition.indexOf("result")>-1){
					HttpGet httpGet = new HttpGet(orderurl);
					HttpClient httpClient = SSLUtil.getHttpClient();
			        HttpResponse res = httpClient.execute(httpGet);
			        HttpEntity entity = res.getEntity();
			        String clztresult = EntityUtils.toString(entity, "UTF-8");
			        System.out.println(clztresult);
			        queryResult = clztresult;
					BYJLorder border = gson.fromJson(queryResult, BYJLorder.class);
					if(!"0".equals(border.getError_code())){
			        	return "{\"errorMessage\":\""+border.getReason()+"\",\"success\":false}";
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
		        System.out.println("BYJL:\r\n" + queryResult);
		        logger.info("BYJL:\r\n" + queryResult);
				BYJL b = gson.fromJson(queryResult, BYJL.class);
				order.setQueryCondition("&vin="+vin);
				
				if(!"0".equals(b.getError_code())){
					order.setQueryResult("&orderId="+orderid);
					payService.updateFinancePayContent(order);
					if("227005".equals(b.getError_code()) || "订单创建成功，正在生成报告，请稍后再试".equals(b.getReason().trim())){
						Runnable myRunnable = new MyRunnable(orderId);
						Thread thread1 = new Thread(myRunnable);
						thread1.start();
						return "{\"errorMessage\":\"报告生成中，耐心等待1分钟，请在记录里查看记录详情\",\"submitOrder\":1}";
					}
		        	return "{\"errorMessage\":\""+b.getReason()+"\",\"submitOrder\":1}";
		        }
				
				queryResult = gson.toJson(b);
System.out.println("QueryResult : "+queryResult);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保养记录查询失败");
				logger.error(StringUtil.errInfo(e));
				return "{\"errorMessage\":\"查询错误,请确认输入数据是否正确\",\"success\":false}";
			}
			
		return queryResult;
	 }
}
