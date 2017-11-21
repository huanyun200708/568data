package com.weixinpay;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.SSLUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixinpay.common.Configure;
import com.weixinpay.model.WXUser;
import com.weixinpay.service.PayService;

/**
 * Servlet implementation class GetOpenId
 */
public class MemberPriceQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberPriceQuery() {
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
			String beHighMemberPrice = PropertiesUtils.getPropertyValueByKey("beHighMemberPrice");
			String beMiddleMemberPrice = PropertiesUtils.getPropertyValueByKey("beMiddleMemberPrice");
			 out.write(("{\"Hprice\":\""+Integer.valueOf(beHighMemberPrice)/100+"\",\"Mprice\":\""+Integer.valueOf(beMiddleMemberPrice)/100+"\"}").getBytes("UTF-8"));  
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
