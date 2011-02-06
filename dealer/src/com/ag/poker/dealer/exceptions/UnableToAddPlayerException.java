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

package com.ag.poker.dealer.exceptions;

/**
 * @author Arild
 *
 */
public class UnableToAddPlayerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int responseToUser;
	
	public UnableToAddPlayerException() {
		super("Unable to add player");
	}

	/**
	 * 
	 */
	public UnableToAddPlayerException(int responseToUser) {
		super("Table was full, not able to add any more players");
		
		this.responseToUser = responseToUser;
	}

	/**
	 * @param detailMessage
	 * @param throwable
	 */
	public UnableToAddPlayerException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	/**
	 * @param detailMessage
	 */
	public UnableToAddPlayerException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable
	 */
	public UnableToAddPlayerException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @return the responseToUser
	 */
	public int getResponseToUser() {
		return responseToUser;
	}

	/**
	 * @param responseToUser the responseToUser to set
	 */
	public void setResponseToUser(int responseToUser) {
		this.responseToUser = responseToUser;
	}
}