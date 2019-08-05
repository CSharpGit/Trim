package com.quan.fems.trim.server;
public class HttpParam {
	public String url;
	public NameValuePair param;
	public HttpListener httpListener;
	public String method="POST";
	public String encoding="utf-8";
	public HttpParam(){
		param=new NameValuePair();
	}
}
