package com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo.ExpenditureInfoBean;
import com.primecloud.huafenghuang.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class ExpandInfoAdapter extends BaseQuickAdapter<ExpenditureInfoBean.DataBean,BaseViewHolder> {

    public ExpandInfoAdapter(int layoutResId, @Nullable List<ExpenditureInfoBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpenditureInfoBean.DataBean item) {
        helper.setText(R.id.ex_title,item.getExpenditureTitle());
        helper.setText(R.id.ex_money,"-"+(item.getAmount()/100.00));
        helper.setText(R.id.ex_info,item.getDetails());
        helper.setText(R.id.ex_time, DateUtils.dateToStrLong(new Date(item.getCreated_at()))+"");
    }
}
