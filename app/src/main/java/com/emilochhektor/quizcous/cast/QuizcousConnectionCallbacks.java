package com.emilochhektor.quizcous.cast;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Hektor on 2015-02-28.
 */
public class QuizcousConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Cast.ApplicationConnectionResult> {

    private static String TAG = "com.emilochhektor.quizcous.cast.QuizcousConnectionCallbacks";
    private ChromecastConnectionHandler connectionHandler;

    public QuizcousConnectionCallbacks(ChromecastConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "QuizcousConnectionCallbacks.onConnected");

        this.connectionHandler.onChromecastConnected(connectionHint);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "QuizcousConnectionCallbacks.onConnectionSuspended");
        // Called when the client is temporarily in a disconnected state.
        // This can happen if there is a problem with the remote service (e.g. a crash or resource problem causes it to be killed by the system).
        // When called, all requests have been canceled and no outstanding listeners will be executed.
        // GoogleApiClient will automatically attempt to restore the connection.
        // Applications should disable UI components that require the service, and wait for a call to onConnected(Bundle) to re-enable them
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed" + ", " + connectionResult.toString());

        this.connectionHandler.onConnectionFailed();
    }



    @Override
    public void onResult(Cast.ApplicationConnectionResult applicationConnectionResult) {
        Log.d(TAG, "onResult");

        this.connectionHandler.onApplicationConnectionResult(applicationConnectionResult);
    }
}
