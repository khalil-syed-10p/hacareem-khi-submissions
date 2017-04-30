package com.hacareem.finaldestination.marshaller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacareem.finaldestination.marshaller.base.Marshaller;
import org.springframework.stereotype.Component;

/**
 * Created by waqas on 4/30/17.
 */
@Component
public class JSONMarshaller implements Marshaller {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> String from(final T obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Override
    public <T> T to(final String from, final Class<T> clazz) throws Exception {
        return objectMapper.readValue(from, clazz);
    }
}
