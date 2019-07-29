package com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderBean;
import com.primecloud.library.baselibrary.base.BaseView;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/10.
 */

public class OrderAdapter extends BaseQuickAdapter<OrderBean.DataBean.RecordsBean,BaseViewHolder> {

    public OrderAdapter(int layoutResId, @Nullable List<OrderBean.DataBean.RecordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.DataBean.RecordsBean item) {
        TextView orderNum = helper.getView(R.id.item_order_num);
        TextView orderTitle = helper.getView(R.id.item_order_title);
        TextView orderType = helper.getView(R.id.item_order_type);
        TextView orderPrice = helper.getView(R.id.item_order_price);
        TextView orderTime = helper.getView(R.id.item_order_time);

//        支付方式： 0 -> 支付宝、 1 -> 微信支付、 2 -> ios内购买、 3 -> 手动创建vip订单、
        if(item.getOrderType() == 0){
            orderType.setText("支付宝支付");
        }else if(item.getOrderType() == 1){
            orderType.setText("微信支付");
        }
        orderNum.setText("订单编号 ："+item.getOrderSn());
        orderTitle.setText(item.getOrderTitle());
        orderPrice.setText("￥"+(item.getPayPrice()/100.00));
        orderTime.setText(item.getPayTime());
    }
}
