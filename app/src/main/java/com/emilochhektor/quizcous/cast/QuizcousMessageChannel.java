package com.emilochhektor.quizcous.cast;

import android.util.Log;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by emiols on 2015-03-02.
 */
public class QuizcousMessageChannel implements Cast.MessageReceivedCallback {

    private static String TAG = "com.emilochhektor.quizcous.cast.QuizcousChannel";
    private final static String NAMESPACE = "urn:x-cast:com.emilochhektor.quizcous";

    private ChromecastConnectionHandler connectionHandler;

    public QuizcousMessageChannel(ChromecastConnectionHandler connectionHandler){
        this.connectionHandler = connectionHandler;
    }


    public void connect() {
        try {
            Cast.CastApi.setMessageReceivedCallbacks(
                this.connectionHandler.getApiClient(),
                this.NAMESPACE,
                this
            );
        } catch (IOException ex) {
            Log.d(TAG, "Exception while creating message channel", ex);
        }
    }

    public void sendMessage(String message){
        try {
            Cast.CastApi.sendMessage(this.connectionHandler.getApiClient(), this.NAMESPACE, message)
                    .setResultCallback(new ChannelResultCallback());
        }
        catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onMessageReceived(CastDevice castDevice, String namespace, String data) {
        try {
            JSONObject json = new JSONObject(data);
            this.connectionHandler.onMessageReceived(json);
        } catch (JSONException ex) {
            Log.d(TAG, "Exception when parsing message received as JSON", ex);
        }
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
