package com.ag.poker.dealer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.ag.poker.dealer.exceptions.CardDeckEmptyException;
import com.ag.poker.dealer.exceptions.UnableToAddPlayerException;
import com.ag.poker.dealer.gameobjects.Card;
import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.logic.PlayerHandler;
import com.ag.poker.dealer.logic.PokerRoundService;
import com.ag.poker.dealer.network.NetworkService;
import com.ag.poker.dealer.network.NetworkServiceImpl;
import com.ag.poker.dealer.utils.Constants;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;
import com.ag.poker.dealer.utils.constants.PokerRoundConstants;

public class Dealer extends BaseGameActivity implements
		DealerConnectionConstants, PokerRoundConstants {

//	public static DealerConnection dealerConnection;
	
	public static NetworkService networkService;

	private static final int SCENE_NUMBER_OF_LAYERS = 4;

	private static final int SCENE_LAYER_TABLE = 0;
	private static final int SCENE_LAYER_DECK = 1;
	private static final int SCENE_LAYER_COMMUNITY_CARDS = 2;
	private static final int SCENE_LAYER_PLAYERS = 3;

	private static final float CARD_SCALE = 0.8f;

	private static int tableWidth;
	private static int tableHeight;

	private Camera camera;

	private Font font;

	private Texture fontTexture;
	private Texture cardDeckTexture;
	private HashMap<Card, TextureRegion> cardToTextureRegionMap;

	private Vector<Sprite> cardSpritesOnTable;

	private static Vector<ChangeableText> playerTexts;

	private float widthTablePerCard = 0.0f;
	private float topOfTableCard = 0.0f;

	private static PokerRoundService pokerRoundService;

	private static ServerMessageHandler serverMessageHandler;
	private static PokerRoundMessageHandler pokerRoundMessageHandler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.anddev.andengine.ui.activity.BaseGameActivity#onCreate(android.os
	 * .Bundle)
	 */
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);

		cardSpritesOnTable = new Vector<Sprite>(5);

		playerTexts = new Vector<ChangeableText>(Constants.MAX_NUMBERS_OF_PLAYERS);
		
		Dealer.serverMessageHandler = new ServerMessageHandler();
		Dealer.pokerRoundMessageHandler = new PokerRoundMessageHandler();
		
		Dealer.pokerRoundService = new PokerRoundService(Dealer.pokerRoundMessageHandler);
		
		Dealer.networkService = new NetworkServiceImpl(Dealer.serverMessageHandler);


	}

	/**
	 * 
	 */
	private void initPlayerTexts() {
		final int border = 20;
		playerTexts.add(new ChangeableText(border, (tableHeight
				- this.font.getLineHeight() - border), this.font, "Seat 1"));

		playerTexts.add(new ChangeableText(border, (tableHeight / 2)
				- (this.font.getLineHeight() / 2), this.font, "Seat 2"));

		playerTexts.add(new ChangeableText(border, (border + this.font
				.getLineHeight()), this.font, "Seat 3"));

		playerTexts.add(new ChangeableText((tableWidth / 2)
				- (this.font.getStringWidth("Seat 4") / 2), (border + this.font
				.getLineHeight()), this.font, "Seat 4"));

		playerTexts.add(new ChangeableText(tableWidth
				- this.font.getStringWidth("Seat 5") - border,
				(border + this.font.getLineHeight()), this.font, "Seat 5"));

		playerTexts.add(new ChangeableText(tableWidth
				- this.font.getStringWidth("Seat 6") - border,
				(tableHeight / 2) - (this.font.getLineHeight() / 2), this.font,
				"Seat 6"));

		playerTexts.add(new ChangeableText(tableWidth
				- this.font.getStringWidth("Seat 7") - border, (tableHeight
				- this.font.getLineHeight() - border), this.font, "Seat 7"));

	}

	/**
	 * @return the pokerRoundHandler
	 */
	public static PokerRoundService getPokerRoundService() {
		return pokerRoundService;
	}

	/**
	 * @param pokerRoundService
	 *            the pokerRoundHandler to set
	 */
	public static void setPokerRoundService(PokerRoundService pokerRoundService) {
		Dealer.pokerRoundService = pokerRoundService;
	}

	/**
	 * @return the cardToTextureRegionMap
	 */
	public HashMap<Card, TextureRegion> getCardToTextureRegionMap() {
		return cardToTextureRegionMap;
	}

	/**
	 * @return the serverHandler
	 */
	public Handler getServerMessageHandler() {
		return Dealer.serverMessageHandler;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadComplete()
	 */
	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadEngine()
	 */
	@Override
	public Engine onLoadEngine() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		tableWidth = dm.widthPixels;
		tableHeight = dm.heightPixels;

		widthTablePerCard = tableWidth / 7;
		topOfTableCard = tableHeight / 4;

		this.camera = new Camera(0, 0, tableWidth, tableHeight);
		return new Engine(
				new EngineOptions(true, ScreenOrientation.LANDSCAPE,
						new RatioResolutionPolicy(tableWidth, tableHeight),
						this.camera));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadResources()
	 */
	@Override
	public void onLoadResources() {

		this.fontTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.font = new Font(this.fontTexture, Typeface.DEFAULT, 32, true,
				Color.BLACK);

		this.cardDeckTexture = new Texture(2048, 2048,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		TextureRegionFactory.createFromAsset(this.cardDeckTexture, this,
				"gfx/card_texture.png", 0, 0);
		this.cardToTextureRegionMap = new HashMap<Card, TextureRegion>();

		for (final Card card : Card.values()) {
			final TextureRegion cardTextureRegion = TextureRegionFactory
					.extractFromTexture(this.cardDeckTexture,
							card.getTexturePositionX(),
							card.getTexturePositionY(), Card.CARD_WIDTH,
							Card.CARD_HEIGHT);
			this.cardToTextureRegionMap.put(card, cardTextureRegion);
		}

		this.mEngine.getTextureManager().loadTexture(this.fontTexture);
		this.mEngine.getTextureManager().loadTexture(this.cardDeckTexture);

		this.mEngine.getFontManager().loadFont(this.font);

		initPlayerTexts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadScene()
	 */
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(SCENE_NUMBER_OF_LAYERS);

		scene.setBackgroundEnabled(true);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		for (ChangeableText changeableText : playerTexts) {
			scene.getChild(SCENE_LAYER_PLAYERS).attachChild(changeableText);
		}

		scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						int index = 0;
						for (Player player : PlayerHandler.getPlayers()) {
							playerTexts.get(index).setText(player.getId());
							index++;
						}
					}
				}));

		this.addCardDeck(scene);

		scene.setTouchAreaBindingEnabled(true);

		return scene;
	};

	private void addCard(final Scene pScene, final Card pCard) {
		final Sprite sprite = new Sprite(0, 0,
				this.cardToTextureRegionMap.get(pCard)) {
			boolean mGrabbed = false;

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.setScale(CARD_SCALE + 0.25f);
					this.mGrabbed = true;
					break;
				case TouchEvent.ACTION_MOVE:
					if (this.mGrabbed) {
						this.setPosition(pSceneTouchEvent.getX()
								- Card.CARD_WIDTH / 2, pSceneTouchEvent.getY()
								- Card.CARD_HEIGHT / 2);
					}
					break;
				case TouchEvent.ACTION_UP:
					if (this.mGrabbed) {
						this.mGrabbed = false;
						this.setScale(CARD_SCALE);
						setCardPosition(this.getZIndex(), this);
					}
					break;
				}
				return true;
			}
		};

		setCardPosition(cardSpritesOnTable.size() + 1, sprite);

		sprite.setZIndex(cardSpritesOnTable.size() + 1);

		cardSpritesOnTable.add(sprite);

		pScene.getChild(SCENE_LAYER_COMMUNITY_CARDS).attachChild(sprite);
		pScene.registerTouchArea(sprite);
	}

	/**
	 * @param cardNumber
	 * @param sprite
	 */
	private void setCardPosition(final int cardNumber, final Sprite sprite) {
		sprite.setScale(CARD_SCALE);

		float scaledCardWidth = sprite.getWidthScaled();

		float positionX = ((widthTablePerCard * cardNumber) + widthTablePerCard)
				- (widthTablePerCard / 2) - (scaledCardWidth / 2);

		sprite.setPosition(positionX, topOfTableCard);
	}

	private void addCardDeck(final Scene pScene) {
		Card pCard = Card.BACK_BLUE;
		int pX = (tableWidth / 2) - (Card.CARD_WIDTH / 2);
		int pY = tableHeight - (Card.CARD_HEIGHT / 2);
		final Sprite sprite = new Sprite(pX, pY,
				this.cardToTextureRegionMap.get(pCard)) {
			boolean mGrabbed = false;

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.setScale(CARD_SCALE + 0.25f);
					this.mGrabbed = true;
					try {
//						ArrayList<Card> drawnCards = Dealer.pokerRoundService
//								.drawCardsForTable();
//
//						for (Card card : drawnCards) {
//							addCard(pScene, card);
//						}
						Dealer.pokerRoundService.nextStage();

					} catch (CardDeckEmptyException e) {
						Log.e(Constants.TAG, e.getMessage());
						Toast.makeText(getApplicationContext(),
								e.getResponseToUser(), Toast.LENGTH_LONG)
								.show();
					} catch (IOException e) {
						Log.e(Constants.TAG, e.getMessage());
					}
					break;
				// case TouchEvent.ACTION_MOVE:
				// if(this.mGrabbed) {
				// this.setPosition(pSceneTouchEvent.getX() - Card.CARD_WIDTH /
				// 2, pSceneTouchEvent.getY() - Card.CARD_HEIGHT / 2);
				// }
				// break;
				case TouchEvent.ACTION_UP:
					if (this.mGrabbed) {
						this.mGrabbed = false;
						this.setScale(CARD_SCALE);
					}
					break;
				}
				return true;
			}
		};

		sprite.setScale(0.80f);

		pScene.getChild(SCENE_LAYER_DECK).attachChild(sprite);
		pScene.registerTouchArea(sprite);
	}

	public void resetTable() {
		mEngine.getScene().getChild(SCENE_LAYER_COMMUNITY_CARDS)
				.detachChildren();
		cardSpritesOnTable.clear();
	}

	
	private class PokerRoundMessageHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == FLAG_ROUND_STATE_START_NEW_ROUND) {
				mEngine.getScene().getChild(SCENE_LAYER_COMMUNITY_CARDS)
						.detachChildren();
				cardSpritesOnTable.clear();
			}
		}
	};
	
	private class ServerMessageHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == SERVER_RESPONSE) {
				if (msg.obj instanceof String) {
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
				}
			} else if (msg.what == SERVER_EXCEPTION) {
				if (msg.arg1 > 0) {
					Toast.makeText(getApplicationContext(), msg.arg1,
							Toast.LENGTH_LONG).show();
				} else if (msg.obj instanceof String) {
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
				}
			} else if (msg.what == CLIENT_REGISTERING) {
				Toast.makeText(getApplicationContext(),
						R.string.client_connected, Toast.LENGTH_LONG).show();
			} else if (msg.what == CLIENT_DISCONNECTED) {
				try {
					PokerRoundService.getPlayerHandler().removePlayer(
							(String) msg.obj);
				} catch (ClassCastException e) {
					Log.e(Constants.TAG,
							"Error while casting msg.obj to String", e);
				}
			} else if (msg.what == PLAYER_DATA_CLIENT_MESSAGE) {
				if (msg.obj instanceof Player) {
					try {
						Player player = (Player) msg.obj;
						PokerRoundService.getPlayerHandler().addPlayer(player);

					} catch (UnableToAddPlayerException e) {
						if (e.getResponseToUser() > 0) {
							Toast.makeText(getApplicationContext(),
									e.getResponseToUser(), Toast.LENGTH_LONG)
									.show();
						}
					}
				}
			}
		}
	}
}