package com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter;


import android.app.Activity;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.timetable.TimeTableBean;
import com.primecloud.huafenghuang.utils.DateUtils;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.util.Date;
import java.util.List;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class TimeTableAdapter extends BaseQuickAdapter<TimeTableBean.DataBean, BaseViewHolder> {

    private final int MAX_LINE_COUNT = 4;// 最大的行数

    private final int STATE_UNKNOW = -1;

    // 记录当前TextView的状态
    private final int STATE_NOT_OVERFLOW = 1;//文本行数不能超过限定行数
    private final int STATE_COLLAPSED = 2;//文本行数超过限定行数，进行折叠
    private final int STATE_EXPANDED = 3;//文本超过限定行数，被点击全文展开
    private SparseArray<Integer> mTextStateList;

    public TimeTableAdapter(int layoutResId, @Nullable List<TimeTableBean.DataBean> data) {
        super(layoutResId, data);
        mTextStateList = new SparseArray<Integer>();
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeTableBean.DataBean item) {
        final int position = helper.getLayoutPosition();

        RoundedImageView head = helper.getView(R.id.time_headrv);
        GlideImageLoader.getInstance().displayImage(mContext, item.getAvatar(), head);



        TextView name = helper.getView(R.id.time_name);
        name.setText(item.getNickname());

        TextView time = helper.getView(R.id.time_time);
        TextView tv_content = helper.getView(R.id.time_content);
        TextView tv_expand = helper.getView(R.id.time_expand);


        RoundedImageView cover = helper.getView(R.id.time_cover);
        GlideImageLoader.getInstance().displayImage(mContext, item.getCoverUrl(), head);

        TextView course_title = helper.getView(R.id.time_title);
        TextView course_num = helper.getView(R.id.time_num);
        TextView course_lable = helper.getView(R.id.time_label);


        TextView tv_good = helper.getView(R.id.time_good);
        TextView copy = helper.getView(R.id.time_copy);
        TextView forward = helper.getView(R.id.time_forward);


        tv_good.setText(item.getLikeCount()+"");
        copy.setText(item.getCopys()+"");
        forward.setText(item.getForwards()+"");

        course_title.setText(item.getCourseName());
        tv_content.setText(item.getContent());
        time.setText(DateUtils.dateToStr(new Date(item.getCreated_at())) + "");


        helper.addOnClickListener(R.id.time_good);
        helper.addOnClickListener(R.id.time_copy);
        helper.addOnClickListener(R.id.time_forward);
        helper.addOnClickListener(R.id.line_course);
        ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
        layoutParams.width = (int) (((Activity) mContext).getResources().getDisplayMetrics().widthPixels * 0.45);
        layoutParams.height = (int) (layoutParams.width * 0.56);

//      用户头像
        GlideImageLoader.getInstance().displayImage(mContext, item.getAvatar(), head);

        //课程封面
        GlideImageLoader.getInstance().displayImage(mContext, item.getCoursePic(), cover);


        if (item.getLikedId() == 0) {// 没有点赞
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tv_good.setCompoundDrawablesWithIntrinsicBounds(null,
                        mContext.getResources().getDrawable(R.mipmap.dianzan_normali, null), null, null);
            } else {
                tv_good.setCompoundDrawablesWithIntrinsicBounds(null,
                        mContext.getResources().getDrawable(R.mipmap.dianzan_normali), null, null);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tv_good.setCompoundDrawablesWithIntrinsicBounds(null,
                        mContext.getResources().getDrawable(R.mipmap.dianzan_select, null), null, null);
            } else {
                tv_good.setCompoundDrawablesWithIntrinsicBounds(null,
                        mContext.getResources().getDrawable(R.mipmap.dianzan_select), null, null);
            }

        }
        int state = mTextStateList.get(position, STATE_UNKNOW);

        if (state == STATE_UNKNOW) {
            tv_content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //这个回掉会调用多次，获取玩行数后记得注销监听
                    tv_content.getViewTreeObserver().removeOnPreDrawListener(this);
//                    如果内容显示的行数大于限定显示行数
                    if (tv_content.getLineCount() > MAX_LINE_COUNT) {
                        tv_content.setMaxLines(MAX_LINE_COUNT);//设置最大显示行数
                        tv_expand.setVisibility(View.VISIBLE);//让其显示全文的文本框状态为显示
                        tv_expand.setText("全部");//设置其文字为全文
                        mTextStateList.put(position, STATE_COLLAPSED);
                    } else {
                        tv_expand.setVisibility(View.GONE);//显示全文隐藏
                        mTextStateList.put(position, STATE_NOT_OVERFLOW);//让其不能超过限定的行数
                    }
                    return true;
                }
            });
            tv_content.setMaxLines(Integer.MAX_VALUE);//设置文本的最大行数，为整数的最大数值
        } else {
//            如果之前已经初始化过了，则使用保存的状态，无需在获取一次
            switch (state) {
                case STATE_NOT_OVERFLOW:// 没超过最大行
                    tv_expand.setVisibility(View.GONE);
                    break;
                case STATE_COLLAPSED:// 收起状态
                    tv_content.setMaxLines(MAX_LINE_COUNT);
                    tv_expand.setVisibility(View.VISIBLE);
                    tv_expand.setText("全部");
                    break;
                case STATE_EXPANDED:// 展开状态
                    tv_content.setMaxLines(Integer.MAX_VALUE);
                    tv_expand.setVisibility(View.VISIBLE);
                    tv_expand.setText("收起");
                    break;
            }

        }

        //        设置显示和收起的点击事件
        tv_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = mTextStateList.get(position, STATE_UNKNOW);
                if (state == STATE_COLLAPSED) {
                    tv_content.setMaxLines(Integer.MAX_VALUE);
                    tv_expand.setText("收起");
                    mTextStateList.put(position, STATE_EXPANDED);
                } else if (state == STATE_EXPANDED) {
                    tv_content.setMaxLines(MAX_LINE_COUNT);
                    tv_expand.setText("全部");
                    mTextStateList.put(position, STATE_COLLAPSED);
                }
            }
        });



    }
}
