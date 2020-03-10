package euromsg.com.euromobileandroid.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
         NotificationEventProcessor.processor(intent, context);
    }
}