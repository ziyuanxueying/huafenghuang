package com.primecloud.huafenghuang.ui.search.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.BuildConfig;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.search.bean.AllDataBean;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.util.List;


public class SearchAdapter extends BaseQuickAdapter<AllDataBean.DataBean.CoursesBean, BaseViewHolder> {


    public SearchAdapter(int layoutResId, @Nullable List<AllDataBean.DataBean.CoursesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllDataBean.DataBean.CoursesBean item) {
        helper.setText(R.id.course_list_title,item.getTitle());
        helper.setText(R.id.course_list_num,item.getCourseView()+"");


        RoundedImageView cover = helper.getView(R.id.course_list_cover);
        GlideImageLoader.getInstance().displayImage(mContext, item.getCoursePic(), cover);

        ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
        layoutParams.width = (int)(((Activity)mContext).getResources().getDisplayMetrics().widthPixels*0.45);
        layoutParams.height = (int)(layoutParams.width*0.56);
    }
}
