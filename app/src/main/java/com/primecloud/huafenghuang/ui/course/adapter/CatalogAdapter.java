package com.primecloud.huafenghuang.ui.course.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.utils.StringUtils;

import java.util.List;

public class CatalogAdapter  extends BaseQuickAdapter<CourseDetailBean.DataBean.CatalogBean.CatalogRecordsBean,BaseViewHolder> {

    private String chapterId;
    private boolean islock;


    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    public CatalogAdapter(int layoutResId, @Nullable List<CourseDetailBean.DataBean.CatalogBean.CatalogRecordsBean> data, String chapterId) {
        super(layoutResId, data);
        this.chapterId = chapterId;
    }


    @Override
    protected void convert(BaseViewHolder helper, CourseDetailBean.DataBean.CatalogBean.CatalogRecordsBean item) {
        helper.setText(R.id.catalog_recycler_item_title,item.getTitle());
        if(StringUtils.notBlank(item.getVideo_timeLen())){//音视频的时长以视频的长度为主
            helper.setText(R.id.catalog_recycler_item_time,item.getVideo_timeLen());
        }else{
            helper.setText(R.id.catalog_recycler_item_time,item.getAudio_timeLen());
        }

        helper.setText(R.id.catalog_recycler_item_study,item.getCourseView()+mContext.getResources().getString(R.string.play_num));

        TextView free = helper.getView(R.id.catalog_recycler_item_free);
        ImageView lock = helper.getView(R.id.catalog_recycler_item_lock);


        if (chapterId.equals(item.getId()))
        {
            helper.setTextColor(R.id.catalog_recycler_item_title,mContext.getResources().getColor(R.color.indicator_color_red));
        }
        else
        {
            helper.setTextColor(R.id.catalog_recycler_item_title,mContext.getResources().getColor(R.color.textcolor_one));
        }


        if (StringUtils.notBlank(item.getFree())&&item.getFree().equals("1")||!islock)
        {

            lock.setVisibility(View.GONE);
            free.setVisibility(View.VISIBLE);
        }
        else
        {
            lock.setVisibility(View.VISIBLE);
            free.setVisibility(View.GONE);
        }

    }
}
