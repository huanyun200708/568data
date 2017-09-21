package com.weixinpay.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

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
	private static Logger logger = Logger.getLogger(CLZT.class);
	
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
	
	public CLZD getCheLiangZiDian(){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		String clzdurl = QueryAppKeyLib.cheliangZiDianUrl+"key="+QueryAppKeyLib.cheliangzhuangtaiQueryAppKey;
		HttpGet clzdhttpGet = new HttpGet(clzdurl);
        //设置请求器的配置
		try {
			HttpClient clzdhttpClient = SSLUtil.getHttpClient();
	        HttpResponse clzdres = clzdhttpClient.execute(clzdhttpGet);
	        HttpEntity clzdentity = clzdres.getEntity();
	        String clzdresult = EntityUtils.toString(clzdentity, "UTF-8");
	        System.out.println("CLZT:\r\n" + clzdresult);
	        logger.info("CLZT:\r\n" + clzdresult);
	        clzdresult = clzdresult.replace("附录1", "fulu1").replace("附录2", "fulu2").replace("附录3", "fulu3").replace("附录4", "fulu4").replace("附录5", "fulu5").replace("附录6", "fulu6");
	        CLZD c = gson.fromJson(clzdresult, CLZD.class);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(StringUtil.errInfo(e));
			logger.error("车辆字典查询失败");
			return null;
		}
	}

	public void translate(CLZT c) {
		CLZD clzd = this.getCheLiangZiDian();
		if(clzd != null){
			String state0 = c.getResult().getState();//fulu5
			String state = "";
			for(char ch : state0.toCharArray()){
				state = state + clzd.getFulu5().get(ch+"") + " ";
			}
			c.getResult().setState(state);
			
			String properties0 = c.getResult().getProperties();//fulu4
			String properties =  "";
			for(char ch : properties0.toCharArray()){
				properties = properties + clzd.getFulu4().get(ch+"") + " ";
			}
			c.getResult().setProperties(properties);
			
			String color0 = c.getResult().getColor();//fulu3
			String color = "";
			for(char ch : color0.toCharArray()){
				color = color + clzd.getFulu3().get(ch+"") ;
			}
			c.getResult().setColor(color);
			
			String fuel0 = c.getResult().getFuel();//fulu6
			String fuel = "";
			for(char ch : fuel0.toCharArray()){
				fuel = fuel + clzd.getFulu6().get(ch+"") ;
			}
			c.getResult().setFuel(fuel);
			
			String vehicleType = c.getResult().getVehicleType();//fulu2
			c.getResult().setVehicleType(clzd.getFulu2().get(vehicleType));
			return;
		}
		c.getResult().setState(c.getResult().getState());
		c.getResult().setProperties(c.getResult().getProperties());
		c.getResult().setColor(c.getResult().getColor());
		c.getResult().setFuel(c.getResult().getFuel());
		c.getResult().setVehicleType(c.getResult().getVehicleType());

	}
	public static void setOrderFee(HttpServletRequest request,OrderInfo order,int memberLevel){
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
	}
	public static String queryResult(HttpServletRequest request,String orderId){
		 	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		 	String queryResult = "";
			String number = request.getParameter("number");
			String cltype = request.getParameter("cltype");
			String cltypevalue = request.getParameter("cltypevalue");
			
			String clzturl = QueryAppKeyLib.cheliangzhuangtaiQueryUrl+"key="+QueryAppKeyLib.cheliangzhuangtaiQueryAppKey+"&number="+number;
			if(!StringUtil.isEmpty(cltype)){
				clzturl = clzturl+"&type="+cltype;
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
					return "{\"errorMessage\":\""+c.reason+"\",\"success\":false}";
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
				logger.error(StringUtil.errInfo(e));
				logger.error("车辆查询失败");
				return "{\"errorMessage\":\"查询错误,请确认输入数据是否正确\",\"success\":false}";
			}
			
		return queryResult;
	 }
	
	public static void main(String[] args) {
		new CLZT().getCheLiangZiDian();
	}
}
