package com.security.converter;

import com.core.util.JsonUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yuangy
 * @create 2020-08-05 9:43
 */
public class ListGrantedAuthorityJsonConverter implements Converter<Object, List<GrantedAuthority>> {

    @Override
    public List<GrantedAuthority> convert(Object value) {
        GrantedAuthorityBO[] array = JsonUtil.parse(JsonUtil.toJson(value), GrantedAuthorityBO[].class);
        return Arrays.stream(array).map(GrantedAuthorityBO::toGrantedAuthority).collect(Collectors.toList());
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructArrayType(Map.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructArrayType(GrantedAuthority.class);
    }

    @Data
    private static class GrantedAuthorityBO {
        String authority;

        public GrantedAuthority toGrantedAuthority() {
            return new SimpleGrantedAuthority(authority);
        }
    }
}
