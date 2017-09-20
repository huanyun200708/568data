package com.weixinpay;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

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
import com.weixinpay.model.WXUser;
import com.weixinpay.service.PayService;

/**
 * Servlet implementation class GetOpenId
 */
public class PayOff extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(PayOff.class);
	

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
		logger.info("PayOff start...............................");
		String code = request.getParameter("code");
		String payType = request.getParameter("payType");
		System.out.println("code : "+code + "----payType : "+payType);
		logger.info("code : "+code + "----payType : "+payType);
		String queryResult = "";
		 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
	    String queryCondition = "";   
		try {
			/*********获取用户openid开始***************/
			System.out.println("获取用户openid开始");
			logger.info("获取用户openid开始");
			String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code";
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = SSLUtil.getHttpClient();
	        HttpResponse res = httpClient.execute(httpGet);
	        HttpEntity entity = res.getEntity();
	        String result = EntityUtils.toString(entity, "UTF-8");
	        System.out.println("userInfo : " + result);
	        logger.info("userInfo : " + result);
	        System.out.println("获取用户openid结束");
	        logger.info("获取用户openid结束");
	        /*********获取用户openid结束***************/
	        WXUser u = gson.fromJson(result, WXUser.class);
	        /*********生成订单开始***************/
	        System.out.println("生成订单开始");
	        logger.info("生成订单开始");
	        String openid = u.getOpenid();
			OrderInfo order = new OrderInfo();
			order.setOpenid(openid);
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			int memberLevel = payService.memberLevel(openid);
			System.out.println("memberLevel : " + memberLevel);
			logger.info("memberLevel : " + memberLevel);
			if("GJHY".equals(payType)){
				order.setBody("Be senior member");
				String beHighMemberPrice = PropertiesUtils.getPropertyValueByKey("beHighMemberPrice");
				order.setTotal_fee(Integer.valueOf(beHighMemberPrice));//设置价格
//order.setTotal_fee(1);//TODO Test Code
			}else if("ZJHY".equals(payType)){
				order.setBody("Be intermediate Member");
				String beMiddleMemberPrice = PropertiesUtils.getPropertyValueByKey("beMiddleMemberPrice");
				order.setTotal_fee(Integer.valueOf(beMiddleMemberPrice));//设置价格
//order.setTotal_fee(1);//TODO Test Code
			}else if("CLZT".equals(payType)){
				order.setBody("Vehicle status query");
				String number = request.getParameter("number");
				String cltypevalue = request.getParameter("cltypevalue");
				if(!StringUtil.isEmpty(number)){
					queryCondition = queryCondition +"&number="+number;
				}
				if(!StringUtil.isEmpty(cltypevalue)){
					queryCondition = queryCondition+"&type="+cltypevalue;
				}
				CLZT.setOrderFee(request, order, memberLevel);
//order.setTotal_fee(1);//TODO Test C
			}else if("BYJL".equals(payType)){
				order.setBody("Vehicle maintenance record query");
				String vin = request.getParameter("vin");
				if(!StringUtil.isEmpty(vin)){
					queryCondition = queryCondition +"&vin="+vin;
				}
				
				BYJL.setOrderFee(request, order, memberLevel);
				
				order.setQueryCondition("vin="+vin);
				Map<String,String> resultMap = payService.getBYJLqueryCondition(order.getOpenid(), vin);
				//说明用户上次上次查询结果报告还没有生成，则不再重复付款
				String condition = resultMap.get("condition");
				if(condition != null && condition.indexOf("&orderId=")>-1){
					JSONObject json = new JSONObject();
					json.put("orderId", resultMap.get("orderId"));
					json.put("openid", openid);
					json.put("notRepeatPay", true);
					response.getWriter().append(json.toJSONString());
					return;
				}
				order.setQueryCondition("");
///order.setTotal_fee(1);//TODO Test C
			}else if("CXJL".equals(payType)){
				order.setBody("Chu xian ji lu");
				
				String licenseNo = request.getParameter("licenseNo");
				String frameNo = request.getParameter("frameNo");
				if(!StringUtil.isEmpty(licenseNo)){
					queryCondition = queryCondition+"&licenseNo="+licenseNo;
				}
				if(!StringUtil.isEmpty(frameNo)){
					queryCondition = queryCondition+"&frameNo="+frameNo;
				}
				
				CXJL.setOrderFee(request, order, memberLevel);
//order.setTotal_fee(1);//TODO Test C
			}else if("TBXX".equals(payType)){
				order.setBody("Tou bao xin xi");
				
				//设置查询条件
				String licenseNo = request.getParameter("licenseNo");
				String carVin = request.getParameter("carVin");
				String engineNo = request.getParameter("engineNo");
				String renewalCarType = request.getParameter("renewalCarType");
				if(!StringUtil.isEmpty(licenseNo)){
					queryCondition = queryCondition +"&licenseNo="+licenseNo;
				}
				if(!StringUtil.isEmpty(carVin)){
					queryCondition = queryCondition +"&carVin="+carVin;
				}
				if(!StringUtil.isEmpty(engineNo)){
					queryCondition = queryCondition + "&engineNo="+engineNo;
				}
				if(!StringUtil.isEmpty(renewalCarType)){
					queryCondition = queryCondition +"&renewalCarType="+renewalCarType;
				}
				
				//设置金额
				TBXX.setOrderFee(request, order, memberLevel);
//order.setTotal_fee(1);//TODO Test C
			}
			
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			order.setSpbill_create_ip("123.57.218.54");
			order.setNotify_url("https://51yangcong.com/568data/PayResult");
			order.setTrade_type("JSAPI");
			order.setSign_type("MD5");
			String sign = Signature.getSign(order);//生成签名
			order.setSign(sign);
			result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			System.out.println("---------下单返回:"+result);
			logger.info("---------下单返回:"+result);
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("xml", OrderReturnInfo.class); 
			OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
			String prepay_id = returnInfo.getPrepay_id();
			System.out.println("生成订单结束");
			logger.info("生成订单结束");
	        /*********生成订单结束***************/
	        
	        /*********签名开始***************/
			System.out.println("签名开始");
			logger.info("签名开始");
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
			logger.info("-------再签名:"+json.toJSONString());
			//插入订单记录表
			order.setQueryResult(queryResult);
			order.setQueryType(payType);
			order.setQueryCondition(queryCondition);
			if(payService.insertFinancePay(order)){
				response.getWriter().append(json.toJSONString());
			}
			System.out.println("签名结束");
			logger.info("签名结束");
			
	        /*********签名结束***************/
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(StringUtil.errInfo(e));
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
