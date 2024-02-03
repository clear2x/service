package com.core.util;

import org.apache.commons.lang3.RegExUtils;

import java.util.Optional;

/**
 * @author yuangy
 * @create 2020-07-16 16:40
 */
public class ThrowableUtil {

    public static String getSimpleMessage(Throwable e) {
        if (e == null) {
            return "";
        }
        String message = Optional.ofNullable(e.getMessage()).orElse("");
        if (e.getCause() != null) {
            message = e.getCause().getMessage();
        }
        return RegExUtils.replaceFirst(message, "\n at.*", "").replaceAll("\"", "'");
    }

}
