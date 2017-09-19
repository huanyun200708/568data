package com.weixinpay;

import java.io.IOException;

import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.weixinpay.common.Configure;
import com.weixinpay.common.RandomStringGenerator;
import com.weixinpay.common.Signature;
import com.weixinpay.model.OrderInfo;
import com.weixinpay.model.ReFoundOrderReturnInfo;
import com.weixinpay.model.RefundOrderInfo;
import com.weixinpay.service.PayService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
public class ReFund  {
	private static Logger logger = Logger.getLogger(ReFund.class);
	private static PayService payService = new PayService(); 
	public static void reFund(OrderInfo order) throws IOException {
		try {
			 HttpClient client = SSLUtil.getHttpClientWithServerVerifyClient();  
	        /*********生成订单开始***************/
	        System.out.println("生成订单开始");
	        logger.info("生成退单开始");
	        RefundOrderInfo reOrder = new RefundOrderInfo();
			reOrder.setAppid(Configure.getAppID());
			reOrder.setMch_id(Configure.getMch_id());
			reOrder.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			reOrder.setRefund_fee(order.getTotal_fee());
			reOrder.setTotal_fee(order.getTotal_fee());
			reOrder.setOut_trade_no(order.getOut_trade_no());
			reOrder.setOut_refund_no(RandomStringGenerator.getRandomStringByLength(64));
			reOrder.setSign_type("MD5");
			String sign = Signature.getSign(reOrder);//生成签名
			reOrder.setSign(sign);
			HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
			//组装请求数据
			XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
			xStreamForRequestPostData.alias("xml", RefundOrderInfo.class);
			// 将要提交给API的数据对象转换成XML格式数据Post给API
			String postDataXML = xStreamForRequestPostData.toXML(reOrder);
			// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
			StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(postEntity);
			// 设置请求器的配置
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
			httpPost.setConfig(requestConfig);
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			String result = EntityUtils.toString(entity, "UTF-8");
	        
			System.out.println("---------下单返回: \r\n"+result);
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("xml", ReFoundOrderReturnInfo.class); 
			ReFoundOrderReturnInfo returnInfo = (ReFoundOrderReturnInfo)xStream.fromXML(result);
			if("SUCCESS".equals(returnInfo.getResult_code()) && "SUCCESS".equals(returnInfo.getReturn_code())){
				payService.updateFinancePayReFound(order);
			}
			System.out.println("生成订单结束");
			 logger.info("退单结束");
	        /*********生成订单结束***************/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("退单失败");
			logger.error(StringUtil.errInfo(e));
		}
	}
}
