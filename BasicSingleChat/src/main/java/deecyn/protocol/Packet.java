package deecyn.protocol;

/**
 * @author Deecyn
 * @version 0.1
 * Description: 通信数据包 Java 对象
 */
public abstract class Packet {

    /**  协议版本 */
    private Byte version = 1;

    /**  获取指令  */
    public abstract Byte getCommand();

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}
