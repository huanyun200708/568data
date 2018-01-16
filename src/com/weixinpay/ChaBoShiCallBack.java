package com.weixinpay;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.alibaba.fastjson.JSONObject;
import com.chaboshi.util.CBS;
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
import com.weixinpay.model.CXJL;
import com.weixinpay.model.OrderInfo;
import com.weixinpay.model.OrderReturnInfo;
import com.weixinpay.model.SignInfo;
import com.weixinpay.model.TBXX;
import com.weixinpay.model.WXUser;
import com.weixinpay.service.PayService;

/**
 * Servlet implementation class GetOpenId
 */
public class ChaBoShiCallBack extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(ChaBoShiCallBack.class);
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("ChaBoShiCallBack start...............................");
		String result = request.getParameter("result");
		String message = request.getParameter("message");
		String orderid = request.getParameter("orderid");
		System.out.println("result : "+result + "----message : "+message + "----orderid : "+orderid);
		logger.info("result : "+result + "----message : "+message + "----orderid : "+orderid);
		String s =  CBS.getInstance("72029","d7174ad1310c8bc704ded21e26f5c25a").getBuyReport("LSGJA52H3GH122854", "","", "http://twvv6x.natappfree.cc/568data/chaBoShiCallBack");
		 System.out.println("s:\r\n"+s);
		if(result!=null){
			String callResult = CBS.getInstance("72029","d7174ad1310c8bc704ded21e26f5c25a").getNewReportJson(orderid);
			System.out.println("callResult:\r\n"+callResult);
			logger.info("callResult:\r\n"+callResult);
		}
		logger.info("ChaBoShiCallBack end...............................");
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
