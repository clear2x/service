package com.core.util;

import org.springframework.util.AntPathMatcher;

import java.util.Set;

/**
 * @author yuangy
 * @create 2020-07-08 9:59
 */
public class HttpRequestPathUtil {

    /**
     * 匹配URI规则
     *
     * @param targetUri targetUri
     * @param patterns  patterns
     * @return 是否匹配
     */
    public static boolean matchPath(String targetUri, Set<String> patterns) {
        AntPathMatcher matcher = new AntPathMatcher();
        for (String pattern : patterns) {
            if (matcher.match(pattern, targetUri)) {
                return true;
            }
        }
        return false;
    }

}
