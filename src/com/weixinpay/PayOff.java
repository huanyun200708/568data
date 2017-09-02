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
public class PayOff extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayOff() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code";
		HttpGet httpGet = new HttpGet(url);
		try {
			/*********获取用户openid开始***************/
			System.out.println("获取用户openid开始");
			HttpClient httpClient = SSLUtil.getHttpClient();
	        HttpResponse res = httpClient.execute(httpGet);
	        HttpEntity entity = res.getEntity();
	        String result = EntityUtils.toString(entity, "UTF-8");
	        System.out.println("userInfo : " + result);
	        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
	        WXUser u = gson.fromJson(result, WXUser.class);
	        System.out.println("获取用户openid结束");
	        /*********获取用户openid结束***************/
	        
	        /*********生成订单开始***************/
	        System.out.println("生成订单开始");
	        String openid = u.getOpenid();
			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody("dfdfdf");
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			//设置价格
			order.setTotal_fee(1);
			order.setSpbill_create_ip("123.57.218.54");
			order.setNotify_url("https://www.see-source.com/weixinpay/PayResult");
			order.setTrade_type("JSAPI");
			order.setOpenid(openid);
			order.setSign_type("MD5");
			//生成签名
			String sign = Signature.getSign(order);
			order.setSign(sign);
			result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			System.out.println("---------下单返回:"+result);
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("xml", OrderReturnInfo.class); 
			OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
			String prepay_id = returnInfo.getPrepay_id();
			System.out.println("生成订单结束");
	        /*********生成订单结束***************/
	        
	        /*********签名开始***************/
			System.out.println("签名开始");
			SignInfo signInfo = new SignInfo();
			signInfo.setAppId(Configure.getAppID());
			long time = System.currentTimeMillis()/1000;
			signInfo.setTimeStamp(String.valueOf(time));
			signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
			signInfo.setRepay_id("prepay_id="+prepay_id);
			signInfo.setSignType("MD5");
			//生成签名
			String sign2 = Signature.getSign(signInfo);
			JSONObject json = new JSONObject();
			json.put("timeStamp", signInfo.getTimeStamp());
			json.put("nonceStr", signInfo.getNonceStr());
			json.put("package", signInfo.getRepay_id());
			json.put("signType", signInfo.getSignType());
			json.put("paySign", sign2);
			json.put("orderId", order.getOut_trade_no());
			System.out.println("-------再签名:"+json.toJSONString());
			if(payService.insertFinancePay(order)){
				response.getWriter().append(json.toJSONString());
			}
			System.out.println("签名结束");
			
	        /*********签名结束***************/
	        
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

	public String excutePay(String code){
		return "";
	}
}
