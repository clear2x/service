package com.code.configure;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.code.provider.MysqlDataSourceProvider;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuangy
 * @create 2020-07-14 14:30
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.dynamic")
@Data
public class DataSourceConfigure {

    private String primary;
    private Map<String, DataSourceProperty> datasource = new LinkedHashMap<>();

    @Bean
    @Primary
    public MysqlDataSourceProvider mysqlDataSourceProvider() {
        DataSourceProperty dataSourceProperty = this.datasource.get(primary);
        return new MysqlDataSourceProvider(datasource, dataSourceProperty.getDriverClassName(), dataSourceProperty.getUrl(), dataSourceProperty.getUsername(), dataSourceProperty.getPassword());
    }
}
