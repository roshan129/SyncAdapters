package com.adivid.syncadapters;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();

    }
}
