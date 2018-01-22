import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chaboshi.util.CBS;
import com.weixinpay.service.PayService;

import cn.com.hq.entity.Account;
import cn.com.hq.util.JsonUtils;
import cn.com.hq.util.QueryAppKeyLib;


public class TestSyso {
	private PayService payService = new PayService();   
	public static void main(String[] args) {
		String s1 =  CBS.getInstance(QueryAppKeyLib.baoyangUserId,QueryAppKeyLib.baoyangUserKey).getCheckBrand("LVSFDFAB0AN170786");
		System.out.println(s1);
		
	}
	public static void testRegex(){
		System.out.println("\\\"+1\\\"".replace("\\\"", "'"));
		System.out.println("吴\\谈adfadfs\\\\asd".replace("\\", ""));
		String regex = "^[\u2E80-\uFE4F\\w\\_\\-\\(\\)\\.\\/\\#\\$\\%\\~\\@\\s\\:\\=\\,\\;]*$";
		
		String line = "<>";
		if(line == null || "".equals(line.trim())){
		}else{
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(line);
			boolean isfind = false;
			while(m.find())
			{
				 isfind = true;
				line = m.group(m.groupCount());
				System.out.println(line);
			}
		}
	}
	
	public static void checkJsonStr(String str){
		System.out.println(str);
		System.out.println(str.indexOf("\""));
		System.out.println(str.charAt(str.indexOf("\"")));
		System.out.println(str.indexOf("\"", str.indexOf("\"")+1));
	}
}
