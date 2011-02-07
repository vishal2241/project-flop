package com.ag.poker.dealer.test.player;

import java.util.HashMap;

import android.os.Handler;
import android.os.Message;

import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.test.utils.TestToolUtil;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

public class MockPlayersTestUtil {
	
	private Handler handler;

	public MockPlayersTestUtil(Handler handler) {
		this.handler = handler;
	}
	
	public void connectPlayers(int numberOfPlayers) {
		HashMap<String, Player> players = TestToolUtil.createTestPlayers(numberOfPlayers);
		
		for (Player player : players.values()) {
			Message msg = handler.obtainMessage(DealerConnectionConstants.PLAYER_DATA_CLIENT_MESSAGE, player);
			handler.sendMessage(msg);
		}
	}

}
