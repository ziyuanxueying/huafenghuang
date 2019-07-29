package com.primecloud.huafenghuang.ui.home.findfragment.adapter;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.CourseDetailsActivity;
import com.primecloud.huafenghuang.ui.course.CourseListActivity;
import com.primecloud.huafenghuang.ui.home.findfragment.WebViewActivity;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.BannerBean;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.ClassifyBean;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.FindBean;
import com.primecloud.huafenghuang.utils.bannerImageLoader;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class FindAdapter extends BaseMultiItemQuickAdapter<FindBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    private List<ClassifyBean> classifyBeans = ClassifyBean.classifyList;
    private List<BannerBean> banners;
    private List<String> bannerPics;
    private ClassifyAdapter classifyAdapter;
    private int screenW;
    private boolean isMessage = false;// 是否有未读消息


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public FindAdapter(List<FindBean> data) {
        super(data);
        addItemType(FindBean.ITEM_HEAD, R.layout.item_find_head);
        addItemType(FindBean.ITEM_TYPE, R.layout.item_find_type_head);
        addItemType(FindBean.ITEM_COURSE, R.layout.item_find_course);

        banners = new ArrayList<>();
        bannerPics = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, FindBean item) {

        switch (helper.getItemViewType()) {

            case FindBean.ITEM_HEAD:// 头布局

                Banner banner = helper.getView(R.id.item_head_banner);
                banner.setImageLoader(new bannerImageLoader());
                banner.setImages(bannerPics);
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                        BannerBean bannerBean = banners.get(position);
                        int type = bannerBean.getType();
                        Intent intent = null;
                        if(type == 1){
                            intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra("url", bannerBean.getUrl());
                            intent.putExtra("title", bannerBean.getTitle());
                        }else if(type == 2){
                            intent = new Intent(mContext, CourseDetailsActivity.class);
                            intent.putExtra("chapterId", bannerBean.getCourseChapterId());
                            intent.putExtra("courseId", bannerBean.getCourseId());
                        }
                        if(intent != null){
                            mContext.startActivity(intent);
                        }

                    }
                });
                banner.start();

                RecyclerView recyclerView = helper.getView(R.id.item_head_recyclerview);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 5, LinearLayoutManager.VERTICAL, false));

                if(isMessage){
                    helper.setImageResource(R.id.item_head_message, R.mipmap.ic_new1);
                }else{
                    helper.setImageResource(R.id.item_head_message, R.mipmap.ic_new);
                }


                helper.addOnClickListener(R.id.item_head_search);
                helper.addOnClickListener(R.id.item_head_yinpin);
                helper.addOnClickListener(R.id.item_head_message);

                classifyAdapter = new ClassifyAdapter(R.layout.item_find_head_classify, classifyBeans);
                recyclerView.setAdapter(classifyAdapter);
                classifyAdapter.setOnItemClickListener(this);


                break;
            case FindBean.ITEM_TYPE:// 类型
                helper.setText(R.id.item_type_name, item.getTypeName());
                helper.addOnClickListener(R.id.item_type_more);

                break;
            case FindBean.ITEM_COURSE:// 课程
                int free = item.getFree();
                TextView vip = helper.getView(R.id.item_course_vip);
                if(free == 1){

                    vip.setVisibility(View.GONE);
                }else{
                    vip.setVisibility(View.VISIBLE);
                    vip.setTextSize(10);
                    vip.setText(mContext.getResources().getString(R.string.course_vip));
                    vip.setBackground(mContext.getResources().getDrawable(R.drawable.shape_pink));
                }

                helper.setText(R.id.item_course_title, item.getChapter_title());
                helper.setText(R.id.item_course_course, item.getCourseView()+"人学习");
                RoundedImageView riv = (RoundedImageView)helper.getView(R.id.item_course_iv);
                GlideImageLoader.getInstance().displayImage(mContext, item.getCoursePic(), riv);
                break;

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);

            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == 0 ? 1 : 2;
                }
            });
        }

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, CourseListActivity.class);
        intent.putExtra("parentId",classifyBeans.get(position).getParentId()+"");
        intent.putExtra("name",classifyBeans.get(position).getName());
        mContext.startActivity(intent);
    }

    // 设置轮播图数据
    public void setBanners(List<BannerBean> banners){
        if(banners != null){
            this.banners.clear();
            this.banners.addAll(banners);
            bannerPics.clear();
            for (BannerBean bannerBean: banners){
                bannerPics.add(bannerBean.getPath());
            }
        }
    }


    // 是否有未读消息
    public void setMessageNotify(boolean messageNotify){

        if(messageNotify == isMessage){
            return;
        }

        this.isMessage = messageNotify;
        notifyItemChanged(0);
    }
}
