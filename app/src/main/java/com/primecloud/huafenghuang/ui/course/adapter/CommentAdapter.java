package com.primecloud.huafenghuang.ui.course.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.course.ReplyActivity;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.utils.DatesUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<CommentBean.DataBean.RecordsBean,BaseViewHolder> {

    public CommentAdapter(int layoutResId, @Nullable List<CommentBean.DataBean.RecordsBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper,CommentBean.DataBean.RecordsBean item) {
        helper.setText(R.id.comment_item_name,item.getUserName());
        helper.setText(R.id.coment_item_conent,item.getContent());
        helper.setText(R.id.comment_item_time,DatesUtils.friendly_time(item.getMsgTime()+":00"));
        helper.setText(R.id.comment_item_reply,mContext.getResources().getString(R.string.reply)+"("+item.getReplyCount()+")");

        RoundedImageView roundedImageView = helper.getView(R.id.comment_item_head_image);
        GlideImageLoader.getInstance().displayImage(mContext,item.getUserPic(),roundedImageView);





    }



  
}
