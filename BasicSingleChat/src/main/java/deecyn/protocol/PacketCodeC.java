package deecyn.protocol;

import deecyn.protocol.request.LoginRequestPacket;
import deecyn.protocol.request.MessageRequestPacket;
import deecyn.protocol.response.LoginResponsePacket;
import deecyn.protocol.response.MessageResponsePacket;
import deecyn.protocol.serialize.JSONSerializer;
import deecyn.protocol.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Deecyn
 * @version 0.1
 * Description: 编解码器
 */
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC SINGLE_INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    public PacketCodeC(){
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(CommandEnum.LOGIN_REQUEST.value, LoginRequestPacket.class);
        packetTypeMap.put(CommandEnum.LOGIN_RESPONSE.value, LoginResponsePacket.class);
        packetTypeMap.put(CommandEnum.MESSAGE_REQUEST.value, MessageRequestPacket.class);
        packetTypeMap.put(CommandEnum.MESSAGE_RESPONSE.value, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /**
     * 编码：将 Java 对象封装成二进制字节数据
     */
    public void encode(ByteBuf byteBuf, Packet packet) {

        // 序列化传进来的 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 封装编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    /**  解码：将二进制字节数据解析成 Java 对象  */
    public Packet decode(ByteBuf byteBuf) {

        // 跳过 magic-number，暂不校验魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);

        // 获取序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();
        // 获取操作指令
        byte command = byteBuf.readByte();

        // 获取实际数据长度
        int length = byteBuf.readInt();
        // 获取实际数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            // 反序列化
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
