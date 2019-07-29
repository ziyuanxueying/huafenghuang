package com.primecloud.huafenghuang.ui.course.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.utils.DatesUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class OfflineCourseAdapter extends BaseQuickAdapter<CourseDetailBean.DataBean.OfflineCourseBean.OfflineRecordsBean, BaseViewHolder> {


    public OfflineCourseAdapter(int layoutResId, @Nullable List<CourseDetailBean.DataBean.OfflineCourseBean.OfflineRecordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseDetailBean.DataBean.OfflineCourseBean.OfflineRecordsBean item) {


        helper.setText(R.id.offline_recycler_item_title, item.getCourseName());

        if (item.getCourseInfo() != null && item.getCourseInfo().size() > 0) {
            if (StringUtils.notBlank(item.getCourseInfo().get(0))) {
                String date = item.getCourseInfo().get(0).replaceAll("-", "/").replaceAll(",", "、\t");


                if (isPastDue(date.trim())) {
                    helper.setText(R.id.offline_recycler_item_title, item.getCourseName() + "(" + mContext.getResources().getString(R.string.activity_end) + ")");
                }

                helper.setText(R.id.offline_recycler_item_date, date);
            }

            if (item.getCourseInfo().size() > 1 && StringUtils.notBlank(item.getCourseInfo().get(1))) {
                helper.setText(R.id.offline_recycler_item_site, item.getCourseInfo().get(1));
            }
        }

        RoundedImageView roundedImageView = helper.getView(R.id.offline_recycler_item_cover);
        ViewGroup.LayoutParams layoutParams = roundedImageView.getLayoutParams();
        layoutParams.width = (int) (((Activity) mContext).getResources().getDisplayMetrics().widthPixels * 0.93);
        layoutParams.height = (int) (layoutParams.width * 0.44);

        GlideImageLoader.getInstance().displayImage(mContext, item.getCourseCover(), roundedImageView);
    }


    /**
     * 是否过期
     */
    public boolean isPastDue(String date) {
        String mm = null;
        String dd = null;
        boolean result = false;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        String da = simpleDateFormat.format(new Date(System.currentTimeMillis())).toString().trim();
        if (StringUtils.notBlank(da)) {
            if (da.length() >= 2) {
                mm = da.substring(0, 2);
            }

            if (da.length() >= 5) {
                dd = da.substring(3, 5);
            }
        }
        if (!StringUtils.notBlank(mm) && !StringUtils.notBlank(dd)) {
            return false;
        }

        String[] text = date.split("、");
        for (int i = 0; i < text.length; i++) {
            String dateText = text[i].trim();
            if (StringUtils.notBlank(dateText)) {

                if (dateText.length() >= 2) {
                    if (Integer.parseInt(mm.trim()) > Integer.parseInt(dateText.substring(0, 2).trim())) {
                        result = true;
                    } else {
                        result = false;
                    }
                }

                if (dateText.length() >= 5) {
                    if (Integer.parseInt(mm.trim()) == Integer.parseInt(dateText.substring(0, 2).trim()) && Integer.parseInt(dd.trim()) > Integer.parseInt(dateText.substring(3, 5).trim()) || Integer.parseInt(mm.trim()) > Integer.parseInt(dateText.substring(0, 2).trim())) {
                        result = true;
                    } else {
                        result = false;
                    }
                }

            }
        }
        return result;
    }
}
