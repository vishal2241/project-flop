package com.ag.poker.dealer.test.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.client.BaseClientMessage;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseClientConnectionListener;
import org.anddev.andengine.extension.multiplayer.protocol.server.ClientConnector;
import org.anddev.andengine.extension.multiplayer.protocol.server.ClientMessageExtractor;
import org.anddev.andengine.extension.multiplayer.protocol.server.IClientMessageSwitch;

import android.os.Handler;

import com.ag.poker.dealer.network.DealerServer;

public class MockDealerServer extends DealerServer<ClientConnector> {

	private BaseClientConnectionListener clientConnectionListener;
	private ClientMessageExtractor clientMessageExtractor;
	private ClientMessageSwitch clientMessageSwitch;
	
	public MockDealerServer(int serverPort, BaseClientConnectionListener clientConnectionListener,
			IServerStateListener serverStateListener, Handler handler) {
		super(serverPort, clientConnectionListener, serverStateListener, handler);
		
		this.clientConnectionListener = clientConnectionListener;
		clientMessageExtractor = new MockMessageExtractor();
		clientMessageSwitch = new ClientMessageSwitch();
		
	}
	
	public void connectClient() throws UnknownHostException, IOException {
		this.mClientConnectors.add(new ClientConnector(new Socket("127.0.0.3", 4444), clientConnectionListener, clientMessageExtractor, clientMessageSwitch));
	}
	
	public void receiveMessage(BaseClientMessage message) throws IOException {
		this.mClientConnectors.get(0).getMessageSwitch().doSwitch(this.mClientConnectors.get(0), message);
	}
	
	
	private class ClientMessageSwitch implements IClientMessageSwitch {

		@Override
		public void doSwitch(ClientConnector pClientConnector, BaseClientMessage pClientMessage)
				throws IOException {
			int i = 0;
			
		}
		
	}
	
	private class MockMessageExtractor extends ClientMessageExtractor {

		/* (non-Javadoc)
		 * @see org.anddev.andengine.extension.multiplayer.protocol.server.ClientMessageExtractor#readMessage(short, java.io.DataInputStream)
		 */
		@Override
		public BaseClientMessage readMessage(short pFlag, DataInputStream pDataInputStream)
				throws IOException {
			
			int i = 0;
			
			return super.readMessage(pFlag, pDataInputStream);
		}
		
		
		
	}

}
