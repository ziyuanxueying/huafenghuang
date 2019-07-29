package com.primecloud.huafenghuang.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.library.baselibrary.log.XLog;

public class TipsFillBar extends FrameLayout {




    private TextView mTipTv;
    private EditText mTipEt;
    private View mLineView;

    public TipsFillBar(@NonNull Context context) {
        this(context, null);
    }

    public TipsFillBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipsFillBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_tipfill_bar, this);
        mTipTv = findViewById(R.id.tv_tipfill_bar);
        mTipEt = findViewById(R.id.et_tipfill_bar);
        mLineView = findViewById(R.id.v_tipfill_bar_linear);

        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TipFillBar);

        // 文本设置
        if (array.hasValue(R.styleable.TipFillBar_tip_tvText)) {
            setTipTvText(array.getString(R.styleable.TipFillBar_tip_tvText));
        }
        if (array.hasValue(R.styleable.TipFillBar_tip_etText)) {
            setTipEtText(array.getString(R.styleable.TipFillBar_tip_etText));
        }

        if (array.hasValue(R.styleable.TipFillBar_tip_etHint)) {
            setTipEtHint(array.getString(R.styleable.TipFillBar_tip_etHint));
        }


        //是否可见 默认可见
        if (array.hasValue(R.styleable.TipFillBar_tip_tvVisible)) {
            setTipTvIsVisible(array.getBoolean(R.styleable.TipFillBar_tip_tvVisible, true));
        }

        // 提示设置
        if (array.hasValue(R.styleable.TipFillBar_tip_tvHint)) {
            setTipTvHint(array.getString(R.styleable.TipFillBar_tip_tvHint));
        }
        if (array.hasValue(R.styleable.TipFillBar_tip_etHint)) {
            setTipEtHint(array.getString(R.styleable.TipFillBar_tip_etHint));
        }
        // 提示设置颜色
        if (array.hasValue(R.styleable.TipFillBar_tip_etHintColor)) {
            setTipEtHintColor(array.getColor(R.styleable.TipFillBar_tip_etHintColor, 0));
        }

        // 图标设置
        if (array.hasValue(R.styleable.TipFillBar_tip_tvIcon)) {
            setTipTvIcon(getContext().getResources().getDrawable(array.getResourceId(R.styleable.TipFillBar_tip_tvIcon, 0)));
        }

        // 文字颜色设置
        if (array.hasValue(R.styleable.TipFillBar_tip_tvColor)) {
            setTipTvColor(array.getColor(R.styleable.TipFillBar_tip_tvColor, 0));
        }
        if (array.hasValue(R.styleable.TipFillBar_tip_etColor)) {
            setTipEtColor(array.getColor(R.styleable.TipFillBar_tip_etColor, 0));
        }


        // 文字大小设置
        if (array.hasValue(R.styleable.TipFillBar_tip_tvSize)) {
            setTipTvSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.TipFillBar_tip_tvSize, 0));
        }
        if (array.hasValue(R.styleable.TipFillBar_tip_etSize)) {
            setTipEtSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.TipFillBar_tip_etSize, 0));
        }

        // 设置Edittext的background
        if (array.hasValue(R.styleable.TipFillBar_tip_etBg)) {
            setTipEtBackground(getContext().getResources().getDrawable(array.getResourceId(R.styleable.TipFillBar_tip_etBg, 0)));
        }
        // 设置Eidttext的inputType
        if (array.hasValue(R.styleable.TipFillBar_android_inputType)) {
            setTipEtInput(array.getInt(R.styleable.TipFillBar_android_inputType, EditorInfo.TYPE_NULL));
        }
        // 设置Eidttext的是否可用
        if (array.hasValue(R.styleable.TipFillBar_android_enabled)) {
            setTipEtEnable(array.getBoolean(R.styleable.TipFillBar_android_enabled, true));
        }
        // 设置Eidttext的是否获取焦点
        if (array.hasValue(R.styleable.TipFillBar_android_focusable)) {
            setTipEtFocusable(array.getBoolean(R.styleable.TipFillBar_android_focusable, true));
        }
        // 设置Eidttext的最大位数
        if (array.hasValue(R.styleable.TipFillBar_android_maxLength)) {
            setTipEtMaxLen(array.getInt(R.styleable.TipFillBar_android_maxLength, Integer.MAX_VALUE));
        }


        // 分割线设置
        if (array.hasValue(R.styleable.TipFillBar_tip_lineColor)) {
            setLineDrawable(array.getDrawable(R.styleable.TipFillBar_tip_lineColor));
        }

        if (array.hasValue(R.styleable.TipFillBar_tip_lineVisible)) {
            setLineVisible(array.getBoolean(R.styleable.TipFillBar_tip_lineVisible, true));
        }

        if (array.hasValue(R.styleable.TipFillBar_tip_lineSize)) {
            setLineSize(array.getDimensionPixelSize(R.styleable.TipFillBar_tip_lineSize, 0));
        }

        if (array.hasValue(R.styleable.TipFillBar_tip_lineMargin)) {
            setLineMargin(array.getDimensionPixelSize(R.styleable.TipFillBar_tip_lineMargin, 0));
        }

        // 设置默认背景选择器
        if (getBackground() == null) {
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.selector_selectable_white);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(drawable);
            }else {
                setBackgroundDrawable(drawable);
            }
        }


        // 回收TypedArray
        array.recycle();

    }




    /**
     * 设置TextView标题
     */
    public void setTipTvText(int stringId) {
        setTipTvText(getResources().getString(stringId));
    }

    public void setTipTvText(CharSequence text) {
        mTipTv.setText(text);
    }

    /**
     * 设置TextView提示
     */
    public void setTipTvHint(int stringId) {
        setTipTvHint(getResources().getString(stringId));
    }

    public void setTipTvHint(CharSequence hint) {
        mTipTv.setHint(hint);
    }

    /**
     * 设置左边的图标
     */
    public void setTipTvIcon(int iconId) {
        if (iconId > 0) {
            setTipTvIcon(getContext().getResources().getDrawable(iconId));
        }
    }

    public void setTipTvIcon(Drawable drawable) {
        mTipTv.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    /**
     * 设置左标题颜色
     */
    public void setTipTvColor(int color) {
        mTipTv.setTextColor(color);
    }

    /**
     * 设置左标题的文本大小
     */
    public void setTipTvSize(int unit, float size) {
        mTipTv.setTextSize(unit, size);
    }


    /**
     * 设置TextView 是否显示
     */
    public void setTipTvIsVisible(boolean isVisible) {
        if (isVisible) {
            mTipTv.setVisibility(View.VISIBLE);
        } else {
            mTipTv.setVisibility(View.GONE);
        }
    }
    //------------------------Edittext-------------------------

    /**
     * 设置Edittext的inputTYPE属性
     * @param inputType
     */

    public void setTipEtInput(int inputType) {
        mTipEt.setInputType(inputType);
    }

    public void setTipEtEnable(boolean aBoolean) {
        mTipEt.setEnabled(aBoolean);
    }

    public void setTipEtFocusable(boolean aBoolean) {
        mTipEt.setFocusable(aBoolean);
    }

    private void setTipEtMaxLen(int length) {
        XLog.i("length:"+length);
        mTipEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    // 设置内容
    public void setTipEtText(CharSequence text) {
        mTipEt.setText(text);
    }

    public void setTipEtText(int stringId) {
        setTipEtText(getContext().getString(stringId));
    }

    // 设置提示
    public void setTipEtHint(CharSequence text) {
        mTipEt.setHint(text);
    }

    public void setTipEtHint(int stringId) {
        setTipEtHint(getContext().getString(stringId));
    }

    // 设置提示颜色
    public void setTipEtHintColor(int color) {
        mTipEt.setHintTextColor(color);
    }

    // 设置文本颜色
    public void setTipEtColor(int color) {
        mTipEt.setTextColor(color);
    }

    // 设置大小
    public void setTipEtSize(int unit, float size) {
        mTipEt.setTextSize(unit, size);
    }

    public void setTipEtBackground(int resourceId) {
        setTipEtBackground(getContext().getResources().getDrawable(resourceId));
    }

    public void setTipEtBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mTipEt.setBackground(drawable);
        }else {
            mTipEt.setBackgroundDrawable(drawable);
        }

    }


    /**
     * 设置分割线是否显示
     */
    public void setLineVisible(boolean visible) {
        mLineView.setVisibility(visible ? VISIBLE : GONE);
    }

    /**
     * 设置分割线的颜色
     */
    public void setLineColor(int color) {
        setLineDrawable(new ColorDrawable(color));
    }

    public void setLineDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mLineView.setBackground(drawable);
        } else {
            mLineView.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 设置分割线的大小
     */
    public void setLineSize(int size) {
        ViewGroup.LayoutParams layoutParams = mLineView.getLayoutParams();
        layoutParams.height = size;
        mLineView.setLayoutParams(layoutParams);
    }

    /**
     * 设置分割线边界
     */
    public void setLineMargin(int margin) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mLineView.getLayoutParams();
        params.leftMargin = margin;
        params.rightMargin = margin;
        mLineView.setLayoutParams(params);
    }

    /**
     * 获取分割线View对象
     */
    public View getLineView() {
        return mLineView;
    }

    /**
     * 获取TextView对象
     */
    public TextView getTipTvView() {
        return mTipTv;
    }

    public EditText getTipEt() {
        return mTipEt;
    }

    public String getTipEtText(){
        return mTipEt.getText().toString().trim();
    }
}

