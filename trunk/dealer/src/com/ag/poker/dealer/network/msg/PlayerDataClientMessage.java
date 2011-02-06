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
import java.io.DataOutputStream;
import java.io.IOException;

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.client.BaseClientMessage;

import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.network.DealerConnection;

/**
 * @author Arild
 *
 */
public class PlayerDataClientMessage extends BaseClientMessage {
	
	private Player player;
	
	public PlayerDataClientMessage(Player player) {
		this.player = player;
	}
	
	public PlayerDataClientMessage(final DataInputStream pDataInputStream) throws IOException {
		this.player = new Player(pDataInputStream);
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.extension.multiplayer.protocol.adt.message.BaseMessage#getFlag()
	 */
	@Override
	public short getFlag() {
		return DealerConnection.FLAG_MESSAGE_CLIENT_REGISTERING;
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.extension.multiplayer.protocol.adt.message.BaseMessage#onAppendTransmissionDataForToString(java.lang.StringBuilder)
	 */
	@Override
	protected void onAppendTransmissionDataForToString(StringBuilder pStringBuilder) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.extension.multiplayer.protocol.adt.message.BaseMessage#onWriteTransmissionData(java.io.DataOutputStream)
	 */
	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream) throws IOException {
		this.player.writeDataOutputstream(pDataOutputStream);

	}
	
	public Player getPlayer() {
		return this.player;
	}

}
