package euromsg.com.euromobileandroid.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public interface EuroMobileNotificationHandler {

    void onNotificationClicked(Intent intent, Context context);
}
