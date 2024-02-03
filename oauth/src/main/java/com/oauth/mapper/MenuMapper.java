package com.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.mysql.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findUserPermissions(String username);
}
