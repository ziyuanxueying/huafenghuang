package com.primecloud.huafenghuang.ui.update;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.huafenghuang.widget.MySeekBar;


/*
 * 下载更新apk
 * */
public class Downloaded extends Dialog implements View.OnClickListener {
	// 进度条
	public MySeekBar seekbar_progress;// 下载进度
	//	 显示下载数值
	public TextView tv_download_progress, tv_soft_name;
	private Button iv_cancel;
	private OnClickListener mOnClickListener1;// 按钮1的单击监听事件
	private LinearLayout btn_cancel;
	private int isUpdate;

	public Downloaded(Context context, int isUpdate) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		setContentView(R.layout.dialog_download_apk_layout);
		this.isUpdate=isUpdate;
		setCancelable(false);
		initView();
		initEvent();
	}

	private void initView() {
		tv_download_progress = (TextView) findViewById(R.id.tv_upload_progress);
		iv_cancel = (Button) findViewById(R.id.iknow_alert_dialog_button);
		btn_cancel = (LinearLayout) findViewById(R.id.iknow_alert_dialog_button_panel);
		if(isUpdate==1){
			ViewUtils.setGone(btn_cancel);
		}
	}

	private void initEvent() {
		iv_cancel.setOnClickListener(this);
	}

	public void setButton(OnClickListener listener1) {
		mOnClickListener1 = listener1;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iknow_alert_dialog_button:
				if (mOnClickListener1 != null) {
					mOnClickListener1.onClick(this, 0);
				}
				break;
		}
	}

	public void setDeleGone(){
		ViewUtils.setGone(btn_cancel);
	}

}

