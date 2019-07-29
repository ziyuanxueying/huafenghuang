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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class BuyCourseActivity extends BasePresenterActivity implements View.OnClickListener {

    @BindView(R.id.head_center_text)
    TextView title;
    @BindView(R.id.head_left_image)
    ImageView imageReturn;
    @BindView(R.id.course_name)
    TextView courseName;//课程名称
    @BindView(R.id.open_account)
    TextView text_account;//开通账户
    @BindView(R.id.buy_money)
    TextView money;//金额
    @BindView(R.id.buy_course_radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.buy_course_balance)
    RadioButton buy_course_balance;

    private PayUtils payUtils;
    private String courseId;
    private String payType = "1";
    private PaySDK.PayMethod payMethod = PaySDK.PayMethod.WX_PAY;
    private String course_name;
    private double  coursePrice;
    private double pay_balance = 0;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_buy_course);
    }

    @Override
    protected void initData() {
        title.setText(getResources().getString(R.string.buy_course));
        payUtils = new PayUtils(this);
        courseId = getIntent().getStringExtra("courseId");
        course_name = getIntent().getStringExtra("courseName");
        coursePrice = getIntent().getDoubleExtra("coursePrice",0)/100;

        radioGroup.check(R.id.buy_course_weixin);


        if (StringUtils.notBlank(course_name)) {
            courseName.setText(course_name);
        }

        money.setText("$" + coursePrice);


        if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            getUserInfo(MyApplication.getInstance().getUserInfo().getId());
        }
    }


    @Override
    protected void initEvent() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buy_course_balance://余额
                        payMethod = null;
                        payType = "4";
                        break;
                    case R.id.buy_course_weixin://微信
                        payMethod = PaySDK.PayMethod.WX_PAY;
                        payType = "1";
                        break;
                    case R.id.buy_course_zhifubao://支付宝
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
                        pay_balance = Double.parseDouble(balance)/100;
                    } else {
                        pay_balance = 0.00;
                    }
                } else {
                    pay_balance = 0.00;
                }

                String balance = getResources().getString(R.string.pay_balance) + "\t(" + getResources().getString(R.string.current_balance) + "\t$" + pay_balance + ")";
                setColor(balance, buy_course_balance);

                if (data.contains("phone")) {
                    String account = jsonObject.getString("phone");

                    if (StringUtils.notBlank(account)) {
                        text_account.setText(account);
                    }
                }


            }

            @Override
            public void onFailure(String data, String errorMsg) {
            }
        });


    }


    @Override
    @OnClick({R.id.confirm_purchase, R.id.head_left_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_purchase://确认购买

                if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {

                    if (payType.equals("4")) {
                        if (pay_balance < coursePrice) {
                            ToastUtils.showToast(BuyCourseActivity.this, getResources().getString(R.string.balance_insufficient));
                            return;
                        }
                    }

                    payUtils.gotoPay(payType, "0", courseId,
                            MyApplication.getInstance().getUserInfo().getId(), "",
                            "", "", "", "", "0", "", payMethod);
                } else {
                    startActivity(new Intent(BuyCourseActivity.this, LoginActivity.class));
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
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FC363C")), index, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(25), index, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            radioButton.setText(spannableStringBuilder);
        }

    }


}
