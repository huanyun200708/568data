package com.weixinpay;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
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
public class PayRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(PayRecord.class);
	

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
		logger.info("PayRecord start...............................");
		String code = request.getParameter("code");
		System.out.println("code : " + code);
		logger.info("code : " + code);
		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		try {
			/*********获取用户openid开始***************/
			System.out.println("获取用户openid开始");
			logger.info("获取用户openid开始");
			String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code";
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = SSLUtil.getHttpClient();
	        HttpResponse res = httpClient.execute(httpGet);
	        HttpEntity entity = res.getEntity();
	        String result = EntityUtils.toString(entity, "UTF-8");
	        System.out.println("userInfo : " + result);
	        logger.info("userInfo : " + result);
	        WXUser u = gson.fromJson(result, WXUser.class);
	        String openid = u.getOpenid();
	        logger.info("openid : " + openid);
	        System.out.println("获取用户openid结束");
	        logger.info("获取用户openid结束");
	        
	        List<JSONObject> jsons = payService.getPayRecordsByOpenId(openid);
	        
	        OutputStream out = response.getOutputStream();  
			 out.write(gson.toJson(jsons).getBytes("UTF-8"));  
	        /*********获取用户openid结束***************/
	       
			System.out.println("PayRecord结束");
			logger.info("PayRecord结束");
			
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(StringUtil.errInfo(e));
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
