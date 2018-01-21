package com.weixinpay;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.weixinpay.model.OrderInfo;
import com.weixinpay.service.PayService;

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
			 out.write(order.getQueryResult().replace("\\", "").replaceAll("null", "\\\"\\\"").getBytes("UTF-8"));  
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
