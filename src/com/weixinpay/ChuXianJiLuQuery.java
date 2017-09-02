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

import com.weixinpay.common.Configure;

/**
 * Servlet implementation class GetOpenId
 */
public class ChuXianJiLuQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChuXianJiLuQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 	名称			类型		必填	说明
 	 *	key			String	是	您申请的key
 	 *	licenseNo	string	否	车牌号码（车牌号、车架号至少传一项）
 	 *	frameNo		string	否	车架号（车牌号、车架号至少传一项）
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String useAppId = request.getParameter("useAppId");
		String licenseNo = request.getParameter("licenseNo");
		String frameNo = request.getParameter("frameNo");
		
		/*******查询用户是否支付成功，成功后才进行查询*******/
		
		/*******查询用户是否支付成功，成功后才进行查询*******/
		
		String url = QueryAppKeyLib.chuxianjiluQueryUrl+"key="+QueryAppKeyLib.chuxianjiluQueryAppKey+"&licenseNo"+licenseNo+"&frameNo"+frameNo;
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
