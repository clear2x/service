package com.datasource.configure;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.core.support.AuthUserInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuangy
 * @create 2020-07-10 10:06
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@ConditionalOnMissingBean(DataSourceAutoConfigure.class)
public class DataSourceAutoConfigure {

    private AuthUserInformation authUserInformation;

    @Autowired(required = false)
    public void setAuthUserInformation(AuthUserInformation authUserInformation) {
        this.authUserInformation = authUserInformation;
    }

    /**
     * 分页插件
     *
     * @return 分页插件
     */
    @Bean
    @ConditionalOnMissingBean(PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    @Bean
    public MybatisPlusConfigure mybatisPlusConfigure() {
        return new MybatisPlusConfigure(authUserInformation);
    }

}
