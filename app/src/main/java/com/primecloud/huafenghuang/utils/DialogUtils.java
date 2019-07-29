package com.primecloud.huafenghuang.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.primecloud.library.baselibrary.log.XLog;
import com.primecloud.huafenghuang.R;

/**
 * Created by ${qc} on 2018/12/20.
 */

public class DialogUtils {

    /**
     * 新版本升级对话框
     */
    public static DeleteDialog showVersionUpdate(Context context, String message, int is_update, String btn, View.OnClickListener listener) {
        DeleteDialog deleteDialog=new DeleteDialog(context);
        Window dialogWindow = deleteDialog.getWindow();
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        if (d.getHeight()>d.getWidth())
        {//竖屏
            p.height = (int) (d.getHeight() * 0.24); // 高度设置为屏幕的0.24
            p.width = (int) (d.getWidth() * 0.76); // 宽度设置为屏幕的0.76
        }
        else
        {//横屏
            p.width = (int) (d.getWidth() * 0.4); // 高度设置为屏幕的0.4
            p.height = (int) (d.getHeight() * 0.4); // 宽度设置为屏幕的0.4
        }

        if (is_update == 1) {//强制升级
//            deleteDialog.setAlertDialogTitle("升级提示");
            deleteDialog.setButtonSur(context.getString(R.string.update_button_new),listener);
        } else {
//            deleteDialog.setAlertDialogTitle("发现新版本");
            deleteDialog.setButton1(context.getString(R.string.choose_photo_ignore),listener);
            deleteDialog.setButton2(btn, listener);
        }
        dialogWindow.setAttributes(p);
        deleteDialog.setAlertDialogMessage(message);
        deleteDialog.setCancelable(false);
        return deleteDialog;
    }
    /**
     *最新版本对话框
     */
    public static SingButtonDialog showNewVersion(Context context, String title, String message,
                                                  int isUpload, View.OnClickListener listener) {
        if (context!=null&&!((Activity) context).isFinishing())
        {
            final SingButtonDialog deleteDialog=new SingButtonDialog(context);
            Window dialogWindow = deleteDialog.getWindow();
            WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            if (d.getHeight()>d.getWidth())
            {//竖屏
                p.height = (int) (d.getHeight() * 0.24); // 高度设置为屏幕的0.24
                p.width = (int) (d.getWidth() * 0.76); // 宽度设置为屏幕的0.76
            }
            else
            {//横屏
                p.width = (int) (d.getWidth() * 0.4); // 高度设置为屏幕的0.4
                p.height = (int) (d.getHeight() * 0.4); // 宽度设置为屏幕的0.4
            }
            dialogWindow.setAttributes(p);
            deleteDialog.setAlertDialogMessage(message);
            if(isUpload==1){
                deleteDialog.setButtonCan(context.getString(R.string.update_button),listener);
            }else{
                deleteDialog.setButtonCan(context.getString(R.string.choose_photo_know),listener);
            }
            deleteDialog.setCancelable(false);
            return deleteDialog;
        }
        return null;
    }

    /**
     *最新版本对话框
     */
    public static DeleteDialog showInfoDialog(Context context,String message
                                              ,String content,View.OnClickListener listener) {
        if (context!=null&&!((Activity) context).isFinishing())
        {
            final DeleteDialog deleteDialog=new DeleteDialog(context);
            Window dialogWindow = deleteDialog.getWindow();
            WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            if (d.getHeight()>d.getWidth())
            {//竖屏
                p.height = (int) (d.getHeight() * 0.24); // 高度设置为屏幕的0.24
                p.width = (int) (d.getWidth() * 0.76); // 宽度设置为屏幕的0.76
            }
            else
            {//横屏
                p.width = (int) (d.getWidth() * 0.4); // 高度设置为屏幕的0.4
                p.height = (int) (d.getHeight() * 0.4); // 宽度设置为屏幕的0.4
            }
            dialogWindow.setAttributes(p);
            deleteDialog.setAlertDialogMessage(message);
            deleteDialog.setButtonCan(content,listener);
            deleteDialog.setCancelable(false);
            return deleteDialog;
        }
        return null;
    }

    /**
     * 对话框
     */
    public static DeleteDialog showDeleteDialog(Context context,String message,String btnC,String btnS,View.OnClickListener listener){
        if (context!=null&&!((Activity) context).isFinishing())
        {
            final DeleteDialog deleteDialog=new DeleteDialog(context);
            Window dialogWindow = deleteDialog.getWindow();
            WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            if (d.getHeight()>d.getWidth())
            {//竖屏
                p.height = (int) (d.getHeight() * 0.24); // 高度设置为屏幕的0.24
                p.width = (int) (d.getWidth() * 0.76); // 宽度设置为屏幕的0.76
            }
            else
            {//横屏
                p.width = (int) (d.getWidth() * 0.4); // 高度设置为屏幕的0.4
                p.height = (int) (d.getHeight() * 0.4); // 宽度设置为屏幕的0.4
            }

            dialogWindow.setAttributes(p);
            deleteDialog.setAlertDialogMessage(message);
            deleteDialog.setButton1(btnC, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO 自动生成的方法存根
                    deleteDialog.dismiss();
                }
            });
            deleteDialog.setButton2(btnS, listener);
            return deleteDialog;
        }
        return null;
    }
    /**
     * 加载数据的dialog 加载用户信息
     * @param context 必须是 activity的实例
     * @param msg     显示的文字
     */
    public static Dialog showProgressDialogWithMessage(Context context, String msg) {
        if (!(context instanceof Activity)) {
            return null;
        }
        dialog = new Dialog(context, R.style.Theme_Light_FullScreenDialogAct);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView = LayoutInflater.from(context).inflate(R.layout.app_loading_message_layout, null);
        TextView textView = (TextView) mView.findViewById(R.id.app_progressbar_hint);
        textView.setText(msg);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.dismiss();
        if (!dialog.isShowing() && context != null && ((Activity) context).hasWindowFocus()) {
            dialog.show();
        }
        return dialog;
    }


    /**
     * 底部弹出相册选择框
     */
    @SuppressWarnings("deprecation")
    public static void choosePicDialog(Activity activity, View.OnClickListener listener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_pic, null);
        dialog = new Dialog(activity, R.style.Theme_Light_FullScreenDialogAct);
        dialog.setContentView(view,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围取消对话框
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Button iv_camera = (Button) view.findViewById(R.id.btn_camera);
        Button iv_photo = (Button) view.findViewById(R.id.btn_photo);
        Button cancel = (Button) view.findViewById(R.id.dialog_pic_cancel);
        iv_camera.setOnClickListener(listener);
        iv_photo.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
    }



    /**
     * 分享
     */
    public static Dialog shareDialog(Context activity, View.OnClickListener clickListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_share, null);
        ImageView weichat = (ImageView) view.findViewById(R.id.weichat);
        ImageView pengyouquan = (ImageView) view.findViewById(R.id.pengyouquan);
        ImageView qq = (ImageView) view.findViewById(R.id.qq);
        TextView cancel = (TextView) view.findViewById(R.id.share_cancel);


        cancel.setOnClickListener(clickListener);
        weichat.setOnClickListener(clickListener);
        pengyouquan.setOnClickListener(clickListener);
        qq.setOnClickListener(clickListener);

        Dialog dialog = new Dialog(activity, R.style.Theme_Light_FullScreenDialogAct);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();

        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围不取消对话框
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }



    /**
     * 分享
     */
    public static Dialog shareDialogWX(Context activity, View.OnClickListener clickListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_share, null);
        ImageView weichat = (ImageView) view.findViewById(R.id.weichat);
        ImageView pengyouquan = (ImageView) view.findViewById(R.id.pengyouquan);
        ImageView qq = (ImageView) view.findViewById(R.id.qq);
        TextView cancel = (TextView) view.findViewById(R.id.share_cancel);
        qq.setVisibility(View.GONE);

        cancel.setOnClickListener(clickListener);
        weichat.setOnClickListener(clickListener);
        pengyouquan.setOnClickListener(clickListener);
        qq.setOnClickListener(clickListener);

        Dialog dialog = new Dialog(activity, R.style.Theme_Light_FullScreenDialogAct);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();

        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围不取消对话框
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }










    /**
     * 底部弹窗  解除绑定
     */
    public static Dialog saveDialog(Context context, String saves, String cancels, View.OnClickListener clickListener) {
        if (null != context) {

            dialog = new Dialog(context, R.style.Theme_Light_FullScreenDialogAct);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View mView = LayoutInflater.from(context).inflate(R.layout.dialog_save, null);
            Button save = (Button) mView.findViewById(R.id.dialog_save);
            Button cancel = (Button) mView.findViewById(R.id.dialog_cancel);
            save.setText(saves);
            cancel.setText(cancels);
            save.setOnClickListener(clickListener);
            cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO 自动生成的方法存根
                    dialog.dismiss();
                }
            });

            dialog.setContentView(mView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            // 设置显示动画
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            wl.gravity = Gravity.BOTTOM;

            return dialog;
        } else {
            return null;
        }
    }

    public static Dialog dialog = null;
    public static void dismiss() {
        XLog.i("DISMISS:");
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
