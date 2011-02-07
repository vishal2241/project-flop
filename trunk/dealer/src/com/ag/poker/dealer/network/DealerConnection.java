package com.ag.poker.dealer.network;

import org.anddev.andengine.extension.multiplayer.protocol.server.BaseServer;
import org.anddev.andengine.extension.multiplayer.protocol.server.ClientConnector;

import android.os.Handler;

import com.ag.poker.dealer.exceptions.InitServerException;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

public class DealerConnection implements DealerConnectionConstants {



	private static final int SERVER_PORT = 4444;

	private BaseServer<ClientConnector> server;
	private ClientConnectionListener clientConnectionListener;
	private ServerStateListener serverStateListener;

	private Handler handler;

	public DealerConnection(Handler handler) {
		this.handler = handler;
		this.clientConnectionListener = new ClientConnectionListener(handler);
		this.serverStateListener = new ServerStateListener(handler);
	}

	public void initServer() throws InitServerException {
		if(this.handler == null) {
			throw new InitServerException("Handler was null");
		}
		this.server = new DealerServer<ClientConnector>(SERVER_PORT, this.clientConnectionListener, this.serverStateListener, this.handler);

		this.server.start();
	}
	
	public void terminateServer() {
		if(this.server != null) {
			this.server.stop();
			handler.sendEmptyMessage(SERVER_TERMINATED);
			this.server = null;
		}
	}

	/**
	 * @return the server
	 */
	public BaseServer<ClientConnector> getServer() {
		return server;
	}

	/**
	 * @return the handler
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * @return the clientConnectionListener
	 */
	public ClientConnectionListener getClientConnectionListener() {
		return clientConnectionListener;
	}

	/**
	 * @param clientConnectionListener the clientConnectionListener to set
	 */
	public void setClientConnectionListener(ClientConnectionListener clientConnectionListener) {
		this.clientConnectionListener = clientConnectionListener;
	}

	/**
	 * @return the serverStateListener
	 */
	public ServerStateListener getServerStateListener() {
		return serverStateListener;
	}

	/**
	 * @param serverStateListener the serverStateListener to set
	 */
	public void setServerStateListener(ServerStateListener serverStateListener) {
		this.serverStateListener = serverStateListener;
	}

	/**
	 * @return the serverPort
	 */
	public static int getServerPort() {
		return SERVER_PORT;
	}
	
	



//	private class ServerStateListener implements IServerStateListener {
//		@Override
//		public void onException(Throwable pThrowable) {
//			Message msg = DealerConnection.this.handler.obtainMessage(SERVER_EXCEPTION);
//			msg.obj = "SERVER: Exception: " + pThrowable;
//			DealerConnection.this.handler.sendMessage(msg);
//			Log.i(Constants.TAG, msg.obj.toString());
//		}
//
//		@Override
//		public void onStarted(int pPort) {
//			Message msg = DealerConnection.this.handler.obtainMessage(SERVER_STARTED);
//			msg.obj = "SERVER: started at port: " + pPort;
//			DealerConnection.this.handler.sendMessage(msg);
//			Log.i(Constants.TAG, msg.obj.toString());
//		}
//
//		@Override
//		public void onTerminated(int pPort) {
//			Message msg = DealerConnection.this.handler.obtainMessage(SERVER_TERMINATED);
//			msg.obj = "SERVER: terminated at port: " + pPort;
//			DealerConnection.this.handler.sendMessage(msg);
//			Log.i(Constants.TAG, msg.obj.toString());
//		}
//
//	}

//	private class ClientConnectionListener extends BaseClientConnectionListener {
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @seeorg.anddev.andengine.extension.multiplayer.protocol.shared.
//		 * BaseConnectionListener
//		 * #onConnectInner(org.anddev.andengine.extension.multiplayer
//		 * .protocol.shared.BaseConnector)
//		 */
//		@Override
//		protected void onConnectInner(final BaseConnector<BaseClientMessage> pConnector) {
//			Message msg = DealerConnection.this.handler.obtainMessage(SERVER_RESPONSE);
//			msg.obj = "SERVER: Client connected: "
//					+ pConnector.getSocket().getInetAddress().getHostAddress();
//			DealerConnection.this.handler.sendMessage(msg);
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @seeorg.anddev.andengine.extension.multiplayer.protocol.shared.
//		 * BaseConnectionListener
//		 * #onDisconnectInner(org.anddev.andengine.extension
//		 * .multiplayer.protocol.shared.BaseConnector)
//		 */
//		@Override
//		protected void onDisconnectInner(BaseConnector<BaseClientMessage> pConnector) {
//			Message msg = DealerConnection.this.handler.obtainMessage(CLIENT_DISCONNECTED);
//			msg.obj = pConnector.getSocket().getInetAddress().getHostAddress();
//			DealerConnection.this.handler.sendMessage(msg);
//		}
//
//	}
}
