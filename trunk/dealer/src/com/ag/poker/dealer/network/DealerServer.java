/**   Copyright 2011 Arild Greni

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.ag.poker.dealer.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.client.BaseClientMessage;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseClientConnectionListener;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseClientMessageSwitch;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseServer;
import org.anddev.andengine.extension.multiplayer.protocol.server.ClientConnector;
import org.anddev.andengine.extension.multiplayer.protocol.server.ClientMessageExtractor;

import android.os.Handler;
import android.util.Log;

import com.ag.poker.dealer.network.msg.PlayerDataClientMessage;
import com.ag.poker.dealer.utils.Constants;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

/**
 * @author Arild
 *
 */
public class DealerServer<CC extends ClientConnector> extends BaseServer<ClientConnector> implements DealerConnectionConstants {

	
	private Handler handler;
	
	/**
	 * 
	 */
	public DealerServer(int serverPort, BaseClientConnectionListener clientConnectionListener, IServerStateListener serverStateListener, Handler handler) {
		super(serverPort, clientConnectionListener, serverStateListener);
		this.handler = handler;
	}

	@Override
	protected ClientConnector newClientConnector(final Socket pClientSocket,
			final BaseClientConnectionListener pClientConnectionListener) throws Exception {
		return new ClientConnector(pClientSocket, pClientConnectionListener,
				new ClientMessageExtractor() {
					@Override
					public BaseClientMessage readMessage(final short pFlag,
							final DataInputStream pDataInputStream) throws IOException {
						switch (pFlag) {
						case FLAG_MESSAGE_CLIENT_REGISTERING:
							return new PlayerDataClientMessage(pDataInputStream);
						default:
							return super.readMessage(pFlag, pDataInputStream);
						}
					}
				}, new BaseClientMessageSwitch() {
					@Override
					public void doSwitch(final ClientConnector pClientConnector,
							final BaseClientMessage pClientMessage) throws IOException {
						super.doSwitch(pClientConnector, pClientMessage);
						switch (pClientMessage.getFlag()) {

						case FLAG_MESSAGE_CLIENT_REGISTERING:
							final PlayerDataClientMessage playerDataClientMessage = (PlayerDataClientMessage)pClientMessage;
							if(playerDataClientMessage != null && playerDataClientMessage.getPlayer() != null) {
								DealerServer.this.handler.sendMessage(
										DealerServer.this.handler.obtainMessage(PLAYER_DATA_CLIENT_MESSAGE, playerDataClientMessage.getPlayer())
								);
							}
						default:
							Log.i(Constants.TAG, "SERVER: ClientMessage received: "
									+ pClientMessage.toString());
							break;
						}
					}
				});
	}

}
