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

package com.ag.poker.dealer.network;

import java.io.IOException;

import android.os.Handler;

import com.ag.poker.dealer.exceptions.InitServerException;
import com.ag.poker.dealer.gameobjects.player.Player;
import com.ag.poker.dealer.gameobjects.player.PlayerList;
import com.ag.poker.dealer.network.msg.PlayerDataServerMessage;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

/**
 * @author Arild
 *
 */
public class NetworkServiceImpl implements NetworkService, DealerConnectionConstants {
	
	
	private static DealerConnection dealerConnection;
	
	public NetworkServiceImpl(Handler networkHandler) {
		NetworkServiceImpl.dealerConnection = new DealerConnection(networkHandler);
	}
	
	/**
	 * @return the dealerConnection
	 */
	public static DealerConnection getDealerConnection() {
		return dealerConnection;
	}



	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#startServer()
	 */
	@Override
	public void startServer() throws InitServerException {
		NetworkServiceImpl.dealerConnection.initServer();
		
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#stopServer()
	 */
	@Override
	public void stopServer() {
		NetworkServiceImpl.dealerConnection.terminateServer();
		
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#sendDealtCardsToPlayer(com.ag.poker.dealer.gameobjects.Player)
	 */
	@Override
	public void sendPlayerData(Player player) throws IOException {
		sendPlayerDataServerMessage(player, FLAG_MESSAGE_SERVER_UPDATE_PLAYER_DATA);
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#sendAllDealtCards(java.util.List)
	 */
	@Override
	public void sendAllPlayersData(PlayerList players) throws IOException {
		for (Player player : players) {
			sendPlayerDataServerMessage(player, FLAG_MESSAGE_SERVER_UPDATE_PLAYER_DATA);
		}
	}


	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#announceNewRound(java.util.List)
	 */
	@Override
	public void announceNewRound(PlayerList players) throws IOException {
		for (Player player : players) {
			sendPlayerDataServerMessage(player, FLAG_MESSAGE_SERVER_ANNOUNCE_NEW_ROUND);
		}
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#askPlayerForBets(com.ag.poker.dealer.gameobjects.Player)
	 */
	@Override
	public void askPlayerForBets(Player player) throws IOException {
		sendPlayerDataServerMessage(player, FLAG_MESSAGE_SERVER_ASK_FOR_BETS);
	}

	/* (non-Javadoc)
	 * @see com.ag.poker.dealer.network.NetworkService#finishRound(java.util.List)
	 */
	@Override
	public void announceFinishedRound(PlayerList winners) throws IOException {
		for (Player winner : winners) {
			sendPlayerDataServerMessage(winner, FLAG_MESSAGE_SERVER_ANNOUNCE_ROUND_FINISHED);
		}
	}
	
	private void sendPlayerDataServerMessage(Player player, short flag) throws IOException {
		PlayerDataServerMessage serverMessage = new PlayerDataServerMessage(player, flag);
		
		NetworkServiceImpl.dealerConnection.getServer().sendBroadcastServerMessage(serverMessage);
	}

}
