package org.geekbang.projects.cs.im.protocol;

import com.alibaba.fastjson.JSON;

public class JsonSerializer implements Serializer {

    public byte getSerializerAlgorithm() {
        return SerializeAlgorithm.json;
    }

    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
