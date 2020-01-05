package com.primecloud.huafenghuang.ui.course.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.CourseListActivity;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.util.List;

public class CourseTypeAdapter extends BaseQuickAdapter<CourseBean.DataBean.SecTagsBean, BaseViewHolder> {


    public CourseTypeAdapter(int layoutResId, @Nullable List<CourseBean.DataBean.SecTagsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean.DataBean.SecTagsBean item) {

        helper.setText(R.id.course_list_title, item.getName());
        helper.setText(R.id.course_list_summary, item.getSummary());
        helper.setText(R.id.course_list_num, item.getCourseView() + mContext.getResources().getString(R.string.play_num));

        TextView lable = helper.getView(R.id.course_list_label);
        if (item.getFreeFlag().equals("1")) {
            lable.setTextSize(12);
            lable.setText(mContext.getResources().getString(R.string.course_free));
            lable.setTextColor(mContext.getResources().getColor(R.color.tab_checked));
            lable.setBackground(null);
        } else {
            lable.setTextSize(10);
            lable.setText(mContext.getResources().getString(R.string.course_vip));
            lable.setTextColor(mContext.getResources().getColor(R.color.white));
            lable.setBackground(mContext.getResources().getDrawable(R.drawable.shape_pink));
        }


        RoundedImageView cover = helper.getView(R.id.course_list_cover);
        ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
        layoutParams.width = (int) (((Activity) mContext).getResources().getDisplayMetrics().widthPixels * 0.45);
        layoutParams.height = (int) (layoutParams.width * 0.56);
        GlideImageLoader.getInstance().displayImage(mContext, item.getCoursePic(), cover);
    }
}
