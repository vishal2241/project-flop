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

import java.io.IOException;
import java.util.ArrayList;

import android.os.Handler;
import android.util.Log;

import com.ag.poker.dealer.Dealer;
import com.ag.poker.dealer.exceptions.CardDeckEmptyException;
import com.ag.poker.dealer.gameobjects.Card;
import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.utils.Constants;
import com.ag.poker.dealer.utils.constants.PokerRoundConstants;

/**
 * @author Arild
 *
 */
public class PokerRoundService implements PokerRoundConstants {
	
	private int round_state;
	private Handler pokerRoundMsgHandler;
	private static CardDeckHandler cardDeckHandler;
	private static PlayerHandler playerHandler;
	
	private int dealer = -1;
	private int smallBlind;
	private int bigBlind;
	
	private static boolean bigBlindPaid = false;
	private static boolean smallBlindPaid = false;
	
	public PokerRoundService(Handler pokerRoundMsgHandler) {
		round_state = FLAG_ROUND_STATE_START_NEW_ROUND;
		this.pokerRoundMsgHandler = pokerRoundMsgHandler;
		
		PokerRoundService.cardDeckHandler = new CardDeckHandler();
		PokerRoundService.playerHandler = new PlayerHandler();
	}

	/**
	 * @return the round_state
	 */
	public int getRound_state() {
		return round_state;
	}
	
	/**
	 * @param round_state the round_state to set
	 */
	public void setRound_state(int round_state) {
		this.round_state = round_state;
	}
	
	/**
	 * @return the pokerRoundMsgHandler
	 */
	public Handler getPokerRoundMsgHandler() {
		return pokerRoundMsgHandler;
	}

	/**
	 * @return the cardDeckHandler
	 */
	public static CardDeckHandler getCardDeckHandler() {
		return cardDeckHandler;
	}

	/**
	 * @return the playerHandler
	 */
	public static PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	private ArrayList<Card> drawCardsForTable() throws CardDeckEmptyException, IOException {
		ArrayList<Card> drawnCards = new ArrayList<Card>();
		
		switch (round_state) {
		case FLAG_ROUND_STATE_START_NEW_ROUND:
			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(true));
			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(false));
			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(false));
			round_state = FLAG_ROUND_STATE_FLOP;
			break;
		case FLAG_ROUND_STATE_FLOP:
			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(true));
			round_state = FLAG_ROUND_STATE_TURN;
			break;
		case FLAG_ROUND_STATE_TURN:
			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(true));
			round_state = FLAG_ROUND_STATE_FINISH_ROUND;
			break;
		case FLAG_ROUND_STATE_RIVER:
			round_state = FLAG_ROUND_STATE_FINISH_ROUND;
			break;
		case FLAG_ROUND_STATE_FINISH_ROUND:
			startNewRound();
			break;
		default:
			Log.e(Constants.TAG, "Illegal state: " + round_state);
			break;
		}
		
		return drawnCards;
	}
	
	public void nextStage() throws CardDeckEmptyException, IOException {
		switch (round_state) {
		case FLAG_ROUND_STATE_START_NEW_ROUND:
			startNewRound();
			round_state = FLAG_ROUND_STATE_WAITING_FOR_BLINDS;
			break;
		case FLAG_ROUND_STATE_WAITING_FOR_BLINDS:
			if(smallBlindPaid && bigBlindPaid) {
				round_state = FLAG_ROUND_STATE_WAITING_FOR_BETS;
			}

		default:
			break;
		}
	}
	
	private void startNewRound() throws CardDeckEmptyException, IOException {
		PokerRoundService.getCardDeckHandler().resetCardDeck();
		
		if(PlayerHandler.getPlayers() != null && !PlayerHandler.getPlayers().isEmpty()) {

			dealCardsToPlayers();
			Dealer.networkService.announceNewRound(PlayerHandler.getPlayers());
		}
		
		pokerRoundMsgHandler.sendEmptyMessage(FLAG_ROUND_STATE_START_NEW_ROUND);
	}
	
	private void dealCardsToPlayers() throws CardDeckEmptyException {
		for (Player player : PlayerHandler.getPlayers()) {
			player.setCard1(PokerRoundService.cardDeckHandler.drawCardForPlayer());
			player.setCard2(PokerRoundService.cardDeckHandler.drawCardForPlayer());
		}
	}
	

	
	private void collectBets() {
//		Dealer.networkService.askPlayerForBets(player)
	}
}
