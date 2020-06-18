package com.zombies.networking;

import com.esotericsoftware.kryonet.Connection;

public interface INetworkedManager {

    void onClientConnected(Connection clientConnection);


}
