package com.emilochhektor.quizcous.cast;

import android.content.Context;

import com.google.android.gms.cast.CastDevice;

import org.json.JSONObject;

/**
 * Created by emiols on 2015-03-02.
 */
public interface IChromecastUser {

    // Activities implementing this Interface doesn't need to implement this
    public abstract Context getApplicationContext();

    public abstract void onTeardown();

    public abstract void onChromecastConnected();
    public abstract void onChromecastDisconnected();

    public abstract void onReceiverApplicationConnected();
    public abstract void onReceiverApplicationDisconnected();

    public abstract void onMessageChannelConnected();
    public abstract void onMessageReceived(JSONObject json);
}
