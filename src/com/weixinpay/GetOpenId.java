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

import com.weixinpay.common.Configure;

/**
 * Servlet implementation class GetOpenId
 */
public class GetOpenId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOpenId() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory (), 443);
		Protocol.registerProtocol("https ", myhttps);*/
		String code = request.getParameter("code");
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code");
        //设置请求器的配置
		try {
			HttpClient httpClient = SSLUtil.getHttpClient();
	        HttpResponse res = httpClient.execute(httpGet);
	        HttpEntity entity = res.getEntity();
	        String result = EntityUtils.toString(entity, "UTF-8");
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
