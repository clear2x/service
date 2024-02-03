package com.job.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 执行结果状态
 *
 * @author yuangy
 * @create 2020-08-20 15:58
 */
public enum JobStatusEnum {

    NORMAL(0, "正常"),
    PAUSED(1, "暂停"),
    COMPLETE(2, "完成"),
    BLOCKED(3, "错误"),
    ERROR(4, "阻塞"),
    NONE(-1, "不存在"),
    ;

    @EnumValue
    private final int code;
    private final String message;

    JobStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static JobStatusEnum instance(String name) {
        try {
            return JobStatusEnum.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
