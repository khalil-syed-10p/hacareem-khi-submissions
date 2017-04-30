package com.hacareem.finaldestination.marshaller.base;

/**
 * Created by waqas on 4/30/17.
 */
public interface Marshaller {
    <T> String from(T obj) throws Exception;

    <T> T to(String from, Class<T> clazz) throws Exception;
}
