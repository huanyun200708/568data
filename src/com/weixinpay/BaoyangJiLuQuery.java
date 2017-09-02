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

import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

import com.weixinpay.common.Configure;
import com.weixinpay.service.PayService;

/**
 * Servlet implementation class GetOpenId
 */
public class BaoyangJiLuQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PayService payService = new PayService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaoyangJiLuQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * postcode=215001&key=申请的KEY
	 * 	名称				类型		必填	说明
 	 * 	key				string	是	你申请的appkey
 	 * 	vin				string	是	车架号
 	 * 	brand_id		int		否	品牌id(目前不需要此参数)
 	 * 	engine_number	string	否	发动机号
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String useAppId = request.getParameter("useAppId");
		String vin = request.getParameter("vin");
		String brand_id = request.getParameter("brand_id");
		String engine_number = request.getParameter("engine_number");
		String orderId = request.getParameter("orderId");
		/*******查询用户是否支付成功，成功后才进行查询*******/
		boolean isOrderFirstQuery = payService.isOrderFirstQuery(orderId);
		/*******查询用户是否支付成功，成功后才进行查询*******/
		if(isOrderFirstQuery){
			String url = QueryAppKeyLib.chuxianjiluQueryUrl+"key="+QueryAppKeyLib.baoyangQueryAppKey+"&licenseNo"+vin;
			if(!StringUtil.isEmpty(engine_number)){
				url = url+"&engine_number="+engine_number;
			}
			HttpGet httpGet = new HttpGet(url);
	        //设置请求器的配置
			try {
				HttpClient httpClient = SSLUtil.getHttpClient();
		        HttpResponse res = httpClient.execute(httpGet);
		        HttpEntity entity = res.getEntity();
		        String result = EntityUtils.toString(entity, "UTF-8");
		        System.out.println(result);
		        response.getWriter().append(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			response.getWriter().append("\"error\":\"has queried\"");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
