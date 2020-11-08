package deecyn.protocol.request;

import deecyn.protocol.CommandEnum;
import deecyn.protocol.Packet;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    public MessageRequestPacket() {
    }

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return CommandEnum.MESSAGE_REQUEST.value;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
