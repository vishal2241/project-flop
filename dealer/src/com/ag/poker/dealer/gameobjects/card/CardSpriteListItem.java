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

package com.ag.poker.dealer.gameobjects.card;

import org.anddev.andengine.entity.sprite.Sprite;

/**
 * @author Arild
 *
 */
public class CardSpriteListItem {
	
	public int posX = 0;
	public int posY = 0;
	private Sprite sprite = null;
	
	public CardSpriteListItem() {
		posX = 0;
		posY = 0;
		sprite = null;;
	}
	
	public CardSpriteListItem(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.sprite = null;
	}

	public CardSpriteListItem(int posX, int posY, Sprite sprite) {
		this.posX = posX;
		this.posY = posY;
		this.sprite = sprite;
	}

	/**
	 * @return the sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	

}
