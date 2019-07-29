package com.primecloud.huafenghuang.ui.share;

import android.text.TextUtils;

import com.primecloud.library.baselibrary.log.XLog;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by zy on 2019/5/13.
 */

public class MyShareUtils {

    /**
     * 分享
     * @param shareBean
     */
    public static void share(ShareBean shareBean){
        if(shareBean == null)
            throw new NullPointerException("传入的shareBean不能为空");

        Platform.ShareParams sp = new Platform.ShareParams();
        // text是分享文本，所有平台都需要这个字段



        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        String imagePath = shareBean.getImagePath();
        XLog.i("imagePath:"+imagePath);
        if (!TextUtils.isEmpty(imagePath)) {//imagePath banner分享部分有缩略图

            if(imagePath.startsWith("http://") || imagePath.startsWith("https://")){
                if(shareBean.getsType() == 1){
                    sp.setImagePath(imagePath);
                }else {
                    sp.setText(shareBean.getText());
                    sp.setImageUrl(imagePath);
                    // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
                    sp.setTitle(shareBean.getTitle());
                }
            }else{
                throw new ClassFormatError("传入的imagePath不是一个完整的路径");
            }
        }

        // url仅在微信（包括好友和朋友圈）中使用
        sp.setUrl(shareBean.getUrl());
        //微信分享使用
        sp.setShareType(shareBean.getShareType());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        sp.setTitleUrl(shareBean.getTitleUrl()); // 标题的超链接
        // site是分享此内容的网站名称，仅在QQ空间使用
        sp.setSite(shareBean.getSite());
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        sp.setSiteUrl(shareBean.getSiteUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        sp.setComment(shareBean.getComment());
        Platform platform = ShareSDK.getPlatform(shareBean.getPlatName());
        PlatformActionListener paListener = shareBean.getListener();
        if(paListener != null){
            platform.setPlatformActionListener(paListener); // 设置分享事件回调
        }

        platform.share(sp);
    }


}
