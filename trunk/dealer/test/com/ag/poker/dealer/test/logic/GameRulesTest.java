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

package com.ag.poker.dealer.test.logic;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.ag.poker.dealer.exceptions.CardDeckEmptyException;
import com.ag.poker.dealer.gameobjects.card.Card;
import com.ag.poker.dealer.gameobjects.player.Player;
import com.ag.poker.dealer.gameobjects.player.PlayerList;
import com.ag.poker.dealer.gameobjects.player.Ranking;
import com.ag.poker.dealer.logic.GameRules;
import com.ag.poker.dealer.logic.PlayerHandler;
import com.ag.poker.dealer.logic.PokerRoundService;
import com.ag.poker.dealer.test.utils.TestToolUtil;
import com.ag.poker.dealer.utils.constants.PokerHandsConstants;

/**
 * @author Arild
 *
 */
public class GameRulesTest extends TestCase implements PokerHandsConstants {
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		new PokerRoundService(null);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPreconditions() {
		assertNotNull(PokerRoundService.getCardDeckHandler());
	}
	
	public void testRoyalFlushShouldReturnPlayerscoreOf9000() throws CardDeckEmptyException {
		PlayerList players = TestToolUtil.createTestPlayers(1);
		
		Player player = players.get(0);
		player.setCard1(Card.SPADE_KING);
		player.setCard2(Card.SPADE_TEN);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.SPADE_ACE, Card.SPADE_QUEEN, Card.SPADE_JACK, Card.DIAMOND_QUEEN, Card.CLUB_EIGHT);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_ROYAL_FLUSH, winners.get(0).getScore());
	}
	
	public void testShouldNotGiveRoyalFlushIfOffSuite() throws CardDeckEmptyException {
		PlayerList players = TestToolUtil.createTestPlayers(1);
		Player player = players.get(0);
		
		player.setCard1(Card.SPADE_KING);
		player.setCard2(Card.SPADE_TEN);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.DIAMOND_ACE, Card.SPADE_QUEEN, Card.SPADE_JACK, Card.DIAMOND_FOUR, Card.CLUB_EIGHT);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player.getId(), winners.get(0).getPlayer().getId());
		assertTrue(winners.get(0).getScore() < HAND_ROYAL_FLUSH);
		
	}
	
	public void testStraightFlushShouldReturnScoreBetween8000and9000() {
		PlayerList players = TestToolUtil.createTestPlayers(1);
		Player player = players.get(0);
		
		player.setCard1(Card.DIAMOND_SIX);
		player.setCard2(Card.DIAMOND_NINE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.DIAMOND_SEVEN, Card.DIAMOND_FIVE, Card.CLUB_FOUR, Card.DIAMOND_EIGHT, Card.CLUB_SIX);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		int score = winners.get(0).getScore();
		
		assertEquals(1, winners.size());
		assertEquals(player.getId(), winners.get(0).getPlayer().getId());
		assertTrue("Score should be between " + HAND_STRAIGHT_FLUSH + " and " + HAND_ROYAL_FLUSH + ", but was: "+ score, score >= HAND_STRAIGHT_FLUSH && score < HAND_ROYAL_FLUSH);
	}
	
	public void testFlushWhichAlsoHasALowerStraightShouldReturnScoreBetween8000and9000() {
		PlayerList players = TestToolUtil.createTestPlayers(1);
		Player player = players.get(0);
		
		player.setCard1(Card.DIAMOND_ACE);
		player.setCard2(Card.DIAMOND_TEN);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.DIAMOND_EIGHT, Card.DIAMOND_FIVE, Card.DIAMOND_THREE, Card.DIAMOND_FOUR, Card.DIAMOND_TWO);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		int score = winners.get(0).getScore();
		
		assertEquals(1, winners.size());
		assertEquals(player.getId(), winners.get(0).getPlayer().getId());
		assertTrue(score >= HAND_STRAIGHT_FLUSH && score < HAND_ROYAL_FLUSH);
	}
	
	public void testStraightFlushAceCountAsOne() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.CLUB_SIX);
		player1.setCard2(Card.CLUB_ACE);
		
		player2.setCard1(Card.DIAMOND_ACE);
		player2.setCard2(Card.DIAMOND_KING);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_FIVE, Card.DIAMOND_TWO, Card.DIAMOND_THREE, Card.DIAMOND_FOUR, Card.DIAMOND_FIVE);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player2.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_STRAIGHT_FLUSH, winners.get(0).getScore());
		
	}
	
	public void testStraightFlushAceCountAsFourteen() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.DIAMOND_NINE);
		player1.setCard2(Card.CLUB_ACE);
		
		player2.setCard1(Card.DIAMOND_ACE);
		player2.setCard2(Card.HEART_ACE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_FIVE, Card.DIAMOND_JACK, Card.DIAMOND_TEN, Card.DIAMOND_QUEEN, Card.DIAMOND_KING);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player2.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_ROYAL_FLUSH, winners.get(0).getScore());
	}
	
	public void testStraightFlushOnTableGivesATie() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.DIAMOND_FIVE);
		player1.setCard2(Card.CLUB_ACE);
		
		player2.setCard1(Card.CLUB_FIVE);
		player2.setCard2(Card.HEART_ACE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.DIAMOND_NINE, Card.DIAMOND_JACK, Card.DIAMOND_TEN, Card.DIAMOND_QUEEN, Card.DIAMOND_KING);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(2, winners.size());
		
		int score1 = winners.get(0).getScore();
		int score2 = winners.get(1).getScore();
		
		assertTrue("Player1 should have a score between 8000 and 9000, but was: " + score1, score1 > HAND_STRAIGHT_FLUSH && score1 < HAND_ROYAL_FLUSH);
		assertTrue("Player1 should have a score between 8000 and 9000, but was: " + score1, score1 > HAND_STRAIGHT_FLUSH && score1 < HAND_ROYAL_FLUSH);
		assertEquals(score1, score2);
	}
	
	public void testFourOfAKindShouldGiveScoreBetween7000And8000() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.DIAMOND_ACE);
		
		player2.setCard1(Card.SPADE_FIVE);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_FIVE, Card.HEART_ACE, Card.CLUB_ACE, Card.DIAMOND_FIVE, Card.DIAMOND_KING);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winners.get(0).getPlayer().getId());
		assertTrue("Expected score between " + HAND_FOUR_OF_A_KIND + " and " + HAND_STRAIGHT_FLUSH+ ", but was " + winners.get(0).getScore(), HAND_FOUR_OF_A_KIND <= winners.get(0).getScore() && winners.get(0).getScore() < HAND_STRAIGHT_FLUSH);
		
	}
	
	public void testFourOfAKindWithHighCardOnTableShouldGiveATie() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.CLUB_FIVE);
		player1.setCard2(Card.DIAMOND_FIVE);
		
		player2.setCard1(Card.SPADE_FIVE);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.SPADE_KING, Card.HEART_KING, Card.CLUB_ACE, Card.CLUB_KING, Card.DIAMOND_KING);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(2, winners.size());
		
		int score1 = winners.get(0).getScore();
		int score2 = winners.get(1).getScore();
		
		assertTrue(HAND_FOUR_OF_A_KIND <= score1 && score1 < HAND_STRAIGHT_FLUSH);
		assertEquals(score1, score2);
	}
	
	public void testHighestCardShouldWinFourOfAKind() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.CLUB_JACK);
		player1.setCard2(Card.HEART_TWO);
		
		player2.setCard1(Card.DIAMOND_QUEEN);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.SPADE_KING, Card.HEART_KING, Card.CLUB_TWO, Card.CLUB_KING, Card.DIAMOND_KING);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player2.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_FOUR_OF_A_KIND, winners.get(0).getScore());
		
	}
	
	public void testFullHouseShouldGiveScoreBetween6000And7000() {
		PlayerList players = TestToolUtil.createTestPlayers(1);
		
		Player player1 = players.get(0);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.HEART_EIGHT);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_EIGHT, Card.HEART_ACE, Card.DIAMOND_EIGHT, Card.DIAMOND_FIVE, Card.SPADE_KING);
		
		ArrayList<Ranking> winners = GameRules.getWinners();

		assertEquals(1, winners.size());
		
		int score1 = winners.get(0).getScore();
		
		assertTrue("Player1 should have a score between 6000 and 7000, but was: " + score1, score1 > HAND_FULL_HOUSE && score1 < HAND_FOUR_OF_A_KIND);
	}
	
	public void testHighestThreeOfAKindWinsInFullHouse() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.HEART_TWO);
		player1.setCard2(Card.HEART_EIGHT);
		
		player2.setCard1(Card.CLUB_FIVE);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_EIGHT, Card.HEART_ACE, Card.DIAMOND_EIGHT, Card.DIAMOND_FIVE, Card.SPADE_TWO);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_FULL_HOUSE, winners.get(0).getScore());
	}
	
	public void testHighestPairWinsIfTwoPlayersHaveSameThreeOfAKindInFullHouse() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.HEART_EIGHT);
		
		player2.setCard1(Card.SPADE_EIGHT);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_EIGHT, Card.HEART_ACE, Card.DIAMOND_EIGHT, Card.DIAMOND_FIVE, Card.SPADE_KING);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_FULL_HOUSE, winners.get(0).getScore());
	}
	
	public void testFullHouseOnTableShouldGiveATie() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.HEART_EIGHT);
		
		player2.setCard1(Card.SPADE_EIGHT);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.HEART_JACK, Card.CLUB_JACK, Card.CLUB_NINE, Card.SPADE_JACK, Card.SPADE_NINE);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(2, winners.size());
		
		int score1 = winners.get(0).getScore();
		int score2 = winners.get(1).getScore();
		
		assertTrue("Player1 should have a score between 6000 and 7000, but was: " + score1, score1 > HAND_FULL_HOUSE && score1 < HAND_FOUR_OF_A_KIND);
		assertTrue("Player2 should have a score between 6000 and 7000, but was: " + score2, score2 > HAND_FULL_HOUSE && score2 < HAND_FOUR_OF_A_KIND);
		assertEquals(score1, score2);
	}
	
	public void testFlushShouldGiveScoreBetween5000And6000() {
		PlayerList players = TestToolUtil.createTestPlayers(1);
		
		Player player1 = players.get(0);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.SPADE_FOUR);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_EIGHT, Card.SPADE_EIGHT, Card.DIAMOND_EIGHT, Card.SPADE_FIVE, Card.SPADE_KING);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		int score = winners.get(0).getScore();
		
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winners.get(0).getPlayer().getId());
		assertTrue(score >= HAND_FLUSH && score < HAND_FULL_HOUSE);
		
	}
	
	public void testFlushOnTableShouldGiveATie() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.HEART_EIGHT);
		
		player2.setCard1(Card.SPADE_EIGHT);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.DIAMOND_ACE, Card.DIAMOND_QUEEN, Card.DIAMOND_SEVEN, Card.DIAMOND_THREE, Card.DIAMOND_FOUR);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(2, winners.size());
		
		int score1 = winners.get(0).getScore();
		int score2 = winners.get(1).getScore();
		
		assertTrue(HAND_FLUSH <= score1 && score1 < HAND_FULL_HOUSE);
		assertEquals(score1, score2);
	}
	
	public void testHighestCardInFlushShouldWin() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.SPADE_JACK);
		player1.setCard2(Card.SPADE_FOUR);
		
		player2.setCard1(Card.SPADE_TEN);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.SPADE_KING, Card.SPADE_ACE, Card.SPADE_QUEEN, Card.DIAMOND_THREE, Card.SPADE_THREE);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_FLUSH, winners.get(0).getScore());
		
	}
	
	public void testStraightShouldGiveScoreBetween4000And5000() {
		PlayerList players = TestToolUtil.createTestPlayers(1);
		
		Player player1 = players.get(0);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.SPADE_FOUR);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_TWO, Card.DIAMOND_FIVE, Card.DIAMOND_EIGHT, Card.HEART_THREE, Card.CLUB_THREE);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_STRAIGHT, winners.get(0).getScore());
	}
	
	public void testHighestCardInStraightShouldWin() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.SPADE_FOUR);
		
		player2.setCard1(Card.SPADE_TEN);
		player2.setCard2(Card.SPADE_SIX);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_TWO, Card.DIAMOND_FIVE, Card.HEART_FOUR, Card.HEART_THREE, Card.CLUB_THREE);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player2.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_STRAIGHT, winners.get(0).getScore());
		
	}
	
	public void testStraightOnTableShouldGiveATie() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.SPADE_FOUR);
		
		player2.setCard1(Card.SPADE_TEN);
		player2.setCard2(Card.SPADE_SIX);

		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_TWO, Card.DIAMOND_FIVE, Card.HEART_FOUR, Card.HEART_THREE, Card.HEART_SIX);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(2, winners.size());
		
		int score1 = winners.get(0).getScore();
		int score2 = winners.get(1).getScore();
		
		assertTrue("Player1 should have a score between 4000 and 5000, but was: " + score1, score1 > HAND_STRAIGHT && score1 < HAND_FLUSH);
		assertTrue("Player2 should have a score between 4000 and 5000, but was: " + score2, score2 > HAND_STRAIGHT && score2 < HAND_FLUSH);
		assertEquals(score1, score2);
	}
	
	public void testThreeOfAKindShouldGiveScoreBetween3000And4000() {
		PlayerList players = TestToolUtil.createTestPlayers(1);
		
		Player player1 = players.get(0);
		
		player1.setCard1(Card.SPADE_ACE);
		player1.setCard2(Card.SPADE_FOUR);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.CLUB_TWO, Card.DIAMOND_FOUR, Card.DIAMOND_EIGHT, Card.HEART_FOUR, Card.CLUB_THREE);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_THREE_OF_A_KIND, winners.get(0).getScore());
	}
	
	public void testThreeOfAKindAndHighestCardsOnTableShouldGiveATie() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.CLUB_TWO);
		player1.setCard2(Card.DIAMOND_FIVE);
		
		player2.setCard1(Card.SPADE_TWO);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.SPADE_KING, Card.HEART_KING, Card.CLUB_ACE, Card.CLUB_KING, Card.HEART_QUEEN);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(2, winners.size());
		
		int score1 = winners.get(0).getScore();
		int score2 = winners.get(1).getScore();
		
		assertTrue("Player1 should have a score between 3000 and 4000, but was: " + score1, score1 > HAND_THREE_OF_A_KIND && score1 < HAND_STRAIGHT);
		assertTrue("Player2 should have a score between 3000 and 4000, but was: " + score2, score2 > HAND_THREE_OF_A_KIND && score2 < HAND_STRAIGHT);
		assertEquals(score1, score2);
	}
	
	public void testHighCardShouldWinThreeOfAKindWhenCheckingRankingList() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.CLUB_ACE);
		player1.setCard2(Card.DIAMOND_THREE);
		
		player2.setCard1(Card.SPADE_QUEEN);
		player2.setCard2(Card.SPADE_JACK);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.SPADE_KING, Card.HEART_KING, Card.CLUB_FOUR, Card.CLUB_KING, Card.HEART_TWO);
		
//		int score1 = GameRules.evaluateHand(player1).getScore();
//		int score2 = GameRules.evaluateHand(player2).getScore();
//		
//		assertTrue("Player1 should have a score between 3000 and 4000, but was: " + score1, score1 > HAND_THREE_OF_A_KIND && score1 < HAND_STRAIGHT);
//		assertTrue("Player2 should have a score between 3000 and 4000, but was: " + score2, score2 > HAND_THREE_OF_A_KIND && score2 < HAND_STRAIGHT);
//		assertEquals(score1, score2);
		
		ArrayList<Ranking> winners = GameRules.getWinners();

		Ranking winner = winners.get(0);

		assertNotNull(winner);
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winner.getPlayer().getId());
		assertTrue(winner.getScore() >= HAND_THREE_OF_A_KIND && winner.getScore() < HAND_STRAIGHT);
	}
	
	public void testHighestThreeOfAKindShouldWin() {
		PlayerList players = TestToolUtil.createTestPlayers(2);
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		player1.setCard1(Card.HEART_JACK);
		player1.setCard2(Card.DIAMOND_JACK);
		
		player2.setCard1(Card.CLUB_FIVE);
		player2.setCard2(Card.HEART_FIVE);
		
		PlayerHandler.setPlayers(players);
		
		dealCardsToTable(Card.DIAMOND_QUEEN, Card.DIAMOND_ACE, Card.CLUB_FOUR, Card.CLUB_JACK, Card.SPADE_FIVE);
		
		ArrayList<Ranking> winners = GameRules.getWinners();
		
		assertEquals(1, winners.size());
		assertEquals(player1.getId(), winners.get(0).getPlayer().getId());
		assertEquals(HAND_THREE_OF_A_KIND, winners.get(0).getScore());
		
	}
	
	private void dealCardsToTable(Card...cards ) {
		ArrayList<Card> cardsOnTable = PokerRoundService.getCardDeckHandler().getCardsOnTable();
		
		for (Card card : cards) {
			cardsOnTable.add(card);
		}
	}
	
}
