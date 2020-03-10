package euromsg.com.euromobileandroid.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import euromsg.com.euromobileandroid.Constants;
import euromsg.com.euromobileandroid.EuroMobileManager;
import euromsg.com.euromobileandroid.model.CarouselSetUp;
import euromsg.com.euromobileandroid.notification.carousel.CarouselBuilder;

class NotificationEventProcessor {


    static void processor(Intent intent, Context context) {

        if (isCarousel(intent)) {

            handleCarouselClick(intent, context);

        } else {
            closeNotificationFromActionButtonClick(context);
        }

        if (EuroMobileManager.getEuroMobileNotificationHandler() != null) {
            EuroMobileManager.getEuroMobileNotificationHandler().onNotificationClicked(intent, context);
        }
    }


    private static boolean isCarousel(Intent intent) {

        if (intent.getExtras().getInt(Constants.ITEM_CLICKED) == Constants.EVENT_LEFT_ITEM_CLICKED || intent.getExtras().getInt(Constants.ITEM_CLICKED) == Constants.EVENT_RIGHT_ARROW_CLICKED) {

            return true;
        }
        return false;
    }

    private static void handleCarouselClick(Intent intent, Context context) {

        int afterThisNumberIsNotCarouselArrow = 2;

        Bundle bundle = intent.getExtras();

        int clickEvent = bundle.getInt(Constants.ITEM_CLICKED);

        CarouselSetUp carouselSetUp = bundle.getParcelable(Constants.CAROUSAL_SET_UP_KEY);
        CarouselBuilder.with(context).handleClickEvent(clickEvent, carouselSetUp);

        if (clickEvent > afterThisNumberIsNotCarouselArrow)
            closeNotificationFromActionButtonClick(context);
    }


    private static void closeNotificationFromActionButtonClick(Context context) {

        context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

}
