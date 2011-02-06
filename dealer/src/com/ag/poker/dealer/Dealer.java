package com.ag.poker.dealer;

import java.util.ArrayList;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.ag.poker.dealer.exceptions.InitServerException;
import com.ag.poker.dealer.exceptions.UnableToAddPlayerException;
import com.ag.poker.dealer.gameobjects.Player;
import com.ag.poker.dealer.logic.PlayerHandler;
import com.ag.poker.dealer.network.DealerConnection;
import com.ag.poker.dealer.utils.Constants;
import com.ag.poker.dealer.utils.constants.DealerConnectionConstants;

public class Dealer extends BaseGameActivity implements DealerConnectionConstants{
	
	public static DealerConnection dealerConnection;
	
	private static int width;
	private static int height;
	
	private Camera camera;
	private Texture fontTexture;
	private Font font;
	
	private static PlayerHandler playerHandler;
	    
    /* (non-Javadoc)
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		
		Dealer.playerHandler = new PlayerHandler();
		
        Dealer.dealerConnection = new DealerConnection(this.serverHandler);
        try {
        	Dealer.dealerConnection.initServer();
        } catch (InitServerException e) {
			Log.e(Constants.TAG, e.getMessage());
			Toast.makeText(getApplicationContext(), R.string.error_couldnt_start_server, Toast.LENGTH_LONG).show();
		}
	}
	
	

    /* (non-Javadoc)
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}



	/* (non-Javadoc)
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}



	/* (non-Javadoc)
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}



	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/**
	 * @return the playerHandler
	 */
	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	/**
	 * @param playerHandler the playerHandler to set
	 */
	public void setPlayerHandler(PlayerHandler playerHandler) {
		Dealer.playerHandler = playerHandler;
	}

	/**
	 * @return the dealerConnection
	 */
	public static DealerConnection getDealerConnection() {
		return dealerConnection;
	}

	/**
	 * @return the serverHandler
	 */
	public Handler getServerHandler() {
		return serverHandler;
	}

	private Handler serverHandler = new Handler() {
    	@Override
    	public void handleMessage(Message msg) {
    		if(msg.what == SERVER_RESPONSE) {
    			if(msg.obj instanceof String) {
    				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
    			}
    		} else if(msg.what == SERVER_EXCEPTION) {
    			if(msg.arg1 > 0) {
    				Toast.makeText(getApplicationContext(), msg.arg1, Toast.LENGTH_LONG).show();
    			} else if(msg.obj instanceof String) {
    				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
    			}
    		} else if(msg.what == CLIENT_REGISTERING) {
    			Toast.makeText(getApplicationContext(), R.string.client_connected, Toast.LENGTH_LONG).show();
    		} else if(msg.what == CLIENT_DISCONNECTED) {
    			try {
    				playerHandler.removePlayer((String)msg.obj);
    			} catch (ClassCastException e) {
					Log.e(Constants.TAG, "Error while casting msg.obj to String", e);
				}
    		}
    		else if(msg.what == PLAYER_DATA_CLIENT_MESSAGE) {
    			if(msg.obj instanceof Player) {
    				try {
						playerHandler.addPlayer((Player)msg.obj);
					} catch (UnableToAddPlayerException e) {
						if(e.getResponseToUser() > 0) {
							Toast.makeText(getApplicationContext(), e.getResponseToUser(), Toast.LENGTH_LONG).show();
						}
					}
    			}
    		}
    	}
    };

	

	/* (non-Javadoc)
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadComplete()
	 */
	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadEngine()
	 */
	@Override
	public Engine onLoadEngine() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		
		this.camera = new Camera(0, 0, width, height);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(width, height), this.camera));
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadResources()
	 */
	@Override
	public void onLoadResources() {
        
        this.fontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.font = new Font(this.fontTexture, Typeface.DEFAULT, 32, true, Color.BLACK);
        
        this.mEngine.getTextureManager().loadTexture(this.fontTexture);
        this.mEngine.getFontManager().loadFont(this.font);
        
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadScene()
	 */
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		final Scene scene = new Scene(3);
		
		scene.setBackgroundEnabled(true);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		
		final ArrayList<ChangeableText> playerTexts = new ArrayList<ChangeableText>();
		final int border = 20;
		playerTexts.add(new ChangeableText(border, (height - this.font.getLineHeight() - border), this.font, "Player 1"));
		playerTexts.add(new ChangeableText(border, (height / 2) - (this.font.getLineHeight()/2), this.font, "Player 2"));
		playerTexts.add(new ChangeableText(border, (border + this.font.getLineHeight()), this.font, "Player 3"));
		playerTexts.add(new ChangeableText((width / 2) - (this.font.getStringWidth("Player 4") / 2), (border + this.font.getLineHeight()), this.font, "Player 4"));
		playerTexts.add(new ChangeableText(width - this.font.getStringWidth("Player 5") - border, (border + this.font.getLineHeight()), this.font, "Player 5"));
		playerTexts.add(new ChangeableText(width - this.font.getStringWidth("Player 6") - border, (height / 2) - (this.font.getLineHeight()/2), this.font, "Player 6"));
		playerTexts.add(new ChangeableText(width - this.font.getStringWidth("Player 7") - border, (height - this.font.getLineHeight() - border), this.font, "Player 7"));
		
		for (ChangeableText changeableText : playerTexts) {
			scene.getChild(scene.getChildCount()-1).attachChild(changeableText);
		}
		
		scene.registerUpdateHandler(new TimerHandler(1/20.0f, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				int index = 0;
				for(Player player : PlayerHandler.getPlayers().values()) {
					playerTexts.get(index).setText(player.getId());
					index++;
				}
				
			}
		}));
		
		return scene;
	};
	
}