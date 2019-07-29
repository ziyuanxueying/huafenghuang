package com.primecloud.primecloudpaysdk.version2_0;

import android.util.Log;

import com.primecloud.primecloudpaysdk.bean.RequestParamter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

class PayHttpUtils{
	
	/**
	 * 进行订单请求
	 * @param requestParamter  请求参数
	 * @param configParameter 请求的配置
	 * @param payMethod 支付的方式
	 */
	public static void sendRequest(final RequestParamter requestParamter , final ConfigParameter configParameter, final PaySDK.PayMethod payMethod) {
			
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 byte[] data = null;
					if(requestParamter != null) {
						data = getRequestData(requestParamter, configParameter).toString().trim().getBytes();
					}
					
				URL url;
				try {
					
					url = new URL(configParameter.getUrl());
					HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.setConnectTimeout(10000); // 设置连接超时时间
					httpURLConnection.setDoInput(true); // 打开输入流，以便从服务器获取数据
					httpURLConnection.setDoOutput(true); // 打开输出流，以便向服务器提交数据
					httpURLConnection.setUseCaches(false); // 使用Post方式不能使用缓存
					setRequestHeader(configParameter, httpURLConnection);
				
					if(configParameter.isPost()) {// post请求
						httpURLConnection.setRequestMethod("POST"); // 设置以Post方式提交数据
						OutputStream outputStream = httpURLConnection.getOutputStream();
						Log.i("sss", "发送请求数据:"+new String(data));
						outputStream.write(data);
						outputStream.flush();
						outputStream.close(); 
					}else {//get请求
						httpURLConnection.setRequestMethod("GET"); // 设置以Get方式提交数据
					}
					
					int response = httpURLConnection.getResponseCode(); // 获得服务器的响应码
					Log.i("sss", response+"");
					if (response == HttpURLConnection.HTTP_OK) {
						InputStream inptStream = httpURLConnection.getInputStream();
						JSONObject obj = dealResponseResult(inptStream);
						inptStream.close();
						configParameter.getCallBack().onSuccess(obj, payMethod);
					}else{
						configParameter.getCallBack().onError("Internal Error");
					}
					
					httpURLConnection.disconnect();
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.i("sss", "MalformedURLException");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.i("sss", "IOException");
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Function : 处理服务器的响应结果（将输入流转化成字符串） Param : inputStream服务器的响应输入流
	 */
	private static JSONObject dealResponseResult(InputStream inputStream) {
		String resultData = null; // 存储处理结果
		JSONObject obj = null;
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
			Log.i("sss 结果", resultData);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		try {
			 obj = new JSONObject(resultData);
			 return obj;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 设置请求头
	 * @param configParameter
	 */
	private static void setRequestHeader(ConfigParameter configParameter, HttpURLConnection httpURLConnection) {
		Map<String, String> headMap = configParameter.getHeadMap();
		if(headMap != null) {
			boolean flag = false;
			for (Map.Entry<String, String> entry : headMap.entrySet()) {  
				String key = entry.getKey();
				if("Content-type".equals(key)) {
					flag = true;
				}
				String value = entry.getValue();
			    httpURLConnection.setRequestProperty(key, value);
			} 
			if(flag == false) {
				httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded"); 
			}
		}
	}

	/**
	 * 获取请求参数
	 * @param payParameter
	 * @param configParameter
	 * @return
	 */
	private static StringBuffer getRequestData(RequestParamter requestParamter, ConfigParameter configParameter) {
		
		StringBuffer sb = new StringBuffer();
		List<SensorData> parameters = PaySDKReflectUtils.sensorDataList(requestParamter);
		splicingParamter(sb, parameters , configParameter.getEncode());
		return sb;
	}
	
	/**
	 * 拼接请求信息
	 * @param sb
	 * @param parameters
	 * @param encode
	 */
	private static void splicingParamter(StringBuffer sb, List<SensorData> parameters, String encode) {
		
		int size = parameters.size();
		for(int i=0; i<size; i++) {
			SensorData sensorData = parameters.get(i);
			
			String key = sensorData.getSensorId();
			Object value = sensorData.getSensorValue();
			
			if(value == null) {
				continue;
			}
			
			String tempValue = String.valueOf(value);
			
			try {
	
				if(i == size-1) {
					sb.append(key).append("=").append(URLEncoder.encode(tempValue, encode));
				}else {
					sb.append(key).append("=").append(URLEncoder.encode(tempValue, encode))
					.append("&");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
	}
}
