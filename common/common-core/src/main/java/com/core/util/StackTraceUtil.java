package com.core.util;

/**
 * @author yuangy
 * @create 2020-08-10 18:22
 */
public class StackTraceUtil {

    public static String callLocation() {
        return callClass() + "." + callMethod();
    }

    public static String callClass() {
        return Thread.currentThread().getStackTrace()[4].getClassName();
    }

    public static String callMethod() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

}
