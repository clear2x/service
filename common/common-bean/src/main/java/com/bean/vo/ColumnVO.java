package com.bean.vo;

import lombok.Data;

import java.util.Objects;

/**
 * 列
 */
@Data
public class ColumnVO {
    /**
     * 名称
     */
    private String name;
    /**
     * 是否为主键
     */
    private Boolean isKey;
    /**
     * 类型
     */
    private String type;
    /**
     * 注释
     */
    private String remark;
    /**
     * 属性名称
     */
    private String field;
    /**
     * 填充类型
     */
    private String fillType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnVO columnVO = (ColumnVO) o;
        return Objects.equals(name, columnVO.name) &&
                Objects.equals(isKey, columnVO.isKey) &&
                Objects.equals(type, columnVO.type) &&
                Objects.equals(field, columnVO.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isKey, type, field);
    }

}
