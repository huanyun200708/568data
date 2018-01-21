package com.weixinpay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.hq.util.QueryAppKeyLib;

import com.chaboshi.util.CBS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixinpay.model.WXJL;
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
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		String result = request.getParameter("result");
		String message = request.getParameter("message");
		String orderid = request.getParameter("orderid");
		System.out.println("result : "+result + "----message : "+message + "----orderid : "+orderid);
		logger.info("result : "+result + "----message : "+message + "----orderid : "+orderid);
		if(result!=null){
			String callResult = CBS.getInstance(QueryAppKeyLib.baoyangUserId,QueryAppKeyLib.baoyangUserKey).getNewReportJson(orderid);
			System.out.println("callResult:\r\n"+callResult);
			WXJL w = gson.fromJson(callResult, WXJL.class);
			w.translateWBJL(w);
			payService.updateBYJLFinancePayContent(w.getVin(),callResult);
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
