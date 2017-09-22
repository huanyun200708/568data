package cn.com.hq.webservice.impl;

import javax.jws.WebService;

import cn.com.hq.webservice.HelloWorldDao;

@WebService
public class HelloWorldImpl implements HelloWorldDao {

    @Override
    public void sayHello(String name) {
        System.out.println("hello," + name);
    }
}
