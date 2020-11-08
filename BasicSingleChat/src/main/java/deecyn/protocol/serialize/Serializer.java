package deecyn.protocol.serialize;

/**
 * @author Deecyn
 * @version 0.1
 * Description: Java 对象的序列化规则
 */
public interface Serializer {

    /**  获取序列化算法标识  */
    byte getSerializerAlgorithm();

    /** 序列化，Java 对象转换成二进制字节数组   */
    byte[] serialize(Object object);

    /**  反序列化，二进制数据转换成 Java 对象  */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**  设置默认的序列化算法，为 JSON 序列化  */
    Serializer DEFAULT = new JSONSerializer();
}
