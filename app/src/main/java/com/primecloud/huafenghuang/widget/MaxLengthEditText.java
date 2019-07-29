package com.primecloud.huafenghuang.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by ${qc} on 2019/1/21.
 */

public class MaxLengthEditText extends EditText {
    Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");

    public MaxLengthEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLength(attrs, context);
    }

    public MaxLengthEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLength(attrs, context);
    }

    private void initLength(AttributeSet a, Context context) {
        //命名空间（别告诉我不熟悉）
        String namespace = "http://schemas.android.com/apk/res/android";
        // 获取属性中设置的最大长度
        int maxLength = a.getAttributeIntValue(namespace, "maxLength", -1);
        // 如果设置了最大长度，给出相应的处理
        if (maxLength > -1) {
            setFilters(new InputFilter[]{new MyLengthFilter(maxLength, context)});
        }
    }

    /**
     * 从源码中复制出来的
     * 来源：InputFilter.LengthFilter
     * <p>
     * <p>
     * 这里只是添加了一句话：
     * Toast.makeText(context, "字数不能超过" + mMax, Toast.LENGTH_SHORT).show();
     * <p>
     * This filter will constrain edits not to make the length of the text
     * greater than the specified length.
     */
    class MyLengthFilter implements InputFilter {
        private final int mMax;
        private Context context;

        public MyLengthFilter(int max, Context context) {
            mMax = max;
            this.context = context;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int keep = mMax - (dest.length() - (dend - dstart));
            Matcher matcher = pattern.matcher(source);
            if (keep <= 0) {
                //这里，用来给用户提示
                Toast.makeText(context, "字数不能超过" + mMax, Toast.LENGTH_SHORT).show();
                return "";
            } else if (keep >= end - start) {
                return null;//keep original
            } else {
                if (!matcher.find()) {
                    return null;
                }
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }

        /**
         * @return the maximum length enforced by this input filter
         */
        public int getMax() {
            return mMax;
        }
    }

}
