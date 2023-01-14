package org.geekbang.projects.cs.im.protocol;

public interface Serializer {


    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * 将对象序列化成二进制
     *
     */
    byte[] serialize(Object object);

    /**
     * 将二进制反序列化为对象
     */
    <T> T deSerialize(Class<T> clazz, byte[] bytes);
}
