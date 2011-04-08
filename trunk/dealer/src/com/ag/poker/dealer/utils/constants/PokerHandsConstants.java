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

package com.ag.poker.dealer.utils.constants;

/**
 * @author Arild
 *
 */
public interface PokerHandsConstants {
	
	public static final int HAND_HIGH_CARD = 0;
	public static final int HAND_PAIR = 1000;
	public static final int HAND_TWO_PAIRS = 2000;
	public static final int HAND_THREE_OF_A_KIND = 3000;
	public static final int HAND_STRAIGHT = 4000;
	public static final int HAND_FLUSH = 5000;
	public static final int HAND_FULL_HOUSE = 6000;
	public static final int HAND_FOUR_OF_A_KIND = 7000;
	public static final int HAND_STRAIGHT_FLUSH = 8000;
	public static final int HAND_ROYAL_FLUSH = 9000;

	public static final short PLAYER_HAND_HIGHER = 0;
	public static final short PLAYER_HAND_LOWER = 1;
	public static final short PLAYER_HAND_SAME = 2;
}
