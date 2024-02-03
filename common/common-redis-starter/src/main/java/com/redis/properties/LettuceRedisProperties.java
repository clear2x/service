package com.redis.properties;

import com.core.constant.ServiceConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 是否开启Lettuce Redis
 *
 * @author yuangy
 * @create 2020/6/30 10:27
 */
@ConfigurationProperties(prefix = ServiceConstant.PROPERTIES_PREFIX + "lettuce.redis")
public class LettuceRedisProperties {

    /**
     * 是否开启Lettuce Redis
     */
    private Boolean enable = Boolean.TRUE;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "LettuceRedisProperties{" +
                "enable=" + enable +
                '}';
    }
}
