package com.primecloud.huafenghuang.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;


public class DeleteDialog extends Dialog implements View.OnClickListener{
	private Context mContext;
	private TextView content_message,content_title;
	private Button buttonCan,buttonSur;

	private OnClickListener mOnClickListener1;//buttonCan
	private OnClickListener mOnClickListener2;//buttonSur
	public DeleteDialog(Context context) {
		super(context, R.style.common_alert_dialog_theme);
		this.mContext = context;
		View view = LayoutInflater.from(mContext).inflate(R.layout.common_delete_dialog, null);
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
		buttonSur = (Button) view.findViewById(R.id.iknow_alert_dialog_button2);
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
			case R.id.iknow_alert_dialog_button2:
				if (mOnClickListener2 != null) {
					mOnClickListener2.onClick(this, 0);
				}
				break;

			default:
				break;
		}
	}
	private void initEvents() {
		buttonCan.setOnClickListener(this);
		buttonSur.setOnClickListener(this);
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
//			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)buttonCan.getLayoutParams();
//			int a = Utils.dip2px(mContext,75);
//			params.width = a;//这里的是你要设置的宽度，单位像素(xp)
//			buttonCan.setLayoutParams(params);
			buttonCan.setBackgroundResource(R.drawable.shape_pink);
			buttonCan.setTextColor(mContext.getResources().getColor(R.color.white));
			buttonCan.setText(text);
			buttonCan.setOnClickListener(listener);
			buttonSur.setVisibility(View.GONE);
		}
	}
	public void setButtonSur(String text,View.OnClickListener listener) {
		if (StringUtils.notBlank(text) && listener != null) {
			buttonSur.setBackgroundResource(R.drawable.shape_pink);
			buttonSur.setTextColor(mContext.getResources().getColor(R.color.white));
			buttonSur.setText(text);
			buttonSur.setOnClickListener(listener);
			buttonCan.setVisibility(View.GONE);
		}
	}
	public void setButton1(String text, View.OnClickListener listener) {
		if (StringUtils.notBlank(text) && listener != null) {
			buttonCan.setText(text);
			buttonCan.setOnClickListener(listener);
		} else {
			buttonCan.setVisibility(View.GONE);
		}
	}
	public void setButton2(String text, View.OnClickListener listener) {
//		buttonSur.setBackgroundResource(R.drawable.common_alter_dialog_selector_rbt);
		if (StringUtils.notBlank(text) && listener != null) {
			buttonSur.setText(text);
			buttonSur.setOnClickListener(listener);
		} else {
			buttonSur.setVisibility(View.GONE);
		}
	}
}
