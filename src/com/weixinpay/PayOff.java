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
		String code = request.getParameter("code");
		String payType = request.getParameter("payType");
System.out.println("code : "+code + "----payType : "+payType);
		String queryResult = "";
		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
	    String queryCondition = "";   
		try {
			/*********获取用户openid开始***************/
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
			OrderInfo order = new OrderInfo();
			order.setOpenid(openid);
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			int memberLevel = payService.memberLevel(openid);
System.out.println("memberLevel : " + memberLevel);
			if("GJHY".equals(payType)){
				order.setBody("Be senior member");
				String beHighMemberPrice = PropertiesUtils.getPropertyValueByKey("beHighMemberPrice");
				order.setTotal_fee(Integer.valueOf(beHighMemberPrice));//设置价格
order.setTotal_fee(1);//TODO Test Code
			}else if("ZJHY".equals(payType)){
				order.setBody("Be intermediate Member");
				String beMiddleMemberPrice = PropertiesUtils.getPropertyValueByKey("beMiddleMemberPrice");
				order.setTotal_fee(Integer.valueOf(beMiddleMemberPrice));//设置价格
order.setTotal_fee(1);//TODO Test Code
			}else if("CLZT".equals(payType)){
				queryResult = CLZT.queryResult(request, order, memberLevel);
				if(queryResult.indexOf("errormassage")>-1){
					 OutputStream out = response.getOutputStream();  
					 out.write(queryResult.getBytes("UTF-8"));  
					return;
				}
order.setTotal_fee(1);//TODO Test C
			}else if("BYJL".equals(payType)){
				order.setBody("Vehicle maintenance record query");
				queryResult = BYJL.queryResult(request, order, memberLevel);
				queryCondition = order.getQueryCondition();
				order.setQueryCondition("");
				if(queryResult.indexOf("errormassage")>-1){
					 OutputStream out = response.getOutputStream();  
					 out.write(queryResult.getBytes("UTF-8"));  
					return;
				}
order.setTotal_fee(1);//TODO Test C
			}else if("CXJL".equals(payType)){
				queryResult = CXJL.queryResult(request, order, memberLevel);
				if(queryResult.indexOf("errormassage")>-1){
					 OutputStream out = response.getOutputStream();  
					 out.write(queryResult.getBytes("UTF-8"));  
					return;
				}
order.setTotal_fee(1);//TODO Test C
			}else if("TBXX".equals(payType)){
				queryResult = TBXX.queryResult(request, order, memberLevel);
				if(queryResult.indexOf("errormassage")>-1){
					 OutputStream out = response.getOutputStream();  
					 out.write(queryResult.getBytes("UTF-8"));  
					return;
				}
order.setTotal_fee(1);//TODO Test C
			}
			
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			order.setSpbill_create_ip("123.57.218.54");
			order.setNotify_url("https://www.see-source.com/weixinpay/PayResult");
			order.setTrade_type("JSAPI");
			order.setSign_type("MD5");
			String sign = Signature.getSign(order);//生成签名
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
			json.put("openid", openid);
			System.out.println("-------再签名:"+json.toJSONString());
			
			//插入订单记录表
			order.setQueryResult(queryResult);
			order.setQueryType(payType);
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
		doGet(request, response);
	}

	public String excutePay(String code){
		return "";
	}
}
