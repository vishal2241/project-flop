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
import java.util.Collection;
import java.util.Vector;

import com.ag.poker.dealer.utils.Constants;

import android.util.Log;

/**
 * @author Arild
 *
 */
public class PlayerList extends ArrayList<Player> {
	
	Vector<Player> bettingOrder = new Vector<Player>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8954288836960184098L;

	private int dealer;
	private int better;
	
	public PlayerList() {
		super();
		dealer = 0;
		better = dealer + 2;
	}

	public PlayerList(Collection<? extends Player> collection) {
		super(collection);
	}

	public PlayerList(int capacity) {
		super(capacity);
	}

	
	public void updateDealer() {
		dealer++;
		if(dealer >= size()) {
			dealer = 0;
		}
		
		updateBettingOrderList();
	}
	
	private void updateBettingOrderList() {
		bettingOrder.clear();
		
		int firstBetterPosition = getPlayerPosition(getBigBlind().getId()) + 1;
		
		if(firstBetterPosition >= size() && size() > 0) {
			firstBetterPosition = firstBetterPosition % size();
		}
		
		int playerPosition = 0;
		for(int i = 0; i < size(); i++) {
			playerPosition = firstBetterPosition + i;
			
			if(playerPosition >= size() && size() > 0) {
				playerPosition = playerPosition % size();
			}
			
			bettingOrder.add(get(playerPosition));
		}
	}

	public Player getNextBettingPlayer() {
		Player next = bettingOrder.firstElement();
		bettingOrder.remove(next);
		
		return next;
	}
	
	public Player getDealer() {
		return get(dealer);
	}
	
	public Player getSmallBlind() {
		int smallBlind = dealer + 1;
		if(smallBlind >= size() && size() > 0) {
			smallBlind = smallBlind % size();
		}
		
		return get(smallBlind);
	}
	
	public Player getBigBlind() {
		int bigBlind = dealer + 2;
		if(bigBlind >= size() && size() > 0) {
			bigBlind = bigBlind % size();
		}
		
		return get(bigBlind);
	}
	
	public Player getBetter() {
		return get(better);
	}
	
	public boolean contains(String playerId) {
		for (Player player : this) {
			if(player.getId().equals(playerId)) {
				return true;
			}
		}
		return false;
	}

	public Player get(String playerId) {
		
		try {
			return get(getPlayerPosition(playerId));
		} catch (IndexOutOfBoundsException e) {
			Log.d(Constants.TAG, "Tried to get a player that wasn't in the list");
		}
		
		return null;
	}

	public boolean remove(String playerId) {
		try {
			return (remove(getPlayerPosition(playerId)) != null);
		} catch (IndexOutOfBoundsException e) {
			Log.d(Constants.TAG, "Tried to remove a player that doesn't exist");
		}
		return false;
	}
	
	public boolean isDealerBetter() {
		return Integer.valueOf(dealer).equals(Integer.valueOf(better));
	}
	
	private int getPlayerPosition(String playerId) {
		for(int index = 0; index < size(); index++) {
			if(get(index).getId().equals(playerId)) {
				return index;
			}
		}
		throw new IndexOutOfBoundsException();
	}
	
	
	
	
	
	
	

}
