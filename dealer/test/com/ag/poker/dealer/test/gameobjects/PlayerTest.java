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

package com.ag.poker.dealer.test.gameobjects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import junit.framework.TestCase;

import com.ag.poker.dealer.gameobjects.Card;
import com.ag.poker.dealer.gameobjects.Player;

/**
 * @author Arild
 *
 */
public class PlayerTest extends TestCase {
	
	Player player;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.player = new Player("player1", "Player 1", 100);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPlayerDataInputStream() throws IOException {
		Card card1 = Card.CLUB_EIGHT;
		Card card2 = Card.DIAMOND_SEVEN;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(baos);
		
		dataOutputStream.writeUTF(player.getId());
		dataOutputStream.writeUTF(player.getName());
		dataOutputStream.writeDouble(player.getChipCount());
		dataOutputStream.writeInt(card1.ordinal());
		dataOutputStream.writeInt(card2.ordinal());
		dataOutputStream.flush();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		DataInputStream dataInputStream = new DataInputStream(bais);
		
		Player actual = new Player(dataInputStream);
		
		assertEquals(player.getId(), actual.getId());
		assertEquals(player.getName(), actual.getName());
		assertEquals(player.getChipCount(), actual.getChipCount());
		assertEquals(card1, actual.getCard1());
		assertEquals(card2, actual.getCard2());
	}
	
	public void testPlayerDataInputStreamWithoutCards() throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(baos);
		
		dataOutputStream.writeUTF(player.getId());
		dataOutputStream.writeUTF(player.getName());
		dataOutputStream.writeDouble(player.getChipCount());
		dataOutputStream.flush();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		DataInputStream dataInputStream = new DataInputStream(bais);
		
		Player actual = new Player(dataInputStream);
		
		assertEquals(player.getId(), actual.getId());
		assertEquals(player.getName(), actual.getName());
		assertEquals(player.getChipCount(), actual.getChipCount());
		assertEquals(null, actual.getCard1());
		assertEquals(null, actual.getCard2());
	}

	public void testPlayerWriteDataOutputstream() throws IOException {
		player.setCard1(Card.CLUB_EIGHT);
		player.setCard2(Card.DIAMOND_SEVEN);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(baos);
		
		player.writeDataOutputstream(dataOutputStream);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		DataInputStream dataInputStream = new DataInputStream(bais);
		
		String playerId = dataInputStream.readUTF();
		String name = dataInputStream.readUTF();
		Double chipCount = dataInputStream.readDouble();
		Card card1 = Card.values()[dataInputStream.readInt()];
		Card card2 = Card.values()[dataInputStream.readInt()];
		
		assertEquals(player.getId(), playerId);
		assertEquals(player.getName(), name);
		assertEquals(player.getChipCount(), chipCount);
		assertEquals(player.getCard1(), card1);
		assertEquals(player.getCard2(), card2);
	}
	
	public void testPlayerWriteDataOutputstreamBeforeDealingCardsOrGivenSeat() throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(baos);
		
		player.writeDataOutputstream(dataOutputStream);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		DataInputStream dataInputStream = new DataInputStream(bais);
		
		String playerId = dataInputStream.readUTF();
		String name = dataInputStream.readUTF();
		Double chipCount = dataInputStream.readDouble();
		try {
			dataInputStream.readInt();
			dataInputStream.readInt();
			fail("Should get EOFException before reaching this line");
		} catch (EOFException e) {
			// do nothing
		}
		
		assertEquals(player.getId(), playerId);
		assertEquals(player.getName(), name);
		assertEquals(player.getChipCount(), chipCount);
	}

}
