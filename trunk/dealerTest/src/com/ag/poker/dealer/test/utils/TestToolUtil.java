package com.ag.poker.dealer.test.utils;

import java.util.HashMap;

import com.ag.poker.dealer.gameobjects.Player;

public class TestToolUtil {
	
	public static HashMap<String, Player> createTestPlayers(int numberOfPlayers) {
		HashMap<String, Player> players = new HashMap<String, Player>();
		
		for(int i = 0; i < numberOfPlayers; i++) {
			players.put("player"+i, new Player("player"+i, "Player "+i, 10));
		}
		return players;
	}

}
