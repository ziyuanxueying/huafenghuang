package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.MaterialBean;
import com.primecloud.huafenghuang.utils.DateUtils;
import com.primecloud.huafenghuang.utils.GridSpacingItemDecoration;
import com.primecloud.huafenghuang.utils.MyGridLayoutManager;
import com.primecloud.huafenghuang.utils.PictureUtils;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassicalMaterialAdapter extends BaseQuickAdapter<MaterialBean.DataBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    private final int MAX_LINE_COUNT = 4;// 最大的行数

    private final int STATE_UNKNOW = -1;

    // 记录当前TextView的状态
    private final int STATE_NOT_OVERFLOW = 1;//文本行数不能超过限定行数
    private final int STATE_COLLAPSED = 2;//文本行数超过限定行数，进行折叠
    private final int STATE_EXPANDED = 3;//文本超过限定行数，被点击全文展开


    private SparseArray<Integer> mTextStateList;
    private int index;

    private Gson gson;

    public ClassicalMaterialAdapter(int layoutResId, @Nullable List<MaterialBean.DataBean> data, int index) {
        super(layoutResId, data);
        mTextStateList = new SparseArray<Integer>();
        this.index = index;
        gson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialBean.DataBean item) {

        final TextView tv_content = helper.getView(R.id.item_material_content);
        final TextView tv_expand = helper.getView(R.id.item_material_expand);
        final int position = helper.getLayoutPosition();

        RoundedImageView roundedImageView = helper.getView(R.id.item_material_headrv);
        GlideImageLoader.getInstance().displayImage(mContext, item.getAvatar(), roundedImageView);

        tv_content.setText(item.getContent());
        helper.setText(R.id.item_material_name, item.getNickname());
        helper.setText(R.id.item_material_time, DateUtils.dateToStr(new Date(item.getCreated_at())));

        helper.setText(R.id.item_material_good, item.getLikeCount()+"");
        helper.setText(R.id.item_material_down, item.getDownloads()+"");
        helper.setText(R.id.item_material_forward, item.getForwards()+"");
        helper.setText(R.id.item_material_copy, item.getCopys()+"");

        TextView tv_good = helper.getView(R.id.item_material_good);

        if (item.getIsLike() == 0) {// 没有点赞
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tv_good.setCompoundDrawablesWithIntrinsicBounds(null,
                        mContext.getResources().getDrawable(R.mipmap.dianzan_normali, null), null, null);
            }else{
                tv_good.setCompoundDrawablesWithIntrinsicBounds(null,
                        mContext.getResources().getDrawable(R.mipmap.dianzan_normali), null, null);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tv_good.setCompoundDrawablesWithIntrinsicBounds(null,
                        mContext.getResources().getDrawable(R.mipmap.dianzan_select, null), null, null);
            }else{
                tv_good.setCompoundDrawablesWithIntrinsicBounds(null,
                        mContext.getResources().getDrawable(R.mipmap.dianzan_select), null, null);
            }

        }

        helper.addOnClickListener(R.id.item_material_down);
        helper.addOnClickListener(R.id.item_material_good);
        helper.addOnClickListener(R.id.item_material_copy);
        helper.addOnClickListener(R.id.item_material_forward);

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
                        tv_expand.setText("全文");//设置其文字为全文
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
                    tv_expand.setText("全文");
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
                    tv_expand.setText("全文");
                    mTextStateList.put(position, STATE_COLLAPSED);
                }
            }
        });

        if (index == 0) {// 图片素材
            picItem(helper, item);
        } else if (index == 1) {// 音频素材
            audioVideoItem(helper, item);
        }
    }

    private void audioVideoItem(BaseViewHolder helper, MaterialBean.DataBean item) {
        RecyclerView recyclerView = helper.getView(R.id.item_material_recyclerview);
        recyclerView.setVisibility(View.GONE);

        helper.setGone(R.id.item_material_cover, false);

        RelativeLayout relativeLayout = helper.getView(R.id.item_material_video_rootview);
        relativeLayout.setVisibility(View.VISIBLE);

        GlideImageLoader.getInstance().displayImage(mContext, item.getCoverUrl(), (ImageView)helper.getView(R.id.item_material_video_cover));

        helper.addOnClickListener(R.id.item_material_video_rootview);
    }

    private void picItem(BaseViewHolder helper, MaterialBean.DataBean item) {

        RecyclerView recyclerView = helper.getView(R.id.item_material_recyclerview);
        ImageView cover = helper.getView(R.id.item_material_cover);

        List<String> pics = item.getResourcePicUrl();

        if (pics.size() == 1) {

            recyclerView.setVisibility(View.GONE);
            cover.setVisibility(View.VISIBLE);

            GlideImageLoader.getInstance().displayImage(mContext, pics.get(0), cover);
            cover.setTag(R.id.image_url, pics.get(0));
            cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String path = (String) cover.getTag(R.id.image_url);
                    List<LocalMedia> localMedias = new ArrayList<>();
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(path);
                    localMedias.add(localMedia);
                    PictureUtils.takePreviewPic((Activity) mContext, localMedias);
                }
            });

        } else {

            recyclerView.setVisibility(View.VISIBLE);
            cover.setVisibility(View.GONE);

            MaterialPicAdapter adapter = (MaterialPicAdapter) recyclerView.getAdapter();
            if (adapter == null) {
                MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
                gridLayoutManager.setScrollEnabled(false);
                recyclerView.setLayoutManager(gridLayoutManager);

                int spanCount = 3;//跟布局里面的spanCount属性是一致的
                int spacing = 8;//每一个矩形的间距
                boolean includeEdge = false;//如果设置成false那边缘地带就没有间距
                //设置每个item间距
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

                adapter = new MaterialPicAdapter(R.layout.item_material_pic, pics);
                adapter.setOnItemClickListener(this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setNewData(pics);
            }

        }
    }


    // 多张图片的点击事件
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        List<String> pics = adapter.getData();
        List<LocalMedia> localMedias = new ArrayList<>();
        LocalMedia localMedia = null;
        for (String path : pics) {
            localMedia = new LocalMedia();
            localMedia.setPath(path);
            localMedias.add(localMedia);
        }
        PictureUtils.takePreviewPic((Activity) mContext, localMedias);
    }
}
