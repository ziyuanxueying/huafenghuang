package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.pay.PayUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;
import com.primecloud.primecloudpaysdk.version2_0.PaySDK;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class DredgeVIPActivity extends BasePresenterActivity implements View.OnClickListener {

    @BindView(R.id.head_center_text)
    TextView title;
    @BindView(R.id.head_left_image)
    ImageView imageReturn;
    @BindView(R.id.dredge_account)
    TextView text_account;//开通账户
    @BindView(R.id.dredge_member_indate)
    TextView indate;//会员有效期
    @BindView(R.id.dredge_price)
    TextView price;//金额
    @BindView(R.id.vip_radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.immediately_apply)
    Button immediately_apply;//立即续费/确认开通
    @BindView(R.id.pay_balance)
    RadioButton pay_balance;
    @BindView(R.id.dredge_member_indate_rela)
    RelativeLayout indate_rela;

    private PayUtils payUtils;
    private String payType = "1";
    private PaySDK.PayMethod payMethod = PaySDK.PayMethod.WX_PAY;
    private int type = 1;//1开通VIP  2续费VIP
    private double balancePrice = 0.00;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_dredge_vip);
    }

    @Override
    protected void initData() {
        title.setText(getResources().getString(R.string.open_vip));
        payUtils = new PayUtils(this);
        type = getIntent().getIntExtra("type", 2);
        radioGroup.check(R.id.pay_weixin);



        if (type == 1) {
            indate_rela.setVisibility(View.GONE);
            immediately_apply.setText(getResources().getString(R.string.confirm_open));
        } else if (type == 2) {
            indate_rela.setVisibility(View.VISIBLE);
            immediately_apply.setText(getResources().getString(R.string.immediately_renewal));
        }


        if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            getUserInfo(MyApplication.getInstance().getUserInfo().getId());
        } else {
            startActivity(new Intent(DredgeVIPActivity.this, LoginActivity.class));
        }


    }


    @Override
    protected void initEvent() {


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pay_balance:
                        payMethod = null;
                        payType = "4";
                        break;
                    case R.id.pay_weixin:
                        payMethod = PaySDK.PayMethod.WX_PAY;
                        payType = "1";
                        break;
                    case R.id.pay_zhifubao:
                        payMethod = PaySDK.PayMethod.AL_PAY;
                        payType = "0";
                        break;
                }
            }
        });

    }


    /**
     * 获取用户信息
     */
    public void getUserInfo(String userId) {
        FengHuangApi.getUserAccount(userId, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {

                JSONObject jsonObject = JSON.parseObject(body.getData());
                if (data.contains("balance")) {
                    String balance = jsonObject.getString("balance");
                    if (StringUtils.notBlank(balance)) {
                        balancePrice = Double.parseDouble(balance)/100;
                    } else {
                        balancePrice = 0.00;
                    }
                } else {
                    balancePrice = 0.00;
                }

                String balance = getResources().getString(R.string.pay_balance) + "\t(" + getResources().getString(R.string.current_balance) + "\t$" + balancePrice + ")";
                setColor(balance, pay_balance);


                if (data.contains("vipPrice"))
                {
                    String vipPrice = jsonObject.getString("vipPrice");
                    if (StringUtils.notBlank(vipPrice))
                    {
                        price.setText(Double.parseDouble(vipPrice)/100 + getResources().getString(R.string.yuan) + "/" + getResources().getString(R.string.year));
                    }
                    else
                    {
                        price.setText("0.0" + getResources().getString(R.string.yuan) + "/" + getResources().getString(R.string.year));
                    }
                }


                if (data.contains("phone")) {
                    String account = jsonObject.getString("phone");
                    if (StringUtils.notBlank(account)) {
                        text_account.setText(account);
                    }
                }

                if (data.contains("expireTime")) {
                    String expireTime = jsonObject.getString("expireTime");
                    if (StringUtils.notBlank(expireTime)) {
                        indate.setText(expireTime);
                    }
                }


            }

            @Override
            public void onFailure(String data, String errorMsg) {
            }
        });


    }


    @Override
    @OnClick({R.id.immediately_apply, R.id.head_left_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.immediately_apply://确认开通
                if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {

                    if (payType.equals("4")) {
                        int index = price.getText().toString().indexOf("元");
                        if (index!=-1)
                        {
                            Double coursePrice = Double.parseDouble(price.getText().toString().substring(0,index));
                            if (balancePrice < coursePrice) {
                                ToastUtils.showToast(DredgeVIPActivity.this, getResources().getString(R.string.balance_insufficient));
                                return;
                            }
                        }

                    }


                    payUtils.gotoPay(payType, "2", null,
                            MyApplication.getInstance().getUserInfo().getId(), "",
                            "", "", "", "", "0", "", payMethod);
                } else {
                    startActivity(new Intent(DredgeVIPActivity.this, LoginActivity.class));
                }
                break;
            case R.id.head_left_image:
                finish();
                break;
        }
    }


    public void setColor(String content, RadioButton radioButton) {
        int index = content.indexOf("(");
        if (index != -1) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FC363C")), index, content.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(25), index, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            radioButton.setText(spannableStringBuilder);

        }

    }
}
