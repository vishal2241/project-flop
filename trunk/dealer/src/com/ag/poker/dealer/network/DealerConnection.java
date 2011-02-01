package com.ag.poker.dealer.network;

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.server.BaseServerMessage;
import org.anddev.andengine.extension.multiplayer.protocol.client.BaseServerConnectionListener;
import org.anddev.andengine.extension.multiplayer.protocol.client.ServerConnector;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseServer;
import org.anddev.andengine.extension.multiplayer.protocol.server.ClientConnector;
import org.anddev.andengine.extension.multiplayer.protocol.shared.BaseConnector;

import android.os.Handler;

public class DealerConnection {
	
	private BaseServer<ClientConnector> mServer;
	private ServerConnector mServerConnector;
	private String mServerIP = "127.0.0.1";
	
	private Handler mHandler;
	
	public DealerConnection(Handler handler) {
		this.mHandler = handler;
	}
	
	private void initServerAndClient() {
		initServer();
		
	}

	private void initServer() {
		
	}
	
	private class ServerConnectionListener extends BaseServerConnectionListener {
		@Override
		protected void onConnectInner(
				BaseConnector<BaseServerMessage> pConnector) {
		}

		@Override
		protected void onDisconnectInner(
				BaseConnector<BaseServerMessage> pConnector) {
			// TODO Auto-generated method stub
			
		}
	}

}
