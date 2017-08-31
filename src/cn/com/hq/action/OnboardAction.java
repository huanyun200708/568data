package cn.com.hq.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.hq.entity.Account;
import cn.com.hq.entity.OnboardInfo;
import cn.com.hq.service.OnboardService;
import cn.com.hq.service.UserService;
import cn.com.hq.serviceimpl.OnboardServiceImpl;
import cn.com.hq.serviceimpl.UserServiceimpl;
import cn.com.hq.serviceimpl.XssServiceImpl;
import cn.com.hq.util.JsonUtils;
import cn.com.hq.util.PropertiesUtils;
import cn.com.hq.util.StringUtil;
import cn.com.hq.vo.OnboardInfoVO;

public class OnboardAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private OnboardService onboardService = new OnboardServiceImpl();
	
	public static void main(String[] args) {
		System.out.println(XssServiceImpl.escapeHtmlForString("{\"name\":\"a&amp;#x5c;1\",\"age\":0}"));
	}
	public String execute() throws Exception {
		return "success";
	}
	
	public void getOnboardInfo(){
		List<OnboardInfoVO> onboardInfos= onboardService.queryAllOnboardInfo();
		
		if(onboardInfos != null){
			responseWriter( JsonUtils.toJson(onboardInfos));
		}else{
			responseWriter("{\"success\":false,\"message\":\"result is null\"}");
		}
	}
	
	public void addOnboardInfo(){
		HttpServletRequest reguest= super.getRequest();
		String onboardInfo = reguest.getParameter("onboardInfo");
		try {
			OnboardInfo b = JsonUtils.fromJson(onboardInfo, OnboardInfo.class);
			String id = "ob" + Math.round(Math.random()*100000);
			b.setId(id);
			Date date = new Date();
			b.setAppointtime(StringUtil.changeDateFormat(date, "yyyy年MM月dd日 HH时mm分ss秒"));
			b.setAppointstatus("0");
			b.setOnboardstatus("0");
			onboardService.addOnboardInfo(b);
			responseWriter("{\"success\":true,\"message\":\"上车预约成功，耐心等待。。。\"}");
		}  catch (Exception e) {
			e.printStackTrace();
			responseWriter("{\"success\":false,\"message\":\"预约失败 \"}");
		}
		
	}
	
	public void modifyOnboardInfo(){
		
	}
	
	public void appointSuccess(){
		HttpServletRequest reguest= super.getRequest();
		String id = reguest.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			List<OnboardInfoVO> onboardInfos= onboardService.queryAllOnboardInfoById(id);
			if(onboardInfos.size()>0){
				OnboardInfoVO vo= onboardInfos.get(0);
				OnboardInfo o = new OnboardInfo();
				o.setId(vo.getId());
				o.setAccountid(vo.getAccountid());
				o.setAppointtime(vo.getAppointtime());
				o.setOnboardaddress(vo.getOnboardaddress());
				o.setOnboardtime(vo.getOnboardtime());
				o.setAppointstatus("1");
				o.setOnboardstatus(vo.getOnboardstatus());
				int count = onboardService.modifyOnboardInfo(o);
				if(count > 0){
					responseWriter("{\"success\":true,\"message\":\"上车预约成功\"}");
				}else{
					responseWriter("{\"success\":false,\"message\":\"更新失败\"}");
				}
			}else{
				responseWriter("{\"success\":false,\"message\":\"没有预约记录，预约失败\"}");
			}
			
		}
		
	}
	
	public void onboardSuccess(){
		HttpServletRequest reguest= super.getRequest();
		String id = reguest.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			List<OnboardInfoVO> onboardInfos= onboardService.queryAllOnboardInfoById(id);
			if(onboardInfos.size()>0){
				OnboardInfoVO vo= onboardInfos.get(0);
				OnboardInfo o = new OnboardInfo();
				o.setId(vo.getId());
				o.setAccountid(vo.getAccountid());
				o.setAppointtime(vo.getAppointtime());
				o.setOnboardaddress(vo.getOnboardaddress());
				o.setOnboardtime(vo.getOnboardtime());
				o.setAppointstatus(vo.getAppointstatus());
				o.setOnboardstatus("1");
				int count = onboardService.modifyOnboardInfo(o);
				if(count > 0){
					responseWriter("{\"success\":true,\"message\":\"上车预约成功\"}");
				}else{
					responseWriter("{\"success\":false,\"message\":\"更新失败\"}");
				}
			}else{
				responseWriter("{\"success\":false,\"message\":\"没有预约记录，预约失败\"}");
			}
			
		}
		
	}
	

}
