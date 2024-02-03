package com.job.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author yuangy
 * @create 2020-08-20 16:00
 */
public enum JobResultEnum {
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    ;

    @EnumValue
    private final int code;
    private final String message;

    JobResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
