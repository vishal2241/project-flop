package com.ag.poker.dealer.test.logic;

import android.os.Handler;
import android.test.AndroidTestCase;

import com.ag.poker.dealer.exceptions.CardDeckEmptyException;
import com.ag.poker.dealer.gameobjects.Card;
import com.ag.poker.dealer.logic.PokerRoundService;
import com.ag.poker.dealer.utils.constants.PokerRoundConstants;

public class PokerRoundServiceTest extends AndroidTestCase implements PokerRoundConstants {
	
	private PokerRoundService pokerRoundHandler;
	private Handler pokerRoundMsgHandler;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.pokerRoundMsgHandler = new Handler();
		
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
		assertEquals(PokerRoundConstants.FLAG_ROUND_STATE_START, pokerRoundHandler.getRound_state());
	}
	
	public void testDrawingCardsForTableWholeRound() throws CardDeckEmptyException {
		assertEquals(52, PokerRoundService.getCardDeckHandler().getCardDeck().size());
		assertEquals(0, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
		assertEquals(FLAG_ROUND_STATE_START, pokerRoundHandler.getRound_state());
		
		//flop
		pokerRoundHandler.drawCardsForTable();
		assertEquals(48, PokerRoundService.getCardDeckHandler().getCardDeck().size());
		assertEquals(3, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
		assertEquals(FLAG_ROUND_STATE_FLOP, pokerRoundHandler.getRound_state());
		
		//turn
		pokerRoundHandler.drawCardsForTable();
		assertEquals(46, PokerRoundService.getCardDeckHandler().getCardDeck().size());
		assertEquals(4, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
		assertEquals(FLAG_ROUND_STATE_TURN, pokerRoundHandler.getRound_state());
		
		//river
		pokerRoundHandler.drawCardsForTable();
		assertEquals(44, PokerRoundService.getCardDeckHandler().getCardDeck().size());
		assertEquals(5, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
		assertEquals(FLAG_ROUND_STATE_FINISHED, pokerRoundHandler.getRound_state());
		
		for (Card card : PokerRoundService.getCardDeckHandler().getCardsOnTable()) {
			assertFalse(PokerRoundService.getCardDeckHandler().getCardDeck().contains(card));
		}
		
		pokerRoundHandler.drawCardsForTable();
		assertEquals(52, PokerRoundService.getCardDeckHandler().getCardDeck().size());
		assertEquals(0, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
		assertEquals(FLAG_ROUND_STATE_START, pokerRoundHandler.getRound_state());
		
		for (Card card : PokerRoundService.getCardDeckHandler().getCardsOnTable()) {
			assertFalse(PokerRoundService.getCardDeckHandler().getCardDeck().contains(card));
		}
	}
	
	public void testResetRound() {
		pokerRoundHandler.setRound_state(FLAG_ROUND_STATE_RIVER);
		PokerRoundService.getCardDeckHandler().getCardDeck().remove(0);
		PokerRoundService.getCardDeckHandler().getCardsOnTable().add(Card.CLUB_EIGHT);
		
		assertEquals(51, PokerRoundService.getCardDeckHandler().getCardDeck().size());
		assertEquals(1, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
		assertEquals(FLAG_ROUND_STATE_RIVER, pokerRoundHandler.getRound_state());
		
		pokerRoundHandler.startNewRound();
		
		assertEquals(52, PokerRoundService.getCardDeckHandler().getCardDeck().size());
		assertEquals(0, PokerRoundService.getCardDeckHandler().getCardsOnTable().size());
		assertEquals(FLAG_ROUND_STATE_START, pokerRoundHandler.getRound_state());
	}
	

}
