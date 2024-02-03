package com.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.mysql.SystemUser;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<SystemUser> {
    SystemUser find(@Param("username") String username,@Param("phone") String phone);
}
