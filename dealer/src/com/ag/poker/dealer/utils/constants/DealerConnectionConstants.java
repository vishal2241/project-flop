
package com.ag.poker.dealer.utils.constants;

public interface DealerConnectionConstants {
	
	public static final int SERVER_RESPONSE = 1;
	public static final int SERVER_STARTED = 2;
	public static final int SERVER_TERMINATED = 3;
	public static final int SERVER_EXCEPTION = 4;
	public static final int CLIENT_CONNECTED = 5;
	public static final int CLIENT_DISCONNECTED = 6;
	public static final int CLIENT_REGISTERING = 7;
	public static final int PLAYER_DATA_CLIENT_MESSAGE = 8;
	
	
	
	public static final short FLAG_MESSAGE_SERVER_UPDATE_PLAYER_DATA = 1;
	public static final short FLAG_MESSAGE_CLIENT_REGISTERING = 2;
	public static final short FLAG_MESSAGE_CLIENT_DISCONNECTED = 3;
	public static final short FLAG_MESSAGE_SERVER_ANNOUNCE_WINNER = 4;
	public static final short FLAG_MESSAGE_SERVER_ANNOUNCE_NEW_ROUND = 5;
	public static final short FLAG_MESSAGE_SERVER_ASK_FOR_BETS = 6;
	public static final short FLAG_MESSAGE_SERVER_ANNOUNCE_ROUND_FINISHED = 7;

}
