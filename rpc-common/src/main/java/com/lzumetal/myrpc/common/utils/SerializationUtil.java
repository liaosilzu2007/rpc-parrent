package com.lzumetal.myrpc.common.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化工具类（基于protstuff实现）
 *
 * @author liaosi
 * @date 2018-08-18
 */
public class SerializationUtil {


    /** schema的缓存 **/
    private static final Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    /** 用于常见实例的objenesis（比用反射的效率高） **/
    private static final Objenesis objenesis = new ObjenesisStd(true);

    

    /**
     * 序列化：对象——>byte数组
     * @param obj
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtobufIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 反序列化：byte数组——>对象
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> T deserialize(byte[] data, Class<T> clazz) {
        T message = objenesis.newInstance(clazz);
        Schema<T> schema = getSchema(clazz);
        ProtobufIOUtil.mergeFrom(data, message, schema);
        return message;
    }


    private static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(clazz);
            cachedSchema.put(clazz, schema);
        }
        return schema;
    }
}
