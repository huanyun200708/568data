package com.weixinpay;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.hq.util.SSLUtil;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.weixinpay.common.Configure;
import com.weixinpay.common.HttpRequest;
import com.weixinpay.common.RandomStringGenerator;
import com.weixinpay.common.Signature;
import com.weixinpay.model.OrderInfo;
import com.weixinpay.model.OrderReturnInfo;
import com.weixinpay.model.SignInfo;
import com.weixinpay.model.WXUser;
import com.weixinpay.service.PayService;

/**
 * Servlet implementation class GetOpenId
 */
public class BeHighMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderId = request.getParameter("orderId");
		String type = request.getParameter("type");
		String openid = request.getParameter("openid");
		/*******查询用户是否支付成功，成功后才进行查询*******/
		boolean isOrderFirstQuery = payService.isOrderFirstQuery(orderId);
		/*******查询用户是否支付成功，成功后才进行查询*******/
		if(isOrderFirstQuery){
			try {
				if(payService.beMember(openid,type)){
					 response.getWriter().append("{\"error\":\"\",\"success\":true}");
				}else{
					response.getWriter().append("{\"error\":\"beMember error\",\"success\":false}");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			response.getWriter().append("{\"error\":\"beMember error\",\"success\":false}");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public String excutePay(String code){
		return "";
	}
}
