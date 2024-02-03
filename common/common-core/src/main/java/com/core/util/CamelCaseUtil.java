package com.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

/**
 * 驼峰式转换
 *
 * @author yuangy
 * @create 2020-07-09 11:00
 */
public class CamelCaseUtil {

    private static final String SEPARATOR = "_";

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0) {
            return value;
        }
        StringBuilder result = new StringBuilder();
        IntStream.range(0, arr.length).forEach(i -> {
            if (i != arr.length - 1) {
                result.append(arr[i]).append(SEPARATOR);
            } else {
                result.append(arr[i]);
            }
        });
        return StringUtils.lowerCase(result.toString());
    }

    /**
     * 下划线转小驼峰
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String underscoreToCamel(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        StringBuilder result = new StringBuilder(underscoreToCapitalizeCamel(value));
        if (result.length() > 0) {
            result.replace(0, 1, String.valueOf(result.charAt(0)).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 下划线转大驼峰
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String underscoreToCapitalizeCamel(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        StringBuilder result = new StringBuilder();
        String[] arr = value.split(SEPARATOR);
        for (String s : arr) {
            result.append((String.valueOf(s.charAt(0))).toUpperCase()).append(s.substring(1));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(underscoreToCamel("zhang_san"));
        System.out.println(underscoreToCapitalizeCamel("zhang_san"));
        System.out.println(camelToUnderscore("z_s"));
        System.out.println(camelToUnderscore("zS"));
        System.out.println(camelToUnderscore("ZaSa"));
    }

}
