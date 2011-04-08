package com.ag.poker.dealer.test.utils;

import com.ag.poker.dealer.gameobjects.player.Player;
import com.ag.poker.dealer.gameobjects.player.PlayerList;

public class TestToolUtil {
	
	public static PlayerList createTestPlayers(int numberOfPlayers) {
		PlayerList players = new PlayerList();
		
		for(int i = 0; i < numberOfPlayers; i++) {
			players.add(new Player("player"+i, "Player "+i, 10));
		}
		return players;
	}

}
