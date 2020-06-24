package com.zombies.networking;

public interface INetworkedManager {
    void cmdOnEventReceived(Object event);

    void rpcOnEventReceived(Object event);
}
