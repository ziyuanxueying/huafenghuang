package com.primecloud.primecloudpaysdk.version1_0;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

	private HttpURLConnection httpURLConnection;
	private InputStream inptStream;
	private PayCallBack back;
	private byte[] data;
	private int type;

	/**
	 * 
	 * @param type
	 * @param strUrlPath
	 * @param headMap 头信息集合
	 * @param params
	 * @param encode
	 * @param back
	 */
	public HttpUtil(int type, String strUrlPath, Map<String, String> headMap, Map<String, String> params, String encode, PayCallBack back) {
		try {
			this.back = back;
			this.type = type;
			if (params != null) {
				data = getRequestData(params, encode).toString().getBytes();// 获得请求体
			} else {
				data = null;
			}
			// String urlPath = "http://192.168.1.9:80/JJKSms/RecSms.php";
			URL url = new URL(strUrlPath);

			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(10000); // 设置连接超时时间
			httpURLConnection.setDoInput(true); // 打开输入流，以便从服务器获取数据
			httpURLConnection.setDoOutput(true); // 打开输出流，以便向服务器提交数据
			httpURLConnection.setUseCaches(false); // 使用Post方式不能使用缓存
			httpURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			if(headMap != null) {
				for (Map.Entry<String, String> entry : headMap.entrySet()) {  
				    httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
				}  
			}
			
			switch (type) {
			case 0:
				httpURLConnection.setRequestMethod("POST"); // 设置以Post方式提交数据
				OutputStream outputStream = httpURLConnection.getOutputStream();
				outputStream.write(data);
				break;
			case 1:
				httpURLConnection.setRequestMethod("GET"); // 设置以Get方式提交数据
				break;
			}
			int response = httpURLConnection.getResponseCode(); // 获得服务器的响应码
			Log.i("sss", response+"");
			if (response == HttpURLConnection.HTTP_OK) {
				inptStream = httpURLConnection.getInputStream();
				dealResponseResult(inptStream);

			}else{
				back.onError(getError(0, type, response, "Internal Error"));
			}

		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	/*
	 * Function : 发送Post请求到服务器 Param : params请求体内容，encode编码格式
	 */

	/*
	 * Function : 封装请求体信息 Param : params请求体内容，encode编码格式
	 */
	public static StringBuffer getRequestData(Map<String, String> params, String encode) {
		StringBuffer stringBuffer = new StringBuffer(); // 存储封装好的请求体信息
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String value = entry.getValue();
				if(value == null) {
					continue;
				}
				stringBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // 删除最后的一个"&"
			Log.i("sss", stringBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/*
	 * Function : 处理服务器的响应结果（将输入流转化成字符串） Param : inputStream服务器的响应输入流
	 */
	public void dealResponseResult(InputStream inputStream) {
		String resultData = null; // 存储处理结果
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(data)) != -1) {
				Log.i("sss","printStackTrace"+len);
				byteArrayOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			resultData = new String(byteArrayOutputStream.toByteArray(),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Log.i("sss", "resultData:"+resultData);
			
		JSONObject obj = new JSONObject(resultData);
		int errorCode = obj.getInt("errorCode");
		
		JSONObject json = null;
		
		int excuCode = obj.getInt("excuCode");
		if(excuCode == 1) {// 有数据
			json = obj.getJSONObject("data");
		}else if(excuCode == 0) {// 没数据
			json = new JSONObject();
			json.put("errorCode", errorCode);
		}
		
		if(true){
			back.onSuccess(json);
		}else{
			back.onError(json);
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject getError(int state,int type,int code,String message){
		JSONObject obj=new JSONObject();
		try {
			obj.put("state", state);
			obj.put("type", type);
			obj.put("excuCode", code);
			obj.put("message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}
}