package cn.com.hq.serviceimpl;

import java.util.List;

import cn.com.hq.dao.UserDAO;
import cn.com.hq.daoImpl.UserDAOImpl;
import cn.com.hq.entity.Account;

public class UserServiceimpl implements cn.com.hq.service.UserService {
	private UserDAO userDAO = new UserDAOImpl();
    
	@Override
	public List<Account> queryAccountByName(String name) {
		
		List<Account> accountList = userDAO.queryAccountByName(name);
		if(accountList.size()>0){
			return accountList;
		}else{
			return null;
		}
		
	}
	@Override
	public void savaOrUpdateAccount(Account a) {
		if(userDAO.queryAccountByName(a.getName()).size()>0){
			userDAO.updateUser(a);
		}else{
			userDAO.saveUser(a);
		}
		
	}
	@Override
	public boolean  createAccount(Account a){
		return userDAO.saveUser(a);
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
}
