package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;
import com.primecloud.library.baselibrary.log.XLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamActivity extends CommonBaseActivity {


    @BindView(R.id.team_num)
    TextView teamNum;
    @BindView(R.id.act_team_yi)
    TextView actTeamYi;
    @BindView(R.id.act_team_wei)
    TextView actTeamWei;
    @BindView(R.id.sb_team_hy)
    SettingBar sbTeamHy;
    @BindView(R.id.sb_team_hehuoren)
    SettingBar sbTeamHehuoren;
    @BindView(R.id.sb_team_fengongsi)
    SettingBar sbTeamFengongsi;
    @BindView(R.id.sb_team_zongzongsi)
    SettingBar sbTeamZongzongsi;
    @BindView(R.id.line)
    LinearLayout linearLayout;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_team);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.text_tuandui));
        ViewUtils.setGone(linearLayout);
        getTeamInfo();
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.sb_team_hy, R.id.sb_team_hehuoren, R.id.sb_team_fengongsi, R.id.sb_team_zongzongsi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sb_team_hy:
                startActivity(MemberActivity.class, 1);
                break;
            case R.id.sb_team_hehuoren:
                startActivity(MemberActivity.class, 2);
                break;
            case R.id.sb_team_fengongsi:
                startActivity(MemberActivity.class, 3);
                break;
            case R.id.sb_team_zongzongsi:
                startActivity(MemberActivity.class, 4);
                break;

            default:
                break;
        }
    }

    public void getTeamInfo() {
        if (NetUtils.isConnected(TeamActivity.this)) {
            FengHuangApi.statistics(MyApplication.getInstance().getUserInfo().getId(), new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    TeamBean teamBean = JSON.parseObject(body.getData(), TeamBean.class);
                    teamNum.setText(StringUtils.notBlank(teamBean.getTeamNumber()) ? teamBean.getTeamNumber():"0");
                    actTeamYi.setText(getResources().getString(R.string.text_team_yiyong) + (StringUtils.notBlank(teamBean.getUsedCount()) ? teamBean.getUsedCount() : "0"));
                    actTeamWei.setText(getResources().getString(R.string.text_team_weiyong) + (StringUtils.notBlank(teamBean.getUnusedCount()) ? teamBean.getUnusedCount() : "0"));
                    sbTeamHy.setRightText(StringUtils.notBlank(teamBean.getMember()) ? teamBean.getMember() : "0");
                    sbTeamHehuoren.setRightText(StringUtils.notBlank(teamBean.getPartner()) ? teamBean.getPartner() : "0");
                    sbTeamFengongsi.setRightText(StringUtils.notBlank(teamBean.getBranchOffice()) ? teamBean.getBranchOffice() :"0");
                    sbTeamZongzongsi.setRightText(StringUtils.notBlank(teamBean.getIndirectOffice()) ? teamBean.getIndirectOffice() : "0");
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }
    }
}
