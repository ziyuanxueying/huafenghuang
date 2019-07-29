package com.primecloud.library.baselibrary.rx.util;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator on 2017/4/12.
 */

public class TUtils {

    /**
     * 获得超类的泛型参数的实际类型。。
     *
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int i) {
        try {
            /**
             * getClass().getGenericSuperclass()返回表示此 Class 所表示的实体（
             * 类、接口、基本类型或 void）的直接超类的 Type
             然后将其转换ParameterizedType。。
             */
            ParameterizedType p = ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass()));
            /**
             * getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组。
             [0]就是这个数组中第一个了。。
             */
            Class<T> mClass = (Class<T>) (p.getActualTypeArguments()[i]);
            return mClass.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassCastException e) {
        }
        return null;
    }

    // 获得类名className对应的Class对象
    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
