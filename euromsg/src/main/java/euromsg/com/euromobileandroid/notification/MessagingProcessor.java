package euromsg.com.euromobileandroid.notification;

import android.content.Context;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import euromsg.com.euromobileandroid.Constants;
import euromsg.com.euromobileandroid.EuroMobileManager;
import euromsg.com.euromobileandroid.model.Message;
import euromsg.com.euromobileandroid.utils.EuroLogger;
import euromsg.com.euromobileandroid.utils.SharedPreference;

public class MessagingProcessor {

    static String title = "{\n" +
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


    public static void processRemoteMessage(RemoteMessage remoteMessage, Context context) {

        //  Map<String, String> remoteMessageData = remoteMessage.getData();
        //  Message pushMessage = new Message(remoteMessageData);

        Message pushMessage = new Gson().fromJson(title, Message.class);

        EuroLogger.debugLog("Message received : " + pushMessage.getMessage());

        PushNotificationManager.generateNotification(context, pushMessage, pushMessage.getPushType());

        String appAlias = SharedPreference.getString(context, Constants.APP_ALIAS);

        EuroMobileManager.init(appAlias, context).reportReceived(pushMessage.getPushId());
    }
}
