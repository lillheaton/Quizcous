package com.emilochhektor.quizcous;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.emilochhektor.quizcous.cast.IChromecastUser;
import com.google.android.gms.cast.CastDevice;


public class LobbyActivity extends ActionBarActivity implements IChromecastUser {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lobby, menu);
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

    @Override
    public void onChromecastSelected(CastDevice castDevice) {
    }

    @Override
    public void onChromecastUnselected() {
    }

    @Override
    public void onChromecastConnected() {
    }
}