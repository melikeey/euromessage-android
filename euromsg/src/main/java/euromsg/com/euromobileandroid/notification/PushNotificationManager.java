package euromsg.com.euromobileandroid.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Map;

import euromsg.com.euromobileandroid.carousalnotification.CarousalNotificationBuilder;
import euromsg.com.euromobileandroid.carousalnotification.CarousalItem;
import euromsg.com.euromobileandroid.model.CarouselElement;
import euromsg.com.euromobileandroid.model.Message;
import euromsg.com.euromobileandroid.utils.EuroLogger;
import euromsg.com.euromobileandroid.utils.Utils;

public class PushNotificationManager {

    private String channelId = "euroChannel";

    public void generateCarouselNotification(Context context, Message pushMessage) {

        ArrayList<CarouselElement> carouselElements = pushMessage.getElements();

        CarousalNotificationBuilder carousalNotificationBuilder = CarousalNotificationBuilder.with(context).beginTransaction();
        carousalNotificationBuilder.setContentTitle(pushMessage.getTitle()).setContentText(pushMessage.getMessage());

        for (CarouselElement item : carouselElements) {
            CarousalItem cItem = new CarousalItem(item.getId(), item.getTitle(), item.getContent(), item.getPicture());
            carousalNotificationBuilder.addCarousalItem(cItem);
        }
        carousalNotificationBuilder.setOtherRegionClickable(true);
        carousalNotificationBuilder.buildCarousal();
    }

    public void generateNotification(Context context, Map<String, String> data, Bitmap image) {

        try {

            Message pushMessage = new Message(data);

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, Utils.getLaunchIntent(context, null), PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = createNotificationBuilder(context, image, pushMessage, contentIntent);

           NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mNotificationManager != null) {
                createNotificationChannel(mNotificationManager, channelId);
            }

            mNotificationManager.notify(12, mBuilder.build());

        } catch (Exception e) {
            EuroLogger.debugLog("Generate notification : " + e.getMessage());
        }
    }


    public NotificationCompat.Builder createNotificationBuilder(Context context, String contentTitle, String contentText){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, channelId);
        mBuilder.setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(Utils.getAppIcon(context))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
        .setAutoCancel(true);

        return mBuilder;
    }

    private NotificationCompat.Builder createNotificationBuilder(Context context,
                                                                 Bitmap pushImage, Message pushMessage, PendingIntent contentIntent) {

        String title = TextUtils.isEmpty(pushMessage.getTitle()) ? Utils.getAppLabel(context, "") : pushMessage.getTitle();
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),
                Utils.getAppIcon(context));

        NotificationCompat.Style style = pushImage == null ?
                new NotificationCompat.BigTextStyle().bigText(pushMessage.getMessage()) :
                new NotificationCompat.BigPictureStyle().bigPicture(pushImage).setSummaryText(pushMessage.getMessage());

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSmallIcon(Utils.getAppIcon(context))
                .setStyle(style)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setColorized(false).setAutoCancel(true)
                .setContentText(pushMessage.getMessage());
        mBuilder.setContentIntent(contentIntent);

        return mBuilder;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(NotificationManager notificationManager, String channelId) {

        CharSequence name = "Euro Message Channel";
        String description = "Channel for Euro Message notifications";
        int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
        notificationChannel.setDescription(description);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        notificationManager.createNotificationChannel(notificationChannel);
    }
}