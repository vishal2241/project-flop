package com.ag.poker.dealer.test.logic;

import android.test.AndroidTestCase;

import com.ag.poker.dealer.exceptions.CardDeckEmptyException;
import com.ag.poker.dealer.gameobjects.card.Card;
import com.ag.poker.dealer.logic.CardDeckHandler;

public class CardDeckHandlerTest extends AndroidTestCase {
	
	private CardDeckHandler cardDeckHandler;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.cardDeckHandler = new CardDeckHandler();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testInitializationOfClass() {
		assertEquals(52, this.cardDeckHandler.getCardDeck().size());
		assertEquals(0, this.cardDeckHandler.getCardsOnTable().size());
	}
	
	public void testDrawACardForPlayer() throws CardDeckEmptyException {
		
		Card card = this.cardDeckHandler.drawCardForPlayer();
		
		assertNotNull(card);
		assertEquals(51, this.cardDeckHandler.getCardDeck().size());
		assertFalse(this.cardDeckHandler.getCardDeck().contains(card));
	}
	
	public void testDrawACardForTableWithBurningCard() throws CardDeckEmptyException {
		this.cardDeckHandler.drawCardForTable(3);
		
		assertFalse(this.cardDeckHandler.getCardsOnTable().isEmpty());
		assertEquals(48, this.cardDeckHandler.getCardDeck().size());
		assertFalse(this.cardDeckHandler.getCardDeck().contains(this.cardDeckHandler.getCardsOnTable()));
		assertEquals(3, this.cardDeckHandler.getCardsOnTable().size());
	}
	
	
	public void testResetCardDeck() {
		this.cardDeckHandler.getCardDeck().remove(0);
		this.cardDeckHandler.getCardDeck().remove(1);
		this.cardDeckHandler.getCardDeck().remove(2);
		this.cardDeckHandler.getCardDeck().remove(3);
		
		this.cardDeckHandler.getCardsOnTable().add(Card.CLUB_ACE);
		
		assertEquals(48, this.cardDeckHandler.getCardDeck().size());
		assertEquals(1, this.cardDeckHandler.getCardsOnTable().size());
		
		this.cardDeckHandler.resetCardDeck();
		
		assertEquals(52, this.cardDeckHandler.getCardDeck().size());
		assertEquals(0, this.cardDeckHandler.getCardsOnTable().size());
	}
	
	public void testThrowsExceptionWhenTryingToDrawFromEmptyDeckForPlayer() {
		this.cardDeckHandler.getCardDeck().removeAllElements();
		
		try {
			this.cardDeckHandler.drawCardForPlayer();
			fail("Should get a CardDeckEmptyException when trying to draw from an empty deck");
		} catch (CardDeckEmptyException e) {
			// TODO: handle exception
		}
	}
	
	public void testThrowsExceptionWhenTryingToDrawFromEmptyDeckForTable() {
		this.cardDeckHandler.getCardDeck().removeAllElements();
		
		try {
			this.cardDeckHandler.drawCardForTable(1);
			fail("Should get a CardDeckEmptyException when trying to draw from an empty deck");
		} catch (CardDeckEmptyException e) {
			// TODO: handle exception
		}
	}

}
