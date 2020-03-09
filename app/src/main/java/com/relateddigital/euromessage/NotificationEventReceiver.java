package com.relateddigital.euromessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import euromsg.com.euromobileandroid.EuroMobileManager;
import euromsg.com.euromobileandroid.utils.AppUtils;

public class NotificationEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        EuroMobileManager.setAction(intent, context);

        if (EuroMobileManager.getRight()) {

        } else if (EuroMobileManager.getLeft()) {
            context.startActivity(AppUtils.getLaunchIntent(context, null));
        } 
    }
}