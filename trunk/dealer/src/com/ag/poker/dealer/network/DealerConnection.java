package com.ag.poker.dealer.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.client.BaseClientMessage;
import org.anddev.andengine.extension.multiplayer.protocol.adt.message.server.BaseServerMessage;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseClientConnectionListener;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseClientMessageSwitch;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseServer;
import org.anddev.andengine.extension.multiplayer.protocol.server.ClientConnector;
import org.anddev.andengine.extension.multiplayer.protocol.server.ClientMessageExtractor;
import org.anddev.andengine.extension.multiplayer.protocol.server.BaseServer.IServerStateListener;
import org.anddev.andengine.extension.multiplayer.protocol.shared.BaseConnector;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ag.poker.dealer.utils.Constants;

public class DealerConnection {

	public static final int SERVER_RESPONSE = 1;
	public static final int CLIENT_CONNECTED = 2;
	
	protected static final short FLAG_MESSAGE_SERVER_DEAL_CARDS = 1;

	private static final int SERVER_PORT = 4444;

	private BaseServer<ClientConnector> mServer;

	private Handler mHandler;

	public DealerConnection(Handler handler) {
		this.mHandler = handler;
		initServer();
	}

	private void initServer() {
		this.mServer = new BaseServer<ClientConnector>(SERVER_PORT, new ClientConnectionListener(),
				new ServerStateListener()) {
			@Override
			protected ClientConnector newClientConnector(final Socket pClientSocket,
					final BaseClientConnectionListener pClientConnectionListener) throws Exception {
				return new ClientConnector(pClientSocket, pClientConnectionListener,
						new ClientMessageExtractor() {
							@Override
							public BaseClientMessage readMessage(final short pFlag,
									final DataInputStream pDataInputStream) throws IOException {
								return super.readMessage(pFlag, pDataInputStream);
							}
						}, new BaseClientMessageSwitch() {
							@Override
							public void doSwitch(final ClientConnector pClientConnector,
									final BaseClientMessage pClientMessage) throws IOException {
								super.doSwitch(pClientConnector, pClientMessage);
								Log.i(Constants.TAG, "SERVER: ClientMessage received: "
										+ pClientMessage.toString());
								try {
									DealerConnection.this.mServer.sendBroadcastServerMessage(new DealCardsServerMessage(5, 10));
								} catch (IOException e) {
									Log.e(Constants.TAG, "Error while sending server message", e);
								}
							}
						});
			}
			
		};

		this.mServer.start();
	}

	private class ServerStateListener implements IServerStateListener {
		@Override
		public void onException(Throwable pThrowable) {
			Message msg = DealerConnection.this.mHandler.obtainMessage(SERVER_RESPONSE);
			msg.obj = "SERVER: Exception: " + pThrowable;
			DealerConnection.this.mHandler.sendMessage(msg);
			Log.i(Constants.TAG, msg.obj.toString());
		}

		@Override
		public void onStarted(int pPort) {
			Message msg = DealerConnection.this.mHandler.obtainMessage(SERVER_RESPONSE);
			msg.obj = "SERVER: started at port: " + pPort;
			DealerConnection.this.mHandler.sendMessage(msg);
			Log.i(Constants.TAG, msg.obj.toString());
		}

		@Override
		public void onTerminated(int pPort) {
			Message msg = DealerConnection.this.mHandler.obtainMessage(SERVER_RESPONSE);
			msg.obj = "SERVER: terminated at port: " + pPort;
			DealerConnection.this.mHandler.sendMessage(msg);
			Log.i(Constants.TAG, msg.obj.toString());
		}

	}

	private class ClientConnectionListener extends BaseClientConnectionListener {

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
			Message msg = DealerConnection.this.mHandler.obtainMessage(SERVER_RESPONSE);
			msg.obj = "SERVER: Client connected: "
					+ pConnector.getSocket().getInetAddress().getHostAddress();
			DealerConnection.this.mHandler.sendMessage(msg);
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
			Message msg = DealerConnection.this.mHandler.obtainMessage(SERVER_RESPONSE);
			msg.obj = "SERVER: Client disconnected: "
					+ pConnector.getSocket().getInetAddress().getHostAddress();
			DealerConnection.this.mHandler.sendMessage(msg);
		}

	}
	
	private static class DealCardsServerMessage extends BaseServerMessage {

		public final int mCard1;
		public final int mCard2;
		
		public DealCardsServerMessage(final int pCard1, final int pCard2) {
			this.mCard1 = pCard1;
			this.mCard2 = pCard2;
		}
		
		public DealCardsServerMessage(final DataInputStream pDataInputStream) throws IOException {
			this.mCard1 = pDataInputStream.readInt();
			this.mCard2 = pDataInputStream.readInt();
		}
		
		@Override
		public short getFlag() {
			return FLAG_MESSAGE_SERVER_DEAL_CARDS;
		}

		@Override
		protected void onAppendTransmissionDataForToString(StringBuilder pStringBuilder) {
			
		}

		@Override
		protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)
				throws IOException {
			pDataOutputStream.writeInt(this.mCard1);
			pDataOutputStream.writeInt(this.mCard2);
		}
		
	}
}
