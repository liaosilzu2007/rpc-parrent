package com.lzumetal.myrpc.common.utils;

import io.protostuff.Schema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化工具类（基于protstuff实现）
 *
 * @author liaosi
 * @date 2018-08-18
 */
public class SerializationUtil {


    private static final Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();




    public static <T> byte[] serialize(Object o) {

    }
}
