package com.primecloud.huafenghuang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用正则表达式进行表单验证
 */
public class RegexValidateUtil {
    static boolean flag = false;
    static String regex = "";

    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证非空
     *
     * @param notEmputy
     * @return
     */
    public static boolean checkNotEmputy(String notEmputy) {
        regex = "^\\s*$";
        return check(notEmputy, regex) ? false : true;
    }

    /**
     * 验证手机号码
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^(1[0-9])\\d{9}$";
        return check(cellphone, regex);
    }

    /**
     * 身份证验证
     */
    public static boolean checkIDCardNum(String idCardNum) {
        String regex = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[X])$)$";
        return check(idCardNum, regex);
    }

    //正则表达式:验证密码(特殊字符_)
    public static boolean checkPassWord(String password) {
        String regex = "^[0-9a-zA-Z_]{6,12}$";
        return check(password, regex);
    }
}