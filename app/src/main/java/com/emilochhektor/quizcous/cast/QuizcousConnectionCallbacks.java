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

    private GoogleApiClient mApiClient;
    private String mAppID;

    public QuizcousConnectionCallbacks(String appID) {
        mAppID = appID;
    }

    public void setApiClient(GoogleApiClient apiClient) {
        mApiClient = apiClient;
    }


    private void launchReceiverApplication() {
        try {
            Cast.CastApi.launchApplication(mApiClient, mAppID, false)
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
