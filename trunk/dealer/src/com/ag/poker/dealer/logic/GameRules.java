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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import com.ag.poker.dealer.gameobjects.card.Card;
import com.ag.poker.dealer.gameobjects.card.CardColor;
import com.ag.poker.dealer.gameobjects.card.CardValue;
import com.ag.poker.dealer.gameobjects.player.Player;
import com.ag.poker.dealer.gameobjects.player.Ranking;
import com.ag.poker.dealer.utils.constants.PokerHandsConstants;

/**
 * @author Arild
 *
 */
public class GameRules implements PokerHandsConstants {
	
	public static TreeMap<Integer, ArrayList<Ranking>> rankedPlayers = new TreeMap<Integer, ArrayList<Ranking>>();
	
	private static void evaluateHand(Player player) {
		ArrayList<Card> communityCards = PokerRoundService.getCardDeckHandler().getCardsOnTable();
		ArrayList<Card> playerHand = new ArrayList<Card>(player.getCards());
		
		ArrayList<Card> mergedList = new ArrayList<Card>(communityCards);
		mergedList.addAll(playerHand);
		
		Collections.sort(mergedList, Collections.reverseOrder());
		
		Ranking rank = new Ranking(0, player, null);
		
		//Royal flush
		if(rank.getScore() <= 0 && (hasRoyalFlush(mergedList, rank))) {
			addRankingToRankedPlayers(rank);
		}
		
		//Straight flush
		else if(rank.getScore() <= 0 && (hasStraightFlush(mergedList, rank))) {
			addRankingToRankedPlayers(rank);
		}
		
		//Four of a kind
		else if(rank.getScore() <= 0 && (hasFourOfAKind(mergedList, rank))) {
			addRankingToRankedPlayers(rank);
		}
		
		//Full house
		else if(rank.getScore() <= 0 && (hasFullHouse(mergedList, rank))) {
			addRankingToRankedPlayers(rank);
		}
		
		//Flush
		else if(rank.getScore() <= 0 && (hasFlush(mergedList, rank))) {
			addRankingToRankedPlayers(rank);
		}
		
		//Straight
		else if(rank.getScore() <= 0 && (hasStraight(mergedList, rank))) {
			addRankingToRankedPlayers(rank);
		}
		
		//Three of a kind
		else if(rank.getScore() <= 0 && (hasThreeOfAKind(mergedList, rank))) {
			addRankingToRankedPlayers(rank);
		}
		
	}
	
	private static void addRankingToRankedPlayers(Ranking rank) {
		int score = rank.getScore();
		if(rankedPlayers.get(score) == null) {
			rankedPlayers.put(score, new ArrayList<Ranking>());
		}
		rankedPlayers.get(score).add(rank);
	}

	private static boolean hasRoyalFlush(ArrayList<Card> cards, Ranking rank) {
		ArrayList<Card> royalFlushDraw = getStraightFlush(cards);
		
		if(royalFlushDraw != null) {
			int numberOfCardsInRoyalFlush = 0;
			for (Card card : royalFlushDraw) {
				if(card.cardValue == CardValue.ACE) {
					numberOfCardsInRoyalFlush++;
				} else if(card.cardValue == CardValue.KING) {
					numberOfCardsInRoyalFlush++;
				} else if(card.cardValue == CardValue.QUEEN) {
					numberOfCardsInRoyalFlush++;
				} else if(card.cardValue == CardValue.JACK) {
					numberOfCardsInRoyalFlush++;
				} else if(card.cardValue == CardValue.TEN) {
					numberOfCardsInRoyalFlush++;
				}
			}
			if(numberOfCardsInRoyalFlush == 5) {
				rank.setCards(royalFlushDraw);
				rank.setScore(HAND_ROYAL_FLUSH);
				return true;
			}
		}
		
		
		return false;
	}
	

	private static boolean hasStraightFlush(ArrayList<Card> cards, Ranking rank) {
		ArrayList<Card> straightFlushDraw = getStraightFlush(cards);
		
		if(straightFlushDraw != null) {
			rank.setCards(straightFlushDraw);
			rank.setScore(HAND_STRAIGHT_FLUSH + straightFlushDraw.get(straightFlushDraw.size()-1).getRelativeValue());
			return true;
		}
		
		return false;
	}
	
	private static boolean hasFourOfAKind(ArrayList<Card> cards, Ranking rank) {
		
		ArrayList<Card> fourOfAKind = getSameValueCards(cards, 4);
		
		if(fourOfAKind != null) {
			removeCardValueFromList(cards, fourOfAKind.get(0).cardValue);
			cards = getHighCards(cards, 1);
			cards.addAll(fourOfAKind);
			
			rank.setCards(cards);
			rank.setScore(HAND_FOUR_OF_A_KIND + fourOfAKind.get(0).getRelativeValue());
			
			return true;
		}
		
		return false;
	}
	
	private static boolean hasFullHouse(ArrayList<Card> cards, Ranking rank) {
		ArrayList<Card> temp = new ArrayList<Card>(cards);
		ArrayList<Card> threeOfAKind = getSameValueCards(temp, 3);
		
		if(threeOfAKind != null) {
			temp.removeAll(threeOfAKind);
			ArrayList<Card> pair = getSameValueCards(temp, 2);
			
			if(pair != null) {
				cards = threeOfAKind;
				cards.addAll(pair);
				
				rank.setCards(cards);
				rank.setScore(HAND_FULL_HOUSE + threeOfAKind.get(0).getRelativeValue());
				
				return true;
			}
		}
		return false;
	}
	
	private static boolean hasFlush(ArrayList<Card> cards, Ranking rank) {
		HashMap<CardColor, ArrayList<Card>> colorSortedCards = sortCardsAfterColor(cards);
		
		for (ArrayList<Card> sortedCards : colorSortedCards.values()) {
			if(sortedCards.size() >= 5) {
				if(sortedCards.size() > 5) {
					sortedCards.subList(5, sortedCards.size()-1).clear();
				}
				
				rank.setCards(sortedCards);
				rank.setScore(HAND_FLUSH + sortedCards.get(0).getRelativeValue());
				
				return true;
			}
		}
		return false;
	}
	
	private static boolean hasStraight(ArrayList<Card> cards, Ranking rank) {
		ArrayList<Card> sortedCards = new ArrayList<Card>(cards);
		
		Collections.sort(sortedCards, new CardValueComparator());
		removeDuplicateCardValueFromSortedList(sortedCards);
		
		ArrayList<Card> straight = getStraight(sortedCards);
		
		if(straight == null && sortedCards.get(0).cardValue == CardValue.ACE) {
			Card removedCard = sortedCards.remove(0);
			sortedCards.add(removedCard);
			
			straight = getStraight(sortedCards);
		}
		
		if(straight != null) {
			if(straight.size() > 5) {
				straight.subList(5, straight.size()-1).clear();
			}
			
			rank.setCards(straight);
			rank.setScore(HAND_STRAIGHT);
			
			return true;
		}
		
		return false;
	}
	
	private static boolean hasThreeOfAKind(ArrayList<Card> mergedList, Ranking rank) {
		ArrayList<Card> threeOfAKind = getSameValueCards(mergedList, 3);
		
		if(threeOfAKind != null && threeOfAKind.size() == 3) {
			removeCardValueFromList(mergedList, threeOfAKind.get(0).cardValue);
			Collections.sort(mergedList, new CardValueComparator());
			Card highCard1 = mergedList.get(0);
			Card highCard2 = mergedList.get(1);
			
			mergedList = threeOfAKind;
			mergedList.add(highCard1);
			mergedList.add(highCard2);
			
			rank.setCards(mergedList);
			rank.setScore(HAND_THREE_OF_A_KIND + threeOfAKind.get(0).getRelativeValue());
			
			return true;
		}
		return false;
	}
	
	private static ArrayList<Card> getStraightFlush(ArrayList<Card> cards) {
		
		HashMap<CardColor, ArrayList<Card>> colorSortedCards = sortCardsAfterColor(cards);
		ArrayList<Card> straightFlush = null;
		
		for (ArrayList<Card> sortedCards : colorSortedCards.values()) {
			straightFlush = getStraight(sortedCards);
			
			if(straightFlush == null && sortedCards.get(0).cardValue == CardValue.ACE) {
				Card removedCard = sortedCards.remove(0);
				sortedCards.add(removedCard);
				
				straightFlush = getStraight(sortedCards);
			}
			
			if(straightFlush != null) {
				return straightFlush;
			}
		}
		
		return null;
	}
	
	private static ArrayList<Card> getStraight(ArrayList<Card> cards) {
		ArrayList<Card> straight = new ArrayList<Card>(5);
		
		if(cards.size() >= 5) {
			Card previousCard = null;
			for (Card card : cards) {
				if(previousCard != null) {
					if(card.isCardValuePreviousInSequence(previousCard)) {
						if(straight.isEmpty()) {
							straight.add(previousCard);
						}
						straight.add(card);
					} else {
						if(straight.size() == 5) {
							return straight;
						}
						straight.clear();
					}
					
				}
				previousCard = card;
			}
			
			if(straight.size() == 5) {
				return straight;
			} 
		}
		
		return null;
	}
	
	private static ArrayList<Card> getSameValueCards(ArrayList<Card> cards, int numberOfCardsWithSameValue) {
		ArrayList<Card> valueSortedCards = new ArrayList<Card>(cards);
		Collections.sort(valueSortedCards, new CardValueComparator());
		
		ArrayList<Card> cardsWithSameValue = new ArrayList<Card>();
		Card previousCard = null;
		for (Card card : valueSortedCards) {
			if(previousCard != null) {
				if(previousCard.cardValue == card.cardValue) {
					if(cardsWithSameValue.isEmpty()) {
						cardsWithSameValue.add(previousCard); //add the previous card
					}
					cardsWithSameValue.add(card);
				} else {
					if(cardsWithSameValue.size() == numberOfCardsWithSameValue) {
						break;
					}
					cardsWithSameValue.clear();
				}
			} 
			previousCard = card;
		}
		
		if(cardsWithSameValue.size() == numberOfCardsWithSameValue) {
			return cardsWithSameValue;
		}
		
		return null;
	}
	
	private static HashMap<CardColor, ArrayList<Card>> sortCardsAfterColor(ArrayList<Card> cards) {
		HashMap<CardColor, ArrayList<Card>> colorSortedCards = new HashMap<CardColor, ArrayList<Card>>();
		
		for (Card card : cards) {
			if(!colorSortedCards.containsKey(card.cardColor)) {
				colorSortedCards.put(card.cardColor, new ArrayList<Card>());
			}
			colorSortedCards.get(card.cardColor).add(card);
		}
		
		return colorSortedCards;
	}
	
	private static void removeCardValueFromList(ArrayList<Card> cards, CardValue value) {
		ArrayList<Card> temp = new ArrayList<Card>(cards);
		for (Card card : temp) {
			if(card.cardValue == value) {
				cards.remove(card);
			}
		}
	}
	
	private static void removeDuplicateCardValueFromSortedList(ArrayList<Card> sortedList) {
		ArrayList<Card> temp = new ArrayList<Card>(sortedList);
		
		Card previousCard = null;
		for (Card card : temp) {
			if(previousCard != null) {
				if(previousCard.cardValue == card.cardValue) {
					sortedList.remove(card);
				}
			}
			previousCard = card;
		}
	}
	
	public static ArrayList<Ranking> getWinners() {
		rankedPlayers.clear();
		ArrayList<Ranking> winners = new ArrayList<Ranking>();

		for (Player player : PlayerHandler.getPlayers()) {
			evaluateHand(player);
		}
		
		Ranking previousRank = null;
		if(!rankedPlayers.isEmpty()) {
			ArrayList<Ranking> topPlayers = rankedPlayers.get(rankedPlayers.lastKey());
			
			if(topPlayers.size() == 1) {
				winners.add(topPlayers.get(0));
			} else {
				for (Ranking rank : topPlayers) {
					if(previousRank != null) {
						short playerHand = hasHigherCardThanPlayer(rank.getCards(), previousRank.getCards());
						if(playerHand == PLAYER_HAND_HIGHER) {
							winners.clear();
							winners.add(rank);
						} else if(playerHand == PLAYER_HAND_SAME) {
							if(winners.isEmpty()) {
								winners.add(previousRank);
							}
							winners.add(rank);
						}
					}
					previousRank = rank;
				}
			}
			
		}
		
		return winners;
	}
	
	private static short hasHigherCardThanPlayer(ArrayList<Card> thisPlayersCards, ArrayList<Card> otherPlayersCards) {
		Collections.sort(thisPlayersCards, new CardValueComparator());
		Collections.sort(otherPlayersCards, new CardValueComparator());
		
		int size = thisPlayersCards.size();
		for(int i = 0; i < size; i++) {
			int thisCardOrdinal = thisPlayersCards.get(i).cardValue.ordinal();
			int otherCardOrdinal = otherPlayersCards.get(i).cardValue.ordinal();
			
			if(thisCardOrdinal > otherCardOrdinal) {
				return PLAYER_HAND_HIGHER;
			} else if(thisCardOrdinal < otherCardOrdinal) {
				return PLAYER_HAND_LOWER;
			}
		}
		
		return PLAYER_HAND_SAME;
	}
	
	private static ArrayList<Card> getHighCards(ArrayList<Card> cards, int numberOfCards) {
		Collections.sort(cards, new CardValueComparator());
		ArrayList<Card> highCards = new ArrayList<Card>();
		
		for(int i = 0; i < numberOfCards && i < cards.size(); i++) {
			highCards.add(cards.get(i));
		}
		
		return highCards;
	}
	
	
//	private static ArrayList<Ranking> getEquallyRankedPlayers() {
//		ArrayList<Ranking> ranks = new ArrayList<Ranking>(ranking);
//		ArrayList<Ranking> bestPlayers = new ArrayList<Ranking>();
//		
//		Collections.sort(ranks, Collections.reverseOrder());
//		
//		Ranking previousRank = null;
//		for (Ranking ranking : ranks) {
//			if(previousRank != null) {
//				if(ranking.getScore())
//			}
//		}
//		
//		return ranks;
//	}

	private static class CardValueComparator implements Comparator<Card> {

		@Override
		public int compare(Card card1, Card card2) {
			return card2.cardValue.compareTo(card1.cardValue);
		}
	}
	
	
	private static class CardValueAceFirstComparator implements Comparator<Card> {

		@Override
		public int compare(Card card1, Card card2) {
			if(card2.cardValue == CardValue.ACE) {
				if(card1.cardValue == CardValue.ACE) {
					return 0;
				} else {
					return -1;
				}
			} else {
				return card1.cardValue.compareTo(card2.cardValue);
			}
		}
	}

}
