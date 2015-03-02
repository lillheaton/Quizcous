package com.emilochhektor.quizcous;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.emilochhektor.quizcous.cast.IChromecastUser;
import com.emilochhektor.quizcous.cast.QuizcousConnectionCallbacks;
import com.emilochhektor.quizcous.cast.QuizcousMediaRouterCallback;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.common.api.GoogleApiClient;


import com.emilochhektor.quizcous.cast.QuizcousCastListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class StartScreenActivity extends ActionBarActivity implements IChromecastUser {

    private static String TAG = "com.emilochhektor.quizcous.StartScreenActivity";
    private static String APP_ID = "B7BD0161";

    // Something to handle selection of chromecast
    private MediaRouter mediaRouter;
    // The selector...
    private MediaRouteSelector mediaRouteSelector;
    // Handle callbacks from when select/unselect chromecast
    private QuizcousMediaRouterCallback mediaRouterCallback;

    private QuizcousCastListener castListener;
    private GoogleApiClient apiClient;
    private QuizcousConnectionCallbacks connectionCallbacks;


    private void init(){
        initMediaRouter();
    }

    private void initMediaRouter() {
        mediaRouter = MediaRouter.getInstance(getApplicationContext());
        mediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast(APP_ID))
                .build();

        mediaRouterCallback = new QuizcousMediaRouterCallback(this);
    }

    private void connectToChromecast(CastDevice castDevice) {
        try {
            castListener = new QuizcousCastListener();
            connectionCallbacks = new QuizcousConnectionCallbacks(APP_ID, this);

            Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions.builder(castDevice, castListener);

            apiClient = new GoogleApiClient.Builder(this)
                    .addApi(Cast.API, apiOptionsBuilder.build())
                    .addConnectionCallbacks(connectionCallbacks)
                    .build();

            connectionCallbacks.setApiClient(apiClient);

            apiClient.connect();

        } catch (Exception e) {
            Log.d(TAG, "Lol exception: " + e.toString());
        }
    }

    private void launchReceiverApplication() {
        try {
            Cast.CastApi.launchApplication(apiClient, APP_ID, false)
                    .setResultCallback(new ResultCallback<Cast.ApplicationConnectionResult>() {
                        @Override
                        public void onResult(Cast.ApplicationConnectionResult applicationConnectionResult) {
                            Status status = applicationConnectionResult.getStatus();
                            if (status.isSuccess()) {
                                // Start new activity on successful receiver launch
                                startLobbyActivity();
                            } else {
                                Log.d(TAG, "No success on status, lol");
                            }
                        }
                    });

        } catch (Exception e) {
            Log.d(TAG, "Failed to launch receiver application", e);
        }
    }

    private void startLobbyActivity() {
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
    }





    public void onChromecastSelected(CastDevice castDevice) {
        connectToChromecast(castDevice);
    }

    public void onChromecastUnselected() {
        // Chromecast is unselected, disconnect connection and let the user be able to reconnect
        apiClient.disconnect();
    }

    public void onChromecastConnected() {
        launchReceiverApplication();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startScreen);

        this.init();
    }

    @Override
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaRouter.addCallback(mediaRouteSelector, mediaRouterCallback, MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onStop() {
        mediaRouter.removeCallback(mediaRouterCallback);
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        MediaRouteActionProvider mediaRouteActionProvider =
                (MediaRouteActionProvider)MenuItemCompat.getActionProvider(mediaRouteMenuItem);

        mediaRouteActionProvider.setRouteSelector(mediaRouteSelector);
        return true;
    }
}
