package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.BuildConfig;
import com.primecloud.huafenghuang.R;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.util.List;

public class MaterialPicAdapter extends BaseQuickAdapter<String , BaseViewHolder> {
    private List<String> data;
    public MaterialPicAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        GlideImageLoader.getInstance().displayImage(mContext,item, helper.getView(R.id.item_material_pic_iv));

    }

}
