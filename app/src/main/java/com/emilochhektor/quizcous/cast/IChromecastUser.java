package com.emilochhektor.quizcous.cast;

import com.google.android.gms.cast.CastDevice;

/**
 * Created by emiols on 2015-03-02.
 */
public interface IChromecastUser {

    public void onChromecastSelected(CastDevice castDevice);
    public void onChromecastUnselected();

    public void onChromecastConnected();
}
