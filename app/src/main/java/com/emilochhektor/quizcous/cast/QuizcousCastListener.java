package com.emilochhektor.quizcous.cast;

import android.util.Log;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;

/**
 * Created by Hektor on 2015-02-28.
 */
public class QuizcousCastListener extends Cast.Listener {

    private static String TAG = "com.emilochhektor.quizcous.cast.QuizcousCastListener";

    @Override
    public void onApplicationDisconnected(int statusCode) {
        super.onApplicationDisconnected(statusCode);
        Log.d(TAG, "CastListener.onApplicationDisconnected");
    }

    @Override
    public void onApplicationMetadataChanged(ApplicationMetadata applicationMetadata) {
        super.onApplicationMetadataChanged(applicationMetadata);
        Log.d(TAG, "CastListener.onApplicationMetadataChanged");
    }

    @Override
    public void onApplicationStatusChanged() {
        super.onApplicationStatusChanged();
        Log.d(TAG, "CastListener.onApplicationStatusChanged");
    }

    @Override
    public void onVolumeChanged() {
        super.onVolumeChanged();
        Log.d(TAG, "CastListener.onVolumeChanged");
    }
}
