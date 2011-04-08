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

import android.os.Handler;

import com.ag.poker.dealer.Dealer;
import com.ag.poker.dealer.exceptions.CardDeckEmptyException;
import com.ag.poker.dealer.gameobjects.player.Player;
import com.ag.poker.dealer.utils.constants.PokerRoundConstants;

/**
 * @author Arild
 *
 */
public class PokerRoundService implements PokerRoundConstants {
	
	private int round_state;
	private int draw_state;
	private Handler pokerRoundMsgHandler;
	private static CardDeckHandler cardDeckHandler;
	private static PlayerHandler playerHandler;
	
	private static boolean bigBlindPaid = false;
	private static boolean smallBlindPaid = false;
	
	public PokerRoundService(Handler pokerRoundMsgHandler) {
		round_state = FLAG_ROUND_STATE_START_NEW_ROUND;
		draw_state = FLAG_ROUND_STATE_FLOP;
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
	 * @return the draw_state
	 */
	public int getDraw_state() {
		return draw_state;
	}

	/**
	 * @param draw_state the draw_state to set
	 */
	public void setDraw_state(int draw_state) {
		this.draw_state = draw_state;
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

//	private ArrayList<Card> drawCardsForTable() throws CardDeckEmptyException, IOException {
//		ArrayList<Card> drawnCards = new ArrayList<Card>();
//		
//		switch (round_state) {
//		case FLAG_ROUND_STATE_START_NEW_ROUND:
//			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(true));
//			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(false));
//			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(false));
//			round_state = FLAG_ROUND_STATE_FLOP;
//			break;
//		case FLAG_ROUND_STATE_FLOP:
//			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(true));
//			round_state = FLAG_ROUND_STATE_TURN;
//			break;
//		case FLAG_ROUND_STATE_TURN:
//			drawnCards.add(PokerRoundService.getCardDeckHandler().drawCardForTable(true));
//			round_state = FLAG_ROUND_STATE_FINISH_ROUND;
//			break;
//		case FLAG_ROUND_STATE_RIVER:
//			round_state = FLAG_ROUND_STATE_FINISH_ROUND;
//			break;
//		case FLAG_ROUND_STATE_FINISH_ROUND:
//			startNewRound();
//			break;
//		default:
//			Log.e(Constants.TAG, "Illegal state: " + round_state);
//			break;
//		}
//		
//		return drawnCards;
//	}
	
	public void nextStage() throws CardDeckEmptyException, IOException {
		switch (round_state) {
		case FLAG_ROUND_STATE_START_NEW_ROUND:
			startNewRound();
			round_state = FLAG_ROUND_STATE_COLLECT_BETS;
			break;
		case FLAG_ROUND_STATE_WAITING_FOR_BLINDS:
			if(smallBlindPaid && bigBlindPaid) {
				Dealer.networkService.askPlayerForBets(PlayerHandler.getPlayers().getBetter());
				round_state = FLAG_ROUND_STATE_WAITING_FOR_BETS;
			}
			break;
		case FLAG_ROUND_STATE_COLLECT_BETS:
			Player nextBettingPlayer = PlayerHandler.getPlayers().getNextBettingPlayer();
			if(nextBettingPlayer != null) {
				Dealer.networkService.askPlayerForBets(PlayerHandler.getPlayers().getNextBettingPlayer());
				round_state = FLAG_ROUND_STATE_WAITING_FOR_BETS;
			} else {
				dealCardsToTable();
			}
		case FLAG_ROUND_STATE_WAITING_FOR_BETS:
			if(PlayerHandler.getPlayers().isDealerBetter()) {
			}
			break;
		default:
			break;
		}
	}
	
	private void startNewRound() throws CardDeckEmptyException, IOException {
		PokerRoundService.getCardDeckHandler().resetCardDeck();
		round_state = FLAG_ROUND_STATE_START_NEW_ROUND;
		draw_state = FLAG_ROUND_STATE_FLOP;
		
		if(PlayerHandler.getPlayers() != null && !PlayerHandler.getPlayers().isEmpty()) {

			dealCardsToPlayers();
			Dealer.networkService.announceNewRound(PlayerHandler.getPlayers());
		}
		
		
		
		pokerRoundMsgHandler.sendEmptyMessage(FLAG_ROUND_STATE_START_NEW_ROUND);
	}
	
	private void dealCardsToTable() throws CardDeckEmptyException {
		switch (draw_state) {
		case FLAG_ROUND_STATE_FLOP:
			cardDeckHandler.drawCardForTable(3);
			draw_state = FLAG_ROUND_STATE_TURN;
			break;
		case FLAG_ROUND_STATE_TURN:
			cardDeckHandler.drawCardForTable(1);
			draw_state = FLAG_ROUND_STATE_RIVER;
			break;
		case FLAG_ROUND_STATE_RIVER:
			cardDeckHandler.drawCardForTable(1);
			draw_state = FLAG_ROUND_STATE_FLOP;
			break;
		default:
			break;
		}
	}
	
	private void dealCardsToPlayers() throws CardDeckEmptyException {
		for (Player player : PlayerHandler.getPlayers()) {
			player.setCard1(PokerRoundService.cardDeckHandler.drawCardForPlayer());
			player.setCard2(PokerRoundService.cardDeckHandler.drawCardForPlayer());
		}
	}
}
