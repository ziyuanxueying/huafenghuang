package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.MessageAdapter;
import com.primecloud.huafenghuang.utils.DeleteDialog;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.widget.LoadingLayout;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;
import com.primecloud.library.baselibrary.log.XLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息
 */
public class MessageActivity extends BasePresenterActivity<MessagePresenter, MessageModel> implements MessageContract.View, MessageAdapter.MessageDeleteListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.message_recycle)
    RecyclerView messageRecycle;
    @BindView(R.id.act_uservideo_select)
    ImageView actUservideoSelect;
    @BindView(R.id.act_uservideo_select_linear)
    LinearLayout actUservideoSelectLinear;
    @BindView(R.id.act_uservideo_delete)
    TextView actUservideoDelete;
    @BindView(R.id.act_uservideo_linear)
    LinearLayout actUservideoLinear;
    @BindView(R.id.error_layout)
    LoadingLayout errorLayout;
    @BindView(R.id.fragment_refresh)
    SwipeRefreshLayout refreshLayout;


    private boolean isAllSelect = false;//是否是全选状态
    private DeleteDialog deleteDialog;//删除弹窗
    MessageAdapter adapter;
    List<MessageBean.DataBean> deleteMessageBeans;//要删除的数据
    private List<Integer> mesId;//要删除的数据id
    public boolean showDelete;//记录是否显示删除
    List<MessageBean.DataBean> messageList;
    private int currentPage = 1;//当前页数
    private int total;//总页数



    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_message);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.text_message_title));
        mToolbar.setToolbarConfirmText(getResources().getString(R.string.text_message_edit));
        messageRecycle.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        EventBus.getDefault().register(this);
        errorLayout.setErrorType(LoadingLayout.NETWORK_LOADING);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);


        mToolbar.setToolbarConfirmOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mToolbar.getToolbarConfirmText();
                if (getResources().getString(R.string.text_message_cancle).equals(text)) {
                    if (isAllSelect) {
                        goSelectAll();
                    }
                    setToolbarState(false);
                } else {
                    if (isAllSelect) {
                        goSelectAll();
                    }
                    setToolbarState(true);
                }

            }
        });
    }

    private void initAdapterOnItemClick() {
        if(adapter != null){
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()){
                        case R.id.content:
                            //已读消息
                            MessageBean.DataBean detail1=messageList.get(position);
//                            消息类型（15 审核身份信息成功 16 审核身份信息失败17,视频审核通过18，视频审核未通过）
                            if(detail1.isIsRead()==false){
                                readMessage(String.valueOf(detail1.getId()),position);
                            }

//                            if(detail1.getType() == 18 || detail1.getType() == 17){
//                                Intent intent = new Intent(UserMessageActivity.this, CompetitionDetailActivity.class);
//                                intent.putExtra("eventId", detail1.getEventId());
////                                intent.putExtra("title", contest.getTitle());
//                                startActivity(intent);
//                            }else if(detail1.getType()==16){
//                                Intent intent=new Intent(UserMessageActivity.this, FillEnrollInforActivity.class);
//                                intent.putExtra("info",1);
//                                intent.putExtra("eventId",Integer.parseInt(detail1.getEventId()));
//                                intent.putExtra("groupId",Integer.parseInt(detail1.getGroupId()));
//                                startActivity(intent);
//                            }
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void initEvent() {
        messageList = new ArrayList<>();
        mesId = new ArrayList<>();
        deleteMessageBeans = new ArrayList<>();
        setMessagelAdapter();
        getMessage();
        initAdapterOnItemClick();
    }

    public void setMessagelAdapter() {
        if (adapter == null) {
            adapter = new MessageAdapter(R.layout.item_message, messageList, this);
            messageRecycle.setAdapter(adapter);
            adapter.setOnLoadMoreListener(this);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取删除的数据
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upLoadEvent(List<MessageBean.DataBean> deleteMessageBeans) {
        this.deleteMessageBeans = deleteMessageBeans;

        if (deleteMessageBeans != null) {
            actUservideoDelete.setText("删除（" + deleteMessageBeans.size() + "）");
        } else {
            actUservideoDelete.setText("删除（0）");
        }

        if(mesId!=null){
            mesId.clear();
        }
        for (int i = 0; i < deleteMessageBeans.size(); i++) {
            mesId.add(deleteMessageBeans.get(i).getId());
        }


    }

    @OnClick({R.id.act_uservideo_select_linear, R.id.act_uservideo_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_uservideo_select_linear:// 全选按钮
                goSelectAll();
                break;
            case R.id.act_uservideo_delete:// 删除按钮
                deleteDialog = DialogUtils.showDeleteDialog(MessageActivity.this, getResources().getString(R.string.text_message_message), getResources().getString(R.string.string_clear_cancle), getResources().getString(R.string.string_clear_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delMessage(mesId);
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
                break;
        }
    }

    /**
     * 编辑--取消
     *
     * @param flag true 显示删除， false 去掉删除
     */
    private void setToolbarState(boolean flag) {
        if (flag) {
            mToolbar.setToolbarConfirmText(getResources().getString(R.string.text_message_cancle));
            actUservideoLinear.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setToolbarConfirmText(getResources().getString(R.string.text_message_edit));
            actUservideoLinear.setVisibility(View.GONE);
            actUservideoDelete.setText("删除（" + 0 + "）");
            actUservideoSelect.setSelected(false);
            isAllSelect = false;
            if (deleteMessageBeans != null) {
                deleteMessageBeans.clear();
            }
            if(mesId!=null){
                mesId.clear();
            }
        }
        setShowDelete(flag);
    }


    /**
     * 是否显示左侧的删除按钮
     *
     * @param delete
     */
    public void setShowDelete(boolean delete) {
        this.showDelete = delete;
        adapter.setShowDelete(showDelete);
        setMessagelAdapter();
    }

    //设置全选
    public void goSelectAll() {
        actUservideoSelect.setSelected(!isAllSelect);
        if (adapter != null) {
            adapter.setAllSelect(!isAllSelect);
            if (showDelete) {
                setMessagelAdapter();
            }
        }
        isAllSelect = !isAllSelect;
    }

    //左滑删除
    @Override
    public void deleteMessage(MessageBean.DataBean uploadedBean) {
        this.deleteMessageBeans.add(uploadedBean);
        mesId.add(uploadedBean.getId());
        delMessage(mesId);
    }


    @Override
    protected void onDestroy() {
        if (deleteDialog != null && deleteDialog.isShowing()) {
            deleteDialog.dismiss();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    public void showMessageList(MessageBean messageBean) {
        List<MessageBean.DataBean> messageBeanData = messageBean.getData();
        if (messageBeanData != null) {
            if (refreshLayout.isRefreshing()) {
                messageList.clear();
            }
            if (messageBeanData != null &&messageBeanData.size()>0) {
                errorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);//隐藏暂无内容
                messageList.addAll(messageBeanData);
            } else {
                if(currentPage ==1 ){
                    mToolbar.setToolbarConfirmText(null);
                    errorLayout.setErrorType(LoadingLayout.NODATA);//暂无内容
                }
            }
            setMessagelAdapter();
        } else {
            if (currentPage == 1) {
                errorLayout.setErrorType(LoadingLayout.NODATA);
            }
        }


    }

    @Override
    public void stopLoad() {
        stopRefreshLoading();
    }

    @Override
    public void noDate() {
        if (currentPage == 1) {
            errorLayout.setErrorType(LoadingLayout.NODATA);
        }
        stopRefreshLoading();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        getMessage();
    }

    //请求数据
    public void getMessage() {
        if (NetUtils.isConnected(this)) {
            mPresenter.getMessage(MyApplication.getInstance().getUserInfo().getId(), currentPage);
        } else {
            errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
            ToastUtils.showToast(MessageActivity.this, getResources().getString(R.string.detection_network));
        }
    }

    @Override
    public void onLoadMoreRequested() {//加载更多
        if (refreshLayout.isRefreshing())
            return;
        if (currentPage + 1 <= total) {
            if (NetUtils.isConnected(MessageActivity.this)) {
                currentPage++;
                mPresenter.getMessage(MyApplication.getInstance().getUserInfo().getId(), currentPage);
            } else {
                errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
                ToastUtils.showToast(MessageActivity.this, getResources().getString(R.string.detection_network));
            }

        }
    }

    // 停止刷新或者加载
    private void stopRefreshLoading() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            adapter.setEnableLoadMore(true);
        }
        if (currentPage >= total) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }

    }

    /**
     * 删除数据
     * @param ids
     */
    public void delMessage(List<Integer> ids) {
        if(NetUtils.isConnected(MessageActivity.this)){
            FengHuangApi.deleteUserMessage(MyApplication.getInstance().getUserInfo().getId(), Utils.listToString(ids), new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    for(int i=0;i<deleteMessageBeans.size();i++){
                        messageList.remove(deleteMessageBeans.get(i));
                    }
                    setMessagelAdapter();
                    setToolbarState(false);
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }else {
            errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
            ToastUtils.showToast(MessageActivity.this, getResources().getString(R.string.detection_network));
        }
    }

    /**
     * 消息已读
     * @param messageId
     * @param position
     */
    public void readMessage(String messageId,int position){
        if (NetUtils.isConnected(MessageActivity.this)) {
            FengHuangApi.updateUserMessageAsReaded(MyApplication.getInstance().getUserInfo().getId(), messageId, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    adapter.getData().get(position).setIsRead(true);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        } else {
            errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
            ToastUtils.showToast(MessageActivity.this, getResources().getString(R.string.detection_network));
        }

    }
}
