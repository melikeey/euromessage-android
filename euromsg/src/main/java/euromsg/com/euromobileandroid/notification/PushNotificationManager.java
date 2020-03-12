package euromsg.com.euromobileandroid.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import euromsg.com.euromobileandroid.connection.ConnectionManager;
import euromsg.com.euromobileandroid.enums.PushType;
import euromsg.com.euromobileandroid.notification.carousel.CarouselBuilder;
import euromsg.com.euromobileandroid.model.CarouselItem;
import euromsg.com.euromobileandroid.model.Element;
import euromsg.com.euromobileandroid.model.Message;
import euromsg.com.euromobileandroid.utils.AppUtils;

public class PushNotificationManager {

    private EuroMessageNotificationBuilder euroMessageNotificationBuilder;

    private Context context;

    private NotificationManager notificationManager;

    public PushNotificationManager(Context context) {
        this.context = context;
        euroMessageNotificationBuilder = new EuroMessageNotificationBuilder(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void generateNotification(Message pushMessage, PushType pushType) {

        NotificationCompat.Builder notificationBuilder;

        createNotificationChannel(pushMessage.getSound());

        switch (pushType) {
            case Text:

                notificationBuilder = euroMessageNotificationBuilder.getStandardNotificationBuilder(null, pushMessage, context);

                notificationManager.notify(12, notificationBuilder.build());

                break;

            case Image:

                Bitmap image = ConnectionManager.getInstance().getBitMapFromUri(pushMessage.getMediaUrl());

                notificationBuilder = euroMessageNotificationBuilder.getStandardNotificationBuilder(image, pushMessage, context);

                if (notificationManager != null) {
                    notificationManager.notify(12, notificationBuilder.build());
                }

                break;

            case Action:

                notificationBuilder = euroMessageNotificationBuilder.getActionNotificationBuilder(pushMessage, context);
                if (notificationManager != null) {
                    notificationManager.notify(1, notificationBuilder.build());
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
    private void createNotificationChannel(String sound) {

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

            if (sound != null) {
                Uri soundUri = AppUtils.getSound(context, sound);
                AudioAttributes attributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION).build();
                notificationChannel.setSound(soundUri, attributes);
            }

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}