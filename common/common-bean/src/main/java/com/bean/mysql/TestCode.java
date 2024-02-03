package com.bean.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bean.mysql.base.CommonField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成测试 Entity
 *
 * @author yuangy
 * @create 2020-07-17 16:42:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_test_code")
@ApiModel
public class TestCode extends CommonField {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", position = 1)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID", position = 2)
    @TableField("tenant_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号", position = 3)
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", position = 4)
    @TableField("password")
    private String password;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", position = 5)
    @TableField("real_name")
    private String realName;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型", position = 6)
    @TableField("type")
    private Integer type;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", position = 7)
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", position = 8)
    @TableField("email")
    private String email;

    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型", position = 9)
    @TableField("certificate_type")
    private Integer certificateType;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码", position = 10)
    @TableField("certificate_no")
    private String certificateNo;

    /**
     * 银行卡类型
     */
    @ApiModelProperty(value = "银行卡类型", position = 11)
    @TableField("bank_card_type")
    private Integer bankCardType;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号", position = 12)
    @TableField("bank_card_no")
    private String bankCardNo;

    /**
     * 省份ID
     */
    @ApiModelProperty(value = "省份ID", position = 13)
    @TableField("province_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long provinceId;

    /**
     * 城市ID
     */
    @ApiModelProperty(value = "城市ID", position = 14)
    @TableField("city_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cityId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", position = 15)
    @TableField("dept_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", position = 16)
    @TableField("gender")
    private Integer gender;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", position = 17)
    @TableField("state")
    private Integer state;

}
