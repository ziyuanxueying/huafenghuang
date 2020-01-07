package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.MaterialBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ResourceTag;
import com.primecloud.huafenghuang.ui.share.ShareUtils;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.GlideImageUtil;
import com.primecloud.huafenghuang.utils.PictureDown;
import com.primecloud.huafenghuang.utils.QRCodeUtil;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.WXHelper;
import com.primecloud.huafenghuang.widget.DownTipPopup;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;
import com.primecloud.library.baselibrary.log.XLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Pattern;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import razerdp.basepopup.BasePopupWindow;

public class ClassicalMaterialFragment extends BasePresenterFragment<MaterialPresenter, MaterialModel> implements MaterialContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.home_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.image_share)
    LinearLayout imageShare;
    @BindView(R.id.imagePoster)
    ImageView imagePoster;
    @BindView(R.id.imageCode)
    ImageView imageCode;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.checkCode)
    TextView checkCode;

    private List<MaterialBean.DataBean> list;
    private ClassicalMaterialAdapter adapter;
    private int index = 0;// 0 表示图片素材  1 表示音频素材
    private int pageNumber = 1;
    private int pageSize = 10;
    private boolean isMoreEnd = false;
    private int resourceId;

    private DownTipPopup downTipPopup;
    private boolean isVisible;
    private String shareName;
    private String strPath;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, null, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        strPath = "/HuaFengHuang/" + UUID.randomUUID().toString() + ".png";
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
        shareName = MyApplication.getInstance().getUserInfo().getUsername();
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (shareName.matches(telRegex)) {
            String share = MyApplication.getInstance().getUserInfo().getUsername();
            shareName = share.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } else {
            shareName = MyApplication.getInstance().getUserInfo().getUsername();
        }
        mPresenter.getMyAccountFirstPage(MyApplication.getInstance().getUserInfo().getId());
//        strPath = Environment.getExternalStorageDirectory().getPath() +  "/HuaFengHuang/" + UUID.randomUUID().toString() + ".png";
        strPath = "/HuaFengHuang/" + UUID.randomUUID().toString() + ".png";
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
                if (isVisible) {
                    WXHelper.openScanner(getContext());
                }
            }
        });
    }

    public void setResourceId(int resourceId) {
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
    public void showMyAccountData(AccountBean.DataBean accountBean) {
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (!TextUtils.isEmpty(accountBean.getRealName())) {
            if (MyApplication.getInstance().getUserInfo().getUsername().matches(telRegex)) {
                shareName = accountBean.getRealName();
            } else {
                shareName = MyApplication.getInstance().getUserInfo().getUsername();
            }
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
                mPresenter.handleAmount(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId() + "", 1, 1, position);
                break;
            case R.id.item_material_copy:// 复制
                Utils.copyToClipBoard(dataBean.getContent(), getContext());
                mPresenter.handleAmount(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId() + "", 1, 2, position);
                break;
            case R.id.item_material_forward:// 转发
                mPresenter.handleAmount(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId() + "", 1, 3, position);
                //分享
                ShareUtils shareUtils = new ShareUtils(getActivity());
                if (index == 0) {
                    List<String> pics = dataBean.getResourcePicUrl();
                    if (pics.size() == 1) {// 一張圖分享
                        catchScreen(pics.get(0));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);//延时1s
                                    shareUtils.shareWX("", null, "", strPath, "", Platform.SHARE_IMAGE, null);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    } else {// 多图分享

                        dialog = DialogUtils.shareDialogWX(getContext(), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                switch (v.getId()) {
                                    case R.id.weichat: {


                                        content = dataBean.getContent();
                                        Utils.copyToClipBoard(dataBean.getContent(), getContext());
                                        PictureDown.pictureDown(false, getContext(), pics, ClassicalMaterialFragment.this);// 下载图片

                                        sharePlat = "wchat";
                                        flag = 0;

                                        downTipPopup.showPopupWindow();
                                    }

                                    break;
                                    case R.id.pengyouquan: {


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

    private class ShareCallBack implements PlatformActionListener {

        private MaterialBean.DataBean dataBean;
        private int position;

        public ShareCallBack(MaterialBean.DataBean dataBean, int position) {
            this.dataBean = dataBean;
            this.position = position;
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            mPresenter.handleAmount(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId() + "", 1, 3, position);
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
        if (operateType == 1) {// 下载量+1
            dataBean.setDownloads(dataBean.getDownloads() + 1);
        } else if (operateType == 2) {// dataBean.setDownloads(dataBean.getDownloads() + 1);
            dataBean.setCopys(dataBean.getCopys() + 1);
        } else if (operateType == 3) {// 转发
            dataBean.setForwards(dataBean.getForwards() + 1);
        }

        adapter.notifyItemChanged(position);
    }

    @Override
    public void showResourceTags(List<ResourceTag.DataBean> resourceTags) {

    }

    @Override
    public void onDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (downTipPopup != null && downTipPopup.isShowing()) {
            downTipPopup.dismiss();
        }

        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }


    private void catchScreen(String url) {
//        imageShare.setVisibility(View.VISIBLE);
        name.setText("我是" + shareName);
//        GlideImageUtil.glideImgLoder(this, "http://img1.doubanio.com/pview/event_poster/raw/public/b06690521b14648.jpg", imagePoster);
        GlideImageUtil.glideImgLoder(getActivity(), url, imagePoster);
        Bitmap qrcode_bitmap = QRCodeUtil.createQRCode("http://admin.huaxiachangxiang.com/api/dis/propaganda/" + MyApplication.getInstance().getUserInfo().getCode(), 100);
        imageCode.setImageBitmap(qrcode_bitmap);
        SpannableString spannableString = new SpannableString(getString(R.string.poster_check));
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#555555"));
        spannableString.setSpan(span, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkCode.setText(spannableString);

        // 1. 初始化布局：
        View decorView = imageShare;
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        new Thread(() -> {
            try {
                Thread.sleep(1000);//延时1s
                //do something
                createPicture(decorView);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);//延时1s
//                    createPicture(decorView);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    // 2. 将布局转成bitmap
    private void createPicture(View view) {
        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        //3. 将bitmap存入本地
//        String strPath = "/huafenghuang/" + UUID.randomUUID().toString() + ".png";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            FileOutputStream fos = null;
            try {
                File file = new File(sdCardDir, strPath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                fos = new FileOutputStream(file);

                //当指定压缩格式为PNG时保存下来的图片显示正常
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                Log.e("MainActivity", "图片生成：" + file.getAbsolutePath());
                ToastUtils.showToast(getActivity(), "图片保存成功");
                fos.flush();
//                ShareUtils shareUtils = new ShareUtils(getActivity());
//                shareUtils.shareWX("", null, "", strPath, "", Platform.SHARE_IMAGE, null);
//                imageShare.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return strPath;
    }
}
