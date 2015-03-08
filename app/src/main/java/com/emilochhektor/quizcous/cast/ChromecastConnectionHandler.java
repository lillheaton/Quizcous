package com.emilochhektor.quizcous.cast;

import com.emilochhektor.quizcous.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

/**
 * Created by Hektor on 2015-03-07.
 */
public class ChromecastConnectionHandler {

    private static String TAG = "com.emilochhektor.quizcous.cast.ChromecastConnectionHandler";

    private String appID;
    private IChromecastUser chromecastUser;


    private MediaRouter mediaRouter;
    private MediaRouteSelector mediaRouteSelector;
    private QuizcousMediaRouterCallback mediaRouterCallback;

    private QuizcousCastListener castListener;
    private QuizcousConnectionCallbacks connectionCallbacks;

    private GoogleApiClient apiClient;
    private CastDevice castDevice;


    // # States
    private boolean waitingToReconnect = false;
    private String sessionID = null;


    public ChromecastConnectionHandler(IChromecastUser chromecastUser) {
        this.chromecastUser = chromecastUser;
        this.appID = chromecastUser.getContext().getString(R.string.app_id);
    }


    // # Public methods
    public void init() {
        this.initMediaRouter();
    }

    // ## Media Routing
    public void startRoutePolling() {
        // Start discovery of cast devices
        this.mediaRouter.addCallback(
                this.mediaRouteSelector,
                this.mediaRouterCallback,
                MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY
        );
    }
    public void stopRoutePolling() {
        this.mediaRouter.removeCallback(this.mediaRouterCallback);
    }

    public void setChromecastMenuItem(MenuItem menuItem) {
        MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mediaRouteActionProvider.setRouteSelector(mediaRouteSelector);
    }


    // # Private methods
    // ## Initialize
    private void initMediaRouter() {
        Context context = chromecastUser.getContext();

        mediaRouter = MediaRouter.getInstance(context);
        mediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast(appID))
                .build();

        mediaRouterCallback = new QuizcousMediaRouterCallback(this);
    }


    // ## Chromecast device
    private void connectToChromecast() {
        try {
            castListener = new QuizcousCastListener(this);
            connectionCallbacks = new QuizcousConnectionCallbacks(this);

            Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions.builder(castDevice, castListener);

            apiClient = new GoogleApiClient.Builder(chromecastUser.getContext())
                    .addApi(Cast.API, apiOptionsBuilder.build())
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(connectionCallbacks)
                    .build();

            apiClient.connect();

            Log.d(TAG, "Connecting to chromecast...");
        } catch (Exception e) {
            Log.d(TAG, "Exception when setting up connection to chromecast", e);
        }
    }



    // ## Receiver application
    private void connectToReceiverApplication() {
        if (this.waitingToReconnect) {

        } else {
            // check for joining existing
            // else
            this.launchReceiverApplication();
        }
    }

    private void launchReceiverApplication() {
        try {
            Cast.CastApi.launchApplication(apiClient, appID, false)
                    .setResultCallback(connectionCallbacks);
        } catch (Exception e) {
            Log.d(TAG, "Failed to launch receiver application", e);
        }
    }


    // Add more to teardown as more communication is added
    private void teardownAll() {
        Log.d(TAG, "Teardown All");

        if (this.apiClient != null) {

            if (this.apiClient.isConnected() || this.apiClient.isConnecting()) {

                Cast.CastApi.stopApplication(this.apiClient, this.sessionID);

                this.apiClient.disconnect();
            }

            this.apiClient = null;
        }

        this.castDevice = null;
        this.waitingToReconnect = false;
        this.sessionID = null;

        this.chromecastUser.onTeardown();
    }







    // # Event callbacks
    // ## QuizcousMediaRouterCallback
    public void onChromecastSelected(CastDevice castDevice) {
        this.castDevice = castDevice;
        this.connectToChromecast();
    }
    public void onChromecastUnselected() {
        this.teardownAll();
    }

    // ## QuizcousCastListener
    public void onApplicationMetaDataChanged() {

    }
    public void onApplicationStatusChanged() {
        String status = Cast.CastApi.getApplicationStatus(apiClient);
    }

    // ## QuizcousConnectionCallbacks
    public void onChromecastConnected(Bundle connectionHint) {
        this.connectToReceiverApplication();

        this.chromecastUser.onChromecastConnected();
    }
    public void onConnectionFailed() {

    }
    public void onApplicationConnectionResult(Cast.ApplicationConnectionResult result) {
        Log.d(TAG, "onApplicationConnectionResult");
        Status status = result.getStatus();

        if (status.isSuccess()) {
            this.sessionID = result.getSessionId();

            ApplicationMetadata applicationMetadata = result.getApplicationMetadata();
            String applicationStatus = result.getApplicationStatus();
            boolean wasLaunched = result.getWasLaunched();

            Log.d(TAG,
                "application name: " + applicationMetadata.getName() +
                ", status: " + applicationStatus +
                ", sessionId: " + this.sessionID +
                ", wasLaunched: " + wasLaunched
            );

            this.chromecastUser.onReceiverApplicationConnected();
        } else {
            Log.d(TAG, "No success in applicationConnectionResult");
            this.teardownAll();
        }
    }
}
