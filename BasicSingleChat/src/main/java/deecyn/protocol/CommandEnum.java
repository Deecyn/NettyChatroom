package deecyn.protocol;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public enum CommandEnum {

    LOGIN_REQUEST(1),
    LOGIN_RESPONSE(2),
    MESSAGE_REQUEST(3),
    MESSAGE_RESPONSE(4);

    public Byte value;

    CommandEnum(byte value) {
        this.value = value;
    }

    CommandEnum(int value) {
        this((byte) value);
    }

}
