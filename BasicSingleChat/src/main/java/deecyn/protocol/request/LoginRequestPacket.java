package deecyn.protocol.request;

import deecyn.protocol.CommandEnum;
import deecyn.protocol.Packet;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public class LoginRequestPacket extends Packet {

    private String userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {

        return CommandEnum.LOGIN_REQUEST.value;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
