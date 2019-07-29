package com.primecloud.huafenghuang.ui.home.findfragment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.ClassifyBean;

import java.util.List;

public class ClassifyAdapter extends BaseQuickAdapter<ClassifyBean, BaseViewHolder> {
    public ClassifyAdapter(int layoutResId, @Nullable List<ClassifyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyBean item) {
        helper.setImageResource(R.id.item_classify_iv, item.getResourceId());
        helper.setText(R.id.item_classify_tv, item.getName());
    }
}
