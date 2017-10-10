package com.weixinpay;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.hq.util.StringUtil;

import com.weixinpay.model.BYJL;
import com.weixinpay.model.CLZT;
import com.weixinpay.model.CXJL;
import com.weixinpay.model.OrderInfo;
import com.weixinpay.model.TBXX;
import com.weixinpay.service.PayService;

/**
 * Servlet implementation class GetOpenId
 */
public class PaySuccess extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(PaySuccess.class);
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("---PaySuccess start---");
		String orderId = request.getParameter("orderId");
		try {
			String payType = request.getParameter("payType");
			logger.info("---PaySuccess start--- orderId : " + orderId);
			logger.info("---PaySuccess start--- payType : " + payType);
			logger.info("---PaySuccess start--- 开始查询 ");
			String queryResult ="";
			//queryResult = CXJL.queryResult(request, orderId);
			if("CLZT".equals(payType)){
				 queryResult = CLZT.queryResult(request, orderId);
			}else if("BYJL".equals(payType)){
				queryResult = BYJL.queryResult(request, orderId);
			}else if("CXJL".equals(payType)){
				queryResult = CXJL.queryResult(request, orderId);
			}else if("TBXX".equals(payType)){
				queryResult = TBXX.queryResult(request, orderId);
			}
			queryResult = queryResult.replace("\\\"", "");
			//查询失败,进行退款处理
			if(queryResult.indexOf("errorMessage")>-1){
				logger.error("---PaySuccess --- 调用聚数接口查询失败");
				//如果是维保查询，那么订单生成成功就不能退款了
				if(queryResult.indexOf("\"submitOrder\":1") == -1){
					OrderInfo order = payService.getQueryOrderByorderId(orderId);
					ReFund.reFund(order);
				}

				 OutputStream out = response.getOutputStream();  
				 out.write(queryResult.getBytes("UTF-8"));  
				return;
				
			}else{
				//查询成功,更新订单表
				logger.info("---PaySuccess start--- 查询成功,更新订单表");
				if(payService.paySucess(orderId,queryResult)){
					logger.info("---PaySuccess start--- 查询成功,更新订单表成功");
					 response.getWriter().append("{\"errorMessage\":\"\",\"success\":true}");
				}else{
					logger.error("---PaySuccess start--- 查询成功,更新订单表失败");
					response.getWriter().append("{\"errorMessage\":\"paySucess error\",\"success\":false}");
				}
			}
			
		} catch (Exception e) {
			logger.error(StringUtil.errInfo(e));
			e.printStackTrace();
			response.getWriter().append("{\"errorMessage\":\"paySucess error\",\"success\":false}");
		}
		logger.info("---PaySuccess end---");
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
