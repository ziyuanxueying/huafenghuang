package com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo.IncomeInfoBean;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class IncomeInfoAdapter extends BaseQuickAdapter<IncomeInfoBean.DataBean,BaseViewHolder> {

    public IncomeInfoAdapter(int layoutResId, @Nullable List<IncomeInfoBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeInfoBean.DataBean item) {
        TextView order = helper.getView(R.id.income_order);
        TextView time = helper.getView(R.id.income_time);
        TextView type = helper.getView(R.id.income_id);
        TextView info = helper.getView(R.id.income_info);

        order.setText("购买用户："+item.getUsername());
        time.setText(item.getCreatedAt());
        if(item.getType() == 1){//收益类型 1 推荐推广代言人 2 推荐合伙人3 推荐直接分公司 4 推荐间接分公司5直接分公司补货 6 间接分公司补货 7卖货收益
            type.setText("收益类型：推广代言人");
        }else if(item.getType() == 2){
            type.setText("收益类型：推荐合伙人");
        }else if(item.getType() == 3){
            type.setText("收益类型：推荐直接分公司");
        }else if(item.getType() == 4){
            type.setText("收益类型：推荐间接分公司");
        }else if(item.getType() == 5){
            type.setText("收益类型：直接分公司补货");
        }else if(item.getType() == 6){
            type.setText("收益类型：间接分公司补货");
        }else if(item.getType() == 7){
            type.setText("收益类型：卖货收益");
        }

        double amount = item.getAmount()/100.00;
        if(amount > 0){
            info.setText("+"+amount);
        }else{
            info.setText(""+amount);
        }


    }
}
