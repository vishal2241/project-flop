package com.ag.poker.dealer.test.player;

import android.os.Handler;
import android.os.Message;

import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.gameobjects.PlayerList;
import com.ag.poker.dealer.test.utils.TestToolUtil;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

public class MockPlayersTestUtil {
	
	private Handler handler;

	public MockPlayersTestUtil(Handler handler) {
		this.handler = handler;
	}
	
	public void connectPlayers(int numberOfPlayers) {
		PlayerList players = TestToolUtil.createTestPlayers(numberOfPlayers);
		
		for (Player player : players) {
			Message msg = handler.obtainMessage(DealerConnectionConstants.PLAYER_DATA_CLIENT_MESSAGE, player);
			handler.sendMessage(msg);
		}
	}

}
