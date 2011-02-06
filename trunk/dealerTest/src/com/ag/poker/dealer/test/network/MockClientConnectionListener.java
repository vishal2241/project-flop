package com.ag.poker.dealer.test.network;

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.client.BaseClientMessage;
import org.anddev.andengine.extension.multiplayer.protocol.shared.BaseConnector;

import android.os.Handler;

import com.ag.poker.dealer.network.ClientConnectionListener;

public class MockClientConnectionListener extends ClientConnectionListener {

	public MockClientConnectionListener(Handler handler) {
		super(handler);
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.ClientConnectionListener#onConnectInner(org.anddev.andengine.extension.multiplayer.protocol.shared.BaseConnector)
	 */
	@Override
	protected void onConnectInner(BaseConnector<BaseClientMessage> pConnector) {
		super.onConnectInner(pConnector);
		
		int i = 0;
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.ClientConnectionListener#onDisconnectInner(org.anddev.andengine.extension.multiplayer.protocol.shared.BaseConnector)
	 */
	@Override
	protected void onDisconnectInner(BaseConnector<BaseClientMessage> pConnector) {
		super.onDisconnectInner(pConnector);
		
		int i = 0;
	}
	
	
	

}
