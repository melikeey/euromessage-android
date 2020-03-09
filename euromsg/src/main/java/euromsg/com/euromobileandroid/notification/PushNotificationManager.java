package euromsg.com.euromobileandroid.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import euromsg.com.euromobileandroid.Constants;
import euromsg.com.euromobileandroid.R;
import euromsg.com.euromobileandroid.connection.ConnectionManager;
import euromsg.com.euromobileandroid.enums.PushType;
import euromsg.com.euromobileandroid.notification.carousel.CarouselBuilder;
import euromsg.com.euromobileandroid.model.CarouselItem;
import euromsg.com.euromobileandroid.model.Element;
import euromsg.com.euromobileandroid.model.Message;
import euromsg.com.euromobileandroid.utils.AppUtils;
import euromsg.com.euromobileandroid.utils.ImageUtils;

public class PushNotificationManager {

    private static String channelId = "euroChannel";

    private static Context mContext;

    public static void generateNotification(Context context, Message pushMessage, PushType pushType) {

        NotificationCompat.Builder mBuilder;

        mContext = context;

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(notificationManager, channelId);


        switch (pushType) {
            case Text:

                mBuilder = createNotificationBuilder(null, pushMessage);

                notificationManager.notify(12, mBuilder.build());

                break;


            case Image:

                Bitmap image = ConnectionManager.getInstance().getBitMapFromUri(pushMessage.getMediaUrl());

                mBuilder = createNotificationBuilder(image, pushMessage);

                notificationManager.notify(12, mBuilder.build());

                break;


            case Action:

                mBuilder  = createActionNotificationBuilder(pushMessage);
                notificationManager.notify(1, mBuilder.build());

                break;

            case Carousel:

                ArrayList<Element> elements = pushMessage.getElements();

                CarouselBuilder carouselBuilder = CarouselBuilder.with(mContext).beginTransaction();
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
    public static void createNotificationChannel(NotificationManager notificationManager, String channelId) {

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

    public static NotificationCompat.Builder createCarouselNotificationBuilder(String contentTitle, String contentText) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext, channelId);
        mBuilder.setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(ImageUtils.getAppIcon(mContext))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setAutoCancel(true);

        return mBuilder;
    }

    private static NotificationCompat.Builder createNotificationBuilder(Bitmap pushImage, Message pushMessage) {

        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, AppUtils.getLaunchIntent(mContext, null), PendingIntent.FLAG_UPDATE_CURRENT);

        String title = TextUtils.isEmpty(pushMessage.getTitle()) ? AppUtils.getAppLabel(mContext, "") : pushMessage.getTitle();
        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(),
                ImageUtils.getAppIcon(mContext));

        NotificationCompat.Style style = pushImage == null ?
                new NotificationCompat.BigTextStyle().bigText(pushMessage.getMessage()) :
                new NotificationCompat.BigPictureStyle().bigPicture(pushImage).setSummaryText(pushMessage.getMessage());

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, channelId)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSmallIcon(ImageUtils.getAppIcon(mContext))
                .setStyle(style)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setColorized(false)
                .setAutoCancel(true)
                .setContentText(pushMessage.getMessage());
        mBuilder.setContentIntent(contentIntent);

        return mBuilder;
    }

    public static NotificationCompat.Builder createActionNotificationBuilder(Message message) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, channelId);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, AppUtils.getLaunchIntent(mContext, null), PendingIntent.FLAG_UPDATE_CURRENT);

        for (int i = 0; i < message.getActionElements().size(); i++) {

            addAction(notificationBuilder, R.drawable.ic_launcher, message.getActionElements().get(i).getButtonTitle(), (i + 1) * 10);
        }

        notificationBuilder.setSmallIcon(R.drawable.ic_carousel_icon)
                .setContentTitle(message.getTitle())
                .setContentText(message.getMessage())
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent).setAutoCancel(true);

        return notificationBuilder;

    }

    private static void addAction(NotificationCompat.Builder notificationBuilder, int logo, String buttonTitle, int eventClicked) {
        notificationBuilder.addAction(R.drawable.ic_launcher, buttonTitle, getPendingIntent(eventClicked));

    }

    private static PendingIntent getPendingIntent(int eventClicked) {

        Intent intent;

        intent = new Intent(mContext, NotificationEventReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ITEM_CLICKED, eventClicked);
        intent.putExtras(bundle);

        return PendingIntent.getBroadcast(mContext, eventClicked, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }
}
