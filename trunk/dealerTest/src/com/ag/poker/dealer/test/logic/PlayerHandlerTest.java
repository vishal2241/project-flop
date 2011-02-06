package com.ag.poker.dealer.test.logic;

import android.test.AndroidTestCase;

import com.ag.poker.dealer.exceptions.UnableToAddPlayerException;
import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.logic.PlayerHandler;

public class PlayerHandlerTest extends AndroidTestCase {
	
	private PlayerHandler playerHandler;

	protected void setUp() throws Exception {
		super.setUp();
		this.playerHandler = new PlayerHandler();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		this.playerHandler = null;
	}
	
	public void testAddingPlayer() throws UnableToAddPlayerException {
		
		String key = "testplayer";
		
		Player expected = new Player(key, "Test Player", 100);
		
		boolean result = this.playerHandler.addPlayer(expected);
		
		assertTrue(result);
		assertEquals(1, PlayerHandler.getPlayers().size());
		assertTrue(PlayerHandler.getPlayers().containsKey(key));
		
		Player actual = PlayerHandler.getPlayers().get(key);
		
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getChipCount(), actual.getChipCount());
		assertTrue(actual.isActive());
	}

	public void testUnableToAddPlayerThatIsNull() throws UnableToAddPlayerException {
		boolean result = this.playerHandler.addPlayer(null);
		
		assertFalse(result);
	}
	
	public void testUnableToAddExistingPlayer() throws UnableToAddPlayerException {
		
		String key = "testplayer";
		
		Player player = new Player(key, "Test Player", 10);
		
		PlayerHandler.getPlayers().put(key, player);
		
		boolean result = this.playerHandler.addPlayer(player);
		
		assertFalse(result);
		
		assertEquals(1, PlayerHandler.getPlayers().size());
	}
	
	public void testPlayerIsMovedFromDisconnectedListToMainList() throws UnableToAddPlayerException {
		
		String key = "testplayer";
		
		Player player = new Player(key, "Test Player", 10);
		
		PlayerHandler.getDisconnectedPlayers().put(key, player);
		
		assertTrue(PlayerHandler.getDisconnectedPlayers().containsKey(key));
		
		boolean result = this.playerHandler.addPlayer(player);
		
		assertTrue(result);
		
		assertTrue(PlayerHandler.getPlayers().containsKey(key));
		assertFalse(PlayerHandler.getDisconnectedPlayers().containsKey(key));
	}
	
	public void testPlayerIsSetInactive() {
		String playerId = "testplayer";
		
		Player player = new Player(playerId, "Test Player", 10);
		
		PlayerHandler.getPlayers().put(playerId, player);
		
		assertTrue(PlayerHandler.getPlayers().get(playerId).isActive());
		
		this.playerHandler.setPlayerInactive(playerId);
		
		assertFalse(PlayerHandler.getPlayers().get(playerId).isActive());
	}
	
	public void testSettingNonExistingPlayerToInactive() {
		String playerId = "testplayer";
		
		try {
			this.playerHandler.setPlayerInactive(playerId);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldn't get an exception when trying to set non-existing player to inactive, should instead just ignore");
		}
	}
	
	public void testSettingNullPlayerToInactive() {
		String playerId = "testplayer";
		Player player = new Player(playerId, "Test Player", 10);
		
		PlayerHandler.getPlayers().put(playerId, player);
		
		try {
			this.playerHandler.setPlayerInactive(null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldn't get an exception when trying to set non-existing player to inactive, should instead just ignore");
		}
	}
	
	public void testRemovePlayer() {
		String player1Id = "testplayer1";
		Player player1 = new Player(player1Id, "Test Player", 10);
		
		String player2Id = "testplayer2";
		Player player2 = new Player(player2Id, "Test Player2", 10);
		
		PlayerHandler.getPlayers().put(player1Id, player1);
		PlayerHandler.getPlayers().put(player2Id, player2);
		
		assertEquals(2, PlayerHandler.getPlayers().size());
		assertTrue(PlayerHandler.getPlayers().containsKey(player1Id));
		assertTrue(PlayerHandler.getPlayers().containsKey(player2Id));
		
		this.playerHandler.removePlayer(player1Id);
		
		assertEquals(1, PlayerHandler.getPlayers().size());
		assertFalse(PlayerHandler.getPlayers().containsKey(player1Id));
		assertTrue(PlayerHandler.getPlayers().containsKey(player2Id));
	}
	
	public void testRemoveNonExistingPlayer() {
		String player1Id = "testplayer1";
		Player player1 = new Player(player1Id, "Test Player", 10);
		
		String player2Id = "testplayer2";
		Player player2 = new Player(player2Id, "Test Player2", 10);
		
		PlayerHandler.getPlayers().put(player1Id, player1);
		PlayerHandler.getPlayers().put(player2Id, player2);
		
		assertEquals(2, PlayerHandler.getPlayers().size());
		assertTrue(PlayerHandler.getPlayers().containsKey(player1Id));
		assertTrue(PlayerHandler.getPlayers().containsKey(player2Id));
		
		this.playerHandler.removePlayer("wrongId");
		
		assertEquals(2, PlayerHandler.getPlayers().size());
		assertTrue(PlayerHandler.getPlayers().containsKey(player1Id));
		assertTrue(PlayerHandler.getPlayers().containsKey(player2Id));
	}
	
	public void testRemoveNullPlayer() {
		String player1Id = "testplayer1";
		Player player1 = new Player(player1Id, "Test Player", 10);
		
		String player2Id = "testplayer2";
		Player player2 = new Player(player2Id, "Test Player2", 10);
		
		PlayerHandler.getPlayers().put(player1Id, player1);
		PlayerHandler.getPlayers().put(player2Id, player2);
		
		assertEquals(2, PlayerHandler.getPlayers().size());
		assertTrue(PlayerHandler.getPlayers().containsKey(player1Id));
		assertTrue(PlayerHandler.getPlayers().containsKey(player2Id));
		
		this.playerHandler.removePlayer(null);
		
		assertEquals(2, PlayerHandler.getPlayers().size());
		assertTrue(PlayerHandler.getPlayers().containsKey(player1Id));
		assertTrue(PlayerHandler.getPlayers().containsKey(player2Id));
	}
	
}
