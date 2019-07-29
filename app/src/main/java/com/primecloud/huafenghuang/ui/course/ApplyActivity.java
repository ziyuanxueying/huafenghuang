package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.pay.PayUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;
import com.primecloud.primecloudpaysdk.version2_0.PaySDK;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyActivity extends BasePresenterActivity implements View.OnClickListener {

    @BindView(R.id.head_center_text)
    TextView center_text;
    @BindView(R.id.head_left_image)
    ImageView image_return;
    @BindView(R.id.apply_name)
    EditText name;
    @BindView(R.id.apply_phone)
    EditText phone;
    @BindView(R.id.apply_profession)
    EditText profession;
    @BindView(R.id.apply_area)
    TextView area;
    @BindView(R.id.apply_date)
    TextView date;
    @BindView(R.id.immediately_apply)
    Button apply;
    @BindView(R.id.apply_price)
    TextView price;
    @BindView(R.id.apply_pay_radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.apply_pay_balance)
    RadioButton button_balance;

    private PaySDK.PayMethod payMethod = PaySDK.PayMethod.WX_PAY;
    private String payType = "1";
    private PayUtils payUtils;
    private String courseId;

    private String coursePrice;
    private OfflineCourseDetailBean.DataBean dataBean;
    private ArrayList<String> areaList;
    private ArrayList<String> dateList;
    private double pay_balance = 0.00;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_apply);
    }

    @Override
    protected void initData() {
        payUtils = new PayUtils(this);
        courseId = getIntent().getStringExtra("courseId");
        coursePrice = getIntent().getStringExtra("coursePrice");
        dataBean = (OfflineCourseDetailBean.DataBean) getIntent().getSerializableExtra("area_time");
        center_text.setText(getResources().getString(R.string.immediately_apply));


        radioGroup.check(R.id.apply_pay_weixin);


        if (StringUtils.notBlank(coursePrice)) {
            price.setText("$" + Double.parseDouble(coursePrice)/100);
        }


        if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            getUserInfo(MyApplication.getInstance().getUserInfo().getId());
        } else {
            startActivity(new Intent(ApplyActivity.this, LoginActivity.class));
        }


        setEditTextInhibitInputSpaChat(name);
    }

    /**
     * EditText只能输入字母数字汉字
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpaChat(EditText editText) {
        InputFilter filter_space = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else
                    return null;
            }
        };
        InputFilter filter_speChat = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                String speChat = "[^a-zA-Z0-9\\u4E00-\\u9FA5]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(charSequence.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter_space, filter_speChat});
    }




    /**
     * 获取地区
     */
    public void getArea() {
        if (dataBean != null) {
            if (areaList == null) {
                areaList = new ArrayList<String>();
            }
            else if (areaList!=null&&areaList.size()>0)
            {
                areaList.clear();
            }
            for (int i = 0; i < dataBean.getCourseInfoList().size(); i++) {
                areaList.add(dataBean.getCourseInfoList().get(i).getArea());
            }
        }
    }

    /**
     * 获取日期
     */
    public void getDate(String stringArea) {
        if (dataBean != null) {
            if (dateList == null) {
                dateList = new ArrayList<String>();
            }
            else if (dateList!=null&&dateList.size()>0)
            {
               dateList.clear();
            }

            for (int i = 0; i < dataBean.getCourseInfoList().size(); i++) {
                if (dataBean.getCourseInfoList().get(i).getArea().equals(stringArea)) {
                    dateList.addAll(dataBean.getCourseInfoList().get(i).getBeginTime());
                }
            }
        }
    }


    /**
     * 获取用户信息
     */
    public void getUserInfo(String userId) {
        FengHuangApi.getUserAccount(userId, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {

                JSONObject jsonObject = JSON.parseObject(body.getData());
                if (data.contains("balance"))
                {
                    String balance = jsonObject.getString("balance");
                    if (StringUtils.notBlank(balance)) {
                        pay_balance = Double.parseDouble(balance)/100;
                    } else {
                        pay_balance = 0.00;
                    }
                }
                else
                {
                    pay_balance = 0.00;
                }

                String balance = getResources().getString(R.string.pay_balance) + "\t(" + getResources().getString(R.string.current_balance) + "\t$" + pay_balance + ")";
                setColor(balance, button_balance);

            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });


    }


    @Override
    protected void initEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.apply_pay_balance:
                        payMethod = null;
                        payType = "4";
                        break;
                    case R.id.apply_pay_weixin:
                        payMethod = PaySDK.PayMethod.WX_PAY;
                        payType = "1";
                        break;
                    case R.id.apply_pay_zhifubao:
                        payMethod = PaySDK.PayMethod.AL_PAY;
                        payType = "0";
                        break;
                }
            }
        });
    }


    @Override
    @OnClick({R.id.apply_area, R.id.apply_date, R.id.immediately_apply, R.id.head_left_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_area:
                getArea();
                ShowBankName(2);
                break;
            case R.id.apply_date:
                if (!StringUtils.notBlank(area.getText().toString()) || StringUtils.notBlank(area.getText().toString()) && area.getText().toString().equals(getResources().getString(R.string.area_hint))) {
                    ToastUtils.showToast(ApplyActivity.this, getResources().getString(R.string.area_hint));
                    return;
                }
                getDate(area.getText().toString());
                ShowBankName(1);
                break;
            case R.id.immediately_apply:


                if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {

                    if (!StringUtils.notBlank(name.getText().toString()) || StringUtils.notBlank(name.getText().toString()) && name.getText().toString().equals(getResources().getString(R.string.name_hint))) {
                        ToastUtils.showToast(ApplyActivity.this, getResources().getString(R.string.name_hint));
                        return;
                    }

                    if (!StringUtils.notBlank(phone.getText().toString()) || StringUtils.notBlank(phone.getText().toString()) && phone.getText().toString().equals(getResources().getString(R.string.phone_hint))) {
                        ToastUtils.showToast(ApplyActivity.this, getResources().getString(R.string.phone_hint));
                        return;
                    } else {
                        if (!Utils.isMobileNO(phone.getText().toString())) {
                            ToastUtils.showToast(ApplyActivity.this, getResources().getString(R.string.correntphone_hint));
                            return;
                        }
                    }

//                    if (!StringUtils.notBlank(profession.getText().toString()) || StringUtils.notBlank(profession.getText().toString()) && profession.getText().toString().equals(getResources().getString(R.string.profession_hint))) {
//                        ToastUtils.showToast(ApplyActivity.this, getResources().getString(R.string.profession_hint));
//                        return;
//                    }

                    if (!StringUtils.notBlank(area.getText().toString()) || StringUtils.notBlank(area.getText().toString()) && area.getText().toString().equals(getResources().getString(R.string.area_hint))) {
                        ToastUtils.showToast(ApplyActivity.this, getResources().getString(R.string.area_hint));
                        return;
                    }

                    if (!StringUtils.notBlank(date.getText().toString()) || StringUtils.notBlank(date.getText().toString()) && date.getText().toString().equals(getResources().getString(R.string.date_hint))) {
                        ToastUtils.showToast(ApplyActivity.this, getResources().getString(R.string.date_hint));
                        return;
                    }


                    if (payType.equals("4")) {
                        if (pay_balance < Double.parseDouble(coursePrice)/100) {
                            ToastUtils.showToast(ApplyActivity.this, getResources().getString(R.string.balance_insufficient));
                            return;
                        }
                    }


                    payUtils.gotoPay(payType, "1", courseId,
                            MyApplication.getInstance().getUserInfo().getId(), name.getText().toString(),
                            phone.getText().toString(), profession.getText().toString(), area.getText().toString(), date.getText().toString(), "0", "", payMethod);
                } else {
                    startActivity(new Intent(ApplyActivity.this, LoginActivity.class));
                }
                break;
            case R.id.head_left_image:
                finish();
                break;
        }
    }


    private void ShowBankName(int type) {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (type == 1)//时间
                {
                    String stringDate = dateList.get(options1);
                    date.setText(stringDate);//将选中的数据返回设置在TextView 上。
                } else {
                    String stringArea = areaList.get(options1);
                    area.setText(stringArea);//将选中的数据返回设置在TextView 上。
                }


            }
        })

                .setDividerColor(Color.parseColor("#E5E5E5"))
                .setTextColorCenter(Color.parseColor("#333333")) //设置选中项文字颜色
                .setTextColorOut(Color.parseColor("#999999"))
                .setContentTextSize(15)//设置文字大小
                .setTitleBgColor(Color.parseColor("#ffffff"))
                .setOutSideCancelable(false)// default is true
                .build();


        if (type == 1)//时间
        {
            pvOptions.setPicker(dateList);//时间选择器
        } else {
            pvOptions.setPicker(areaList);//地区选择器
        }

        pvOptions.show();
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
