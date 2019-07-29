package com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member.MemberBean;
import com.primecloud.huafenghuang.widget.CircleImageView;
import com.primecloud.library.baselibrary.log.XLog;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import java.util.List;

/**
 * 会员
 */

public class MemberAdapter extends BaseQuickAdapter<MemberBean.DataBean.RecordsBean,BaseViewHolder> {

    private int flag;//1会员 2 合伙人 3 分公司 4间接分公司
    public MemberAdapter(int layoutResId, @Nullable List<MemberBean.DataBean.RecordsBean> data,int flag) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberBean.DataBean.RecordsBean item) {
        CircleImageView touxiang = helper.getView(R.id.huiyuan_touxiang);
        TextView yonghu = helper.getView(R.id.huiyuan_yonghu);
        TextView shenfen = helper.getView(R.id.huiyuan_shenfen);
        TextView num = helper.getView(R.id.huiyuan_num);
        TextView vip = helper.getView(R.id.huiyuan_vip);

        helper.addOnClickListener(R.id.huiyuan_call);
        helper.addOnClickListener(R.id.huiyuan_liuyan);
        GlideImageLoader.getInstance().displayImage(mContext, item.getPic(), touxiang);

//        分销用户等级： 1 -> 注册用户、 2 -> 代言人、 3 -> 合伙人、 4 -> 分公司、 5 -> 合作导师（等同于分公司）、
        if(item.getLevel() == 1){
            shenfen.setText(mContext.getResources().getString(R.string.text_senfen_zhuce));
        }else if(item.getLevel() == 2){
            shenfen.setText(mContext.getResources().getString(R.string.text_senfen_daiyanren));
        }else if(item.getLevel() == 3){
            shenfen.setText(mContext.getResources().getString(R.string.text_senfen_hehuoren));
        }else if(item.getLevel() == 4){
            shenfen.setText(mContext.getResources().getString(R.string.text_senfen_fengongsi));
        }
        num.setText("团队人数："+item.getTeamNumber());
        vip.setText("已购VIP个数："+item.getSoldCount());
        yonghu.setText(item.getUsername());


    }
}
