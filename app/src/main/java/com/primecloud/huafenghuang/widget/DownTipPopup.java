package com.primecloud.huafenghuang.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import com.primecloud.huafenghuang.R;

import razerdp.basepopup.BasePopupWindow;

public class DownTipPopup extends BasePopupWindow implements View.OnClickListener {
    public DownTipPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.pop_tip_rootview).setOnClickListener(this);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.pop_download_tip);
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
