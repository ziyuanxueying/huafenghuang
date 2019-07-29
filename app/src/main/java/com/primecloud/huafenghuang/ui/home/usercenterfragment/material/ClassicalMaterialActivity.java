package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.adapter.ViewPagerAdapter;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.MaterialBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ResourceTag;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassicalMaterialActivity extends BasePresenterActivity<MaterialPresenter, MaterialModel> implements MaterialContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.act_material_pictv)
    TextView actMaterialPictv;
    @BindView(R.id.act_material_pic_indicator)
    View actMaterialPicIndicator;
    @BindView(R.id.act_material_pic_choice)
    ImageView actMaterialPicChoice;
    @BindView(R.id.act_material_videotv)
    TextView actMaterialVideotv;
    @BindView(R.id.act_material_video_indicator)
    View actMaterialVideoIndicator;
    @BindView(R.id.act_material_video_choice)
    ImageView actMaterialVideoChoice;
    @BindView(R.id.act_material_videopager)
    ViewPager actMaterialViewPager;
    @BindView(R.id.act_material_pic_rootview)
    LinearLayout actMaterialPicRootview;
    @BindView(R.id.act_material_table)
    LinearLayout actMaterialTable;
    @BindView(R.id.act_material_choiceView)
    LinearLayout actMaterialChoiceView;
    @BindView(R.id.act_material_recyclerview)
    RecyclerView actMaterialRecyclerview;
    @BindView(R.id.line)
    LinearLayout linearLayout;

    private ClassicalMaterialFragment picFragment;
    private ClassicalMaterialFragment videoFragment;
    private List<Fragment> fragments;

    private ViewPagerAdapter adapter;
    private List<ResourceTag.DataBean> resourceTags;
    private ResourceAdapter resourceAdapter;

    private int index;
    private int picResId = 0;// 图片tag的id
    private int videoResId = 0;// 视频tag的id


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_material);
    }


    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent("经典素材");
        com.primecloud.huafenghuang.utils.ViewUtils.setGone(linearLayout);
        fragments = new ArrayList<>();

        picFragment = new ClassicalMaterialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", 0);
        picFragment.setArguments(bundle);

        videoFragment = new ClassicalMaterialFragment();
        bundle = new Bundle();
        bundle.putInt("index", 1);
        videoFragment.setArguments(bundle);

        fragments.add(picFragment);

        fragments.add(videoFragment);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        actMaterialViewPager.setAdapter(adapter);
        adapter.setList(fragments);

        actMaterialRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        resourceTags = new ArrayList<>();
        resourceAdapter = new ResourceAdapter(R.layout.item_mateiral_resourcetag, resourceTags);
        actMaterialRecyclerview.setAdapter(resourceAdapter);
    }


    @Override
    protected void initEvent() {
        actMaterialViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                if (position == 0) {
                    changeState(R.id.act_material_pictv);
                } else {
                    changeState(R.id.act_material_videotv);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        resourceAdapter.setOnItemClickListener(this);
    }


    @OnClick({R.id.act_material_pictv, R.id.act_material_pic_choice, R.id.act_material_videotv, R.id.act_material_video_choice, R.id.act_material_choiceView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_material_pictv:// 图片素材
                actMaterialViewPager.setCurrentItem(0);
                actMaterialChoiceView.setVisibility(View.GONE);
                break;
            case R.id.act_material_pic_choice:// 图片素材筛选

                actMaterialViewPager.setCurrentItem(0);
                actMaterialChoiceView.setVisibility(View.VISIBLE);
                mPresenter.getResourceTags(1);

                break;
            case R.id.act_material_videotv:// 音频素材
                actMaterialViewPager.setCurrentItem(1);
                actMaterialChoiceView.setVisibility(View.GONE);
                break;
            case R.id.act_material_video_choice:// 音频素材筛选

                actMaterialViewPager.setCurrentItem(1);
                mPresenter.getResourceTags(2);
                actMaterialChoiceView.setVisibility(View.VISIBLE);

                break;
            case R.id.act_material_choiceView:
                actMaterialChoiceView.setVisibility(View.GONE);
                break;
        }
    }

    private void changeState(int viewId) {
        if (viewId == R.id.act_material_pictv) {
            actMaterialPictv.setTextColor(getResources().getColor(R.color.theme_color));
            actMaterialPicIndicator.setVisibility(View.VISIBLE);

            actMaterialVideotv.setTextColor(getResources().getColor(R.color.textcolor_one));
            actMaterialVideoIndicator.setVisibility(View.INVISIBLE);
        } else {
            actMaterialVideotv.setTextColor(getResources().getColor(R.color.theme_color));
            actMaterialVideoIndicator.setVisibility(View.VISIBLE);

            actMaterialPictv.setTextColor(getResources().getColor(R.color.textcolor_one));
            actMaterialPicIndicator.setVisibility(View.INVISIBLE);
        }


    }


    @Override
    public void showPicData(List<MaterialBean.DataBean> picDocuments) {

    }

    @Override
    public void showVidoeData(List<MaterialBean.DataBean> videoDatas) {

    }

    @Override
    public void showLikeResult(boolean isLike, int position, LikeResultBean.DataBean dataBean) {

    }

    @Override
    public void downLoadFileResult(List<File> files) {

    }

    @Override
    public void handleAmountResult(int operateType, int position) {

    }

    @Override
    public void showResourceTags(List<ResourceTag.DataBean> resourceTags) {

        if(resourceTags != null){
            if(index == 0){
                for (ResourceTag.DataBean dataBean: resourceTags){
                    if(dataBean.getId() == picResId){
                        dataBean.setSelect(true);
                    }
                }
            }else{
                for (ResourceTag.DataBean dataBean: resourceTags){
                    if(dataBean.getId() == videoResId){
                        dataBean.setSelect(true);
                    }
                }
            }

            resourceAdapter.setNewData(resourceTags);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ResourceTag.DataBean resourceData = (ResourceTag.DataBean) adapter.getItem(position);


        if(index == 0){
            picResId = resourceData.getId();
            picFragment.setResourceId(picResId);
        }else{
            videoResId = resourceData.getId();
            videoFragment.setResourceId(videoResId);
        }


        actMaterialChoiceView.setVisibility(View.GONE);

    }
}
