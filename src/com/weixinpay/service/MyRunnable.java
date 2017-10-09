package com.weixinpay.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixinpay.model.BYJL;
import com.weixinpay.model.OrderInfo;

public class MyRunnable  implements Runnable {
    private int i = 0;
    private String orderId = "";
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(MyRunnable.class);
    @Override
    public void run() {
        for (i = 0; i < 12; i++) {
        	try {
        	OrderInfo order = payService.getQueryOrderByorderId(orderId);
					String queryResult = order.getQueryResult();
					if(queryResult.indexOf("&orderId=")>-1){
						Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
						String juheorderid = queryResult.replace("&orderId=", "");
						String url = QueryAppKeyLib.baoyangQueryUrl+"key="+QueryAppKeyLib.baoyangQueryAppKey;
						HttpGet httpGet = new HttpGet(url +"&orderId="+ juheorderid);
						HttpClient httpClient = SSLUtil.getHttpClient();
						HttpResponse res = httpClient.execute(httpGet);
						HttpEntity entity = res.getEntity();
						String clztresult = EntityUtils.toString(entity, "UTF-8");
				        System.out.println("BYJLRunnable--again:\r\n" + clztresult);
				        logger.info("BYJLRunnable--again:\r\n" + clztresult);
						BYJL b = gson.fromJson(clztresult, BYJL.class);
						
						if("0".equals(b.getError_code())){
							order.setQueryResult(clztresult.replace("\\", ""));
				        	payService.updateFinancePayContent(order);
				        	return;
				        }
						
						Thread.sleep(20000);
					}
        	} catch (Exception e) {
    			e.printStackTrace();
    			logger.error(StringUtil.errInfo(e));
    		}
        }
    }
    
    
	public MyRunnable(String orderId) {
		super();
		this.orderId = orderId;
	}


	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    
    
}
