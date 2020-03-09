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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import euromsg.com.euromobileandroid.Constants;
import euromsg.com.euromobileandroid.R;
import euromsg.com.euromobileandroid.notification.carousel.CarouselBuilder;
import euromsg.com.euromobileandroid.model.CarouselItem;
import euromsg.com.euromobileandroid.model.Element;
import euromsg.com.euromobileandroid.model.Message;
import euromsg.com.euromobileandroid.notification.carousel.NotificationEvenReceiver;
import euromsg.com.euromobileandroid.utils.EuroLogger;
import euromsg.com.euromobileandroid.utils.AppUtils;
import euromsg.com.euromobileandroid.utils.ImageUtils;

public class PushNotificationManager {
    private String cls;

    Context context;

    private String channelId = "euroChannel";

    public void generateCarouselNotification(Context context, Message pushMessage) {

        ArrayList<Element> elements = pushMessage.getElements();

        CarouselBuilder carouselBuilder = CarouselBuilder.with(context).beginTransaction();
        carouselBuilder.setContentTitle(pushMessage.getTitle()).setContentText(pushMessage.getMessage());

        for (Element item : elements) {
            CarouselItem cItem = new CarouselItem(item.getId(), item.getTitle(), item.getContent(), item.getPicture());
            carouselBuilder.addCarouselItem(cItem);
        }
        carouselBuilder.setOtherRegionClickable(true);
        carouselBuilder.buildCarousel();
    }

    public void generateNotification(Context context, Message pushMessage, Bitmap image) {

        try {
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, AppUtils.getLaunchIntent(context, null), PendingIntent.FLAG_UPDATE_CURRENT);

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

    public void generateActionNotification(Context context, String cls, Message message) {

        this.cls = cls;
        this.context = context;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            createNotificationChannel(notificationManager, channelId);
        }

        NotificationCompat.Builder notificationBuilder = createNotificationBuilder(message);

        notificationBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(1, notificationBuilder.build());

    }

    private PendingIntent getPendingIntent(int eventClicked) {
        Intent intent = null;
        try {
            Class<?> clsCl = Class.forName(cls);

            intent = new Intent(context, clsCl);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.ITEM_CLICKED, eventClicked);
            intent.putExtras(bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return PendingIntent.getBroadcast(context, eventClicked, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public NotificationCompat.Builder createNotificationBuilder(Message message) {

        NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(context, channelId);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, AppUtils.getLaunchIntent(context, null), PendingIntent.FLAG_UPDATE_CURRENT);


        for (int i = 0; i<message.getActionElements().size(); i++) {

            addAction(notificationBuilder, R.drawable.ic_launcher, message.getActionElements().get(i).getButtonTitle(), (i+1)*10);
        }

        notificationBuilder.setSmallIcon(R.drawable.ic_carousel_icon)
                .setContentTitle(message.getTitle())
                .setContentText(message.getMessage())
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent).setAutoCancel(true);

        return notificationBuilder;

    }

    private void addAction(NotificationCompat.Builder notificationBuilder ,int logo, String buttonTitle, int eventClicked) {
        notificationBuilder.addAction(R.drawable.ic_launcher, buttonTitle, getPendingIntent(eventClicked));

    }

    public NotificationCompat.Builder createNotificationBuilder(Context context, String contentTitle, String contentText) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, channelId);
        mBuilder.setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(ImageUtils.getAppIcon(context))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setAutoCancel(true);

        return mBuilder;
    }

    private NotificationCompat.Builder createNotificationBuilder(Context context,
                                                                 Bitmap pushImage, Message pushMessage, PendingIntent contentIntent) {

        String title = TextUtils.isEmpty(pushMessage.getTitle()) ? AppUtils.getAppLabel(context, "") : pushMessage.getTitle();
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),
                ImageUtils.getAppIcon(context));

        NotificationCompat.Style style = pushImage == null ?
                new NotificationCompat.BigTextStyle().bigText(pushMessage.getMessage()) :
                new NotificationCompat.BigPictureStyle().bigPicture(pushImage).setSummaryText(pushMessage.getMessage());

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSmallIcon(ImageUtils.getAppIcon(context))
                .setStyle(style)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setColorized(false)
                .setAutoCancel(true)
                .setContentText(pushMessage.getMessage());
        mBuilder.setContentIntent(contentIntent);

        return mBuilder;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(NotificationManager notificationManager, String channelId) {

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
