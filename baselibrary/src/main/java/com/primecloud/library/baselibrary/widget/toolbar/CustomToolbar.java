package com.primecloud.library.baselibrary.widget.toolbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primecloud.library.baselibrary.R;


/**
 * Created by zy on 2017/9/14.
 */

public class CustomToolbar extends Toolbar {

    private boolean isDefaultBack = false;// 是否使用Toolbar默认的返回按钮，默认是不使用

    private TextView toolbar_back;// 返回
    private TextView toolbar_title;// 标题
    private TextView toolbar_confirm;// 确定
    private LinearLayout line;//分割线IAN
    public CustomToolbar(Context context) {
        this(context,null);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        toolbar_back = (TextView) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_confirm = (TextView) findViewById(R.id.toolbar_confirm);
    }

    /**
     * 设置标题内容
     */
    public void setToolbarTitleContent(String content){

        this.setTitle("");
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(content);
    }

    /**
     * 设置标题颜色
     */
    public void setToolbarTitleColor(int color){
        toolbar_title.setTextColor(color);
    }

    /**
     * 设置返回文字
     * @param text
     */
    public void setToolbarBackText(String text){
        toolbar_back.setText(text);
        toolbar_back.setVisibility(View.VISIBLE);
    }

    /**
     * 设置返回文字颜色
     *
     */
    public void setToolbarBackTColor(int color){
        toolbar_back.setTextColor(color);
    }

    //设置返回图标
    public void setToolbarBackDrawable(int res) {
        if(-1 == res){
            toolbar_back.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable dwBack = ContextCompat.getDrawable(getContext(), res);
        dwBack.setBounds(0, 0, dwBack.getMinimumWidth(), dwBack.getMinimumHeight());
        toolbar_back.setCompoundDrawables(dwBack, null, null, null);
    }

    /**
     * 返回点击事件
     * @param clickListener
     */
    public void setToolbarBackOnClick(OnClickListener clickListener){
        toolbar_back.setOnClickListener(clickListener);
    }

    /**
     * shez
     */
    /**
     * 设置确定文字
     * @param text
     */
    public void setToolbarConfirmText(String text){
        if (text == null || "".equals(text)){
            toolbar_confirm.setVisibility(View.INVISIBLE);
//            toolbar_confirm.setOnClickListener(null);
        }else {
            toolbar_confirm.setText(text);
            toolbar_confirm.setVisibility(View.VISIBLE);
        }

    }
    /**
     * 设置确定文字颜色
     *
     */
    public void setToolbarConfirmTColor(int color){
        toolbar_confirm.setTextColor(color);
    }

    //设置确定图标
    public void setToolbarConfirmDrawable(int res) {
        Drawable dwConfirm = ContextCompat.getDrawable(getContext(), res);
        dwConfirm.setBounds(0, 0, dwConfirm.getMinimumWidth(), dwConfirm.getMinimumHeight());
        toolbar_confirm.setCompoundDrawables(null, null, dwConfirm, null);
    }

    /**
     * 确定点击事件
     * @param clickListener
     */
    public void setToolbarConfirmOnClick(OnClickListener clickListener){
        toolbar_confirm.setOnClickListener(clickListener);
    }

    public void isUseDefaultBack(ActionBar actionBar, boolean isShow){
        actionBar.setDisplayHomeAsUpEnabled(isShow);  // 给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayShowHomeEnabled(isShow);
    }


    public void setGone(int view)
    {
        findViewById(view).setVisibility(GONE);
    }

    public String getToolbarConfirmText(){
        return toolbar_confirm.getText().toString().trim();
    }
}
