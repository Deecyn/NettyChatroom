package deecyn.protocol.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {

        return SerializerAlgorithmEnum.JSON.value;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
