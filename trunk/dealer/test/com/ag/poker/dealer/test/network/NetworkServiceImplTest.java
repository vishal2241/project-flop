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

package com.ag.poker.dealer.test.network;

import android.os.Handler;
import android.test.AndroidTestCase;

import com.ag.poker.dealer.exceptions.InitServerException;
import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.network.NetworkServiceImpl;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

/**
 * @author Arild
 *
 */
public class NetworkServiceImplTest extends AndroidTestCase implements DealerConnectionConstants {
	
	private NetworkServiceImpl networkServiceImpl;

	private Handler handler;
	/**
	 * @param name
	 */
	public NetworkServiceImplTest() {
		super();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		handler = new Handler();
		
		this.networkServiceImpl = new NetworkServiceImpl(handler);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		if(NetworkServiceImpl.getDealerConnection().getServer() != null) {
			NetworkServiceImpl.getDealerConnection().getServer().stop();
		}
	}
	
	public void testClassInitialization() {
		assertNotNull(NetworkServiceImpl.getDealerConnection());
		assertSame(handler, NetworkServiceImpl.getDealerConnection().getHandler());
	}
	
	public void testStartingServer() throws InterruptedException {
		
		try {
			networkServiceImpl.startServer();
		} catch (InitServerException e) {
			fail("Exception while starting server: " + e.getMessage());
		}
		
		assertNotNull(NetworkServiceImpl.getDealerConnection().getServer());
	}
	
	public void testInitServerExceptionIsThrownIfHandlerIsNull() {
		networkServiceImpl = new NetworkServiceImpl(null);
		
		try {
			networkServiceImpl.startServer();
			fail("Should have thrown an InitServerException");
		} catch (InitServerException e) {
			//do nothing
		}
		
		assertNull(NetworkServiceImpl.getDealerConnection().getServer());
	}
	
	public void testShouldNotFailIfTryingToStartTheServerWhenAlreadyRunning() throws InitServerException, InterruptedException {
		networkServiceImpl.startServer();
		
		assertNotNull(NetworkServiceImpl.getDealerConnection().getServer());
		
		try {
			networkServiceImpl.startServer();
		} catch (Exception e) {
			fail("Should not get an exception when trying to start server again");
		}
	}
	
	public void testStoppingServer() throws InitServerException, InterruptedException {
		NetworkServiceImpl.getDealerConnection().initServer();
		
		assertNotNull(NetworkServiceImpl.getDealerConnection().getServer());
		
		networkServiceImpl.stopServer();
		
		assertNull(NetworkServiceImpl.getDealerConnection().getServer());
		
	}
	
	public void testSendingPlayerDataWhenServerNotRunningThrowsException() {
		assertNull(NetworkServiceImpl.getDealerConnection().getServer());
		
		Player player = new Player("test");
		
		try {
			networkServiceImpl.sendPlayerData(player);
			fail("Should throw exception when trying to send cards to player and the server isn't running");
		} catch (Exception e) {
			// do nothing
		}
	}
}
