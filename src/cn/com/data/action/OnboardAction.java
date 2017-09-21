package cn.com.data.action;

import javax.servlet.http.HttpServletRequest;

import cn.com.hq.action.BaseAction;
import cn.com.hq.serviceimpl.XssServiceImpl;
import cn.com.hq.util.JsonUtils;
import cn.com.hq.websocket.ForwardWebSocket;

public class OnboardAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		System.out.println(XssServiceImpl.escapeHtmlForString("{\"name\":\"a&amp;#x5c;1\",\"age\":0}"));
	}
	public String execute() throws Exception {
		return "success";
	}
	
	public void get(){
		HttpServletRequest reguest= super.getRequest();
		ForwardWebSocket.sendUser("","{\"message\":"+JsonUtils.toJson("")+"}");
		responseWriter("{\"success\":false,\"message\":\"预约失败 \"}");
	}

}
