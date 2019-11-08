package com.harman.borsuki.carpooling.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class AndroidAutoMessagingService extends IntentService {

    private static final String ACTION_REPLY = "com.harman.borsuki.carpooling.REPLY";
    private static final String ACTION_MARK_AS_READ = "com.harman.borsuki.carpooling.MARK_AS_READ";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AndroidAutoMessagingService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
