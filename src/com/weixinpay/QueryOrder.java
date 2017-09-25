package com.weixinpay;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.weixinpay.common.Configure;
import com.weixinpay.common.HttpRequest;
import com.weixinpay.common.RandomStringGenerator;
import com.weixinpay.common.Signature;
import com.weixinpay.model.BYJL;
import com.weixinpay.model.CLZT;
import com.weixinpay.model.OrderInfo;
import com.weixinpay.model.OrderReturnInfo;
import com.weixinpay.model.SignInfo;
import com.weixinpay.model.WXUser;
import com.weixinpay.service.PayService;
import com.weixinpay.test.Data;

/**
 * Servlet implementation class GetOpenId
 */
public class QueryOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(QueryOrder.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryOrder() {
        super();
    }

	/**
	 * @param payType:
	 * 			GJHY : 高级会员
	 * 			ZJHY : 中级会员
	 * 			CLZT : 车辆状态
	 * 			BYJL : 保养记录
	 * 			CXJL : 出险记录
	 * 			TBXX : 投保信息
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderId = request.getParameter("orderId");
		System.out.println("orderId : "+orderId);
		 OutputStream out = response.getOutputStream();  
		try {
			OrderInfo order = payService.getQueryOrderByorderId(orderId);
			 String payType = request.getParameter("payType");
				if("BYJL".equals(payType)){
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
				        System.out.println("BYJL--again:\r\n" + clztresult);
				        logger.info("BYJL--again:\r\n" + clztresult);
						BYJL b = gson.fromJson(clztresult, BYJL.class);
						
						if(!"0".equals(b.getError_code())){
				        	out.write(("{\"errorMessage\":\""+b.getReason()+"\",\"submitOrder\":1}").getBytes("UTF-8"));  
				        }else{
				        	order.setQueryResult(clztresult.replace("\\", ""));
				        	payService.updateFinancePayContent(order);
				        	 out.write(order.getQueryResult().replace("\\", "").getBytes("UTF-8"));  
				        	return;
				        }
					}
				}
			 out.write(order.getQueryResult().replace("\\", "").getBytes("UTF-8"));  
			//response.getWriter().append(result);
		} catch (Exception e) {
			out.write(("{\"errorMessage\":\"查询出错\",\"submitOrder\":1}").getBytes("UTF-8"));  
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public String excutePay(String code){
		return "";
	}
}
