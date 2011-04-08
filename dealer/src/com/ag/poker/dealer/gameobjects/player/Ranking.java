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

package com.ag.poker.dealer.gameobjects.player;

import java.util.ArrayList;

import com.ag.poker.dealer.gameobjects.card.Card;

/**
 * @author Arild
 *
 */
public class Ranking implements Comparable<Ranking> {
	
	private int score;
	private Player player;
	private ArrayList<Card> cards;
	
	public Ranking() {
		this.score = 0;
		this.player = null;
		this.cards = new ArrayList<Card>();
	}

	public Ranking(int score, Player player, ArrayList<Card> cards) {
		super();
		this.score = score;
		this.player = player;
		this.cards = cards;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the cards
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * @param cards the cards to set
	 */
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	@Override
	public int compareTo(Ranking another) {
		return Integer.valueOf(score).compareTo(Integer.valueOf(another.getScore()));
	}
	
}
