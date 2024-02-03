package com.code.provider;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.core.util.safety.AESUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * @author yuangy
 * @create 2020-07-14 11:58
 */
public class MysqlDataSourceProvider extends AbstractJdbcDataSourceProvider {

    public static final String ivs = "86B027B9B3E049CA";
    public static final String key = "5BA057D9B13847C8";

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // mysql数据库的驱动类
    @SuppressWarnings("all")
    private static final String sql = "select database_poll_name,database_url,database_username,database_password from code_generator_config";

    private final Map<String, DataSourceProperty> datasourceMap;

    public MysqlDataSourceProvider(Map<String, DataSourceProperty> datasource, String driverClassName, String url, String username, String password) {
        super(driverClassName, url, username, password);
        this.datasourceMap = datasource;
    }


    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {

        ResultSet resultSet = statement.executeQuery(sql);

        /*
        获取信息
         */
        while (resultSet.next()) {
            String databasePollName = resultSet.getString("database_poll_name");
            String databaseUrl = resultSet.getString("database_url");
            String databaseUsername = resultSet.getString("database_username");
            String databasePassword = resultSet.getString("database_password");

            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            dataSourceProperty.setPollName(databasePollName);
            dataSourceProperty.setDriverClassName(DRIVER);
            dataSourceProperty.setUrl(databaseUrl);
            dataSourceProperty.setUsername(databaseUsername);
            dataSourceProperty.setPassword(AESUtil.decrypt(databasePassword, key, ivs));

            datasourceMap.put(databasePollName, dataSourceProperty);
        }

        return datasourceMap;
    }

    public static void main(String[] args) {
        String password = "Mysql@314159...";
        System.out.println(password);
        String encrypt = AESUtil.encrypt(password, key, ivs);
        System.out.println(encrypt);
        System.out.println(AESUtil.decrypt(encrypt, key, ivs));
    }
}
