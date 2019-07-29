package cn.jzvd;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DialogUtils {


    public static TextView textView;

    /**
     * 是否使用wifi播放
     */
    public static Dialog isWIFIDialog(Context activity,View.OnClickListener clickListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_iswifi, null);
        Button button_pause = (Button) view.findViewById(R.id.iswifi_pause);
        Button button_continue = (Button) view.findViewById(R.id.iswifi_continue);
        textView = (TextView) view.findViewById(R.id.iswifi_text);


        button_continue.setOnClickListener(clickListener);
        button_pause.setOnClickListener(clickListener);

        Dialog dialog = new Dialog(activity, R.style.Theme_Light_FullScreenDialogAct);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = (int)(((Activity)activity).getWindowManager().getDefaultDisplay().getWidth()*0.56);
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        // 设置点击外围不取消对话框
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
