package cn.com.hq.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.com.hq.util.PropertiesUtils;
public class Dao {
	public  static Connection staticConnection = null;
	public static String dbFlag = "Common";
	public  Connection getCommonConnection()
	{
		Connection connection = null;
        try {
            /*****填写数据库相关信息(请查找数据库详情页)*****/
        	 String username = PropertiesUtils.getPropertyValueByKey("username");
             String password = PropertiesUtils.getPropertyValueByKey("password");
             String driverName = PropertiesUtils.getPropertyValueByKey("driverName");
             String dbUrl = PropertiesUtils.getPropertyValueByKey("dbUrl");

            /******接着连接并选择数据库名为databaseName的服务器******/
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbUrl, username,
                    password);
            System.out.println("---CommonConnection DB success!!!");
            return connection;
            
        } catch (Exception e) {
        	 System.out.println("---CommonConnection DB failed!!!");
            return getLocalConnection();
        }
	        
	}
	
	public  Connection getLocalConnection(){
        try {
        	Connection connection = null;
            /*****填写数据库相关信息(请查找数据库详情页)*****/
            String username = PropertiesUtils.getPropertyValueByKey("username");
            String password = PropertiesUtils.getPropertyValueByKey("password");
            String driverName = PropertiesUtils.getPropertyValueByKey("driverName");
            String dbUrl = PropertiesUtils.getPropertyValueByKey("dbUrl");

            /******接着连接并选择数据库名为databaseName的服务器******/
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbUrl, username,
                    password);
            System.out.println("---LocalConnection DB success!!!");
            dbFlag = "Local";
            return connection;
            
        } catch (Exception e) {
        	 System.out.println("---LocalConnection DB failed!!!");
            e.printStackTrace();
            return null;
        }
	}
	
	public  Connection getDBConnection(){
		
		String v = PropertiesUtils.getPropertyValueByKey("isDbConnectionSingleStatic");
		if("true".equals(v)){
			if(staticConnection == null){
				staticConnection = getCommonConnection();
				return staticConnection;
			}else{
				return staticConnection;
			}
		}else{
			return getCommonConnection();
		}
		
	}
	
	public  void closeConnection(Connection connection){
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				connection = null;
			}
		}
	}
	
	public  void closeStatement(Statement statement){
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				statement = null;
			}
		}
	}
	
	public  void closeResultSet(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				rs = null;
			}
		}
	}
}
