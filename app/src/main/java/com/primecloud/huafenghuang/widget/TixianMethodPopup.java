package com.primecloud.huafenghuang.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import com.primecloud.huafenghuang.R;

import razerdp.basepopup.BasePopupWindow;

public class TixianMethodPopup extends BasePopupWindow implements View.OnClickListener {
    public TixianMethodPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.popup_tixian_rootview).setOnClickListener(this);
        findViewById(R.id.popup_tixian_close).setOnClickListener(this);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_tixian_method);
    }


    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 500);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 500);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
