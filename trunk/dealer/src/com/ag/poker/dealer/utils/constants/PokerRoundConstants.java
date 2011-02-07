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
public interface PokerRoundConstants {
	
	public static final int FLAG_ROUND_STATE_START_NEW_ROUND = 0;
	public static final int FLAG_ROUND_STATE_FLOP = 1;
	public static final int FLAG_ROUND_STATE_TURN = 2;
	public static final int FLAG_ROUND_STATE_RIVER = 3;
	public static final int FLAG_ROUND_STATE_FINISH_ROUND = 4;
	
	
	public static final int FLAG_ROUND_STATE_WAITING_FOR_BLINDS = 5;
	public static final int FLAG_ROUND_STATE_WAITING_FOR_BETS = 6;
	public static final int FLAG_ROUND_STATE_COLLECT_BLINDS = 7;
	public static final int FLAG_ROUND_STATE_COLLECT_BETS = 8;

}
