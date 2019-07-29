package com.primecloud.huafenghuang.ui.course;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class ApplyResultActivity extends BasePresenterActivity {


    @BindView(R.id.apply_result_linearLayout)
    LinearLayout result;
    @BindView(R.id.apply_result_image)
    ImageView result_image;
    @BindView(R.id.apply_result_text)
    TextView result_text;
    @BindView(R.id.head_left_image)
    ImageView image_return;
    @BindView(R.id.head_center_text)
    TextView text_title;
    private String productType = "0";//商品类型 0线上课程支付，1线下课程支付，2vip会员支付


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_apply_result);
    }


    @Override
    protected void initData() {

        productType = getIntent().getStringExtra("productType");

        if (StringUtils.notBlank(productType))
        {
            if (productType.equals("1"))
            {
                text_title.setText(getResources().getString(R.string.apply_success_title));
                result_text.setText(getResources().getString(R.string.apply_success));
            }
            else if (productType.equals("0"))
            {
                text_title.setText(getResources().getString(R.string.success_course));
                result_text.setText(getResources().getString(R.string.success_course));
            }
            else if (productType.equals("2"))
            {
                text_title.setText(getResources().getString(R.string.success_openvip));
                result_text.setText(getResources().getString(R.string.success_openvip));
            }
        }
        else
        {
            text_title.setText(getResources().getString(R.string.success_course));
            result_text.setText(getResources().getString(R.string.success_course));
        }



       LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) result.getLayoutParams();
        layoutParams.topMargin = (int)(getResources().getDisplayMetrics().heightPixels*0.17);
    }


    @Override
    protected void initEvent() {

        image_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(3);
        super.onDestroy();
    }
}
