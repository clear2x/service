package com.oauth.strategy;

import com.alibaba.fastjson.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Preconditions;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

import java.nio.charset.StandardCharsets;

public class JacksonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    protected static ObjectMapper mapper = new ObjectMapper();

    {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationJackson2Deserializer(OAuth2Authentication.class));
        module.addDeserializer(OAuth2RefreshToken.class, new DefaultExpiringOAuth2RefreshTokenJackson2Deserializer(DefaultExpiringOAuth2RefreshToken.class));
        mapper.registerModule(module);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Preconditions.checkArgument(clazz != null, "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return mapper.readValue(new String(bytes, IOUtils.UTF8), clazz);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public String deserializeString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes, IOUtils.UTF8);
    }

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }

        try {
            return mapper.writeValueAsBytes(object);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] serialize(String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }

        return data.getBytes(StandardCharsets.UTF_8);
    }
}
