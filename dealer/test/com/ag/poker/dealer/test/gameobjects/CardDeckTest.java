package com.ag.poker.dealer.test.gameobjects;

import android.test.AndroidTestCase;

import com.ag.poker.dealer.exceptions.CardDeckEmptyException;
import com.ag.poker.dealer.gameobjects.Card;
import com.ag.poker.dealer.gameobjects.CardDeck;

public class CardDeckTest extends AndroidTestCase {
	
	private CardDeck cardDeck;

	protected void setUp() throws Exception {
		super.setUp();
		this.cardDeck = new CardDeck();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testDrawCard() throws CardDeckEmptyException {
		Card card = this.cardDeck.drawCard();
		
		assertNotNull(card);
		assertEquals(51, this.cardDeck.size());
		assertFalse(this.cardDeck.contains(card));
	}
	
	public void testThrowsExceptionWhenTryingToDrawFromEmptyDeck() {
		this.cardDeck.removeAllElements();
		
		try {
			this.cardDeck.drawCard();
			fail("Should get a CardDeckEmptyException when trying to draw from an empty deck");
		} catch (CardDeckEmptyException e) {
			// TODO: handle exception
		}
	}

}
