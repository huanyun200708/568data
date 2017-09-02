package com.weixinpay.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
			ps.setString(6, "");
			ps.setString(7, "");
			ps.setString(8, "");
			ps.setInt(9, -1);
			ps.setString(10, "");
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
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
}
