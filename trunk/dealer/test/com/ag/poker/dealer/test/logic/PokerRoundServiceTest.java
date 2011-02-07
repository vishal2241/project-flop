package com.ag.poker.dealer.test.logic;

import java.io.IOException;

import android.os.Handler;
import android.test.AndroidTestCase;

import com.ag.poker.dealer.Dealer;
import com.ag.poker.dealer.exceptions.InitServerException;
import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.gameobjects.PlayerList;
import com.ag.poker.dealer.logic.PokerRoundService;
import com.ag.poker.dealer.network.NetworkService;
import com.ag.poker.dealer.utils.constants.PokerRoundConstants;

public class PokerRoundServiceTest extends AndroidTestCase implements PokerRoundConstants {
	
	private PokerRoundService pokerRoundHandler;
	private Handler pokerRoundMsgHandler;
	
	private boolean hasAnnouncedNewRound = false;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.pokerRoundMsgHandler = new Handler();
		
		new Dealer();
		Dealer.networkService = new MockNetworkService();
		
		this.pokerRoundHandler = new PokerRoundService(this.pokerRoundMsgHandler);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testClassInitialization() {
		assertNotNull(pokerRoundHandler);
		assertNotNull(PokerRoundService.getCardDeckHandler());
		assertNotNull(PokerRoundService.getPlayerHandler());
		assertSame(this.pokerRoundMsgHandler, pokerRoundHandler.getPokerRoundMsgHandler());
		assertEquals(PokerRoundConstants.FLAG_ROUND_STATE_START_NEW_ROUND, pokerRoundHandler.getRound_state());
	}
	
//	public void testDrawingCardsForTableWholeRound() throws CardDeckEmptyException, IOException {
//		assertEquals(52, PokerRoundService.getCardDeckHandler().getCardDeck().size());
//		assertEquals(0, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
//		assertEquals(FLAG_ROUND_STATE_START_NEW_ROUND, pokerRoundHandler.getRound_state());
//		
//		//flop
//		pokerRoundHandler.drawCardsForTable();
//		assertEquals(48, PokerRoundService.getCardDeckHandler().getCardDeck().size());
//		assertEquals(3, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
//		assertEquals(FLAG_ROUND_STATE_FLOP, pokerRoundHandler.getRound_state());
//		
//		//turn
//		pokerRoundHandler.drawCardsForTable();
//		assertEquals(46, PokerRoundService.getCardDeckHandler().getCardDeck().size());
//		assertEquals(4, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
//		assertEquals(FLAG_ROUND_STATE_TURN, pokerRoundHandler.getRound_state());
//		
//		//river
//		pokerRoundHandler.drawCardsForTable();
//		assertEquals(44, PokerRoundService.getCardDeckHandler().getCardDeck().size());
//		assertEquals(5, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
//		assertEquals(FLAG_ROUND_STATE_FINISH_ROUND, pokerRoundHandler.getRound_state());
//		
//		for (Card card : PokerRoundService.getCardDeckHandler().getCardsOnTable()) {
//			assertFalse(PokerRoundService.getCardDeckHandler().getCardDeck().contains(card));
//		}
//		
//		pokerRoundHandler.drawCardsForTable();
//		assertEquals(52, PokerRoundService.getCardDeckHandler().getCardDeck().size());
//		assertEquals(0, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
//		assertEquals(FLAG_ROUND_STATE_START_NEW_ROUND, pokerRoundHandler.getRound_state());
//		
//		for (Card card : PokerRoundService.getCardDeckHandler().getCardsOnTable()) {
//			assertFalse(PokerRoundService.getCardDeckHandler().getCardDeck().contains(card));
//		}
//	}
//	
//	public void testResetRound() throws CardDeckEmptyException, IOException {
//		pokerRoundHandler.setRound_state(FLAG_ROUND_STATE_RIVER);
//		PokerRoundService.getCardDeckHandler().getCardDeck().remove(0);
//		PokerRoundService.getCardDeckHandler().getCardsOnTable().add(Card.CLUB_EIGHT);
//		
//		assertEquals(51, PokerRoundService.getCardDeckHandler().getCardDeck().size());
//		assertEquals(1, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
//		assertEquals(FLAG_ROUND_STATE_RIVER, pokerRoundHandler.getRound_state());
//		
//		pokerRoundHandler.startNewRound();
//		
//		assertEquals(52, PokerRoundService.getCardDeckHandler().getCardDeck().size());
//		assertEquals(0, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
//		assertEquals(FLAG_ROUND_STATE_START_NEW_ROUND, pokerRoundHandler.getRound_state());
//	}
//	
//	public void testPlayersAreGivenCardsAtRoundStart() throws CardDeckEmptyException, IOException {
//		String playerId1 = "player1";
//		String playerId2 = "player2";
//		
//		HashMap<String, Player> players = new HashMap<String, Player>();
//		players.put(playerId1, new Player(playerId1));
//		players.put(playerId2, new Player(playerId2));
//		PlayerHandler.setPlayers(players);
//		players = PlayerHandler.getPlayers();
//		
//		assertFalse(hasAnnouncedNewRound);
//		
//		pokerRoundHandler.startNewRound();
//		
//		assertTrue(hasAnnouncedNewRound);
//		
//		assertEquals(48, PokerRoundService.getCardDeckHandler().getCardDeck().size());
//		assertEquals(0, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
//		assertEquals(FLAG_ROUND_STATE_START_NEW_ROUND, pokerRoundHandler.getRound_state());
//		
//		assertNotNull(players.get(playerId1).getCard1());
//		assertNotNull(players.get(playerId1).getCard2());
//		
//		assertNotNull(players.get(playerId2).getCard1());
//		assertNotNull(players.get(playerId2).getCard2());
//		
//	}

	private class MockNetworkService implements NetworkService {

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#startServer()
	 */
	@Override
	public void startServer() throws InitServerException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#stopServer()
	 */
	@Override
	public void stopServer() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#sendPlayerData(com.ag.poker.dealer.gameobjects.Player)
	 */
	@Override
	public void sendPlayerData(Player player) throws IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#sendAllPlayersData(com.ag.poker.dealer.gameobjects.PlayerList)
	 */
	@Override
	public void sendAllPlayersData(PlayerList players) throws IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#announceNewRound(com.ag.poker.dealer.gameobjects.PlayerList)
	 */
	@Override
	public void announceNewRound(PlayerList players) throws IOException {
		hasAnnouncedNewRound = true;
		
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#askPlayerForBets(com.ag.poker.dealer.gameobjects.Player)
	 */
	@Override
	public void askPlayerForBets(Player player) throws IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#announceFinishedRound(com.ag.poker.dealer.gameobjects.PlayerList)
	 */
	@Override
	public void announceFinishedRound(PlayerList winners) throws IOException {
		// TODO Auto-generated method stub
		
	}

		
	}
}
