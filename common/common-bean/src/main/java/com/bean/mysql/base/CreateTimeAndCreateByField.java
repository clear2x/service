package com.bean.mysql.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公共字段
 *
 * @author yuangy
 * @create 2020-07-17 15:32
 */
@Data
public class CreateTimeAndCreateByField {

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 99)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 99)
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

}
