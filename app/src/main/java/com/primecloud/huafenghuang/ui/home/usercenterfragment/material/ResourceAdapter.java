package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ResourceTag;
import com.primecloud.library.baselibrary.log.XLog;

import java.util.List;

public class ResourceAdapter extends BaseQuickAdapter<ResourceTag.DataBean, BaseViewHolder>  {
    public ResourceAdapter(int layoutResId, @Nullable List<ResourceTag.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResourceTag.DataBean item) {

        helper.setText(R.id.item_resourcetag_tv, item.getName());

        XLog.i("item.isSelect():"+item.isSelect()+"item:"+item.getName());
        if(item.isSelect()){
            helper.setTextColor(R.id.item_resourcetag_tv, mContext.getResources().getColor(R.color.theme_color));
        }else{
            helper.setTextColor(R.id.item_resourcetag_tv, mContext.getResources().getColor(R.color.black));
        }
    }
}
