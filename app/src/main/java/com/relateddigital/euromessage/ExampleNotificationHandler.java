package com.relateddigital.euromessage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import euromsg.com.euromobileandroid.EuroMobileManager;
import euromsg.com.euromobileandroid.notification.EuroMobileNotificationHandler;

public class ExampleNotificationHandler implements EuroMobileNotificationHandler {

    @Override
    public void onNotificationClicked(Intent intent, Context context) {

        switch (EuroMobileManager.getEvent(intent)) {

            case ACTION_MID:

                Log.e("Mid action", "mid");

                break;

            case ACTION_LEFT:
                Log.e("Left action", "left");

                break;

            case ACTION_RIGHT:
                Log.e("Right action", "right");
                break;


            case CAROUSEL_LEFT_ITEM:
                Log.e("Carousel", "Left");

                break;

            case CAROUSEL_RIGHT_ITEM:
                Log.e("Carousel", "Right");

                break;
        }


    }
}