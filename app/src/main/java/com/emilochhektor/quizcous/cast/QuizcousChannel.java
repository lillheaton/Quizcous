package com.emilochhektor.quizcous.cast;

import android.util.Log;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by emiols on 2015-03-02.
 */
public class QuizcousChannel implements Cast.MessageReceivedCallback {

    private static String TAG = "com.emilochhektor.quizcous.cast.QuizcousChannel";
    private final static String NAMESPACE = "urn:x-cast:com.emilochhektor.quizcous";
    private GoogleApiClient apiClient;
    private IChromecastUser chromecastUser;

    public QuizcousChannel(GoogleApiClient apiClient, IChromecastUser chromecastUser){
        this.apiClient = apiClient;
        this.chromecastUser = chromecastUser;
    }

    public void sendMessage(String message){
        try{
            Cast.CastApi.sendMessage(this.apiClient, this.NAMESPACE, message)
                    .setResultCallback(new ChannelResultCallback());
        }
        catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onMessageReceived(CastDevice castDevice, String s, String s2) {
    }


    private class ChannelResultCallback implements ResultCallback<Status> {

        @Override
        public void onResult(Status result) {
            if (!result.isSuccess()) {
                Log.e(TAG, "Sending message failed");
            }
        }
    }
}
