package cn.com.hq.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.hq.dao.Dao;
import cn.com.hq.dao.OnboardDao;
import cn.com.hq.entity.Account;
import cn.com.hq.entity.OnboardInfo;
import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.StringUtil;
import cn.com.hq.vo.OnboardInfoVO;

public class OnboardDaoImpl implements OnboardDao {
	private Dao dao = new Dao();
	@Override
	public void addOnboardInfo(OnboardInfo b) {
		//INSERT INTO huangqidb.onboardinfo VALUES ('ob002', 'u001', '00:00:00', 'JN');
		
		String sql = "INSERT INTO  huangqidb.onboardinfo (id,accountid,appointtime,onboardtime,onboardaddress,appointstatus,onboardstatus ) VALUES (?,?,?,?,?,?,?)";
		Connection connection =  dao.getDBConnection();
		if(dao.dbFlag.equals("Common")){
			sql = sql.replaceAll("huangqidb\\.", "");
		}
		PreparedStatement  ps;
		boolean result = false;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, b.getId());
			ps.setString(2, b.getAccountid());
			ps.setString(3, b.getAppointtime());
			ps.setString(4, b.getOnboardtime());
			ps.setString(5, b.getOnboardaddress());
			ps.setString(6, b.getAppointstatus());
			ps.setString(7, b.getOnboardstatus());
			ps.executeUpdate();
			dao.closeStatement(ps);
			String v = PropertiesUtils.getPropertyValueByKey("isDbConnectionSingleStatic");
			if(!"true".equals(v)){
				dao.closeConnection(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OnboardInfoVO> queryAllOnboardInfo(String infoid) {
		List<OnboardInfoVO> infos = new ArrayList<OnboardInfoVO>(10);
		String sql = "SELECT id, a.accountid,a.name,onboardtime,appointtime,onboardaddress,appointstatus,onboardstatus FROM huangqidb.onboardinfo o, huangqidb.account a where o.accountid=a.accountid";
		Connection connection =  dao.getDBConnection();
		if(!StringUtil.isEmpty(infoid)){
			sql = "SELECT id,a.accountid, a.name,onboardtime,appointtime,onboardaddress,appointstatus,onboardstatus FROM huangqidb.onboardinfo o, huangqidb.account a"
					+ " where o.accountid=a.accountid and o.id=?";
		}
		try {
			PreparedStatement  ps;
			ResultSet rs;
			boolean result = false;
			ps = connection.prepareStatement(sql);
			if(!StringUtil.isEmpty(infoid)){
				ps.setString(1,infoid);
				rs = ps.executeQuery();
			}else{
				rs = ps.executeQuery(sql);
			}
			
			while(rs.next()){
				 String id = rs.getString(1);
				 String accountid = rs.getString(2);
		        String accountname = rs.getString(3);
		        String onboardtime = rs.getString(4);
		        String appointtime = rs.getString(5);
		        String onboardaddress = rs.getString(6);
		        String appointstatus = rs.getString(7);
		        String onboardstatus = rs.getString(8);
		        OnboardInfoVO o = new OnboardInfoVO();
		        o.setId(id);
		        o.setAccountid(accountid);
		        o.setAccountname(accountname);
		        o.setAppointtime(appointtime);
		        o.setOnboardtime(onboardtime);
		        o.setOnboardaddress(onboardaddress);
		        o.setAppointstatus(appointstatus);
		        o.setOnboardstatus(onboardstatus);
		        infos.add(o);
		    }
			dao.closeResultSet(rs);
			dao.closeStatement(ps);
			String v = PropertiesUtils.getPropertyValueByKey("isDbConnectionSingleStatic");
			if(!"true".equals(v)){
				dao.closeConnection(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infos;
	}

	@Override
	public int modifyOnboardInfo(OnboardInfo b) {
		int resultCount = 0;
		String sql = "update  huangqidb.onboardinfo"
				+ " set accountid=?"
				+ " , appointtime=?"
				+ " , onboardtime=?"
				+ " , onboardaddress=?"
				+ " , appointstatus=?"
				+ " , onboardstatus=?"
				+ " where id=?";
		Connection connection =  dao.getDBConnection();
		if(dao.dbFlag.equals("Common")){
			sql = sql.replaceAll("huangqidb\\.", "");
		}
		PreparedStatement  ps;
		boolean result = false;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, b.getAccountid());
			ps.setString(2, b.getAppointtime());
			ps.setString(3, b.getOnboardtime());
			ps.setString(4, b.getOnboardaddress());
			ps.setString(5, b.getAppointstatus());
			ps.setString(6, b.getOnboardstatus());
			ps.setString(7, b.getId());
			resultCount = ps.executeUpdate();
			dao.closeStatement(ps);
			String v = PropertiesUtils.getPropertyValueByKey("isDbConnectionSingleStatic");
			if(!"true".equals(v)){
				dao.closeConnection(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultCount;
	}

}
