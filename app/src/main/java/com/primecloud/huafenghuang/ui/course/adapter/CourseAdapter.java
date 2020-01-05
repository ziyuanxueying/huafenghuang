package com.primecloud.huafenghuang.ui.course.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class CourseAdapter extends BaseQuickAdapter<CourseBean.DataBean.CourseListBean, BaseViewHolder> {

    private int queryType;//课程类别：1免费，2热门

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public CourseAdapter(int layoutResId, @Nullable List<CourseBean.DataBean.CourseListBean> data) {
        super(layoutResId, data);
        queryType = -1;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean.DataBean.CourseListBean item) {

        if (StringUtils.notBlank(item.getTitle())) {
            helper.setText(R.id.course_list_title, item.getTitle());

        } else {
            helper.setText(R.id.course_list_title, item.getChapter_title());
        }
        helper.setText(R.id.course_list_summary, item.getSummary());
        helper.setText(R.id.course_list_num, item.getCourseView() + mContext.getResources().getString(R.string.play_num));

        TextView label = helper.getView(R.id.course_list_label);
        if (StringUtils.notBlank(item.getFree())&&item.getFree().equals("1")||queryType == 1) {
            label.setTextSize(12);
            label.setText(mContext.getResources().getString(R.string.course_free));
            label.setTextColor(mContext.getResources().getColor(R.color.tab_checked));
            label.setBackground(null);
        } else {
            label.setTextSize(10);
            label.setText(mContext.getResources().getString(R.string.course_vip));
            label.setTextColor(mContext.getResources().getColor(R.color.white));
            label.setBackground(mContext.getResources().getDrawable(R.drawable.shape_pink));
        }

        RoundedImageView cover = helper.getView(R.id.course_list_cover);
        ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
        layoutParams.width = (int) (((Activity) mContext).getResources().getDisplayMetrics().widthPixels * 0.45);
        layoutParams.height = (int) (layoutParams.width * 0.56);
        GlideImageLoader.getInstance().displayImage(mContext, item.getCoursePic(), cover);
    }
}
