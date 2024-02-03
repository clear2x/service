package com.core.util;

import java.math.BigDecimal;

/**
 * 金额分-元互转
 */
public class MoneyUtil {

    /**
     * 元转分
     *
     * @param yuan 元
     * @return 分
     */
    public static Integer Yuan2Fen(BigDecimal yuan) {
        return yuan.movePointRight(2).intValue();
    }

    /**
     * 分转元
     *
     * @param fen 分
     * @return 元
     */
    public static BigDecimal Fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2);
    }
}
