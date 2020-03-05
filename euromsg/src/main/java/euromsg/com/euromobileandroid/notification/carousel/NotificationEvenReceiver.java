package euromsg.com.euromobileandroid.notification.carousel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import euromsg.com.euromobileandroid.Constants;
import euromsg.com.euromobileandroid.model.CarouselSetUp;
import euromsg.com.euromobileandroid.utils.AppUtils;

public class NotificationEvenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            int event = bundle.getInt(Constants.ITEM_CLICKED);
            CarouselSetUp carouselSetUp = bundle.getParcelable(Constants.CAROUSAL_SET_UP_KEY);


            if (event > 2) {
                context.startActivity(AppUtils.getLaunchIntent(context, null));
                context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
            }

            if (event > 0 && carouselSetUp != null)
                CarouselBuilder.with(context).handleClickEvent(event, carouselSetUp);
        }
    }
}