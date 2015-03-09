package com.emilochhektor.quizcous.cast;

import android.content.Context;

import com.google.android.gms.cast.CastDevice;

import org.json.JSONObject;

/**
 * Created by emiols on 2015-03-02.
 */
public interface IChromecastUser {

    public abstract Context getContext();

    public abstract void onTeardown();

    public abstract void onChromecastConnected();
    public abstract void onChromecastDisconnected();

    public abstract void onReceiverApplicationConnected();
    public abstract void onReceiverApplicationDisconnected();

    public abstract void onMessageReceived(JSONObject json);
}
