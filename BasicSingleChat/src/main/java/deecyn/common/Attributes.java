package deecyn.common;

import deecyn.server.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author Deecyn
 * @version 0.1
 * Description:
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
