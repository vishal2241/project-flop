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

package com.ag.poker.dealer.network;

import org.anddev.andengine.extension.multiplayer.protocol.server.BaseServer.IServerStateListener;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ag.poker.dealer.utils.Constants;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

/**
 * @author Arild
 *
 */
public class ServerStateListener implements IServerStateListener, DealerConnectionConstants {
	
	
	private Handler handler;

	public ServerStateListener(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void onException(Throwable pThrowable) {
		Message msg = this.handler.obtainMessage(SERVER_EXCEPTION);
		msg.obj = "SERVER: Exception: " + pThrowable;
		this.handler.sendMessage(msg);
		Log.i(Constants.TAG, msg.obj.toString());
	}

	@Override
	public void onStarted(int pPort) {
		Message msg = this.handler.obtainMessage(SERVER_STARTED);
		msg.obj = "SERVER: started at port: " + pPort;
		this.handler.sendMessage(msg);
		Log.i(Constants.TAG, msg.obj.toString());
	}

	@Override
	public void onTerminated(int pPort) {
		Message msg = this.handler.obtainMessage(SERVER_TERMINATED);
		msg.obj = "SERVER: terminated at port: " + pPort;
		this.handler.sendMessage(msg);
		Log.i(Constants.TAG, msg.obj.toString());
	}

}
