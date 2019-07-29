package com.primecloud.huafenghuang.utils;

import android.content.Context;
import android.os.Build;

public class ToastUtils {

	private static final String TextView = null;
	public static TipsToast tipsToast;

	/**
	 * 显示toast信息
	 *
	 * @param context
	 * @param log
	 */
	public static void showToast(Context context, String log) {
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
			if (TipsToast.toastTag != 2) {
				tipsToast = TipsToast.showToast(context, log);
			}
		} else {
			tipsToast = TipsToast.showToast(context, log);
		}
		tipsToast.show();
		tipsToast.setMsg(log);
	}

}
