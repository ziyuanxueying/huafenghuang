package com.primecloud.primecloudpaysdk.version1_0;

import org.json.JSONObject;

interface  PayCallBack {

	public void onSuccess(JSONObject back);
	public void onError(JSONObject back);
}
