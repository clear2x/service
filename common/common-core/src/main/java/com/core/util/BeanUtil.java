package com.core.util;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author yuangy
 * @create 2020-07-15 14:37
 */
public class BeanUtil {

    /**
     * 深拷贝
     *
     * @param source 源对象
     * @param target 目标对象类型
     * @return 目标对象
     */
    public static <T extends Serializable> T deepCopy(Object source, Class<T> target) {
        T result;
        try {
            result = JsonUtil.parse(JsonUtil.toJson(source), target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 浅拷贝
     *
     * @param source 源对象
     * @param target 目标对象类型
     * @return 目标对象
     */
    public static <T> T shallowCopy(Object source, Class<T> target) {
        T result;
        try {
            result = target.newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
