package ${basePackage}.${entityPackage};

<#if hasDate = true>
import java.time.LocalDateTime;
</#if>
<#if hasBigDecimal = true>
import java.math.BigDecimal;
</#if>
import com.core.entity.param.base.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ${tableComment} EntityPageQuery
 *
 * @author ${author}
 * @create ${date}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel
public class ${pageQueryName} extends BasePageQuery{

<#if columns??>
    <#list columns as column>
    /**
     * ${column.remark}
     */
    @ApiModelProperty(value = "${column.remark}", position = ${column_index+1})
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
