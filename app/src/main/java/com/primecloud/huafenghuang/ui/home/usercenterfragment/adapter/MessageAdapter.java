package com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message.MessageBean;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.library.baselibrary.log.XLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${qc} on 2019/5/9.
 */

public class MessageAdapter extends BaseQuickAdapter<MessageBean.DataBean,BaseViewHolder> {

    private List<MessageBean.DataBean> selects;// 用于保存选中的数据
    private List<MessageBean.DataBean> data;
    private MessageDeleteListener deleteListener;
    public MessageAdapter(int layoutResId, @Nullable List<MessageBean.DataBean> data ,MessageDeleteListener listener) {
        super(layoutResId, data);
        this.data = data;
        this.deleteListener = listener;
        selects = new ArrayList<>();
    }

    public interface MessageDeleteListener{
        void deleteMessage(MessageBean.DataBean uploadedBean);
    }

    private boolean showDelete;
    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }
    @Override
    protected void convert(final BaseViewHolder helper, final MessageBean.DataBean item) {
        TextView time = helper.getView(R.id.tv_time);
        TextView des = helper.getView(R.id.tv_desc);
        ImageView mess_icon = helper.getView(R.id.message_icon);
        final ImageView iv_delete = helper.getView(R.id.item_uploading_select);
        LinearLayout linear_select = helper.getView(R.id.item_uploading_select_linear);


        time.setText(item.getCreatedAt()+"");
        des.setText(item.getContent());

        if(item.isIsRead()==true){
            ViewUtils.setGone(mess_icon);
        }else {
            ViewUtils.setVisible(mess_icon);
            mess_icon.setImageResource(R.mipmap.icon_remind);
        }

        if(showDelete == true){
            linear_select.setVisibility(View.VISIBLE);
            iv_delete.setSelected(item.isSelect());
        }else{
            linear_select.setVisibility(View.GONE);
            item.setSelect(false);
            iv_delete.setSelected(false);
        }

        helper.addOnClickListener(R.id.content);

        helper.getView(R.id.item_uploading_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);
                easySwipeMenuLayout.resetStatus();
                deleteListener.deleteMessage(item);
            }
        });


        linear_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = item.isSelect();
                iv_delete.setSelected(!select);
                item.setSelect(!select);


                if(select == false){//当前为未选中，去做选中的状态
                    if(!selects.contains(item)){
                        selects.add(item);
                    }
                }else{//当前为选中，去做未选中的状态
                    if(selects.contains(item)){
                        selects.remove(item);
                    }
                }

                EventBus.getDefault().post(selects);

            }
        });

    }

    private boolean isAllSelect;
    //全部不选
    public void setAllSelect(boolean isAllSelect){
        this.isAllSelect = isAllSelect;

        for(MessageBean.DataBean up : data){
            up.setSelect(isAllSelect);
            if(isAllSelect == true){// 全选
                if(!selects.contains(up)){
                    selects.add(up);
                }
            }else{// 全部都不选
                selects.clear();
            }
        }

        if(showDelete){

            EventBus.getDefault().post(selects);
        }

    }


}
