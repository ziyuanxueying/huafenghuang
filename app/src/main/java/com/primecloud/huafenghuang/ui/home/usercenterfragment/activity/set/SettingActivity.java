package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.helper.UIHelper;
import com.primecloud.huafenghuang.ui.home.HomeActivity;
import com.primecloud.huafenghuang.ui.update.UpdateManager;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.utils.DeleteDialog;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.huafenghuang.widget.SwitchButton;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends CommonBaseActivity implements SwitchButton.OnCheckedChangeListener {


    @BindView(R.id.sb_setting_auto)
    SettingBar sbSettingAuto;
    @BindView(R.id.sb_setting_zhanghao)
    SettingBar sbSettingZhanghao;
    @BindView(R.id.sb_setting_about)
    SettingBar sbSettingAbout;
    @BindView(R.id.sb_setting_qingchu)
    SettingBar sbSettingQingchu;
    @BindView(R.id.sb_setting_version)
    SettingBar sbSettingVersion;
    @BindView(R.id.sb_setting_out)
    SettingBar sbSettingOut;
    @BindView(R.id.sb_setting_switch)
    SwitchButton sbSettingSwitch;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.text_setting_title));
        // 设置切换按钮的监听
        sbSettingSwitch.setOnCheckedChangeListener(this);
        String cacheSize=Utils.caculateCacheSize(SettingActivity.this);
        sbSettingQingchu.setRightText(cacheSize);
        String version = Utils.getVersionName(SettingActivity.this);
        sbSettingVersion.setRightText("v "+version);
    }

    private DeleteDialog exitDialig;
    @OnClick({R.id.sb_setting_zhanghao, R.id.sb_setting_about, R.id.sb_setting_qingchu, R.id.sb_setting_version, R.id.sb_setting_out})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.sb_setting_zhanghao:
                UIHelper.showSimpleBack(SettingActivity.this, SimpleBackPage.Change_Safe);
                break;
            case R.id.sb_setting_about:
                UIHelper.showSimpleBack(SettingActivity.this, SimpleBackPage.Change_About);
                break;
            case R.id.sb_setting_qingchu:
                clearCache();
                break;
            case R.id.sb_setting_version:
                UpdateManager.getUpdateManager().checkAppVersionInfo(this,1);
                break;
            case R.id.sb_setting_out:
                exitDialig= DialogUtils.showDeleteDialog(SettingActivity.this, getResources().getString(R.string.text_message_exit),getResources().getString(R.string.choose_photo_cancel),
                        getResources().getString(R.string.save), new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO 自动生成的方法存根
                                switch (v.getId()) {
                                    case R.id.iknow_alert_dialog_button1:
                                        break;

                                    case R.id.iknow_alert_dialog_button2:
                                        MyApplication.doLogout(SettingActivity.this);
                                        Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                                        EventBus.getDefault().post(0);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        break;

                                    default:
                                        break;
                                }
                                exitDialig.dismiss();
                            }
                        });
                exitDialig.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {

    }

    DeleteDialog clearDialog;
    //清除缓存
    public void clearCache(){
        clearDialog = DialogUtils.showDeleteDialog(this, getString(R.string.string_clear_message), getResources().getString(R.string.string_clear_cancle),getResources().getString(R.string.string_clear_sure),
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.iknow_alert_dialog_button1:
                                clearDialog.dismiss();
                                break;

                            case R.id.iknow_alert_dialog_button2:
                                UIHelper.clearAppCache(SettingActivity.this);
                                sbSettingQingchu.setRightText("0KB");
                                clearDialog.dismiss();
                                break;

                            default:
                                break;
                        }
                    }
                });

        clearDialog.show();
    }


}
