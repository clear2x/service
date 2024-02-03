package com.bean.mysql;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 代码生成配置表
 */
@Data
@TableName("code_generator_config")
@ApiModel
public class GeneratorConfig {

    public static final Integer TRIM_YES = 1;
    public static final Integer TRIM_NO = 0;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键ID", position = 1)
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 数据库连接名称
     */
    @ApiModelProperty(value = "数据库连接名称", position = 2)
    @TableField("database_poll_name")
    private String databasePollName;

    /**
     * 数据库URL
     */
    @ApiModelProperty(value = "数据库URL", position = 3)
    @TableField("database_url")
    @Size(max = 128, message = "{noMoreThan}")
    private String databaseUrl;

    /**
     * 数据库用户名
     */
    @ApiModelProperty(value = "数据库用户名", position = 4)
    @TableField("database_username")
    @Size(max = 20, message = "{noMoreThan}")
    private String databaseUsername;

    /**
     * 数据库密码
     */
    @ApiModelProperty(value = "数据库密码", position = 5)
    @TableField("database_password")
    @Size(max = 20, message = "{noMoreThan}")
    @JsonIgnore
    private String databasePassword;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者", position = 6)
    @TableField("author")
    @Size(max = 20, message = "{noMoreThan}")
    private String author;

    /**
     * 基础包名
     */
    @ApiModelProperty(value = "基础包名", position = 7)
    @TableField("base_package")
    @Size(max = 50, message = "{noMoreThan}")
    private String basePackage;

    /**
     * entity文件存放路径
     */
    @ApiModelProperty(value = "entity文件存放路径", position = 8)
    @TableField("entity_package")
    @Size(max = 20, message = "{noMoreThan}")
    private String entityPackage;

    /**
     * mapper文件存放路径
     */
    @ApiModelProperty(value = "mapper文件存放路径", position = 9)
    @TableField("mapper_package")
    @Size(max = 20, message = "{noMoreThan}")
    private String mapperPackage;

    /**
     * mapper xml文件存放路径
     */
    @ApiModelProperty(value = "mapper xml文件存放路径", position = 10)
    @TableField("mapper_xml_package")
    @Size(max = 20, message = "{noMoreThan}")
    private String mapperXmlPackage;

    /**
     * servcie文件存放路径
     */
    @ApiModelProperty(value = "servcie文件存放路径", position = 11)
    @TableField("service_package")
    private String servicePackage;

    /**
     * serviceImpl文件存放路径
     */
    @ApiModelProperty(value = "serviceImpl文件存放路径", position = 12)
    @TableField("service_impl_package")
    @Size(max = 20, message = "{noMoreThan}")
    private String serviceImplPackage;

    /**
     * controller文件存放路径
     */
    @ApiModelProperty(value = "controller文件存放路径", position = 13)
    @TableField("controller_package")
    @Size(max = 20, message = "{noMoreThan}")
    private String controllerPackage;

    /**
     * 是否去除前缀
     */
    @ApiModelProperty(value = "是否去除前缀", position = 14)
    @TableField("is_trim")
    private Integer isTrim;

    /**
     * 前缀内容
     */
    @ApiModelProperty(value = "前缀内容", position = 15)
    @TableField("trim_value")
    private String trimValue;

    /**
     * java文件路径，固定值
     */
    private transient String javaPath = "/src/main/java/";
    /**
     * 配置文件存放路径，固定值
     */
    private transient String resourcesPath = "src/main/resources";
    /**
     * 文件生成日期
     */
    private transient String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    /**
     * 表名
     */
    private transient String tableName;
    /**
     * 表注释
     */
    private transient String tableComment;
    /**
     * 数据表对应的类名
     */
    private transient String className;
    /**
     * Query类
     */
    private transient String queryName;
    /**
     * PageQuery类
     */
    private transient String pageQueryName;
    /**
     * Param类
     */
    private transient String paramName;
    /**
     * vo类
     */
    private transient String voName;

}
