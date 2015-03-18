package com.emilochhektor.quizcous;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.emilochhektor.quizcous.cast.ChromecastConnectionHandler;
import com.emilochhektor.quizcous.cast.IChromecastUser;

import org.json.JSONObject;


public class UserSelectionActivity extends ActionBarActivity implements IChromecastUser {

    private ChromecastConnectionHandler connectionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_user_select);

        this.connectionHandler = ChromecastConnectionHandler.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);

        this.connectionHandler.setChromecastMenuItem(mediaRouteMenuItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // @Implements
    public Context getApplicationContext() {
        return this.getApplicationContext();
    }
    public void onTeardown() { }
    public void onChromecastConnected() { }
    public void onChromecastDisconnected() { }
    public void onReceiverApplicationConnected() { }
    public void onReceiverApplicationDisconnected() { }
    public void onMessageChannelConnected() { }
    public void onMessageReceived(JSONObject json) { }
}
