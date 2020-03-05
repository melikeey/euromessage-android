package euromsg.com.euromobileandroid.service;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;


import java.util.Map;

import euromsg.com.euromobileandroid.Constants;
import euromsg.com.euromobileandroid.EuroMobileManager;

import euromsg.com.euromobileandroid.connection.ConnectionManager;
import euromsg.com.euromobileandroid.model.Message;
import euromsg.com.euromobileandroid.notification.PushNotificationManager;
import euromsg.com.euromobileandroid.utils.EuroLogger;
import euromsg.com.euromobileandroid.utils.SharedPreference;

public class EuroFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        try {
            EuroLogger.debugLog("On new token : " + token);
            EuroMobileManager.getInstance().subscribe(token, this);
        } catch (Exception e) {
            EuroLogger.debugLog(e.toString());
            EuroLogger.debugLog("Failed to complete token refresh");
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        PushNotificationManager pushNotificationManager = new PushNotificationManager();

        Map<String, String> remoteMessageData = remoteMessage.getData();
        //Message pushMessage = new Message(remoteMessageData);


        String title = "{\n" +
                "    \"pushType\": \"Action\",\n" +
                "    \"url\": \"http://www.google.com.tr\",\n" +
                "    \"mediaUrl\": \"\",\n" +
                "    \"pushId\": \"bea5303f-11aa-4ac7-aae8-2265ba63b535\",\n" +
                "    \"altUrl\": \"\",\n" +
                "    \"sound\": \"default\",\n" +
                "    \"message\": \"Sepetinizde ürün var Alışverişe Devam edin\",\n" +
                "    \"title\": \"Dikkat\",\n" +
                "    \"actionElements\": [{\n" +
                "            \"id\": \"1\",\n" +
                "            \"buttonTitle\": \"Tamam\"\n" +
                "        }, {\n" +
                "             \"id\": \"2\",\n" +
                "            \"buttonTitle\": \"Şimdilik İstemiyorum\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Message pushMessage = new Gson().fromJson(title, Message.class);


        EuroLogger.debugLog("Message received : " + pushMessage.getMessage());

        switch (pushMessage.getPushType()) {

            case Image:
                if (pushMessage.getElements() != null) {

                    pushNotificationManager.generateCarouselNotification(this, pushMessage);
                } else
                    pushNotificationManager.generateNotification(this, remoteMessageData, ConnectionManager.getInstance().getBitMapFromUri(pushMessage.getMediaUrl()));
                break;

            case Text:
                pushNotificationManager.generateNotification(this, remoteMessageData, null);

                break;

            case Action:

                pushNotificationManager.generateActionNotification(this, SharedPreference.getString(this, "cls"), pushMessage );
        }

        String applicationKey = SharedPreference.getString(this, Constants.APP_ALIAS);

        EuroMobileManager.createInstance(applicationKey, this).reportReceived(pushMessage.getPushId());
    }
}