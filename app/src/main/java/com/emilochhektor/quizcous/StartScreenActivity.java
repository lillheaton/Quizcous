package com.emilochhektor.quizcous;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.emilochhektor.quizcous.cast.ChromecastConnectionHandler;
import com.emilochhektor.quizcous.cast.IChromecastUser;

import org.json.JSONObject;


public class StartScreenActivity extends ActionBarActivity implements IChromecastUser {

    private static String TAG = "com.emilochhektor.quizcous.StartScreenActivity";

    private ChromecastConnectionHandler connectionHandler;

    // Color theme
    // http://www.materialpalette.com/orange/teal

    private void init(){
        this.connectionHandler = ChromecastConnectionHandler.getInstance(this);
        this.connectionHandler.init();
    }



    private void startUserSelectionScreen() {
        Intent intent = new Intent(this, UserSelectionActivity.class);
        startActivity(intent);
    }




    // @Implements
    public void onTeardown() { }
    public void onChromecastConnected() {
        Log.d(TAG, "Chromecast connected :)");
    }
    public void onChromecastDisconnected() { }
    public void onReceiverApplicationConnected() {
        Log.d(TAG, "Receiver application connected :D");
        this.connectionHandler.sendMessage("{\"message\": \"Hello mr receiver!\"}");
        startUserSelectionScreen();
    }
    public void onReceiverApplicationDisconnected() { }

    public void onMessageChannelConnected() {
    }
    public void onMessageReceived(JSONObject json) {
        Toast.makeText(this, "Message received", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
//        setContentView(R.layout.screen_user_select);

        this.init();
    }


    // Should not be done in onPause/onResume
    // https://developer.android.com/guide/topics/media/mediarouter.html#attach-mr-callback
    @Override
    protected void onStart() {
        super.onStart();
        this.connectionHandler.startRoutePolling();
    }

    @Override
    protected void onStop() {
        this.connectionHandler.stopRoutePolling();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);

        this.connectionHandler.setChromecastMenuItem(mediaRouteMenuItem);

        return true;
    }
}
