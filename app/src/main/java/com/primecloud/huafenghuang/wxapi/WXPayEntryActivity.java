package com.primecloud.huafenghuang.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.ApplyResultActivity;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.primecloudpaysdk.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, "wxd87d46e79456b626");
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.i("sss", "onPayFinish, errCode = " + resp.errCode);

//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("title");
//			builder.setMessage("pay_result_callback_msg"+ String.valueOf(resp.errCode));
//			builder.show();
//		}

		switch (resp.errCode)
		{
			case 0://成功
				Intent intent = new Intent(this, ApplyResultActivity.class);
				intent.putExtra("productType",MyApplication.getInstance().getProductType());
				startActivity(intent);
				if (MyApplication.getInstance().getBuyConetxt()!=null)
				{
					((Activity)MyApplication.getInstance().getBuyConetxt()).finish();
				}
				break;
			case -1://失败
				ToastUtils.showToast(this,"支付失败");
				break;
			case -2://取消
				ToastUtils.showToast(this,"取消支付");
				break;
		}

		finish();
	}
}