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
	private int seat;
	
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

	public void writeDataOutputstream(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeUTF(this.id);
		dataOutputStream.writeUTF(this.name);
		dataOutputStream.writeDouble(this.chipCount);
		dataOutputStream.writeInt(this.seat);
	}
}
