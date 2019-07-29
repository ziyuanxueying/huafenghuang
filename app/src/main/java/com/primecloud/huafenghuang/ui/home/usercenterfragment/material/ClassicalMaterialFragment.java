package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.MaterialBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ResourceTag;
import com.primecloud.huafenghuang.ui.share.ShareUtils;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.PictureDown;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.WXHelper;
import com.primecloud.huafenghuang.widget.DownTipPopup;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;
import com.primecloud.library.baselibrary.log.XLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import razerdp.basepopup.BasePopupWindow;

public class ClassicalMaterialFragment extends BasePresenterFragment<MaterialPresenter, MaterialModel> implements MaterialContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.home_refresh)
    SwipeRefreshLayout refreshLayout;

    private List<MaterialBean.DataBean> list;
    private ClassicalMaterialAdapter adapter;
    private int index = 0;// 0 表示图片素材  1 表示音频素材
    private int pageNumber = 1;
    private int pageSize = 10;
    private boolean isMoreEnd = false;
    private int resourceId;

    private DownTipPopup downTipPopup;
    private boolean isVisible;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, null, false);
    }

    @Override
    public void initData() {

        downTipPopup = new DownTipPopup(getContext());

        index = getArguments().getInt("index", 0);

        Utils.setSwipeRefreshLayout(refreshLayout);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        setMaterialAdapter();

        refreshLayout.setRefreshing(true);
        requestData();
    }

    public void setMaterialAdapter() {
        if (adapter == null) {
            adapter = new ClassicalMaterialAdapter(R.layout.item_material, list, index);
            recyclerView.setAdapter(adapter);
            adapter.setOnLoadMoreListener(this, recyclerView);
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
            adapter.setOnItemChildClickListener(this);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(this);
        downTipPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(isVisible){
                    WXHelper.openScanner(getContext());
                }
            }
        });
    }

    public void setResourceId(int resourceId){
        this.resourceId = resourceId;

        refreshLayout.setRefreshing(true);
        pageNumber = 1;
        isMoreEnd = false;
        requestData();
    }

    public void requestData() {
        if (index == 0) {
            mPresenter.getResourceImageDocument(MyApplication.getInstance().getUserInfo().getId(), pageNumber, pageSize, resourceId);
        } else if (index == 1) {
            mPresenter.getResourceVideo(MyApplication.getInstance().getUserInfo().getId(), pageNumber, pageSize, resourceId);
        }
    }

    @Override
    public void showPicData(List<MaterialBean.DataBean> picDocuments) {
        if (picDocuments != null) {
            if (refreshLayout.isRefreshing()) {// 正在刷新
                adapter.setNewData(picDocuments);
            } else {
                adapter.addData(picDocuments);
            }

        }
        stopRefreshOrLoadMore(picDocuments);
    }

    private void stopRefreshOrLoadMore(List<MaterialBean.DataBean> picDocuments) {

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        if (adapter.isLoading()) {
            if (picDocuments == null || picDocuments.size() == 0) {// 说明是最后一页
                isMoreEnd = true;
                adapter.loadMoreEnd();
            } else {
                isMoreEnd = false;
                adapter.loadMoreComplete();
            }
        }
    }

    @Override
    public void showVidoeData(List<MaterialBean.DataBean> videoDatas) {
        if (videoDatas != null) {
            if (refreshLayout.isRefreshing()) {// 正在刷新
                adapter.setNewData(videoDatas);
            } else {
                adapter.addData(videoDatas);
            }

        }
        stopRefreshOrLoadMore(videoDatas);
    }

    @Override
    public void showLikeResult(boolean isLike, int position, LikeResultBean.DataBean dataBean) {


        MaterialBean.DataBean materialBean = adapter.getItem(position);
        if (isLike) {// 点赞成功
            materialBean.setIsLike(1);
            materialBean.setLikedId(dataBean.getId());
            materialBean.setLikeCount(materialBean.getLikeCount() + 1);
        } else {// 取消点赞成功
            materialBean.setIsLike(0);
            materialBean.setLikedId(0);
            materialBean.setLikeCount(materialBean.getLikeCount() - 1);
        }

        adapter.notifyItemChanged(position);
    }


    @Override
    public void onRefresh() {

        pageNumber = 1;
        isMoreEnd = false;
        requestData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (isMoreEnd) {// 最后一页
            stopRefreshOrLoadMore(null);
        } else {
            ++pageNumber;
            requestData();
        }
    }

    private Dialog dialog;

    private String sharePlat;
    private int flag;
    private String content;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<MaterialBean.DataBean> tempList = adapter.getData();
        MaterialBean.DataBean dataBean = tempList.get(position);

        switch (view.getId()) {
            case R.id.item_material_good:// 点赞

                int isLike = dataBean.getIsLike();

                if (isLike == 0) {// 没有点赞调用点赞接口
                    mPresenter.resorceLike(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId(), position);
                } else {
                    mPresenter.resourceDislike(MyApplication.getInstance().getUserInfo().getId(), dataBean.getLikedId(), position);
                }

                break;
            case R.id.item_material_down:// 下载
                if (index == 0) {
                    List<String> pics = dataBean.getResourcePicUrl();
                    PictureDown.pictureDown(true, getContext(), pics, this);
                } else {
                    Utils.downLoadVideo(getContext(), dataBean.getDownloadUrl());
                }
                mPresenter.handleAmount(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"", 1, 1, position);
                break;
            case R.id.item_material_copy:// 复制
                Utils.copyToClipBoard(dataBean.getContent(), getContext());
                mPresenter.handleAmount(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"", 1, 2, position);
                break;
            case R.id.item_material_forward:// 转发
                mPresenter.handleAmount(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"", 1, 3, position);
                //分享
                ShareUtils shareUtils = new ShareUtils(getActivity());
                if (index == 0) {
                    List<String> pics = dataBean.getResourcePicUrl();
                    if (pics.size() == 1) {// 一張圖分享
                        shareUtils.shareWX("", null, "", pics.get(0), "", Platform.SHARE_IMAGE, null);
                    } else {// 多图分享

                        dialog = DialogUtils.shareDialogWX(getContext(), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                switch (v.getId()) {
                                    case R.id.weichat:
                                    {



                                        content = dataBean.getContent();
                                        Utils.copyToClipBoard(dataBean.getContent(), getContext());
                                        PictureDown.pictureDown(false, getContext(), pics, ClassicalMaterialFragment.this);// 下载图片

                                        sharePlat = "wchat";
                                        flag = 0;

                                        downTipPopup.showPopupWindow();
                                    }

                                        break;
                                    case R.id.pengyouquan:
                                    {



                                        content = dataBean.getContent();
                                        Utils.copyToClipBoard(dataBean.getContent(), getContext());
                                        PictureDown.pictureDown(false, getContext(), pics, ClassicalMaterialFragment.this);// 下载图片

                                        sharePlat = "wchat";
                                        flag = 0;

                                        downTipPopup.showPopupWindow();

                                    }

                                        break;
                                    case R.id.qq:
                                        sharePlat = "qq";
                                        flag = 0;

                                        break;
                                    case R.id.share_cancel:

                                        break;
                                }
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                } else {
                    shareUtils.shareWX("视频分享", dataBean.getContent(), dataBean.getContent(), dataBean.getCoverUrl(), dataBean.getShareUrl(), Platform.SHARE_WEBPAGE, null);
                }
                break;

            case R.id.item_material_video_rootview:
                Intent intent = new Intent(getContext(), PlayVideoActivity.class);
                intent.putExtra("videoPath", dataBean.getResourceVideoUrl());
                getContext().startActivity(intent);
                break;
        }
    }

    // 分享回調

    private  class ShareCallBack implements PlatformActionListener{

        private MaterialBean.DataBean dataBean;
        private int position;
        public ShareCallBack(MaterialBean.DataBean dataBean, int position){
            this.dataBean = dataBean;
            this.position = position;
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            mPresenter.handleAmount(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"", 1, 3, position);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            XLog.i("onError");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            XLog.i("onCancel");
        }
    }



    @Override
    public void downLoadFileResult(List<File> files) {

    }

    @Override
    public void handleAmountResult(int operateType, int position) {
        MaterialBean.DataBean dataBean = adapter.getItem(position);
        if(operateType == 1){// 下载量+1
            dataBean.setDownloads(dataBean.getDownloads() + 1);
        }else if (operateType == 2){// dataBean.setDownloads(dataBean.getDownloads() + 1);
            dataBean.setCopys(dataBean.getCopys() + 1);
        }else if(operateType == 3){// 转发
            dataBean.setForwards(dataBean.getForwards() + 1);
        }

        adapter.notifyItemChanged(position);
    }

    @Override
    public void showResourceTags(List<ResourceTag.DataBean> resourceTags) {

    }

    @Override
    public void onDestroy() {
        if(dialog !=null && dialog.isShowing()){
            dialog.dismiss();
        }
        if(downTipPopup != null && downTipPopup.isShowing()){
            downTipPopup.dismiss();
        }

        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }
}
