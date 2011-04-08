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

/**
 * @author Arild
 *
 */
public enum Card {
	
	CLUB_TWO(CardColor.CLUB, CardValue.TWO),
	CLUB_THREE(CardColor.CLUB, CardValue.THREE),
	CLUB_FOUR(CardColor.CLUB, CardValue.FOUR),
	CLUB_FIVE(CardColor.CLUB, CardValue.FIVE),
	CLUB_SIX(CardColor.CLUB, CardValue.SIX),
	CLUB_SEVEN(CardColor.CLUB, CardValue.SEVEN),
	CLUB_EIGHT(CardColor.CLUB, CardValue.EIGHT),
	CLUB_NINE(CardColor.CLUB, CardValue.NINE),
	CLUB_TEN(CardColor.CLUB, CardValue.TEN),
	CLUB_JACK(CardColor.CLUB, CardValue.JACK),
	CLUB_QUEEN(CardColor.CLUB, CardValue.QUEEN),
	CLUB_KING(CardColor.CLUB, CardValue.KING),
	CLUB_ACE(CardColor.CLUB, CardValue.ACE),
	
	SPADE_TWO(CardColor.SPADE, CardValue.TWO),
	SPADE_THREE(CardColor.SPADE, CardValue.THREE),
	SPADE_FOUR(CardColor.SPADE, CardValue.FOUR),
	SPADE_FIVE(CardColor.SPADE, CardValue.FIVE),
	SPADE_SIX(CardColor.SPADE, CardValue.SIX),
	SPADE_SEVEN(CardColor.SPADE, CardValue.SEVEN),
	SPADE_EIGHT(CardColor.SPADE, CardValue.EIGHT),
	SPADE_NINE(CardColor.SPADE, CardValue.NINE),
	SPADE_TEN(CardColor.SPADE, CardValue.TEN),
	SPADE_JACK(CardColor.SPADE, CardValue.JACK),
	SPADE_QUEEN(CardColor.SPADE, CardValue.QUEEN),
	SPADE_KING(CardColor.SPADE, CardValue.KING),
	SPADE_ACE(CardColor.SPADE, CardValue.ACE),
	
	DIAMOND_TWO(CardColor.DIAMOND, CardValue.TWO),
	DIAMOND_THREE(CardColor.DIAMOND, CardValue.THREE),
	DIAMOND_FOUR(CardColor.DIAMOND, CardValue.FOUR),
	DIAMOND_FIVE(CardColor.DIAMOND, CardValue.FIVE),
	DIAMOND_SIX(CardColor.DIAMOND, CardValue.SIX),
	DIAMOND_SEVEN(CardColor.DIAMOND, CardValue.SEVEN),
	DIAMOND_EIGHT(CardColor.DIAMOND, CardValue.EIGHT),
	DIAMOND_NINE(CardColor.DIAMOND, CardValue.NINE),
	DIAMOND_TEN(CardColor.DIAMOND, CardValue.TEN),
	DIAMOND_JACK(CardColor.DIAMOND, CardValue.JACK),
	DIAMOND_QUEEN(CardColor.DIAMOND, CardValue.QUEEN),
	DIAMOND_KING(CardColor.DIAMOND, CardValue.KING),
	DIAMOND_ACE(CardColor.DIAMOND, CardValue.ACE),
	
	HEART_TWO(CardColor.HEART, CardValue.TWO),
	HEART_THREE(CardColor.HEART, CardValue.THREE),
	HEART_FOUR(CardColor.HEART, CardValue.FOUR),
	HEART_FIVE(CardColor.HEART, CardValue.FIVE),
	HEART_SIX(CardColor.HEART, CardValue.SIX),
	HEART_SEVEN(CardColor.HEART, CardValue.SEVEN),
	HEART_EIGHT(CardColor.HEART, CardValue.EIGHT),
	HEART_NINE(CardColor.HEART, CardValue.NINE),
	HEART_TEN(CardColor.HEART, CardValue.TEN),
	HEART_JACK(CardColor.HEART, CardValue.JACK),
	HEART_QUEEN(CardColor.HEART, CardValue.QUEEN),
	HEART_KING(CardColor.HEART, CardValue.KING),
	HEART_ACE(CardColor.HEART, CardValue.ACE),
	
	BACK_RED(CardColor.BACK, CardValue.FIVE),
	BACK_BLUE(CardColor.BACK, CardValue.ACE);
	
	public final CardColor cardColor;
	public final CardValue cardValue;
	
	public static final int CARD_WIDTH = 150;
	public static final int CARD_HEIGHT = 215;
	
	private Card(final CardColor cardColor, final CardValue cardValue) {
		this.cardColor = cardColor;
		this.cardValue = cardValue;
	}
	
	public int getTexturePositionX() {
		return this.cardValue.ordinal() * CARD_WIDTH;
	}
	
	public int getTexturePositionY() {
		return this.cardColor.ordinal() * CARD_HEIGHT;
	}
	
	public int getRelativeValue() {
		return (this.cardValue.ordinal()+1) * 13;
	}
	
	public boolean isCardNextInSequence(Card previousCard) {
		if(previousCard.cardValue == CardValue.ACE) {
			if(previousCard.cardColor == this.cardColor && this.cardValue == CardValue.TWO) {
				return true;
			} else {
				return false;
			}
		} else {
			if((this.ordinal() - previousCard.ordinal()) == 1) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean isCardValuePreviousInSequence(Card previousCard) {
		if(previousCard.cardValue == CardValue.TWO) {
			if(this.cardValue == CardValue.ACE) {
				return true;
			} else {
				return false;
			}
		} else {
			if((previousCard.cardValue.ordinal() - this.cardValue.ordinal()) == 1) {
				return true;
			} else {
				return false;
			}
		}
	}

}
