package com.weixinpay.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.weixinpay.model.OrderInfo;

import cn.com.hq.dao.Dao;
import cn.com.hq.util.PropertiesUtils;
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
	
	public String getQueryResult(String orderId){
		String sql = "SELECT content FROM 568db.finance_pay where orderid=?;";
		Connection connection =  dao.getDBConnection();
		String result = "";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, orderId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result = rs.getString(1);
				break;
		    }
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean insertFinancePay(OrderInfo orderInfo){
		boolean result = false;
		String sql = "INSERT INTO  568db.finance_pay (userid,openid,orderid,fee,paytime,ip,title,content,confirmTime,queryType) VALUES (?,?,?,?,?,?,?,?,?,?)";
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
			result = ps.executeUpdate() > 0;
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
				result = ps.executeUpdate() > 0;
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

	public boolean paySucess(String out_trade_no){
		boolean result = false;
		String sql = "update 568db.finance_pay set confirmTime=0 where orderid=?";
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
}
