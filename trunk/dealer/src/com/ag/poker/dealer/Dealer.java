package com.ag.poker.dealer;

import com.ag.poker.dealer.network.DealerConnection;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class Dealer extends Activity {
	
	private DealerConnection mDealerConnection;
	private Handler handler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        handler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        		if(msg.what == DealerConnection.SERVER_RESPONSE) {
        			if(msg.obj instanceof String) {
        				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
        			}
        		} 
        	}
        };
        
        this.mDealerConnection = new DealerConnection(handler);
        
    }
}