package com.primecloud.huafenghuang.ui.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.utils.DialogUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareUtils {

    private Context mContext;
    private Dialog dialog;

    public ShareUtils(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     *
     * @param title 标题
     * @param titleUel 标题链接
     * @param text 描述
     * @param imagePath 图片
     * @param url 链接
     */
    public  void share(final String title, String titleUel, String text, String imagePath, String url,int shareType) {

        share(title, titleUel, text, imagePath, url, shareType,0);

    }



    public void shareWX(final String title, String titleUel, String text, String imagePath, String url, int shareType, PlatformActionListener platformActionListener) {
        ShareBean shareBean = new ShareBean();
        shareBean.setText(text);
        shareBean.setImagePath(imagePath);
        shareBean.setTitle(title);
        shareBean.setTitleUrl(titleUel);
        shareBean.setUrl(url);
        if(platformActionListener == null){
            platformActionListener = new ShareCallBackListener();
        }
        shareBean.setListener(platformActionListener);
        shareBean.setShareType(shareType);

        dialog = DialogUtils.shareDialogWX(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case  R.id.weichat:
                        shareBean.setPlatName(Wechat.NAME);
                        MyShareUtils.share(shareBean);
                        break;
                    case R.id.qq:
                        shareBean.setPlatName(QQ.NAME);
                        MyShareUtils.share(shareBean);
                        break;
                    case R.id.pengyouquan:
                        shareBean.setPlatName(WechatMoments.NAME);
                        MyShareUtils.share(shareBean);
                        break;
                    case R.id.share_cancel:
                        break;
                }

                if (dialog!=null&&dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }
        });
        if (dialog!=null&&!dialog.isShowing())
        {
            dialog.show();
        }
    }

    /**
     *
     * @param title 标题
     * @param titleUel 标题链接
     * @param text 描述
     * @param imagePath 图片
     * @param url 链接
     */
    public  void share(final String title, String titleUel, String text, String imagePath, String url, int shareType ,int sType) {
        ShareBean shareBean = new ShareBean();
        shareBean.setText(text);
        shareBean.setImagePath(imagePath);
        shareBean.setTitle(title);
        shareBean.setTitleUrl(titleUel);
        shareBean.setUrl(url);
        shareBean.setListener(new ShareCallBackListener());
        shareBean.setShareType(shareType);
        shareBean.setsType(sType);

        dialog = DialogUtils.shareDialog(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case  R.id.weichat:
                        shareBean.setPlatName(Wechat.NAME);
                        MyShareUtils.share(shareBean);
                        break;
                    case R.id.qq:
                        shareBean.setPlatName(QQ.NAME);
                        MyShareUtils.share(shareBean);
                        break;
                    case R.id.pengyouquan:
                        shareBean.setPlatName(WechatMoments.NAME);
                        MyShareUtils.share(shareBean);
                        break;
                    case R.id.share_cancel:
                        break;
                }

                if (dialog!=null&&dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }
        });
        if (dialog!=null&&!dialog.isShowing())
        {
            dialog.show();
        }

    }




    public class ShareCallBackListener implements PlatformActionListener
    {


        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Log.i("sss","分享。。。。。。。。失败"+platform.getName()+",,,,,,,"+i+"............"+throwable.getMessage());
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Log.i("sss","分享。。。。。。。。成功"+platform.getName()+",,,,,,,"+i+"............"+hashMap.toString());
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Log.i("sss","分享。。。。。。。。取消"+platform.getName()+",,,,,,,"+i);
        }
    }

}
