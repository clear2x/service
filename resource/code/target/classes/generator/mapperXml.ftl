<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.${mapperPackage}.${className}Mapper">

    <resultMap id="BaseResultMap" type="${basePackage}.${entityPackage}.${voName}">
        <#list originColums as column>
            <#if column.isKey = true>
        <id column="${column.name}" property="${column.field?uncap_first}"/>
            <#else>
        <result column="${column.name}" property="${column.field?uncap_first}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="BaseColumns">
        <#list originColums as column>
            <#if column_has_next=true>
        ${column.name},
            <#else>
        ${column.name}
            </#if>
        </#list>
    </sql>

    <select id="list" resultMap="BaseResultMap">
        SELECT
        <include refid="BaseColumns"/>
        FROM
        ${tableName}
        <where>
            <#list columns as column>
                <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
                || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
                || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char')>
            <if test="query.${column.field?uncap_first}!=null and query.${column.field?uncap_first}!=''">
                AND ${column.name} = <#noparse>#{</#noparse>query.${column.field?uncap_first}<#noparse>}</#noparse>
            </if>
                <#else>
            <if test="query.${column.field?uncap_first}!=null">
                AND ${column.name} = <#noparse>#{</#noparse>query.${column.field?uncap_first}<#noparse>}</#noparse>
            </if>
                </#if>
            </#list>
        </where>
    </select>

</mapper>
