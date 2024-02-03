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
public class UpdateTimeField {

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", position = 99)
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}