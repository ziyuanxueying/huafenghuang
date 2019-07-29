package com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.collect.CollectionBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderBean;
import com.primecloud.huafenghuang.utils.StringUtils;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/10.
 */

public class CollectionAdapter extends BaseQuickAdapter<CollectionBean.DataBean.recordsBean, BaseViewHolder> {

    public CollectionAdapter(int layoutResId, @Nullable List<CollectionBean.DataBean.recordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionBean.DataBean.recordsBean item) {
        if (null != item) {
            RoundedImageView orderNum = helper.getView(R.id.course_list_cover);
            TextView title = helper.getView(R.id.course_list_title);
            TextView number = helper.getView(R.id.course_list_num);
            TextView label = helper.getView(R.id.course_list_label);

            ViewGroup.LayoutParams layoutParams = orderNum.getLayoutParams();
            layoutParams.width = (int) (((Activity) mContext).getResources().getDisplayMetrics().widthPixels * 0.45);
            layoutParams.height = (int) (layoutParams.width * 0.56);


            Glide.with(mContext)
                    .load(item.getCoursePic()) //图片地址
                    .into(orderNum);
            if (StringUtils.notBlank(item.getTitle())) {
                title.setText(item.getTitle());
                number.setText(item.getCourseView() + "人学习");
                //vip_free	vip免费标志 1.免费
                if (item.getVip_free() == 1) {
                    label.setText("VIP免费");
                }
            }
        }
    }
}
