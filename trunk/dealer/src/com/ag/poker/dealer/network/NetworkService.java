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

import com.ag.poker.dealer.exceptions.InitServerException;
import com.ag.poker.dealer.gameobjects.player.Player;
import com.ag.poker.dealer.gameobjects.player.PlayerList;

/**
 * @author Arild
 *
 */
public interface NetworkService {
	
	public void startServer() throws InitServerException;
	
	public void stopServer();
	
	public void sendPlayerData(Player player) throws IOException;
	
	public void sendAllPlayersData(PlayerList players) throws IOException;
	
	public void announceNewRound(PlayerList players) throws IOException;

	public void askPlayerForBets(Player player) throws IOException;
	
	public void announceFinishedRound(PlayerList winners) throws IOException;
	
}
