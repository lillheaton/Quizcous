package com.emilochhektor.quizcous.cast;

import android.util.Log;

import android.support.v7.media.MediaRouter;

import com.google.android.gms.cast.CastDevice;

/**
 * Created by emiols on 2015-03-02.
 */
public class QuizcousMediaRouterCallback extends MediaRouter.Callback {

    private static String TAG = "com.emilochhektor.quizcous.cast.QuizcousMediaRouterCallback";
    private ChromecastConnectionHandler connectionHandler;

    public QuizcousMediaRouterCallback(ChromecastConnectionHandler connectionHandler){
        this.connectionHandler = connectionHandler;
    }

    @Override
    public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
        Log.d(TAG, "onRouteSelected");

        CastDevice castDevice = CastDevice.getFromBundle(info.getExtras());
        this.connectionHandler.onChromecastSelected(castDevice);
    }

    @Override
    public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
        Log.d(TAG, "onRouteUnselected");

        this.connectionHandler.onChromecastUnselected();
    }

    @Override
    public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo info) {
        Log.d(TAG, "onRouteAdded");
    }

    @Override
    public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo info) {
        Log.d(TAG, "onRouteRemove");
    }

    @Override
    public void onRouteChanged(MediaRouter router, MediaRouter.RouteInfo info) {
        Log.d(TAG, "onRouteChanged");
    }

    @Override
    public void onRouteVolumeChanged(MediaRouter router, MediaRouter.RouteInfo info) {
        Log.d(TAG, "onRouteVolumeChanged");
    }
}
