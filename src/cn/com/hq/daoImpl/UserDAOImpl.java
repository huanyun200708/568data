package cn.com.hq.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.hq.dao.Dao;
import cn.com.hq.dao.UserDAO;
import cn.com.hq.entity.Account;
import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.StringUtil;

public class UserDAOImpl implements UserDAO {
	private Dao dao = new Dao();
	public boolean saveUser(Account a) {
		String sql = "INSERT INTO  huangqidb.`ACCOUNT`(`accountid`, `name`, `password`, `age`, `address`, `email`, `phone`, `message`)"
				+ " VALUES ("
				+ "'" + a.getAccountid() +"',"
				+ "'" + a.getName() +"',"
				+ "'" + a.getPassWord() +"',"
				+ "" + a.getAge() +","
				+ "'" + a.getAddress() +"',"
				+ "'" + a.getEmail() +"',"
				+ "'" + a.getPhone() +"',"
				+ "'" + a.getMessage() + 
				"')";
		Connection connection =  dao.getDBConnection();
		if(dao.dbFlag.equals("Common")){
			sql = sql.replaceAll("huangqidb\\.", "");
		}
		Statement stmt;
		boolean result = false;
		try {
			stmt = connection.createStatement();
			result = stmt.execute(sql);
			dao.closeStatement(stmt);
			dao.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
       return result;
	}

	public void updateUser(Account u) {
		
	}

	public void deleteUser(Account u) {
		System.out.println("user deleted!");
	}

	public Account getUser(Account u) {
		if ("admin".endsWith(u.getName()) && "admin123".equals(u.getPassWord())) {
			u.setAge(18);
			return u;
		}
		return null;
	}

	@Override
	public List<Account> queryAccountByName(String name) {
		List<Account> accountList = new ArrayList<Account>();
		String sql = "";
		if(!StringUtil.isEmpty(name)){
			sql = "SELECT accountid,name,message FROM huangqidb.ACCOUNT where name='"+name+"'";
		}else{
			sql = "SELECT accountid,name,message FROM huangqidb.ACCOUNT";
		}
		if(dao.dbFlag.equals("Common")){
			sql = sql.replaceAll("huangqidb\\.", "");
		}
		Connection connection =  dao.getDBConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				 String accountid = rs.getString(1);
		        String accourtName = rs.getString(2);
		        String accourtMessage = rs.getString(3);
		        Account a = new Account();
		        a.setAccountid(accountid);
		        a.setName(accourtName);
		        a.setMessage(accourtMessage);
		        accountList.add(a);
		    }
			dao.closeResultSet(rs);
			dao.closeStatement(stmt);
			String v = PropertiesUtils.getPropertyValueByKey("isDbConnectionSingleStatic");
			if(!"true".equals(v)){
				dao.closeConnection(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}
}
