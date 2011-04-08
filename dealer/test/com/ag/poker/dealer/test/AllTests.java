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

package com.ag.poker.dealer.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.ag.poker.dealer.test.gameobjects.CardDeckTest;
import com.ag.poker.dealer.test.gameobjects.PlayerTest;
import com.ag.poker.dealer.test.logic.CardDeckHandlerTest;
import com.ag.poker.dealer.test.logic.GameRulesTest;
import com.ag.poker.dealer.test.logic.PlayerHandlerTest;
import com.ag.poker.dealer.test.logic.PokerRoundServiceTest;
import com.ag.poker.dealer.test.network.DealerConnectionTest;
import com.ag.poker.dealer.test.network.NetworkServiceImplTest;

/**
 * @author Arild
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(DealerTest.class);
		suite.addTestSuite(CardDeckTest.class);
		suite.addTestSuite(PlayerTest.class);
		suite.addTestSuite(PlayerHandlerTest.class);
		suite.addTestSuite(CardDeckHandlerTest.class);
		suite.addTestSuite(PokerRoundServiceTest.class);
		suite.addTestSuite(DealerConnectionTest.class);
		suite.addTestSuite(NetworkServiceImplTest.class);
		suite.addTestSuite(GameRulesTest.class);
		//$JUnit-END$
		return suite;
	}

}
