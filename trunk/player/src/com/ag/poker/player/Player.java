package com.ag.poker.player;

import com.ag.poker.player.network.PlayerConnection;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class Player extends Activity {
    private Handler mHandler;
	private PlayerConnection mPlayerConnection;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mHandler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        		if(msg.what == PlayerConnection.SERVER_RESPONSE) {
        			if(msg.obj != null && msg.obj instanceof String) {
        				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
        			} else {
        				Toast.makeText(getApplicationContext(), "Card 1: " + msg.arg1 + " - Card 2: " + msg.arg2, Toast.LENGTH_LONG).show();
        			}
        		}
        		super.handleMessage(msg);
        	}
        };
        
        mPlayerConnection = new PlayerConnection(this.mHandler);
    }
}