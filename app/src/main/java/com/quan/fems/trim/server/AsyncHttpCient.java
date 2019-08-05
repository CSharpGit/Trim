package com.quan.fems.trim.server;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncHttpCient extends AsyncTask<HttpParam,Integer,String>{
	private HttpListener httpListener=null;
	@Override
	protected String doInBackground(HttpParam... params) {
		if(params.length>0){
			httpListener=params[0].httpListener;
			String rtn=respose(params[0].url,params[0].param,params[0].method,params[0].encoding);
			return rtn;
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		if(result!=null){
			if(httpListener!=null){
				System.out.println(result);
				httpListener.onPostData(result);
			}
		}
	}
	private String respose(String url,NameValuePair param,String method,String encoding){
		System.out.println(url);
		HttpURLConnection hcnn=getHttpURLConnection(url,method);
		String rtn=null;
		try {
			byte[] data = param.entry().getBytes(encoding);
			hcnn.setRequestProperty("Content-Length", String.valueOf(data.length));
            System.out.println(new String(data));
			OutputStream outStream = hcnn.getOutputStream();
	        outStream.write(data);
	        outStream.flush();
	        outStream.close();
	        if(hcnn.getResponseCode()==200){
	            InputStream inStream = hcnn.getInputStream();
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    int ch=0;
			   	while((ch=inStream.read())!=-1){
			      	baos.write(ch);
			    }
			    byte[] bdt=baos.toByteArray();
			    baos.close();
			    rtn = new String(bdt).trim();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}
	private HttpURLConnection getHttpURLConnection(String url,String method){
		HttpURLConnection mHttpURLConnection=null;
		try {
			URL mUrl=new URL(url);
			mHttpURLConnection=(HttpURLConnection)mUrl.openConnection();
			//设置链接超时时间
			mHttpURLConnection.setConnectTimeout(15000);
			//设置读取超时时间
			mHttpURLConnection.setReadTimeout(15000);
			//设置请求参数
			mHttpURLConnection.setRequestMethod(method);
			//添加Header
			mHttpURLConnection.setRequestProperty("Connection","Keep-Alive");
			//接收输入流
			mHttpURLConnection.setDoInput(true);
			//传递参数时需要开启
			mHttpURLConnection.setDoOutput(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mHttpURLConnection;
	}
}
