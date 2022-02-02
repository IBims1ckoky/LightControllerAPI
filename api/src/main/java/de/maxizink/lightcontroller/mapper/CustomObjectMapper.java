package de.maxizink.lightcontroller.mapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomObjectMapper {

  public static ObjectMapper create() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    objectMapper.setDefaultTyping(createCustomTypeResolver());

    return objectMapper;
  }

  private static CustomTypeResolver createCustomTypeResolver() {
    CustomTypeResolver customTypeResolver = new CustomTypeResolver();
    customTypeResolver.init(JsonTypeInfo.Id.CLASS, null);
    customTypeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
    return customTypeResolver;
  }
}