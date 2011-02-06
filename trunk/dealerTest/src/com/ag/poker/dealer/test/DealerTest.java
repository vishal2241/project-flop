package com.ag.poker.dealer.test;

import java.util.HashMap;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Handler;
import android.test.ActivityUnitTestCase;

import com.ag.poker.dealer.Dealer;
import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.logic.PlayerHandler;
import com.ag.poker.dealer.test.utils.TestToolUtil;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

public class DealerTest extends ActivityUnitTestCase<Dealer> {
	
	private Dealer dealer;
	private Instrumentation instrumentation;

	public DealerTest() {
		super(Dealer.class);
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
		this.dealer = startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		
		assertNotNull(this.dealer.getPlayerHandler());
		assertNotNull(Dealer.dealerConnection);
		assertTrue(Dealer.dealerConnection.getServer().isAlive());
	}


	public void testThatThePlayerListStaysConsistentBetweenPauseAndResume() {
		this.dealer = startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		
		int numberOfPlayers = 5;
		
		HashMap<String, Player> players = TestToolUtil.createTestPlayers(numberOfPlayers);
		PlayerHandler.setPlayers(players);
		
		this.instrumentation.callActivityOnPause(this.dealer);
		
		this.instrumentation.callActivityOnResume(this.dealer);
		
		assertEquals(numberOfPlayers, PlayerHandler.getPlayers().size());
		assertFalse(PlayerHandler.getPlayers().isEmpty());
	}
	

	public void testThatThePlayerListStaysConsistentBetweenStopAndResume() {
		this.dealer = startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		
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
		this.dealer = startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		
		int numberOfPlayers = 5;
		
		HashMap<String, Player> players = TestToolUtil.createTestPlayers(numberOfPlayers);
		PlayerHandler.setPlayers(players);
		
		this.instrumentation.callActivityOnDestroy(this.dealer);
		
		this.instrumentation.callActivityOnResume(this.dealer);
		
		assertEquals(numberOfPlayers, PlayerHandler.getPlayers().size());
		assertFalse(PlayerHandler.getPlayers().isEmpty());
		
	}
	
	public void testServerHandlerAddPlayer() {
		this.dealer = startActivity(new Intent(Intent.ACTION_MAIN), null, null);
		
		Player player = new Player("player1", "Player 1", 100);
		
		Handler serverHandler = this.dealer.getServerHandler();
		
		serverHandler.sendMessage(serverHandler.obtainMessage(DealerConnectionConstants.PLAYER_DATA_CLIENT_MESSAGE, player));
		
		assertTrue(PlayerHandler.players.containsKey(player.getId()));
	}

}
