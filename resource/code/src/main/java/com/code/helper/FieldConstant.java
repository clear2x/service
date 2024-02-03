package com.code.helper;


import java.util.Arrays;
import java.util.List;

public interface FieldConstant {

    String DATE = "date";
    String DATETIME = "datetime";
    String TIMESTAMP = "timestamp";
    String DECIMAL = "decimal";
    String NUMERIC = "numeric";

    /*============================================ 填充类型 ==============================================*/

    String FILL_TYPE_NONE =  "none";
    String FILL_TYPE_INSERT =  "insert";
    String FILL_TYPE_UPDATE =  "update";

    /*============================================ 字段处理列表 ==========================================*/

    /**
     * entity param的json忽略字段
     */
    List<String> ENTITY_PARAM_IGNORE_LIST = Arrays.asList("isDeleted", "createBy", "createTime", "updateBy", "updateTime");

    /**
     * entity query/pageQuery的过滤字段（相对于entity不生成的字段）
     */
    List<String> ENTITY_QUERY_LIST = Arrays.asList("isDeleted", "createBy", "createTime", "updateBy", "updateTime");

    /**
     * 新增时填充字段处理
     */
    List<String> FILL_INSERT_LIST = Arrays.asList("isDeleted", "createBy", "createTime");

    /**
     * 更新时填充字段处理
     */
    List<String> FILL_UPDATE_LIST = Arrays.asList("updateBy", "updateTime");
}
