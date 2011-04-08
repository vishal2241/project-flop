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

package com.ag.poker.dealer.gameobjects.card;

import java.util.Vector;

import com.ag.poker.dealer.exceptions.CardDeckEmptyException;

/**
 * @author Arild
 *
 */
public class CardDeck extends Vector<Card> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CardDeck() {
		add(Card.CLUB_ACE);
		add(Card.CLUB_TWO);
		add(Card.CLUB_THREE);
		add(Card.CLUB_FOUR);
		add(Card.CLUB_FIVE);
		add(Card.CLUB_SIX);
		add(Card.CLUB_SEVEN);
		add(Card.CLUB_EIGHT);
		add(Card.CLUB_NINE);
		add(Card.CLUB_TEN);
		add(Card.CLUB_JACK);
		add(Card.CLUB_QUEEN);
		add(Card.CLUB_KING);
		
		add(Card.SPADE_ACE);
		add(Card.SPADE_TWO);
		add(Card.SPADE_THREE);
		add(Card.SPADE_FOUR);
		add(Card.SPADE_FIVE);
		add(Card.SPADE_SIX);
		add(Card.SPADE_SEVEN);
		add(Card.SPADE_EIGHT);
		add(Card.SPADE_NINE);
		add(Card.SPADE_TEN);
		add(Card.SPADE_JACK);
		add(Card.SPADE_QUEEN);
		add(Card.SPADE_KING);
		
		add(Card.DIAMOND_ACE);
		add(Card.DIAMOND_TWO);
		add(Card.DIAMOND_THREE);
		add(Card.DIAMOND_FOUR);
		add(Card.DIAMOND_FIVE);
		add(Card.DIAMOND_SIX);
		add(Card.DIAMOND_SEVEN);
		add(Card.DIAMOND_EIGHT);
		add(Card.DIAMOND_NINE);
		add(Card.DIAMOND_TEN);
		add(Card.DIAMOND_JACK);
		add(Card.DIAMOND_QUEEN);
		add(Card.DIAMOND_KING);
		
		add(Card.HEART_ACE);
		add(Card.HEART_TWO);
		add(Card.HEART_THREE);
		add(Card.HEART_FOUR);
		add(Card.HEART_FIVE);
		add(Card.HEART_SIX);
		add(Card.HEART_SEVEN);
		add(Card.HEART_EIGHT);
		add(Card.HEART_NINE);
		add(Card.HEART_TEN);
		add(Card.HEART_JACK);
		add(Card.HEART_QUEEN);
		add(Card.HEART_KING);
	}
	
	public Card drawCard() throws CardDeckEmptyException {
		if(isEmpty()) {
			throw new CardDeckEmptyException();
		}
		
		int random = (int)(Math.random()*size());
		
		Card card = get(random);
		
		remove(random);
		
		return card;
	}
	
}
