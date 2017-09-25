package com.weixinpay.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.weixinpay.model.OrderInfo;

import cn.com.hq.dao.Dao;
import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.StringUtil;
import cn.com.hq.vo.OnboardInfoVO;

public class PayService {
	
	private Dao dao = new Dao();
	
	public boolean  isOrderFirstQuery(String orderId){
		String sql = "select confirmTime from 568db.finance_pay where orderid=?";
		Connection connection =  dao.getDBConnection();
		boolean result = false;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, orderId);
			ResultSet rs = ps.executeQuery();
			String confirmTime = "10000";
			int confirmTiemint = 10000;
			while(rs.next()){
				confirmTime = rs.getString(1);
				confirmTiemint = Integer.valueOf(confirmTime);
				 if(confirmTiemint==0){
					 result = true;
				 }
		    }
			dao.closeResultSet(rs);
			dao.closeStatement(ps);
			if(confirmTiemint!=-1){
				sql = "update 568db.finance_pay set confirmTime="+(Integer.valueOf(confirmTime)+1) + " where orderid=?";
				ps = connection.prepareStatement(sql);
				ps.setString(1, orderId);
				ps.executeUpdate();
				dao.closeResultSet(rs);
				dao.closeStatement(ps);
			 }
			
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public OrderInfo getQueryOrderByorderId(String orderId){
		String sql = "SELECT userid,openid,orderid,fee,paytime,ip,title,content,confirmTime,queryType,querycondition FROM 568db.finance_pay where orderid=?";
		Connection connection =  dao.getDBConnection();
		OrderInfo order = new OrderInfo();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, orderId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				order.setUserid(rs.getString(1));
				order.setOpenid(rs.getString(2));
				order.setOut_trade_no(orderId);
				order.setTotal_fee(rs.getInt(4));
				order.setTitle(rs.getString(7));
				order.setQueryResult(rs.getString(8));
				order.setQueryCondition(rs.getString(11));
				order.setQueryType(rs.getString(10));
				break;
		    }
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	public List<JSONObject> getPayRecordsByOpenId(String openid){
		 List<JSONObject> results = new ArrayList<JSONObject>();
		String sql = "SELECT orderid,paytime,queryType,querycondition,content"
				+ " FROM 568db.finance_pay t"
				+ " where openid=? and (t.`isRefound` <> 'Y' OR t.`isRefound` IS NULL) ORDER BY t.`paytime` DESC";
		Connection connection =  dao.getDBConnection();
		OrderInfo order = new OrderInfo();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, openid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if(StringUtil.isEmpty(rs.getString(5))){
					continue;
				}
				JSONObject json = new JSONObject();
				String orderid = rs.getString(1);
				String paytime = rs.getString(2);
				String queryType = rs.getString(3);
				String querycondition = rs.getString(4);
				String content = "";
				String content1 = rs.getString(5);
				
				DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
				Date d = format2.parse(paytime);
				format2  = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				paytime = format2.format(d);
				
				if ("ZJHY".equals(queryType)) {
					content = "升级为中级会员";
				}else if("GJHY".equals(queryType)){
					content = "升级为高级会员";
				}else if("CLZT".equals(queryType)){
					content = "车辆状态查询	\r\n";
					if(!StringUtil.isEmpty(querycondition) && querycondition.indexOf("number")>-1){
						content = content + "车牌号:" +querycondition.replaceAll("&number=", "").replaceAll("&type.*", "");
					}else if(!StringUtil.isEmpty(querycondition) && querycondition.indexOf("type")>-1){
						content = content + " 车辆类型:" +querycondition.replaceAll(".*&type=", "");
					}
				}else if("BYJL".equals(queryType)){
					content = "维保信息查询	\r\n";
					if(!StringUtil.isEmpty(querycondition)){
						content = content + "车架号:" +querycondition.replace("&vin=", "");
					}
					if(content1.indexOf("&orderId=")>-1){
						content1 = "正在查询";
					}
				}else if("CXJL".equals(queryType)){
					content = "出险信息查询	\r\n";
					if(!StringUtil.isEmpty(querycondition) && querycondition.indexOf("licenseNo")>-1){
						content = content + "车牌号:" +querycondition.replaceAll("&licenseNo=", "").replaceAll("&frameNo.*", "");
					}else if(!StringUtil.isEmpty(querycondition) && querycondition.indexOf("licenseNo")>-1){
						content = content + " 车架号:" +querycondition.replaceAll(".*&frameNo=", "");
					}
				}else if("TBXX".equals(queryType)){
					content = "投保信息查询	\r\n";
					if(!StringUtil.isEmpty(querycondition) && querycondition.indexOf("licenseNo")>-1){
						content = content + "车牌号:" +querycondition.replaceAll("&licenseNo=", "").replaceAll("&carVin.*", "");
					}else if(!StringUtil.isEmpty(querycondition) && querycondition.indexOf("carVin")>-1){
						content = content + " 车架号:" +querycondition.replaceAll(".*&carVin=", "").replaceAll("&engineNo.*", "");
					}else if(!StringUtil.isEmpty(querycondition) && querycondition.indexOf("engineNo")>-1){
						content = content + " 发动机号:" +querycondition.replaceAll(".*&engineNo=", "").replaceAll("&renewalCarType.*", "");
					}
				}
				
				json.put("orderid",orderid);
				json.put("paytime", paytime);
				json.put("queryType", queryType);
				json.put("querycondition", querycondition);
				json.put("content", content);
				json.put("content1", content1);
				
				results.add(json);
		    }
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public boolean insertFinancePay(OrderInfo orderInfo){
		boolean result = false;
		String sql = "INSERT INTO  568db.finance_pay (userid,openid,orderid,fee,paytime,ip,title,content,confirmTime,queryType,querycondition) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			Date date = new Date();
			DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
			ps = connection.prepareStatement(sql);
			ps.setString(1, orderInfo.getOpenid());
			ps.setString(2, orderInfo.getOpenid());
			ps.setString(3, orderInfo.getOut_trade_no());
			ps.setInt(4, orderInfo.getTotal_fee());
			ps.setString(5,format2.format(date));
			ps.setString(6, "127.0.0.1");
			ps.setString(7, orderInfo.getBody());
			ps.setString(8, orderInfo.getQueryResult()==null?"":orderInfo.getQueryResult());
			//-1代表没有付款,0代表已付款并初次查询
			ps.setInt(9, -1);
			ps.setString(10, orderInfo.getQueryType());
			ps.setString(11, orderInfo.getQueryCondition()==null?"":orderInfo.getQueryCondition());
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Map<String,String> getBYJLqueryCondition(String openId,String vin){
		Map<String,String> result = new HashMap<String,String>();
		String sql = "select content,orderid  from 568db.finance_pay where openid=? and querycondition=CONCAT('&vin=',?) and content<> '' ";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, openId);
			ps.setString(2, vin);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result.put("condition", rs.getString(1));
				result.put("orderId", rs.getString(2));
				break;
		    }
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean beMember(String openid,String type){
		boolean result = false;
		String sql ="";
		Date date = new Date();
		DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
		if("M".equals(type)){
			sql = "update 568db.member set groupid=1,Registrationtime='"+format2.format(date)+"' where openid=?";
		}else if("H".equals(type)){
			sql = "update 568db.member set groupid=2,Registrationtime='"+format2.format(date)+"' where openid=?";
		}
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, openid);
			if(ps.executeUpdate() == 0){
				if("M".equals(type)){
					sql = "INSERT INTO 568db.member  (userid,groupid,openid,Registrationtime) VALUES ('"+openid+"',1,'"+openid+"','"+format2.format(date)+"')";
				}else if("H".equals(type)){
					sql = "INSERT INTO 568db.member  (userid,groupid,openid,Registrationtime) VALUES ('"+openid+"',2,'"+openid+"','"+format2.format(date)+"')";
				}
				result = ps.executeUpdate(sql) > 0;
			}else{
				result = true;
			}
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}

	public boolean paySucess(String out_trade_no,String content){
		boolean result = false;
		String sql = "update 568db.finance_pay set confirmTime=0,content='"+content+"' where orderid=?";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			Date date = new Date();
			DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
			ps = connection.prepareStatement(sql);
			ps.setString(1, out_trade_no);
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int memberLevel(String openid){
		int result = 0;
		String sql = "SELECT groupid ,Registrationtime FROM 568db.member where openid=?";
		Connection connection =  dao.getDBConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, openid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result = rs.getInt(1);
				String Registrationtime = rs.getString(2);
				Calendar calendar = Calendar.getInstance();
				Calendar nowcalendar = Calendar.getInstance();
				SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");//如2016081020:40:00
				SimpleDateFormat simpleFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//如2016081020:40:00
				try {
					Date rdate = simpleFormat.parse(Registrationtime);
					calendar.setTime(rdate);
					calendar.add(Calendar.YEAR, 1);
					calendar.add(Calendar.DAY_OF_YEAR, -1);
					 System.out.println("会员到期时间：" + simpleFormat2.format(calendar.getTime()));
					nowcalendar.setTime(new Date());
					if(nowcalendar.before(calendar)){
						return result;
					}else{
						 System.out.println("会员已到期!!!");
						return 0;
					}
				} catch (ParseException e) {
					result  = 0;
					e.printStackTrace();
				}
		    }
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void updateFinancePayContent(OrderInfo order) {
		boolean result = false;
		String sql = "update 568db.finance_pay set content='"+order.getQueryResult()+"' where orderid=?";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			Date date = new Date();
			DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
			ps = connection.prepareStatement(sql);
			ps.setString(1, order.getOut_trade_no());
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateFinancePayReFound(OrderInfo order) {
		boolean result = false;
		String sql = "update 568db.finance_pay set isRefound='Y' where orderid=?";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			Date date = new Date();
			DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
			ps = connection.prepareStatement(sql);
			ps.setString(1, order.getOut_trade_no());
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
