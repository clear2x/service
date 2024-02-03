package com.oauth.strategy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;

import java.io.IOException;
import java.util.Date;

/**
 * DefaultExpiringOAuth2RefreshToken的反序列化
 */
public class DefaultExpiringOAuth2RefreshTokenJackson2Deserializer extends StdDeserializer<DefaultExpiringOAuth2RefreshToken> {

    protected DefaultExpiringOAuth2RefreshTokenJackson2Deserializer(Class<?> vc) {
        super(vc);
    }

    private static String readString(ObjectMapper mapper, JsonNode jsonNode) {
        return readValue(mapper, jsonNode, String.class);
    }

    private static <T> T readValue(ObjectMapper mapper, JsonNode jsonNode, Class<T> clazz) {
        if (mapper == null || jsonNode == null || clazz == null) {
            return null;
        }
        try {
            return mapper.readValue(jsonNode.traverse(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T readValue(ObjectMapper mapper, JsonNode jsonNode, TypeReference<T> type) {
        if (mapper == null || jsonNode == null || type == null) {
            return null;
        }
        try {
            return mapper.readValue(jsonNode.traverse(), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DefaultExpiringOAuth2RefreshToken deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        String value = readValue(mapper, jsonNode.get("value"), String.class);
        Date expiration = readValue(mapper, jsonNode.get("expiration"), Date.class);
        return new DefaultExpiringOAuth2RefreshToken(value, expiration);
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

}
