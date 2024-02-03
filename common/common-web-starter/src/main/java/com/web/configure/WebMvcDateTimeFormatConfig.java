package com.web.configure;

import com.core.util.DateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 对不是@RequestBody的时间处理
 *
 * @author yuangy
 * @create 2020-07-20 14:27
 */
@Configuration
@SuppressWarnings("all")
public class WebMvcDateTimeFormatConfig {

    /**
     * 日期参数接收转换器，将json字符串转为日期类型
     *
     * @return MVC LocalDateTime 参数接收转换器
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConvert() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateUtil.DEFAULT_DATE_TIME_FORMATTER);
            }
        };
    }

    /**
     * 日期参数接收转换器，将json字符串转为日期类型
     *
     * @return MVC LocalDate 参数接收转换器
     */
    @Bean
    public Converter<String, LocalDate> localDateConvert() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateUtil.DEFAULT_DATE_FORMATTER);
            }
        };
    }

    /**
     * 日期参数接收转换器，将json字符串转为日期类型
     *
     * @return MVC LocalTime 参数接收转换器
     */
    @Bean
    public Converter<String, LocalTime> localTimeConvert() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateUtil.DEFAULT_TIME_FORMATTER);
            }
        };
    }

}
