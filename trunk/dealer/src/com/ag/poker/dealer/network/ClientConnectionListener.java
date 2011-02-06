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

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.client.BaseClientMessage;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseClientConnectionListener;
import org.anddev.andengine.extension.multiplayer.protocol.shared.BaseConnector;

import android.os.Handler;
import android.os.Message;

import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

/**
 * @author Arild
 *
 */
public class ClientConnectionListener extends BaseClientConnectionListener implements DealerConnectionConstants {

	private Handler handler;
	
	/**
	 * 
	 */
	public ClientConnectionListener(Handler handler) {
		this.handler = handler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.anddev.andengine.extension.multiplayer.protocol.shared.
	 * BaseConnectionListener
	 * #onConnectInner(org.anddev.andengine.extension.multiplayer
	 * .protocol.shared.BaseConnector)
	 */
	@Override
	protected void onConnectInner(final BaseConnector<BaseClientMessage> pConnector) {
		Message msg = this.handler.obtainMessage(SERVER_RESPONSE);
		msg.obj = "SERVER: Client connected: "
				+ pConnector.getSocket().getInetAddress().getHostAddress();
		this.handler.sendMessage(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.anddev.andengine.extension.multiplayer.protocol.shared.
	 * BaseConnectionListener
	 * #onDisconnectInner(org.anddev.andengine.extension
	 * .multiplayer.protocol.shared.BaseConnector)
	 */
	@Override
	protected void onDisconnectInner(BaseConnector<BaseClientMessage> pConnector) {
		Message msg = this.handler.obtainMessage(CLIENT_DISCONNECTED);
		msg.obj = pConnector.getSocket().getInetAddress().getHostAddress();
		this.handler.sendMessage(msg);
	}

}
