package com.ag.poker.dealer.test.network;

import junit.framework.TestCase;
import android.os.Handler;

import com.ag.poker.dealer.exceptions.InitServerException;
import com.ag.poker.dealer.network.ClientConnectionListener;
import com.ag.poker.dealer.network.DealerConnection;
import com.ag.poker.dealer.network.ServerStateListener;

public class DealerConnectionTest extends TestCase {

	
	private DealerConnection dealerConnection;
	private Handler handler;
	private ClientConnectionListener clientConnectionListener;
	private ServerStateListener serverStateListener;
	
	protected void setUp() throws Exception {
		super.setUp();

		handler = new Handler();
		
		dealerConnection = new DealerConnection(handler);
		
		clientConnectionListener = new ClientConnectionListener(handler);
		serverStateListener = new ServerStateListener(handler);
		
		dealerConnection.setClientConnectionListener(clientConnectionListener);
		dealerConnection.setServerStateListener(serverStateListener);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testThatTheServerIsInitiatedCorrectly() {
		try {
			dealerConnection.initServer();
		} catch (InitServerException e) {
			fail("Couldn't initiate server");
		}
		
		assertNotNull(dealerConnection.getServer());
		assertSame(dealerConnection.getClientConnectionListener(), clientConnectionListener);
		assertSame(dealerConnection.getServerStateListener(), serverStateListener);
		assertTrue(dealerConnection.getServer().isAlive());
		
	}
	
	public void testThatServerCantInitiateWhenHandlerIsNull() {
		dealerConnection = new DealerConnection(null);
		
		try {
			dealerConnection.initServer();
			fail("Server shouldn't be able to initate when Handler is null, but should instead throw an exception");
		} catch (InitServerException e) {
			//do nothing
		}
	}

}
