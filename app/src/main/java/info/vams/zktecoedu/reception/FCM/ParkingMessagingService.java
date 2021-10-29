package info.vams.zktecoedu.reception.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Date;
import java.util.Map;

import info.vams.zktecoedu.reception.Activity.LoginActivity;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetVisitor.GetParkVisitorResp;
import info.vams.zktecoedu.reception.Model.ParkingModels.NotificationParkingData;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Service.NotificationAreaService;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.SPbean;

public class ParkingMessagingService extends FirebaseMessagingService {
//    public MyFirebaseMessagingService() {
//        super();
//    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
//        sendNotification(remoteMessage);
        System.out.println("MyFirebaseMessagingService: " + remoteMessage.getData().toString());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        System.out.println("FCM TOKEN ID: " + s);
    }


    private void sendNotification(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        if (data.containsKey("parkingVisitorId") &&
                data.containsKey("message") &&
                data.containsKey("parkingLocation") &&
                data.containsKey("vehicleNo")) {


            String vistrName = "", inTime = "", status = "", mobile = "", vehicleNo = "", parkingLocation = "", message = "", companyName = "";
            int parkingVisitorId = 0;
            String notiJson = "";
            try {
                parkingVisitorId = Integer.parseInt(data.get("parkingVisitorId").toString());
                message = data.get("message").toString();
                parkingLocation = data.get("parkingLocation").toString();
                vehicleNo = data.get("vehicleNo").toString();
                vistrName = data.get("name").toString();
                inTime = data.get("inTime").toString();
                status = data.get("status").toString();
                mobile = data.get("mobile").toString();
                try {
                    companyName = data.get("companyName").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                NotificationParkingData notificationParkingData = new NotificationParkingData();
                notificationParkingData.setParkingVisitorId(parkingVisitorId);
                notificationParkingData.setParkingLocation(parkingLocation);
                notificationParkingData.setMessage(message);
                notificationParkingData.setVehicleNo(vehicleNo);
                notificationParkingData.setName(vistrName);
                notificationParkingData.setInTime(inTime);
                notificationParkingData.setStatus(status);
                notificationParkingData.setCompanyName(companyName);
                notificationParkingData.setMobile(mobile);

                notiJson = new Gson().toJson(notificationParkingData, NotificationParkingData.class);
//                new SPbean(this).setPreference(Constants.NOTIFICATION_DATA_PARKING, notiJson);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!notiJson.isEmpty()) {
                Intent intent = new Intent(this, NotificationAreaService.class);
                intent.putExtra(Constants.NOTIFICATION_DATA_PARKING, notiJson);
                intent.setAction(Long.toString(System.currentTimeMillis()));
                PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Parking Checkout Notification")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                notificationBuilder.setStyle(new
                        NotificationCompat.BigTextStyle().bigText(message));

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "VAMS_Godrej_checkout";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "VAMS Godrej",
                            NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                    notificationBuilder.setChannelId(channelId);
                }
                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                notificationManager.notify(m, notificationBuilder.build());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
