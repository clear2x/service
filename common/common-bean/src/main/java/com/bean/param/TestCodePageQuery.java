package com.bean.param;

import com.bean.param.base.BasePageQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 代码生成测试 EntityPageQuery
 *
 * @author yuangy
 * @create 2020-07-17 09:23:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel
public class TestCodePageQuery extends BasePageQuery {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", position = 1)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID", position = 2)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号", position = 3)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", position = 4)
    private String password;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", position = 5)
    private String realName;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型", position = 6)
    private Integer type;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", position = 7)
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", position = 8)
    private String email;

    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型", position = 9)
    private Integer certificateType;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码", position = 10)
    private String certificateNo;

    /**
     * 银行卡类型
     */
    @ApiModelProperty(value = "银行卡类型", position = 11)
    private Integer bankCardType;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号", position = 12)
    private String bankCardNo;

    /**
     * 省份ID
     */
    @ApiModelProperty(value = "省份ID", position = 13)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long provinceId;

    /**
     * 城市ID
     */
    @ApiModelProperty(value = "城市ID", position = 14)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cityId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", position = 15)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", position = 16)
    private Integer gender;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", position = 17)
    private Integer state;

    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除", position = 18)
    private Integer isDeleted = 0;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 19)
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 20)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人", position = 21)
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", position = 22)
    private LocalDateTime updateTime;

}
