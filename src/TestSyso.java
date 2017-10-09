import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.hq.entity.Account;
import cn.com.hq.util.JsonUtils;


public class TestSyso {

	public static void main(String[] args) {
		System.out.println((1*1.00/100));
		System.out.println("吴\\谈adfadfs\\\\asd".replace("\\", ""));
	}
	public static void testRegex(){
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
