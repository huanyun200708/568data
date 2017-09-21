package cn.com.hq.websocket;

import com.alibaba.fastjson.JSONObject;

public class MyRunnable implements Runnable{
    
    public MyRunnable() { }
     
    @Override
    public void run() {
    	while (true) {
    		Integer[] data1 = new Integer[24];
        	Integer[] data2 = new Integer[24];
        	Integer[] data3 = new Integer[24];
        	
        	for(int i=0; i<data1.length;i++){
        		data1[i] = (int)(Math.random()*50);
        	}
        	
        	for(int i=0; i<data2.length;i++){
        		data2[i] = (int)(Math.random()*50);
        	}
        	
        	for(int i=0; i<data3.length;i++){
        		data3[i] = (int)(Math.random()*50);
        	}
        	JSONObject json = new JSONObject();
        	json.put("data1", data1);
        	json.put("data2", data2);
        	json.put("data3", data3);
        	ForwardWebSocket.sendAll(json.toString());
        	try {
    			Thread.sleep(5000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
			
		}
    	
    }
}