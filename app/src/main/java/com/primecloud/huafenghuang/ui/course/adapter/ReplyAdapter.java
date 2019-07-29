package com.primecloud.huafenghuang.ui.course.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.utils.DatesUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.util.List;

public class ReplyAdapter  extends BaseQuickAdapter<CommentBean.DataBean.RecordsBean,BaseViewHolder> {


    public ReplyAdapter(int layoutResId, @Nullable List<CommentBean.DataBean.RecordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean.DataBean.RecordsBean item) {
        helper.setText(R.id.item_reply_name,item.getUserName());
        helper.setText(R.id.item_reply_content,item.getContent());
        helper.setText(R.id.item_reply_time,DatesUtils.friendly_time(item.getMsgTime()+":00"));

        RoundedImageView roundedImageView = helper.getView(R.id.item_reply_image);
        if (StringUtils.notBlank(item.getUserPic()))
        {
            GlideImageLoader.getInstance().displayImage(mContext,item.getUserPic(),roundedImageView);

        }

    }



}
