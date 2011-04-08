package com.ag.poker.dealer.network.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.anddev.andengine.extension.multiplayer.protocol.adt.message.server.BaseServerMessage;

import com.ag.poker.dealer.gameobjects.player.Player;

public class PlayerDataServerMessage extends BaseServerMessage {

	private Player player;
	private short flag;
	
	public PlayerDataServerMessage(Player player, short flag) {
		this.player = player;
		this.flag = flag;
	}
	
	public PlayerDataServerMessage(final DataInputStream pDataInputStream) throws IOException {
		this.player = new Player(pDataInputStream);
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.extension.multiplayer.protocol.adt.message.BaseMessage#getFlag()
	 */
	@Override
	public short getFlag() {
		return flag;
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
