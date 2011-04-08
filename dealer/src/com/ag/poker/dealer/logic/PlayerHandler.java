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

import java.util.HashMap;

import android.util.Log;

import com.ag.poker.dealer.R;
import com.ag.poker.dealer.exceptions.UnableToAddPlayerException;
import com.ag.poker.dealer.gameobjects.player.Player;
import com.ag.poker.dealer.gameobjects.player.PlayerList;
import com.ag.poker.dealer.utils.Constants;

/**
 * @author Arild
 * 
 */
public class PlayerHandler {

	public static PlayerList players;
	public static HashMap<String, Player> disconnectedPlayers;

	public PlayerHandler() {
		PlayerHandler.players = new PlayerList(7);

		PlayerHandler.disconnectedPlayers = new HashMap<String, Player>(5);
	}

	/**
	 * @return the players
	 */
	public static PlayerList getPlayers() {
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public static void setPlayers(PlayerList players) {
		PlayerHandler.players.addAll(players);
	}

	/**
	 * @return the disconnectedPlayers
	 */
	public static HashMap<String, Player> getDisconnectedPlayers() {
		return disconnectedPlayers;
	}

	/**
	 * @param disconnectedPlayers
	 *            the disconnectedPlayers to set
	 */
	public static void setDisconnectedPlayers(HashMap<String, Player> disconnectedPlayers) {
		PlayerHandler.disconnectedPlayers = disconnectedPlayers;
	}

	public boolean addPlayer(Player player) throws UnableToAddPlayerException {
		if (player == null || player.getId() == null || player.getId().equals("")) {
			Log.e(Constants.TAG, "Tried to add empty player");
			return false;
		}

		if (PlayerHandler.players.size() >= 7) {
			Log.i(Constants.TAG, "Table is full, not able to add any more players");
			throw new UnableToAddPlayerException(R.string.error_add_player_table_full);
		}

		if (players.contains(player.getId())) {
			Log.i(Constants.TAG, "Player already exists, NOT ADDED: " + player.getId());
			return false;
		} else if (PlayerHandler.disconnectedPlayers.containsKey(player.getId())) {
			Player disconnectedPlayer = PlayerHandler.disconnectedPlayers.get(player.getId());
			PlayerHandler.players.add(disconnectedPlayer);
			PlayerHandler.disconnectedPlayers.remove(player.getId());
			return true;
		} else {
			PlayerHandler.players.add(player);
			Log.i(Constants.TAG, "Player added: " + player.getId());
			return true;
		}
	}

	public void setPlayerInactive(String playerId) {
		if (!PlayerHandler.players.isEmpty() && PlayerHandler.players.contains(playerId)) {
			PlayerHandler.players.get(playerId).setActive(false);
			Log.i(Constants.TAG, "Player deactivated: " + playerId);
		}
	}

	public void removePlayer(String playerId) {
		Player player = PlayerHandler.players.get(playerId);

		if (player != null) {
			PlayerHandler.disconnectedPlayers.put(player.getId(), player);
			PlayerHandler.players.remove(playerId);
		}
	}

}
