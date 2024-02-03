package ${basePackage}.${entityPackage};

<#if hasDate = true>
import java.time.LocalDateTime;
</#if>
<#if hasBigDecimal = true>
import java.math.BigDecimal;
</#if>
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ${tableComment} Entity
 *
 * @author ${author}
 * @create ${date}
 */
@Data
@TableName("${tableName}")
@ApiModel
public class ${className} {

<#if columns??>
    <#list columns as column>
    /**
     * ${column.remark}
     */
    @ApiModelProperty(value = "${column.remark}", position = ${column_index+1})
        <#if column.isKey = true>
    @TableId(value = "${column.name}", type = IdType.ASSIGN_ID)
        <#elseif column.fillType = 'insert'>
    @TableField(value = "${column.name}", fill = FieldFill.INSERT)
        <#elseif column.fillType = 'update'>
    @TableField(value = "${column.name}", fill = FieldFill.UPDATE)
        <#else>
    @TableField("${column.name}")
        </#if>
        <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
        || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
        || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char')>
    private String ${column.field?uncap_first};

        </#if>
        <#if column.type = 'timestamp' || column.type = 'date' || column.type = 'datetime'||column.type = 'TIMESTAMP' || column.type = 'DATE' || column.type = 'DATETIME'>
    private LocalDateTime ${column.field?uncap_first};

        </#if>
        <#if column.type = 'int' || column.type = 'smallint'>
    private Integer ${column.field?uncap_first};

        </#if>
        <#if column.type = 'double'>
    private Double ${column.field?uncap_first};

        </#if>
        <#if column.type = 'bigint'>
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ${column.field?uncap_first};

        </#if>
        <#if column.type = 'tinyint'>
    private Byte ${column.field?uncap_first};

        </#if>
        <#if column.type = 'decimal' || column.type = 'numeric'>
    private BigDecimal ${column.field?uncap_first};
        </#if>
    </#list>
</#if>
}
