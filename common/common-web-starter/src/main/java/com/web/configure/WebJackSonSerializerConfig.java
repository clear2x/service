package com.web.configure;

import com.core.util.DateUtil;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * JackSon的日期序列化补充
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@Primary
public class WebJackSonSerializerConfig implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateUtil.DEFAULT_DATE_TIME_FORMATTER))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateUtil.DEFAULT_DATE_TIME_FORMATTER))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateUtil.DEFAULT_DATE_FORMATTER))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateUtil.DEFAULT_DATE_FORMATTER))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateUtil.DEFAULT_TIME_FORMATTER))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateUtil.DEFAULT_TIME_FORMATTER))
        ;
    }

}
