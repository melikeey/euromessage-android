package com.relateddigital.euromessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import euromsg.com.euromobileandroid.Constants;
import euromsg.com.euromobileandroid.model.CarouselSetUp;
import euromsg.com.euromobileandroid.notification.carousel.CarouselBuilder;
import euromsg.com.euromobileandroid.utils.AppUtils;

public class NotificationEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            int event = bundle.getInt(Constants.ITEM_CLICKED);

            if (event == 20) {
                context.startActivity(AppUtils.getLaunchIntent(context, null));
                context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
                Toast.makeText(context, "Right side click", Toast.LENGTH_LONG).show();
            } else if (event == 10) {
                context.startActivity(AppUtils.getLaunchIntent(context, null));
                context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

                Toast.makeText(context, "Left side click", Toast.LENGTH_LONG).show();
            }
        }
    }
}