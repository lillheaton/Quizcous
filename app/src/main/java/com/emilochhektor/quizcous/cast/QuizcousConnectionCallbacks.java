package com.emilochhektor.quizcous.cast;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Hektor on 2015-02-28.
 */
public class QuizcousConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks {

    private static String TAG = "com.emilochhektor.quizcous.cast.QuizcousConnectionCallbacks";
    private GoogleApiClient apiClient;
    private String appID;
    private IChromecastUser chromecastUser;

    public QuizcousConnectionCallbacks(String appID, IChromecastUser chromecastUser) {
        this.appID = appID;
        this.chromecastUser = chromecastUser;
    }

    public void setApiClient(GoogleApiClient apiClient) {
        this.apiClient = apiClient;
    }

    private void launchReceiverApplication() {
        try {
            Cast.CastApi.launchApplication(apiClient, appID, false)
                .setResultCallback(new ResultCallback<Cast.ApplicationConnectionResult>() {
                    @Override
                    public void onResult(Cast.ApplicationConnectionResult applicationConnectionResult) {
                        Status status = applicationConnectionResult.getStatus();
                        if (status.isSuccess()) {

                        } else {
                            Log.d(TAG, "No success on status, lol");
                        }
                    }
                });

        } catch (Exception e) {
            Log.d(TAG, "Failed to launch receiver application", e);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "QuizcousConnectionCallbacks.onConnected");
        launchReceiverApplication();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "QuizcousConnectionCallbacks.onConnectionSuspended");
    }
}
