package com.weixinpay;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.weixinpay.model.TBXXUserInfo;
import com.weixinpay.model.WXUser;
import com.weixinpay.service.PayService;
import com.weixinpay.test.Data;

/**
 * Servlet implementation class GetOpenId
 */
public class MemberQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberQuery() {
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
		OutputStream out = response.getOutputStream(); 
		try {
			String code = request.getParameter("code");
			 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
			System.out.println("获取用户openid开始");
			String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code";
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = SSLUtil.getHttpClient();
	        HttpResponse res = httpClient.execute(httpGet);
	        HttpEntity entity = res.getEntity();
	        String result = EntityUtils.toString(entity, "UTF-8");
	        System.out.println("userInfo : " + result);
	        System.out.println("获取用户openid结束");
	        /*********获取用户openid结束***************/
	        WXUser u = gson.fromJson(result, WXUser.class);
	        /*********生成订单开始***************/
	        System.out.println("生成订单开始");
	        String openid = u.getOpenid();
			int memberLevel = payService.memberLevel(openid);
System.out.println("memberLevel : " + memberLevel);
	        if(memberLevel == 0){
				 out.write("{\"result\":\"普通会员\",\"error\":\"0\"}".getBytes("UTF-8"));  
	        }else if(memberLevel == 1){
				 out.write("{\"result\":\"中级会员\",\"error\":\"0\"}".getBytes("UTF-8"));  
	        }else if(memberLevel == 2){
				 out.write("{\"result\":\"高级会员\",\"error\":\"0\"}".getBytes("UTF-8"));  
	        }
		} catch (Exception e) {
			out.write("{\"result\":\"错误\",\"error\":\"1\"}".getBytes("UTF-8"));  
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
