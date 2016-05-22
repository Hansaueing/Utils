package com.han.musicplayer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.han.musicplayer.entity.Music;



/**
 * 封装一些静态方法供其他类调用 ex:get/post
 * 
 * @author Administrator
 * 
 */
public class HttpUtils {
	
	/**
	 * 
	 * @param is
	 *            从服务端获取的输入流
	 * @return 返回字符串
	 */
	public static String getStr(InputStream is) {
		StringBuffer buff = new StringBuffer();
		// 流的包装
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		try {
			line = br.readLine();
			while (line != null) {
				buff.append(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff.toString();
	}
	/**
	 * post方式获取输入流
	 * @return
	 */
	public static InputStream postIs(String path,String parma){
		InputStream is = null;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			OutputStream os = conn.getOutputStream();
			os.write(parma.getBytes("utf-8"));
			os.flush();
//			conn.setRequestProperty("Content",
//					"application/x-www-form-urlencoded");
			is = conn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	/**
	 * get方式获取输入流
	 * @return
	 */
	public static InputStream getIs(String path){
		InputStream is = null;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			is = conn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	public static Music getJson(String str){
		Music result = new Music();
		try {
			JSONObject object = new JSONObject(str);
			String re=(String) (object.isNull("result")?"":object.get("result"));
			String msg=(String) (object.isNull("msg")?"":object.get("msg"));
			//result.setMsg(msg);
			//result.setResult(re);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * @param path
	 * @param param
	 * @return
	 * 通过post请求获取输入流
	 */
	public static InputStream  postLogin(String path,String param,String cookie){
		InputStream is=null;
		try {
			//url
			URL url=new URL(path);
			//conn
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			//请求方法
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			//返回cookid
			conn.setRequestProperty("Cookie", cookie);
			OutputStream os=conn.getOutputStream();
			os.write(param.getBytes("utf-8"));
			
			os.flush();
			is=conn.getInputStream();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return is;
	}
	
	
}
