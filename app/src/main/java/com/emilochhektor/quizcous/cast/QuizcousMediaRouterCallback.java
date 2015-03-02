package com.emilochhektor.quizcous.cast;

import android.support.v7.media.MediaRouter;
import android.util.Log;
import com.google.android.gms.cast.CastDevice;

/**
 * Created by emiols on 2015-03-02.
 */
public class QuizcousMediaRouterCallback extends MediaRouter.Callback {

    private static String TAG = "com.emilochhektor.quizcous.cast.QuizcousMediaRouterCallback";
    private CastDevice castDevice;
    private IChromecastUser routeListener;

    public QuizcousMediaRouterCallback(IChromecastUser routeListener){
        this.routeListener = routeListener;
    }

    @Override
    public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
        Log.d("Chromecast selected", info.getName());
        castDevice = CastDevice.getFromBundle(info.getExtras());
        this.routeListener.onChromecastSelected(castDevice);
    }

    @Override
    public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
        Log.d(TAG, "Chromecast unselected");
        this.castDevice = null;
        this.routeListener.onChromecastUnselected();
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
