package com.quan.fems.trim.server;
import java.util.HashMap;
import java.util.Map;

public class NameValuePair {
	private HashMap<String, Object> _lst;
	public NameValuePair(){
		_lst=new HashMap<String, Object>();
	}
	public void add(String name,Object value){
		_lst.put(name, value);
	}
	public String entry(){
		String rtn="";
		for (Map.Entry<String, Object> itm : _lst.entrySet()) {
			if("".equals(rtn)){
				rtn = itm.getKey()+"="+itm.getValue();
			}else{
				rtn += "&"+itm.getKey()+"="+itm.getValue();
			}
		}
		return rtn;
	}
}