import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;  

import cn.com.hq.webservice.HelloWorldDao;
import cn.com.hq.webservice.impl.HelloWorldImpl;
public class TestClient {  
    public static void main(String[] args) {  
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
        factory.setServiceClass(HelloWorldDao.class);  
        factory.setAddress("http://localhost:8080/568data/webservice/helloworld");  
        HelloWorldDao service = (HelloWorldDao) factory.create();  
        System.out.println("#############Client getUserByName##############");  
        service.sayHello("aa");
    }  
}  
