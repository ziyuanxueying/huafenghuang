/**
 *
 */
package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.helper.InputTextHelper;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.huafenghuang.widget.MaxLengthEditText;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;

/**
 * 建议反馈
 */
public class SuggestFragment extends BaseFragmentV4 implements View.OnClickListener {


    TextView toolbarBack;
    TextView toolbarTitle;
    MaxLengthEditText editText;
    Button btnSuggest;
    InputTextHelper inputTextHelper;
    LinearLayout line;
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggest, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        line = currentView.findViewById(R.id.line);
        editText = currentView.findViewById(R.id.edit_content);
        btnSuggest = currentView.findViewById(R.id.btn_suggest);
    }

    @Override
    public void initData() {
        toolbarTitle.setText(getResources().getString(R.string.text_suggest_title));
        ViewUtils.setGone(line);
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        inputTextHelper = new InputTextHelper(btnSuggest);
        inputTextHelper.addViews(editText);
    }

    @Override
    public void initListener() {
        btnSuggest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String conent = editText.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_suggest:
                if (StringUtils.notBlank(conent)) {
                    suggest(conent);
                } else {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.text_suggest_content));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        inputTextHelper.removeViews();
        super.onDestroy();
    }

    /**
     * 意见反馈
     * @param conent
     */
    public void suggest(String conent) {
        FengHuangApi.feedback(MyApplication.getInstance().getUserInfo().getId(), conent, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                ToastUtils.showToast(getActivity(), getResources().getString(R.string.text_btn_suggest_success));
                getActivity().finish();
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }
}

