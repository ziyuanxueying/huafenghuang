package com.primecloud.huafenghuang.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;


public class SingButtonDialog extends Dialog implements View.OnClickListener{
	private Context mContext;
	private TextView content_message,content_title;
	private Button buttonCan;

	private OnClickListener mOnClickListener1;//buttonCan
	private OnClickListener mOnClickListener2;//buttonSur
	public SingButtonDialog(Context context) {
		super(context, R.style.common_alert_dialog_theme);
		this.mContext = context;
		View view = LayoutInflater.from(mContext).inflate(R.layout.common_single_dialog, null);
		setContentView(view);
		initViews(view);
		initEvents();
		setCancelable(true);
		setCanceledOnTouchOutside(false);
	}
	private void initViews(View view) {
		content_title =(TextView) view.findViewById(R.id.iknow_alert_dialog_content_title);
		content_message = (TextView) view.findViewById(R.id.iknow_alert_dialog_content_message);
		buttonCan = (Button) view.findViewById(R.id.iknow_alert_dialog_button1);
	}
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
			case R.id.iknow_alert_dialog_button1:
				if (mOnClickListener1 != null) {
					mOnClickListener1.onClick(this, 0);
				}
				break;
			default:
				break;
		}
	}
	private void initEvents() {
		buttonCan.setOnClickListener(this);
	}
	/**
	 * 设置title
	 */
	public void setAlertDialogTitle(String message) {
		if (StringUtils.notBlank(message)) {
			content_title.setVisibility(View.VISIBLE);
			content_title.setText(message);
		} else {
			content_title.setVisibility(View.INVISIBLE);
		}
	}
	/**
	 * 设置message
	 * @param message
	 */
	public void setAlertDialogMessage(String message) {
		if (StringUtils.notBlank(message)) {
			content_message.setText(message);
		} else {
			content_message.setVisibility(View.INVISIBLE);
		}
	}
	public void setButtonCan(String text,View.OnClickListener listener) {
		if (StringUtils.notBlank(text) && listener != null) {
//		buttonCan.setBackgroundResource(R.drawable.shape_orange);
//		buttonCan.setTextColor(mContext.getResources().getColor(R.color.white));
		buttonCan.setText(text);
		buttonCan.setOnClickListener(listener);
	}
	}
}
