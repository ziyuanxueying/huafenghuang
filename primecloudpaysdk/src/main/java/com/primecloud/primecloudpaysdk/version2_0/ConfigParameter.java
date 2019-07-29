package com.primecloud.primecloudpaysdk.version2_0;



import android.support.annotation.NonNull;

import java.util.Map;

/**
 * 获取订单信息需要的参数，所对应的bean
 * @author zy
 *
 */
public class ConfigParameter {

	private int connectionTime = 10000;// 连接时间
	/**
	 * 请求订单信息对应的url
	 */
	private String url;
	
	/**
	 * 请求方式，默认post请求
	 */
	private boolean isPost = true;
	
	/**
	 * 请求头
	 * @param url
	 */
	private Map<String, String> headMap;
	
	/**
	 * 编码格式， 默认UTF-8
	 */
	private String encode = "UTF-8";
	/**
	 * 请求所带的参数
	 */

	private DealCallBackResult callBack;
	
	public ConfigParameter(@NonNull String url) {
		this.url = url;
	}

	public DealCallBackResult getCallBack() {
		if(callBack == null) {
			callBack = new DefaultDealCallBack();
		}
		return callBack;
	}

	public void setCallBack(DealCallBackResult callBack) {
		this.callBack = callBack;
	}


	public int getConnectionTime() {
		return connectionTime;
	}

	public void setConnectionTime(int connectionTime) {
		this.connectionTime = connectionTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public Map<String, String> getHeadMap() {
		return headMap;
	}

	public void setHeadMap(Map<String, String> headMap) {
		this.headMap = headMap;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	class Parameter{
		
	}
	
}
