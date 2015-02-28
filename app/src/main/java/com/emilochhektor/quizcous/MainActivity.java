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
import android.view.View;
import android.widget.Button;

import com.emilochhektor.quizcous.cast.QuizcousConnectionCallbacks;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.common.api.GoogleApiClient;


import com.emilochhektor.quizcous.cast.QuizcousCastListener;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private static String TAG = "com.emilochhektor.quizcous.MainActivity";
    private static String APP_ID = "B7BD0161";


    private MediaRouter mMediaRouter;
    private MediaRouteSelector mMediaRouteSelector;
    private MyMediaRouterCallback mMediaRouterCallback;

    private CastDevice mCastDevice;
    private QuizcousCastListener mCastListener;
    private GoogleApiClient mApiClient;

    private QuizcousConnectionCallbacks mConnectionCallbacks;





    private void initMediaRouter() {
        mMediaRouter = MediaRouter.getInstance(getApplicationContext());
        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast(APP_ID))
                .build();

        mMediaRouterCallback = new MyMediaRouterCallback();
    }


    private void launchReceiver() {
        try {
            mCastListener = new QuizcousCastListener();
            mConnectionCallbacks = new QuizcousConnectionCallbacks(APP_ID);

            Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions.builder(mCastDevice, mCastListener);

            mApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Cast.API, apiOptionsBuilder.build())
                    .addConnectionCallbacks(mConnectionCallbacks)
                    .build();

            mConnectionCallbacks.setApiClient(mApiClient);

            mApiClient.connect();

        } catch (Exception e) {
            Log.d(TAG, "Lol exception: " + e.toString());
        }
    }


    private void startGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);

        intent.putExtra("CastDevice", mCastDevice);

        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMediaRouter();

        Button btn = (Button)findViewById(R.id.btn_start);
        btn.setOnClickListener(this);
        btn.setVisibility(View.GONE); // Hide button until we're connected to chromecast
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaRouter.addCallback(mMediaRouteSelector, mMediaRouterCallback, MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onPause() {
        if (isFinishing()) {
            mMediaRouter.removeCallback(mMediaRouterCallback);
        }
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaRouter.addCallback(mMediaRouteSelector, mMediaRouterCallback, MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onStop() {
        mMediaRouter.removeCallback(mMediaRouterCallback);
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        MediaRouteActionProvider mediaRouteActionProvider =
                (MediaRouteActionProvider)MenuItemCompat.getActionProvider(mediaRouteMenuItem);

        mediaRouteActionProvider.setRouteSelector(mMediaRouteSelector);
        return true;
    }




    @Override
    public void onClick(View v) {
        this.startGameActivity();
    }





    // Inner class not yet moved to separate file
    public class MyMediaRouterCallback extends MediaRouter.Callback {

        @Override
        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
            Log.d("Chromecast selected", info.getName());
            mCastDevice = CastDevice.getFromBundle(info.getExtras());
            launchReceiver();
        }

        @Override
        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
            Log.d(TAG, "Chromecast unselected");
            mCastDevice = null;
        }
    }
}
