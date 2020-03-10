package euromsg.com.euromobileandroid.service;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import euromsg.com.euromobileandroid.EuroMobileManager;

import euromsg.com.euromobileandroid.notification.MessagingProcessor;
import euromsg.com.euromobileandroid.utils.EuroLogger;

public class EuroFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        try {
            EuroLogger.debugLog("On new token : " + token);
            EuroMobileManager.getManager().subscribe(token, this);
        } catch (Exception e) {
            EuroLogger.debugLog(e.toString());
            EuroLogger.debugLog("Failed to complete token refresh");
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        MessagingProcessor.processRemoteMessage(remoteMessage, this);
    }
}