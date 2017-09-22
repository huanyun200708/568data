package cn.com.hq.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloWorldDao {
    public void sayHello(@WebParam(name = "name") String name);

}
