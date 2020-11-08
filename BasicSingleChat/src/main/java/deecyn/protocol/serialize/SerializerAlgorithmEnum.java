package deecyn.protocol.serialize;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public enum SerializerAlgorithmEnum {

    JSON(1);

    public byte value;

    SerializerAlgorithmEnum(byte value) {
        this.value = value;
    }
    SerializerAlgorithmEnum(int value) {
        this((byte) value);
    }
}
