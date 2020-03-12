package euromsg.com.euromobileandroid.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import euromsg.com.euromobileandroid.connection.ConnectionManager;
import euromsg.com.euromobileandroid.enums.PushType;
import euromsg.com.euromobileandroid.notification.carousel.CarouselBuilder;
import euromsg.com.euromobileandroid.model.CarouselItem;
import euromsg.com.euromobileandroid.model.Element;
import euromsg.com.euromobileandroid.model.Message;

public class PushNotificationManager {

    private NotificationBuilder notificationBuilder;

    private Context context;

    public PushNotificationManager(Context context) {
         notificationBuilder = new NotificationBuilder(context);
         this.context = context;
    }

    public void generateNotification(Message pushMessage, PushType pushType) {

        NotificationCompat.Builder mBuilder;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(notificationManager);

        switch (pushType) {
            case Text:

                mBuilder = notificationBuilder.createStandardNotificationBuilder(null, pushMessage, context);

                notificationManager.notify(12, mBuilder.build());

                break;

            case Image:

                Bitmap image = ConnectionManager.getInstance().getBitMapFromUri(pushMessage.getMediaUrl());

                mBuilder = notificationBuilder.createStandardNotificationBuilder(image, pushMessage, context);

                if (notificationManager != null) {
                    notificationManager.notify(12, mBuilder.build());
                }

                break;

            case Action:

                mBuilder = notificationBuilder.createActionNotificationBuilder(pushMessage, context);
                if (notificationManager != null) {
                    notificationManager.notify(1, mBuilder.build());
                }

                break;

            case Carousel:

                ArrayList<Element> elements = pushMessage.getElements();

                CarouselBuilder carouselBuilder = CarouselBuilder.with(context).beginTransaction();
                carouselBuilder.setContentTitle(pushMessage.getTitle()).setContentText(pushMessage.getMessage());

                for (Element item : elements) {
                    CarouselItem cItem = new CarouselItem(item.getId(), item.getTitle(), item.getContent(), item.getPicture());
                    carouselBuilder.addCarouselItem(cItem);
                }
                carouselBuilder.setOtherRegionClickable(true);
                carouselBuilder.buildCarousel();

                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static void createNotificationChannel(NotificationManager notificationManager) {

        String channelId = "euroChannel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {

            CharSequence name = "Euro Message Channel";
            String description = "Channel for Euro Message notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
            notificationChannel.setDescription(description);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}