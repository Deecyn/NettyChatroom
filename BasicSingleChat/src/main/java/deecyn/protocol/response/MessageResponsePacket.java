package deecyn.protocol.response;

import deecyn.protocol.CommandEnum;
import deecyn.protocol.Packet;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class MessageResponsePacket extends Packet {

    private String fromUserId;
    private String fromUserName;
    private String message;

    public MessageResponsePacket() {
    }

    public MessageResponsePacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return CommandEnum.MESSAGE_RESPONSE.value;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
