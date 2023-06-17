package com.allNetworks.adsnets;

import android.app.Application;

import com.onesignal.OneSignal;

public class application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!AdsUnites.OneSignalKey.isEmpty()){

            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

            OneSignal.initWithContext(this);
            OneSignal.setAppId(AdsUnites.OneSignalKey);
            OneSignal.promptForPushNotifications();
        }
    }
}
