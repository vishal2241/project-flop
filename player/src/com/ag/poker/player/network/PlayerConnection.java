package com.ag.poker.player.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.server.BaseServerMessage;
import org.anddev.andengine.extension.multiplayer.protocol.adt.message.server.connection.ConnectionAcceptedServerMessage;
import org.anddev.andengine.extension.multiplayer.protocol.adt.message.server.connection.ConnectionRefusedServerMessage;
import org.anddev.andengine.extension.multiplayer.protocol.client.BaseServerConnectionListener;
import org.anddev.andengine.extension.multiplayer.protocol.client.BaseServerMessageSwitch;
import org.anddev.andengine.extension.multiplayer.protocol.client.ServerConnector;
import org.anddev.andengine.extension.multiplayer.protocol.client.ServerMessageExtractor;
import org.anddev.andengine.extension.multiplayer.protocol.shared.BaseConnector;

import com.ag.poker.player.utils.Constants;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class PlayerConnection {

	public static final int SERVER_RESPONSE = 0;
	
	private static final int SERVER_PORT = 4444;

	protected static final short FLAG_MESSAGE_SERVER_DEAL_CARDS = 1;

	private Handler mHandler;

	private ServerConnector mServerConnector;

	private String mServerIp = "127.0.0.1";

	protected Object mEngine;

	public PlayerConnection(Handler handler) {
		this.mHandler = handler;
		initClient();
	}

	private void initClient() {
		try {
			this.mServerConnector = new ServerConnector(new Socket(this.mServerIp, SERVER_PORT),
					new ServerConnectionListener(), new ServerMessageExtractor() {
						@Override
						public BaseServerMessage readMessage(final short pFlag,
								final DataInputStream pDataInputStream) throws IOException {
							switch (pFlag) {
							case FLAG_MESSAGE_SERVER_DEAL_CARDS:
								return new DealCardsServerMessage(pDataInputStream);
							default:
								return super.readMessage(pFlag, pDataInputStream);
							}
						}
					}, new BaseServerMessageSwitch() {

						@Override
						public void doSwitch(final ServerConnector pServerConnector,
								final BaseServerMessage pServerMessage) throws IOException {
							switch (pServerMessage.getFlag()) {
							case FLAG_MESSAGE_SERVER_DEAL_CARDS:
								final DealCardsServerMessage dealCardsServerMessage = (DealCardsServerMessage)pServerMessage;
								PlayerConnection.this.mHandler.sendMessage(PlayerConnection.this.mHandler.obtainMessage(SERVER_RESPONSE, dealCardsServerMessage.mCard1, dealCardsServerMessage.mCard2));
								break;
							default:
								super.doSwitch(pServerConnector, pServerMessage);
								Log.i(Constants.TAG, "Client: ServerMessage received: " + pServerMessage.toString());
								break;
							}
							super.doSwitch(pServerConnector, pServerMessage);
						}

						@Override
						protected void onHandleConnectionRefusedServerMessage(
								ServerConnector pServerConnector,
								ConnectionRefusedServerMessage pServerMessage) {
							Message msg = PlayerConnection.this.mHandler.obtainMessage(SERVER_RESPONSE);
							msg.obj = "CLIENT: Connection refused";
							PlayerConnection.this.mHandler.sendMessage(msg);
							Log.e(Constants.TAG, msg.obj.toString());
						}

						@Override
						protected void onHandleConnectionAcceptedServerMessage(
								ServerConnector pServerConnector,
								ConnectionAcceptedServerMessage pServerMessage) {
							Message msg = PlayerConnection.this.mHandler.obtainMessage(SERVER_RESPONSE);
							msg.obj = "CLIENT: Connection accepted";
							PlayerConnection.this.mHandler.sendMessage(msg);
							Log.e(Constants.TAG, msg.obj.toString());

						}
					});
			
			this.mServerConnector.start();

		} catch (final Throwable t) {
			Log.d(Constants.TAG, "Error while initiating client", t);
		}
	}

	private class ServerConnectionListener extends BaseServerConnectionListener {

		@Override
		protected void onConnectInner(BaseConnector<BaseServerMessage> pConnector) {
			// TODO Auto-generated method stub

		}

		@Override
		protected void onDisconnectInner(BaseConnector<BaseServerMessage> pConnector) {
			// TODO Auto-generated method stub

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
