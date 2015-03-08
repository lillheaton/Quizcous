package com.emilochhektor.quizcous;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.emilochhektor.quizcous.cast.ChromecastConnectionHandler;
import com.emilochhektor.quizcous.cast.IChromecastUser;


public class StartScreenActivity extends ActionBarActivity implements IChromecastUser {

    private static String TAG = "com.emilochhektor.quizcous.StartScreenActivity";

    private ChromecastConnectionHandler connectionHandler;

    private void init(){
        connectionHandler = new ChromecastConnectionHandler(this);
        connectionHandler.init();
    }



    private void startLobbyActivity() {
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
    }




    // @Implements
    public Context getContext() {
        return this.getApplicationContext();
    }
    public void onTeardown() { }
    public void onChromecastConnected() {
        Log.d(TAG, "Chromecast connected :)");
    }
    public void onChromecastDisconnected() { }
    public void onReceiverApplicationConnected() {
        Log.d(TAG, "Receiver application connected :D");
    }
    public void onReceiverApplicationDisconnected() { }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        this.init();
    }

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

    /*@Override

    // Should not be done in onPause/onResume
    // https://developer.android.com/guide/topics/media/mediarouter.html#attach-mr-callback

    protected void onResume() {
        super.onResume();
        mediaRouter.addCallback(mediaRouteSelector, mediaRouterCallback, MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }
    @Override
    protected void onPause() {
        if (isFinishing()) {
            mediaRouter.removeCallback(mediaRouterCallback);
        }
        super.onPause();
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);

        this.connectionHandler.setChromecastMenuItem(mediaRouteMenuItem);
        return true;
    }
}
