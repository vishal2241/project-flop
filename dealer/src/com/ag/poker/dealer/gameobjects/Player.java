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

package com.ag.poker.dealer.gameobjects;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * @author Arild
 *
 */
public class Player {
	
	private String id;
	private String name;
	private double chipCount;
	private boolean active = true;
	private int seat = -1;
	private Card card1 = null;
	private Card card2 = null;
	
	public Player(String id) {
		this.id = id;
		this.name = "";
	}
	
	/**
	 * @param id
	 * @param name
	 * @param chipCount
	 */
	public Player(String id, String name, double chipCount) {
		this.id = id;
		this.name = name;
		this.chipCount = chipCount;
	}
	/**
	 * @param pDataInputStream
	 */
	public Player(DataInputStream pDataInputStream) throws IOException {
		this.id = pDataInputStream.readUTF();
		this.name = pDataInputStream.readUTF();
		this.chipCount = pDataInputStream.readDouble();
		this.seat = pDataInputStream.readInt();
		
		try {
			this.card1 = Card.values()[pDataInputStream.readInt()];
			this.card2 = Card.values()[pDataInputStream.readInt()];
		} catch (EOFException e) {
			//The cards isn't on the stream, just ignore
		}
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the chipCount
	 */
	public double getChipCount() {
		return chipCount;
	}
	/**
	 * @param chipCount the chipCount to set
	 */
	public void setChipCount(double chipCount) {
		this.chipCount = chipCount;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	

	/**
	 * @return the seat
	 */
	public int getSeat() {
		return seat;
	}

	/**
	 * @param seat the seat to set
	 */
	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	/**
	 * @return the card1
	 */
	public Card getCard1() {
		return card1;
	}

	/**
	 * @param card1 the card1 to set
	 */
	public void setCard1(Card card1) {
		this.card1 = card1;
	}

	/**
	 * @return the card2
	 */
	public Card getCard2() {
		return card2;
	}

	/**
	 * @param card2 the card2 to set
	 */
	public void setCard2(Card card2) {
		this.card2 = card2;
	}

	public void writeDataOutputstream(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeUTF(this.id);
		dataOutputStream.writeUTF(this.name);
		dataOutputStream.writeDouble(this.chipCount);
		dataOutputStream.writeInt(this.seat);
		if(this.card1 != null) {
			dataOutputStream.writeInt(this.card1.ordinal());
		}
		if(this.card2 != null) {
			dataOutputStream.writeInt(this.card2.ordinal());
		}
		
		dataOutputStream.flush();
	}
}
