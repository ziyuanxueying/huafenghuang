package com.primecloud.huafenghuang.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${qc} on 2018/12/20.
 */

public class StringUtils {

    /**
     * 字符串不为 null 而且不为 "" 时返回 true
     */
    public static boolean notBlank(String str) {
        return str == null || "".equals(str.trim()) || str.equals("null") ? false : true;
    }


    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /***
     * 截取字符串
     *
     * @param start
     *            从那里开始，0算起
     * @param num
     *            截取多少个
     * @param str
     *            截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    public static String getMD5(byte[] bytes) {
        String strTemp = "";
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);
            return toHexString(algorithm.digest(), "");
        } catch (Exception e) {
            strTemp = "";
        }

        return strTemp;
    }

    public static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            if (Integer.toHexString(0xFF & b).length() == 1)
                hexString.append("0").append(Integer.toHexString(0xFF & b));
            else
                hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

    /**
     * 添加输入密码的限制
     *
     * @param evPwd
     */
    public static TextWatcher setTextWatcher(final EditText evPwd) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String editable = evPwd.getText().toString();
                String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable);
                String str = m.replaceAll("").trim();    //删掉不是字母或数字的字符
                if (!editable.equals(str)) {
                    evPwd.setText(str);  //设置EditText的字符
                    evPwd.setSelection(str.length()); //因为删除了字符，要重写设置新的光标所在位置
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;
    }
}
