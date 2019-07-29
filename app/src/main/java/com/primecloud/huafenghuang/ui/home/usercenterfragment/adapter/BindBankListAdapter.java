package com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankCardBean;
import com.primecloud.huafenghuang.utils.Utils;

import java.util.List;

public class BindBankListAdapter extends BaseQuickAdapter<BankCardBean, BaseViewHolder> {
    private boolean isTiXian;

    public BindBankListAdapter(int layoutResId, @Nullable List<BankCardBean> data, boolean isTiXian) {
        super(layoutResId, data);
        this.isTiXian = isTiXian;
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardBean item) {


        if (isTiXian) {
            if (item.isWx()) {

                helper.setText(R.id.item_bankcard_name, "微信提现");
                helper.setText(R.id.item_bankcard_num, item.getRealName());
            } else {
                helper.setText(R.id.item_bankcard_name, item.getBankName());
                helper.setText(R.id.item_bankcard_num, Utils.cardNumFormat(item.getCardNumber()));
            }
            helper.setGone(R.id.item_bankcard_default, false);
            helper.setGone(R.id.item_bankcard_delete, false);
            helper.setGone(R.id.item_bankcard_setdefault, false);

        } else {
            helper.setText(R.id.item_bankcard_name, item.getBankName());
            helper.setText(R.id.item_bankcard_num, Utils.cardNumFormat(item.getCardNumber()));
            helper.setVisible(R.id.item_bankcard_default, item.isIsDefault());

            helper.setGone(R.id.item_bankcard_setdefault, !item.isIsDefault());
            helper.addOnClickListener(R.id.item_bankcard_delete);
            helper.addOnClickListener(R.id.item_bankcard_setdefault);

        }
    }
}
