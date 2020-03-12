package euromsg.com.euromobileandroid.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import euromsg.com.euromobileandroid.Constants;
import euromsg.com.euromobileandroid.R;
import euromsg.com.euromobileandroid.model.ActionElement;
import euromsg.com.euromobileandroid.model.Message;
import euromsg.com.euromobileandroid.utils.AppUtils;
import euromsg.com.euromobileandroid.utils.ImageUtils;

public class NotificationBuilder {

    private NotificationCompat.Builder notificationBuilder;

    public NotificationBuilder(Context context) {

        String channelId = "euroChannel";
        notificationBuilder = new NotificationCompat.Builder(context, channelId);
    }

    NotificationCompat.Builder createStandardNotificationBuilder(Bitmap pushImage, Message pushMessage, Context context) {

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, AppUtils.getLaunchIntent(context, null), PendingIntent.FLAG_UPDATE_CURRENT);

        String contentTitle = TextUtils.isEmpty(pushMessage.getTitle()) ? AppUtils.getAppLabel(context, "") : pushMessage.getTitle();
        Bitmap largeIcon = ImageUtils.getAppIconAsBitmap(context);

        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSmallIcon(ImageUtils.getAppIcon(context))
                .setStyle(ImageUtils.getNotificationImageStyle(pushImage, pushMessage.getMessage()))
                .setLargeIcon(largeIcon)
                .setContentTitle(contentTitle)
                .setColorized(false)
                .setAutoCancel(true)
                .setContentText(pushMessage.getMessage()).setContentIntent(contentIntent);

        return notificationBuilder;
    }


    public NotificationCompat.Builder createCarouselNotificationBuilder(String contentTitle, String contentText, Context context) {

        notificationBuilder.setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(ImageUtils.getAppIcon(context))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setAutoCancel(true);

        return notificationBuilder;
    }

    NotificationCompat.Builder createActionNotificationBuilder(Message message, Context context) {

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, AppUtils.getLaunchIntent(context, null), PendingIntent.FLAG_UPDATE_CURRENT);

        addAction(notificationBuilder, message.getActionElements(), context);

        notificationBuilder.setSmallIcon(R.drawable.ic_carousel_icon)
                .setContentTitle(message.getTitle())
                .setContentText(message.getMessage())
                .setColor(Color.BLACK)
                .setContentIntent(contentIntent).setAutoCancel(true);

        return notificationBuilder;
    }

    private static void addAction(NotificationCompat.Builder notificationBuilder, ArrayList<ActionElement> actionElements, Context context) {

        for (int i = 0; i < actionElements.size(); i++) {
            notificationBuilder.addAction(android.R.drawable.star_off, actionElements.get(i).getButtonTitle(), getPendingIntent((i + 1) * 10, context));
        }
    }

    private static PendingIntent getPendingIntent(int eventClicked, Context context) {

        Intent intent = new Intent(context, NotificationEventReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ITEM_CLICKED, eventClicked);
        intent.putExtras(bundle);

        return PendingIntent.getBroadcast(context, eventClicked, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
