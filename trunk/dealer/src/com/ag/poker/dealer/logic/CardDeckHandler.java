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

package com.ag.poker.dealer.logic;

import java.util.ArrayList;

import com.ag.poker.dealer.exceptions.CardDeckEmptyException;
import com.ag.poker.dealer.gameobjects.Card;
import com.ag.poker.dealer.gameobjects.CardDeck;

/**
 * @author Arild
 *
 */
public class CardDeckHandler {
	
	private CardDeck cardDeck;
	
	private ArrayList<Card> cardsOnTable;
	
	public CardDeckHandler() {
		
		this.cardDeck = new CardDeck();
		this.cardsOnTable = new ArrayList<Card>();
	}
	
	/**
	 * @return the cardDeck
	 */
	public CardDeck getCardDeck() {
		return cardDeck;
	}

	/**
	 * @return the cardsOnTable
	 */
	public ArrayList<Card> getCardsOnTable() {
		return cardsOnTable;
	}

	public Card drawCardForPlayer() throws CardDeckEmptyException {
		return this.cardDeck.drawCard();
	}
	
	public Card drawCardForTable(boolean burnCard) throws CardDeckEmptyException {
		if(burnCard) {
			this.cardDeck.drawCard();
		}
		
		Card card = this.cardDeck.drawCard();
		this.cardsOnTable.add(card);
		
		return card;
	}
	
	public void resetCardDeck() {
		this.cardDeck = new CardDeck();
		this.cardsOnTable.clear();
	}

}
