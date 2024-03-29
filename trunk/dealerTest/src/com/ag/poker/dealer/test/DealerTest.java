package com.ag.poker.dealer.test;

import java.util.HashMap;

import android.app.Instrumentation;
import android.os.Handler;
import android.os.Message;
import android.test.ActivityInstrumentationTestCase2;

import com.ag.poker.dealer.Dealer;
import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.logic.PlayerHandler;
import com.ag.poker.dealer.test.utils.TestToolUtil;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

public class DealerTest extends ActivityInstrumentationTestCase2<Dealer> {
	
	private Dealer dealer;
	private Instrumentation instrumentation;

	public DealerTest() {
		super("com.ag.poker.dealer", Dealer.class);
		
		setName("DealerTest");
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		this.dealer = getActivity();
		this.instrumentation = this.getInstrumentation();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testClassSetup() {
		assertNotNull(this.dealer.getPlayerHandler());
		assertNotNull(Dealer.dealerConnection);
		assertTrue(Dealer.dealerConnection.getServer().isAlive());
	}


	public void testThatThePlayerListStaysConsistentBetweenPauseAndResume() {
		int numberOfPlayers = 5;
		
		HashMap<String, Player> players = TestToolUtil.createTestPlayers(numberOfPlayers);
		PlayerHandler.setPlayers(players);
		
		this.instrumentation.callActivityOnPause(this.dealer);
		
		this.instrumentation.callActivityOnResume(this.dealer);
		
		assertEquals(numberOfPlayers, PlayerHandler.getPlayers().size());
		assertFalse(PlayerHandler.getPlayers().isEmpty());
	}
	

	public void testThatThePlayerListStaysConsistentBetweenStopAndResume() {
		int numberOfPlayers = 5;
		
		HashMap<String, Player> players = TestToolUtil.createTestPlayers(numberOfPlayers);
		PlayerHandler.setPlayers(players);
		
		this.instrumentation.callActivityOnPause(this.dealer);
		this.instrumentation.callActivityOnStop(this.dealer);
		this.instrumentation.callActivityOnRestart(this.dealer);
		this.instrumentation.callActivityOnStart(this.dealer);
		
		this.instrumentation.callActivityOnResume(this.dealer);
		
		assertEquals(numberOfPlayers, PlayerHandler.getPlayers().size());
		assertFalse(PlayerHandler.getPlayers().isEmpty());
	}
	
	public void testThatThePlayerListStaysConsistentBetweenDestroyAndResume() {
		
		int numberOfPlayers = 5;
		
		HashMap<String, Player> players = TestToolUtil.createTestPlayers(numberOfPlayers);
		PlayerHandler.setPlayers(players);
		
		this.instrumentation.callActivityOnDestroy(this.dealer);
		
		this.instrumentation.callActivityOnResume(this.dealer);
		
		assertEquals(numberOfPlayers, PlayerHandler.getPlayers().size());
		assertFalse(PlayerHandler.getPlayers().isEmpty());
		
	}
	
	public void testServerHandlerAddPlayer() throws Throwable {
		final Player player = new Player("player1", "Player 1", 100);
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Handler serverHandler = dealer.getServerHandler();
				
				Message msg = serverHandler.obtainMessage(DealerConnectionConstants.PLAYER_DATA_CLIENT_MESSAGE, player);
				serverHandler.sendMessage(msg);
				
			}
		});
		
		assertTrue(PlayerHandler.players.containsKey(player.getId()));
	}
	
	public void testServerHandlerAddPlayerWithNullArgument() throws Throwable {
		
		try {
			runTestOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Handler serverHandler = dealer.getServerHandler();
					
					Message msg = serverHandler.obtainMessage(DealerConnectionConstants.PLAYER_DATA_CLIENT_MESSAGE, null);
					serverHandler.sendMessage(msg);
					
				}
			});
		} catch (Exception e) {
			fail("Exception: " + e.getMessage());
		}
		
		assertTrue(PlayerHandler.players.isEmpty());
	}
	
	public void testServerHandlerAddPlayerWithIdNull() throws Throwable {
		final Player player = new Player(null, "Player 1", 100);
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Handler serverHandler = dealer.getServerHandler();
				
				Message msg = serverHandler.obtainMessage(DealerConnectionConstants.PLAYER_DATA_CLIENT_MESSAGE, player);
				serverHandler.sendMessage(msg);
				
			}
		});
		
		assertFalse(PlayerHandler.players.containsKey(player.getId()));
		assertTrue(PlayerHandler.players.isEmpty());
	}
	
	public void testServerHandlerAddPlayerWithEmptyId() throws Throwable {
		final Player player = new Player("", "Player 1", 100);
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Handler serverHandler = dealer.getServerHandler();
				
				Message msg = serverHandler.obtainMessage(DealerConnectionConstants.PLAYER_DATA_CLIENT_MESSAGE, player);
				serverHandler.sendMessage(msg);
				
			}
		});
		
		assertFalse(PlayerHandler.players.containsKey(player.getId()));
		assertTrue(PlayerHandler.players.isEmpty());
	}
	
	public void testServerHandlerRemovePlayer() throws Throwable {
		final Player player = new Player("player1", "Player 1", 100);
		
		PlayerHandler.players.put(player.getId(), player);
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Handler serverHandler = dealer.getServerHandler();
				
				Message msg = serverHandler.obtainMessage(DealerConnectionConstants.CLIENT_DISCONNECTED, player.getId());
				serverHandler.sendMessage(msg);
			}
		});
		
		assertFalse(PlayerHandler.players.containsKey(player.getId()));
		assertTrue(PlayerHandler.disconnectedPlayers.containsKey(player.getId()));
	}
}
