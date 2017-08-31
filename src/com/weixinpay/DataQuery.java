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
public class DataQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * postcode=215001&key=申请的KEY
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String url = QueryAppKeyLib.postcodeQueryUrl+"postcode=215001&key="+QueryAppKeyLib.postcodeQueryAppKey;
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
