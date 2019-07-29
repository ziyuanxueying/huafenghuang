package com.primecloud.huafenghuang.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.primecloud.huafenghuang.R;


@SuppressLint("InflateParams")
public class TipsToast extends Toast {

	public static int toastTag = 0;

	public TipsToast(Context context) {
		super(context);
	}

	public static TipsToast makeText(Context context, CharSequence text, int duration) {
		toastTag = 1;
		TipsToast result = new TipsToast(context);
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.view_tips, null);
		result.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);
		return result;
	}

	public static TipsToast makeTexts(Context context, String resId, int duration) throws Resources.NotFoundException {
		return makeText(context, resId, duration);
	}

	/**
	 * 显示toast信息
	 *
	 * @param context
	 * @param log
	 */
	public static TipsToast showToast(Context context, String log) {
		toastTag = 2;
		View toastRoot = LayoutInflater.from(context).inflate(R.layout.common_brevity_toast, null);
		TipsToast toast = new TipsToast(context);
		toast.setGravity(Gravity.BOTTOM, 0, 400);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		return toast;
	}

	public void setMsg(String msg) {
		if (getView() == null) {
			throw new RuntimeException("This Toast was not created with Toast.makeText()");
		}
		TextView tv = (TextView) getView().findViewById(R.id.common_toast_message);
		if (tv == null) {
			throw new RuntimeException("This Toast was not created with Toast.makeText()");
		}
		tv.setText(msg);
	}

	public void setIcon(int iconResId) {
		if (getView() == null) {
			throw new RuntimeException("This Toast was not created with Toast.makeText()");
		}
		ImageView iv = (ImageView) getView().findViewById(R.id.tips_icon);
		if (iv == null) {
			throw new RuntimeException("This Toast was not created with Toast.makeText()");
		}
		iv.setImageResource(iconResId);
	}

	@Override
	public void setText(CharSequence s) {
		if (getView() == null) {
			throw new RuntimeException("This Toast was not created with Toast.makeText()");
		}
		TextView tv = (TextView) getView().findViewById(R.id.tips_msg);
		if (tv == null) {
			throw new RuntimeException("This Toast was not created with Toast.makeText()");
		}
		tv.setText(s);
	}

}
