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

package com.ag.poker.dealer.network.msg;

import java.io.DataInputStream;
import java.io.IOException;

import com.ag.poker.dealer.gameobjects.player.Player;

/**
 * @author Arild
 *
 */
public class BetClientMessage extends PlayerDataClientMessage {
	
	private double betAmount;

	/**
	 * @param player
	 * @param flag
	 */
	public BetClientMessage(Player player, short flag, double betAmount) {
		super(player, flag);
		
		this.betAmount = betAmount;
	}

	public BetClientMessage(DataInputStream pDataInputStream)
			throws IOException {
		super(pDataInputStream);
		
		this.betAmount = pDataInputStream.readDouble();
	}

	/**
	 * @return the betAmount
	 */
	public double getBetAmount() {
		return betAmount;
	}

	/**
	 * @param betAmount the betAmount to set
	 */
	public void setBetAmount(double betAmount) {
		this.betAmount = betAmount;
	}
	
	

}
